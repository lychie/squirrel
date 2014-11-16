package org.squirrel;

public class SQLCriterion implements Criterion {

	private String sql;
	private Object[] params;
	
	SQLCriterion(String sql, Object[] params){
		this.sql = sql;
		this.params = params;
	}
	
	@Override
	public String toSQLString(Criteria criteria) {
		for(Object param : params)
			criteria.getParams().add(param);
		return new StringBuilder(criteria.getSqlStatement()).append(toString()).toString();
	}

	@Override
	public String toString() {
		return sql;
	}

}