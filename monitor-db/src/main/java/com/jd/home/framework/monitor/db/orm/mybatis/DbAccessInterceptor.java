package com.jd.home.framework.monitor.db.orm.mybatis;


import com.jd.ump.profiler.proxy.Profiler;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.dbcp.BasicDataSource;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.ibatis.cache.CacheKey;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import javax.sql.DataSource;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.Properties;
import java.util.Set;

/**
 * 数据库Sql执行监控 <p/>
 * Description:  基于Mybatis拦截器拦截Executor执行,并增加监控功能.
 *               主要提供慢sql监控,sql执行监控,连接数过多监控
 * <p/>
 * @author <a href=mailto:wangsongpeng@jd.com>王宋鹏</a>
 * @since 2018/01/25
 */
@Intercepts({
	@Signature(
		  type = Executor.class,
		  method = "query",
		  args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class}),
	@Signature(
				  type = Executor.class,
				  method = "query",
				  args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class, CacheKey.class, BoundSql.class}),
	@Signature(
			  type = Executor.class,
			  method = "update",
			  args = {MappedStatement.class, Object.class})
})
public class DbAccessInterceptor implements Interceptor{
	
	private static Logger logger = LoggerFactory.getLogger(DbAccessInterceptor.class);

	/********************开关配置Start********************/
	/**
	 * SQL执行异常报警开关。
	 */
	private boolean sqlExceptionEnabled = true;

	/**
	 * 慢SQL报警开关。
	 */
	private boolean slowSqlEnabled = true;

	/**
	 * 连接数过多报警开关。
	 */
	private boolean tooManyActiveConnEnabled = true;

	/********************开关配置End********************/

	/******************** umpKey Start********************/
	/**
	 * SQL执行异常UMP报警的key。
	 */
	private String sqlExceptionKey = "ql.app.sql.exception";
	
	/**
	 * SQL执行异常UMP报警的key。
	 */
	private String slowSqlKey = "ql.app.slowsql.timeout";


	/**
	 * 连接数过多UMP报警的key。
	 */
	private String tooManyActiveConnKey = "ql.app.tooMany.active.connection";

	/******************** umpKey End********************/

	/******************** 指标参数配置 *********************/

	/**
	 * 慢SQL执行超时时间，单位是毫秒。
	 */
	private long slowSqlTimeout = 1000;


	/**
	 * 连接数过多报警。活跃连接占比允许的最大值，超过该值将会报警。
	 */
	private float maxActiveConRatio = 0.7f;


	/**
	 * 日志中是否打印DB的连接url。
	 */
	private boolean isLogDBUrl = false;


	/**
	 * 忽略的sql语句id列表，多个id请以";"隔开。sql的id必须是包含namespace的完整id值。
	 */
	private String excludeStatementIds;

	/******************** 指标参数配置 *********************/



	/**
	 * 最小的慢SQL超时时间，不能小于这个值，以免报警过于频繁。
	 */
	public static final long MIN_SLOW_SQL_TIMEOUT = 20;
	

	/**
	 * 最低允许的活跃连接占比。不能过低，以免导致报警过于频繁。
	 */
	public static final float MIN_MAX_ACTIVE_CONNECTION_RATIO = 0.3f;

	/**
	 * 监控过滤器，通过过滤的才需要监控。
	 */
	private DbAccessFilter filter = new DefaultDbAccessFilter();



