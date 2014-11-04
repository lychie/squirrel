package org.squirrel;

import java.util.LinkedList;

public class Criteria {

	private Query query;
	private Order order;
	private String sqlStatement;
	private LinkedList<Object> params;
	
	Criteria(Query query) {
		this.query = query;
		this.params = new LinkedList<Object>();
	}
	
	public Criteria add(Criterion criterion) {
		setContext(criterion);
		return this;
	}
	
	public Criteria add(Order order) {
		this.order = order;
		return this;
	}

	public String toSQLString() {
		StringBuilder sql = new StringBuilder(query.toSQLString());
		if(sqlStatement != null)
			sql.append(" WHERE ").append(sqlStatement);
		return sql.toString();
	}
	
	void setContext(Criterion criterion){
		this.sqlStatement = criterion.toSQLString(this);
	}

	Query getQuery() {
		return query;
	}

	LinkedList<Object> getParams() {
		return params;
	}

	String getSqlStatement() {
		return sqlStatement == null ? "" : sqlStatement;
	}

	Object[] params() {
		int length = params.size();
		Object[] values = new Object[length];
		for(int i = 0; i < length; i++)
			values[i] = params.get(i);
		return values;
	}
	
	String build(boolean build){
		String orderSQL = order == null ? "" : order.toSQLString();
		String sql = toSQLString().trim() + orderSQL;
		if(build) System.out.println(sql);
		return sql;
	}
	
}