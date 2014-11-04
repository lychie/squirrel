package org.squirrel;

public interface Criterion {

	String toSQLString(Criteria criteria);
	
}