package com.jd.nb.monitor.resource.db.orm.mybatis;

import org.apache.ibatis.mapping.MappedStatement;

import java.util.List;

/**
 * 支持排除的数据库访问过滤器。
 * @author yangwubing
 *
 */
public class DefaultDbAccessFilter implements DbAccessFilter {

	/**
	 * 排除的语句列表，通过mybatis的 statementId，是包含namespace的完整id值。
	 */
	private List<String> excludedStatements;
	
	public List<String> getExcludedStatements() {
		return excludedStatements;
	}

	public void setExcludedStatements(List<String> excludedStatements) {
		this.excludedStatements = excludedStatements;
	}

	@Override
	public boolean filter(MappedStatement statement, Object parameterObject) {
		boolean resultExclu = true;
		if(this.excludedStatements!=null && this.excludedStatements.size()>0){
			if(this.excludedStatements.contains(statement.getId())){
				resultExclu = false;
			}
			else{
				resultExclu = true;
			}
		}
		return resultExclu;
	}

}
