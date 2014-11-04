package org.squirrel;

import java.util.Collection;
import org.squirrel.LogicalCriterion.Logical;

public class Restrictions {

	private Restrictions() {}
	
	public static SQLCriterion eq(String propertyName, Object value) {
		return new SQLCriterion(propertyName + " = ? ", new Object[]{value});
	}
	
	public static SQLCriterion ne(String propertyName, Object value){
		return new SQLCriterion(propertyName + " != ? ", new Object[]{value});
	}
	
	public static SQLCriterion gt(String propertyName, Object value){
		return new SQLCriterion(propertyName + " > ? ", new Object[]{value});
	}
	
	public static SQLCriterion ge(String propertyName, Object value){
		return new SQLCriterion(propertyName + " >= ? ", new Object[]{value});
	}
	
	public static SQLCriterion lt(String propertyName, Object value){
		return new SQLCriterion(propertyName + " < ? ", new Object[]{value});
	}
	
	public static SQLCriterion le(String propertyName, Object value){
		return new SQLCriterion(propertyName + " <= ? ", new Object[]{value});
	}
	
	public static SQLCriterion in(String propertyName, Object[] values){
		StringBuilder sql = new StringBuilder();
		sql.append(propertyName).append(" IN ( ");
		for(int i = 0; i < values.length; i++)
			sql.append("?, ");
		return new SQLCriterion(sql.substring(0, sql.length() - 2) + " ) ", values);
	}
	
	public static SQLCriterion in(String propertyName, Collection<Object> values){
		int index = 0;
		Object[] objects = new Object[values.size()];
		for(Object value : values)
			objects[index++] = value;
		return in(propertyName, objects);
	}
	
	public static SQLCriterion notIn(String propertyName, Object[] values){
		StringBuilder sql = new StringBuilder();
		sql.append(propertyName).append(" NOT IN ( ");
		for(int i = 0; i < values.length; i++)
			sql.append("?, ");
		return new SQLCriterion(sql.substring(0, sql.length() - 2) + " ) ", values);
	}
	
	public static SQLCriterion notIn(String propertyName, Collection<Object> values){
		int index = 0;
		Object[] objects = new Object[values.size()];
		for(Object value : values)
			objects[index++] = value;
		return notIn(propertyName, objects);
	}
	
	public static SQLCriterion between(String propertyName, Object lo, Object ho){
		return new SQLCriterion(propertyName + " BETWEEN ? AND ? ", new Object[]{lo, ho});
	} 
	
	public static SQLCriterion like(String propertyName, Object value){
		return new SQLCriterion(propertyName + " LIKE '?'", new Object[]{value});
	}
	
	public static SQLCriterion isEmpty(String propertyName){
		return eq(propertyName, "");
	}
	
	public static SQLCriterion isNotEmpty(String propertyName){
		return ne(propertyName, "");
	}
	
	public static SQLCriterion isNull(String propertyName){
		return eq(propertyName, null);
	}
	
	public static SQLCriterion isNotNull(String propertyName){
		return ne(propertyName, null);
	}
	
	public static LogicalCriterion or(Criterion lc, Criterion rc){
		return new LogicalCriterion(lc, Logical.OR, rc);
	}
	
	public static LogicalCriterion and(Criterion lc, Criterion rc){
		return new LogicalCriterion(lc, Logical.AND, rc);
	}
	
}