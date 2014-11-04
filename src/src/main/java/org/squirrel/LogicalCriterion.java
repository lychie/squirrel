package org.squirrel;

public class LogicalCriterion implements Criterion {

	private Criterion lc;
	private Criterion rc;
	private Logical logical;
	
	LogicalCriterion(Criterion lc, Logical logical, Criterion rc) {
		this.lc = lc;
		this.rc = rc;
		this.logical = logical;
	}
	
	@Override
	public String toSQLString(Criteria criteria) {
		lc.toSQLString(criteria);
		rc.toSQLString(criteria);
		return new StringBuilder(criteria.getSqlStatement()).append(toString()).toString();
	}

	@Override
	public String toString() {
		StringBuilder sql = new StringBuilder();
		sql.append("( ").append(lc.toString()).append(logical.code).append(rc.toString());
		return sql.append(") ").toString();
	}
	
	enum Logical {
		
		AND("AND "), OR("OR ");
		
		private final String code;
		
		private Logical(String code){
			this.code = code;
		}
		
		public String toCode(){
			return code;
		}
		
	}

}