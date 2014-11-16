package org.squirrel;
/**
 * 条件SQL
 * @author Lychie Fan ( lychie@yeah.net )
 * @since 1.0.0
 */
public interface Criterion {

	String toSQLString(Criteria criteria);
	
}