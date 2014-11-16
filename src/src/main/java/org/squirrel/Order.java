package org.squirrel;
/**
 * 排序
 * @author Lychie Fan ( lychie@yeah.net )
 * @since 1.0.0
 */
public class Order {

	private String statement;
	private String[] orders;
	
	private Order(String statement, String[] orders) {
		this.statement = statement;
		this.orders = orders;
	}
	
	public static Order by(String statement) {
		return new Order(statement, null);
	}
	
	public static Order by(String keys, String... orders) {
		return new Order(keys, orders);
	}
	
	public String toSQLString() {
		StringBuilder builder = new StringBuilder(" ORDER BY ");
		String orderSQL = builder.append(statement).toString();
		if(orders == null)
			return orderSQL;
		for(String order : orders)
			orderSQL = orderSQL.replaceFirst("\\?", order.toUpperCase());
		return orderSQL;
	}
	
}