	@Override
	public Object intercept(Invocation invocation) throws Throwable {
		//读取参数。
		MappedStatement statement = null;
		Object parameterObject = null;
		BasicDataSource basicDataSource = null;
		
		//从运行环境中获取参数。
		Object[] args = invocation.getArgs();
		if(args!=null && args.length>1){
			if(args[0]!=null && args[0] instanceof MappedStatement){
				statement = (MappedStatement)args[0];
				DataSource dataSource = statement.getConfiguration().getEnvironment().getDataSource();
				if(dataSource!=null && dataSource instanceof BasicDataSource){
					basicDataSource = (BasicDataSource)dataSource;
				}
			}
			if(args[1]!=null){
				parameterObject = args[1];
			}
		}
		
		
		//执行过滤，若过滤通过才可报警。
		boolean needMonitor = filter.filter(statement, parameterObject);
		
		try{
			
			//执行原始数据库访问逻辑。
			long s = System.currentTimeMillis();
			Object result = invocation.proceed();
			long e = System.currentTimeMillis();
			long d = e-s;
			
			//执行监控逻辑。
			try{
				//未通过过滤，则直接跳出，不需要监控。
				if(!needMonitor){
					return result;
				}
				
				//慢sql监控。
				if(slowSqlEnabled){
					if(d>this.slowSqlTimeout){
						if(statement!=null){
							BoundSql sql = statement.getBoundSql(parameterObject);
							logger.warn(this.appendDbUrl(basicDataSource, "报警key="+slowSqlKey+",sql执行耗时"+d+"毫秒，超过阀值，执行的sql语句是["+sql.getSql()+"]"));
						}
						Profiler.businessAlarm(slowSqlKey, "sql执行时间太慢，耗时"+d+"毫秒，超过阀值"+this.slowSqlTimeout);
					}
				}
				
				//连接过多监控。
				if(tooManyActiveConnEnabled){
					if(statement!=null){
						if(basicDataSource!=null){
							int an = basicDataSource.getNumActive();
							float ratio = (an*1.0f)/basicDataSource.getMaxActive();
							if(ratio>= this.maxActiveConRatio){
								logger.warn(this.appendDbUrl(basicDataSource, "报警key="+maxActiveConRatio+",数据库连接数过多，使用率已经超过了"+(this.maxActiveConRatio*100)+"%, 当前活跃连接数"+basicDataSource.getNumActive()+",允许最大活跃连接数"+basicDataSource.getMaxActive()));
								Profiler.businessAlarm(tooManyActiveConnKey, "数据库连接数过多，使用率已经超过了"+(this.maxActiveConRatio*100)+"%, 当前活跃连接数"+basicDataSource.getNumActive()+",允许最大活跃连接数"+basicDataSource.getMaxActive());
							}
						}
					}
				}
			}catch (Throwable t) {
				logger.error("数据库访问报警异常", t);
			}
			return result;
		}catch (Throwable e) {
			
			//sql执行异常报警。
			if(needMonitor && sqlExceptionEnabled){
				Throwable targetException = e;
				if(e instanceof InvocationTargetException){
					InvocationTargetException exception = (InvocationTargetException)e;
					targetException = exception.getTargetException();
				}
				if(targetException!=null){
					logger.error(this.appendDbUrl(basicDataSource,"执行SQL异常，报警key="+sqlExceptionKey), e);
					String[] rootCauseTrackTraceArray = ExceptionUtils.getRootCauseStackTrace(e);
					StringBuffer rootCauseTrackTrace = new StringBuffer(sqlExceptionKey);
					if(rootCauseTrackTraceArray!=null || rootCauseTrackTraceArray.length>0){
						for(int i=0;i<rootCauseTrackTraceArray.length;i++){
							rootCauseTrackTrace.append(rootCauseTrackTraceArray[i]);
						}
					}
					Profiler.businessAlarm(sqlExceptionKey, "-sql执行异常，ump key= "+rootCauseTrackTrace.toString());
				}
			}
			throw e;
		}
	}
	
	protected String appendDbUrl(BasicDataSource basicDataSource, String log) {
		if(basicDataSource!=null && isLogDBUrl){
			log = "DBUrl=["+basicDataSource.getUrl()+"]"+log;
		}
		return log;
	}

	@Override
	public Object plugin(Object target) {
		return Plugin.wrap(target, this);
	}

