package org.squirrel;

public class Query {

	private String table;
	private Column column;
	private String columns;
	private String sqlWhere;
	private Object[] params;
	private Class<?> entityClass;
	private Order order;

	public Query() {}

	public Query(Class<?> entityClass) {
		this.entityClass = entityClass;
	}

	public Query select(String columns) {
		this.columns = columns;
		return this;
	}

	public Query select(Column column) {
		this.column = column;
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

	Class<?> getEntityClass() {
		return entityClass;
	}

	String toSQLString() {
		columns = columns == null ? "*" : columns;
		columns = column == null ? columns : column.from(entityClass).toCode();
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