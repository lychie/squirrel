package org.squirrel.dialect;

import org.squirrel.MySQLDialect;
/**
 * SQL方言工厂
 * @author Lychie Fan ( lychie@yeah.net )
 * @since 1.0.0
 */
public class DialectFactory {

	private DialectFactory() {}
	
	public static Dialect getDialect(String name) {
		if(name.equals("MySQLDialect"))
			return new MySQLDialect();
		if(name.equals("OracleDialect"))
			throw new UnsupportedOperationException("Not support the oracle database.");
//			return new OracleDialect();
		if(name.equals("SQLServerDialect"))
			throw new UnsupportedOperationException("Not support the sqlserver database.");
//			return new SQLServerDialect();
		throw new UnsupportedOperationException("The Dialect name '" + name + "' was not found!");
	}
	
}