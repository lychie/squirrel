package org.squirrel.sample;

import java.util.List;
import java.util.Map;

import org.junit.BeforeClass;
import org.junit.Test;
import org.squirrel.Pagination;
import org.squirrel.Query;
import org.squirrel.ResultSet;
import org.squirrel.Session;
import org.squirrel.sample.model.Emp;
import org.squirrel.sample.util.Context;
import org.squirrel.sample.util.Testing;
/**
 * Query 查询样例
 */
public class QuerySample {

	private static Session session;
	
	@BeforeClass
	public static void prepare() {
		session = Context.getSession();
	}
	
	/**
	 * 简单的 SQL 查询
	 */
	@Test
	public void simpleQuery() {
		Query query = new Query().select("*").from("Emp").where("salary < ?", 5000)
			 .orderBy("salary, id ASC").to(Emp.class);
		List<Emp> emps = session.queryList(query);
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
		List<Emp> emps = session.queryList(query);
		Testing.printlnObject(emps);
	}
	
	/**
	 * 关联表查询
	 */
	@Test
	public void joinQuery() {
		Query query = new Query().select("e.*, d.name AS deptName")
			  .from("emp e left join dept d on e.deptId = d.id")
			  .orderBy("e.id ASC");
		ResultSet rs = session.queryResultSet(query);
		while(rs.hasNext())
			print(rs.next());
	}
	
	/**
	 * 关联表分页查询
	 */
	@Test
	public void paginateQuery() {
		Query query = new Query().select("e.*, d.name AS deptName")
			  .from("emp e left join dept d on e.deptId = d.id")
			  .orderBy("e.id ASC")
			  .pagination(2, 5); // 从第2页开始, 每页5条记录, 即 6-10.
		Pagination pagination = session.queryPage(query);
		while(pagination.getResultSet().hasNext())
			print(pagination.getResultSet().next());
	}
	
	private void print(Map<String, Object> map) {
		for(String key : map.keySet())
			System.out.print(map.get(key) + "\t");
		System.out.println();
	}
}