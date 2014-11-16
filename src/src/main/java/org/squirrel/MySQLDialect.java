package org.squirrel;

import org.squirrel.dialect.Dialect;

public class MySQLDialect extends Dialect {

	public Dialect pagination(Query query) {
		return buildPaginationSQL(query, query.build(false));
	}

	public Dialect pagination(Criteria criteria) {
		return buildPaginationSQL(criteria.getQuery(), criteria.build(false));
	}
	
	private Dialect buildPaginationSQL(Query query, String sqlStr) {
		StringBuilder sqlBuilder = new StringBuilder(sqlStr);
		int start = paginateStartIndex(query.page());
		int amount = query.page().getPageItems();
		sqlBuilder.append(" LIMIT ").append(start).append(", ").append(amount);
		sql = sqlBuilder.toString();
		return this;
	}

}