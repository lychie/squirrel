package org.squirrel.sample;

import java.util.List;
import java.util.Map;
import org.junit.Test;
import org.squirrel.Query;
import org.squirrel.sample.model.Emp;
import org.squirrel.sample.util.Context;
import org.squirrel.sample.util.Testing;
/**
 * Query 查询样例
 */
public class QuerySample {

	/**
	 * 简单的 SQL 查询
	 * SELECT * FROM Emp WHERE salary < ? ORDER BY salary, id ASC
	 */
	@Test
	public void simpleQuery() {
		Query query = new Query().select("*").from("Emp").where("salary < ?", 5000)
			 .orderBy("salary, id ASC").to(Emp.class);
		List<Emp> emps = Context.getSession().queryList(query);
		Testing.printlnObject(emps);
	}
	
	/**
	 * 简化的 SQL 查询
	 * 缺省值如示：
	 * 1> SELECT *
	 * 2> FROM SimpleClassName
	 */
	@Test
	public void simplifyQuery() {
		Query query = new Query(Emp.class).where("salary < ?", 5000).orderBy("salary, id ASC");
		List<Emp> emps = Context.getSession().queryList(query);
		Testing.printlnObject(emps);
	}
	
	@Test
	public void glQuery() {
		Query query = new Query(Emp.class).select("e.*, d.name AS deptName")
			  .from("emp e left join dept d on e.deptId = d.id");
		List<Map<String, Object>> result = Context.getSession().queryRawList(query);
		for(Map<String, Object> map : result)
			Testing.printlnObject(map);
	}
}