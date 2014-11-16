package org.squirrel.sample;

import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;
import org.squirrel.Criteria;
import org.squirrel.Criterion;
import org.squirrel.Order;
import org.squirrel.Query;
import org.squirrel.Restrictions;
import org.squirrel.Session;
import org.squirrel.sample.model.Emp;
import org.squirrel.sample.util.Context;
import org.squirrel.sample.util.Testing;

/**
 * Criteria 查询样例
 */
public class CriteriaSample {

	private static Session session;
	
	@BeforeClass
	public static void prepare() {
		session = Context.getSession();
	}

	/**
	 * 简单查询
	 * 等价于 QuerySample.simpleQuery、QuerySample.simplifyQuery
	 */
	@Test
	public void simpleCriteria() {
		Criteria criteria = new Query(Emp.class).createCriteria();
		criteria.add(
			Restrictions.lt("salary", 5000)
		).add(Order.by("salary, id ASC"));
		List<Emp> emps = session.queryList(criteria);
		Testing.printlnObject(emps);
	}
	
	/**
	 * A AND B 模式, A OR B 模式类似 ( Restrictions.or )
	 */
	@Test
	public void A_AND_B() {
		Criteria criteria = new Query(Emp.class).createCriteria();
		criteria.add(
			Restrictions.and(
				Restrictions.lt("salary", 5000),
				Restrictions.eq("sex", "女")
			)
		).add(Order.by("salary, id ASC"));
		List<Emp> emps = session.queryList(criteria);
		Testing.printlnObject(emps);
	}
	
	/**
	 * (A or B) and C 模式, 其余的拼法类似 ...
	 */
	@Test
	public void A_OR_B_AND_C() {
		Criteria criteria = new Query(Emp.class).createCriteria();
		Criterion criterion = Restrictions.or(
			Restrictions.eq("deptId", 3),
			Restrictions.eq("deptId", 4)
		);
		criteria.add(
			Restrictions.and(
				criterion,
				Restrictions.le("sex", "女")
			)
		);
		List<Emp> emps = session.queryList(criteria);
		Testing.printlnObject(emps);
	}
	
	/**
	 * 模糊查询
	 */
	@Test
	public void like() {
		Criteria criteria = new Query(Emp.class).createCriteria();
		criteria.add(Restrictions.like("name", "%文%"));
		List<Emp> emps = session.queryList(criteria);
		Testing.printlnObject(emps);
	}
	
}