package org.squirrel;

import org.squirrel.dialect.Dialect;

public class SQLServerDialect extends Dialect {

	public Dialect pagination(Query query) {
		
		return null;
	}

	public Dialect pagination(Criteria criteria) {
		
		return null;
	}
	
	public static void main(String[] args) {
		String sql = "SELECT E.* FROM @TABLE E, ( SELECT TOP @AMOUNT ROW_NUMBER() OVER( @ORDER_BY ) N, @PK FROM @TABLE ) T WHERE T.@PK = E.@PK AND T.N > @START ORDER BY T.N ASC";
		sql = sql.replaceAll("@TABLE", "EMP");
		sql = sql.replaceAll("@START", String.valueOf(0));
		sql = sql.replaceAll("@AMOUNT", String.valueOf(10));
		sql = sql.replaceAll("@ORDER_BY", "ORDER BY ID DESC");
		sql = sql.replaceAll("@PK", "ID");
		System.out.println(sql);
//		SELECT E.*, D.NAME FROM EMP E LEFT JOIN DEPT D ON E.deptId = D.id, 
//		( SELECT TOP 10 ROW_NUMBER() OVER( ORDER BY HIREDATE DESC, ID DESC ) N, ID FROM EMP ) T 
//		WHERE T.ID = E.ID AND T.N > 0 ORDER BY T.N ASC
	}
	
}