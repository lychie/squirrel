package org.squirrel;

public class Query {

	private String table;
	private String columns;
	private String sqlWhere;
	private Object[] params;
	private Class<?> entityClass;
	private Order order;
	private Pagination page;

	public Query() {}

	public Query(Class<?> entityClass) {
		this.entityClass = entityClass;
	}

	public Query select(String columns) {
		this.columns = columns;
		return this;
	}

	public Query from(String table) {
		this.table = table;
		return this;
	}
	
	public Query where(String sqlWhere, Object... params) {
		this.params = params;
		this.sqlWhere = sqlWhere;
		return this;
	}

	public Query to(Class<?> entityClass) {
		this.entityClass = entityClass;
		return this;
	}
	
	public Query orderBy(String statement) {
		this.order = Order.by(statement);
		return this;
	}
	
	public Query orderBy(String keys, String... orders) {
		this.order = Order.by(keys, orders);
		return this;
	}

	public Criteria createCriteria() {
		return new Criteria(this);
	}
	
	public Query pagination(Integer currentPage) {
		return pagination(currentPage, Pagination.DEFAULT_PAGE_SIZE);
	}
	
	public Query pagination(Integer currentPage, Integer pageItems) {
		page = new Pagination(currentPage, pageItems);
		return this;
	}
	
	Pagination page() {
		return page;
	}

	Class<?> getEntityClass() {
		return entityClass;
	}

	String toSQLString() {
		columns = columns == null ? "*" : columns;
		table = table == null ? entityClass.getSimpleName() : table;
		return new StringBuilder("SELECT ").append(columns).append(" FROM ").append(table).toString();
	}
	
	String toQuerySQLString() {
		if(sqlWhere == null)
			return toSQLString();
		return new StringBuilder(toSQLString()).append(" WHERE ").append(sqlWhere).toString();
	}
	
	Object[] params() {
		return params;
	}
	
	String build(boolean build) {
		String orderSQL = order == null ? "" : order.toSQLString();
		String sql = toQuerySQLString().trim() + orderSQL;
		if(build) System.out.println(sql);
		return sql;
	}

}