	@Override
	public void setProperties(Properties properties) {
		//解析属性配置值，设置到对应的拦截器中。
		Set<String> keys = properties.stringPropertyNames();
		for(String key:keys){
			String value = properties.getProperty(key);
			if(value!=null && value.length()>0){
				try {
					BeanUtils.setProperty(this, key, value);
				} catch (Throwable e) {
					logger.error("属性值设置出错，请检查属性"+key+"的配置是否支持，或者属性的值类型不正确。");
				}
			}
		}
		
	}

	public boolean isSqlExceptionEnabled() {
		return sqlExceptionEnabled;
	}

	public void setSqlExceptionEnabled(boolean sqlExceptionEnabled) {
		this.sqlExceptionEnabled = sqlExceptionEnabled;
	}

	public String getSqlExceptionKey() {
		return sqlExceptionKey;
	}

	public void setSqlExceptionKey(String sqlExceptionKey) {
		if(sqlExceptionKey!=null && sqlExceptionKey.length()>0 && sqlExceptionKey.trim().length()>0){
			this.sqlExceptionKey = sqlExceptionKey;
		}
	}

	public boolean isSlowSqlEnabled() {
		return slowSqlEnabled;
	}

	public void setSlowSqlEnabled(boolean slowSqlEnabled) {
		this.slowSqlEnabled = slowSqlEnabled;
	}

	public String getSlowSqlKey() {
		return slowSqlKey;
	}

	public void setSlowSqlKey(String slowSqlKey) {
		if(slowSqlKey!=null && slowSqlKey.length()>0 && slowSqlKey.trim().length()>0){
			this.slowSqlKey = slowSqlKey;
		}
	}

	public long getSlowSqlTimeout() {
		return slowSqlTimeout;
	}

	public void setSlowSqlTimeout(long slowSqlTimeout) {
		if(slowSqlTimeout>= MIN_SLOW_SQL_TIMEOUT){
			this.slowSqlTimeout = slowSqlTimeout;
		}
	}

	public boolean isTooManyActiveConnEnabled() {
		return tooManyActiveConnEnabled;
	}

	public void setTooManyActiveConnEnabled(boolean tooManayActiveConnEnabled) {
		this.tooManyActiveConnEnabled = tooManayActiveConnEnabled;
	}

	public String getTooManyActiveConnKey() {
		return tooManyActiveConnKey;
	}

	public void setTooManyActiveConnKey(String tooManyActiveConnKey) {
		if(tooManyActiveConnKey!=null && tooManyActiveConnKey.length()>0 && tooManyActiveConnKey.trim().length()>0){
			this.tooManyActiveConnKey = tooManyActiveConnKey;
		}
	}

	public float getMaxActiveConRatio() {
		return maxActiveConRatio;
	}

	public void setMaxActiveConRatio(float maxActiveConRatio) {
		if(maxActiveConRatio>=MIN_MAX_ACTIVE_CONNECTION_RATIO){
			if(maxActiveConRatio>1.0){
				this.maxActiveConRatio = 1.0f;
			}
			else{
				this.maxActiveConRatio = maxActiveConRatio;
			}
		}
	}


	public String getExcludeStatementIds() {
		return excludeStatementIds;
	}

	public void setExcludeStatementIds(String excludeStatementIds) {
		this.excludeStatementIds = excludeStatementIds;
		if(excludeStatementIds!=null && excludeStatementIds.length()>0){
			((DefaultDbAccessFilter)this.filter).setExcludedStatements(Arrays.asList(excludeStatementIds.split(";")));
		}
		else{
			((DefaultDbAccessFilter)this.filter).setExcludedStatements(null);
		}
	}

	public boolean isLogDBUrl() {
		return isLogDBUrl;
	}

	public void setLogDBUrl(boolean isLogDBUrl) {
		this.isLogDBUrl = isLogDBUrl;
	}

	public DbAccessFilter getFilter() {
		return filter;
	}

	public void setFilter(DbAccessFilter filter) {
		this.filter = filter;
	}
}
