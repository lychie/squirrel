package org.squirrel;

import java.util.LinkedList;
/**
 * SQL条件查询
 * @author Lychie Fan ( lychie@yeah.net )
 * @since 1.0.0
 */
public class Criteria {

	private Query query;
	private Order order;
	private String sqlStatement;
	private LinkedList<Object> params;
	
	Criteria(Query query) {
		this.query = query;
		this.params = new LinkedList<Object>();
	}
	
	/**
	 * <des> 添加条件 </des>
	 */
	public Criteria add(Criterion criterion) {
		setContext(criterion);
		return this;
	}
	
	/**
	 * <des> 添加排序 </des>
	 */
	public Criteria add(Order order) {
		this.order = order;
		return this;
	}

	String toSQLString() {
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
		return params.toArray();
	}
	
	String build(boolean build){
		String orderSQL = order == null ? "" : order.toSQLString();
		String sql = toSQLString().trim() + orderSQL;
		if(build) System.out.println(sql);
		return sql;
	}
	
}