package com.jd.nb.monitor.resource.db.orm.mybatis;

import org.apache.ibatis.mapping.MappedStatement;

/**
 * 数据库监控过滤器<p/>
 * Description:  过滤一些无须监控sql
 *
 * <p/>
 * @author <a href=mailto:wangsongpeng@jd.com>王宋鹏</a>
 * @since 2018/01/25
 */
public interface DbAccessFilter {

	/**
	 * 根据映射语句和参数来过滤掉无需监控的数据库访问行为。
	 * @param statement 执行的语句。
	 * @param parameterObject 执行的参数。
	 * @return 过滤结果，true表示通过过滤，需要监控；false表示未通过过滤，不需要监控。
	 */
	public boolean filter(MappedStatement statement, Object parameterObject);
	
	
}
