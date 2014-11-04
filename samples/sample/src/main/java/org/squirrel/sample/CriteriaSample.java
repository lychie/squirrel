package org.squirrel.sample;

import java.util.List;

import org.junit.Test;
import org.squirrel.Criteria;
import org.squirrel.Criterion;
import org.squirrel.Order;
import org.squirrel.Query;
import org.squirrel.Restrictions;
import org.squirrel.sample.model.Emp;
import org.squirrel.sample.util.Context;
import org.squirrel.sample.util.Testing;

/**
 * Criteria 查询样例
 */
public class CriteriaSample {

	/**
	 * 等价于 QuerySample.simpleQuery、QuerySample.simplifyQuery
	 */
	@Test
	public void simpleCriteria() {
		Criteria criteria = new Query(Emp.class).createCriteria();
		criteria.add(
			Restrictions.lt("salary", 5000)
		).add(Order.by("salary, id ASC"));
		List<Emp> emps = Context.getSession().queryList(criteria);
		Testing.printlnObject(emps);
	}
	
	/**
	 * A and B 模式
	 */
	@Test
	public void AAndB() {
		Criteria criteria = new Query(Emp.class).createCriteria();
		criteria.add(
			Restrictions.and(
				Restrictions.le("salary", 5000),
				Restrictions.eq("deptId", 3)
			)
		);
		List<Emp> emps = Context.getSession().queryList(criteria);
		Testing.printlnObject(emps);
	}
	
	/**
	 * A or B 模式
	 */
	@Test
	public void AOrB() {
		Criteria criteria = new Query(Emp.class).createCriteria();
		criteria.add(
			Restrictions.or(
				Restrictions.le("salary", 5000),
				Restrictions.gt("salary", 8000)
			)
		);
		List<Emp> emps = Context.getSession().queryList(criteria);
		Testing.printlnObject(emps);
	}
	
	/**
	 * (A or B) and C 模式, 其余的拼法类似 ...
	 */
	@Test
	public void AOrBAndC() {
		Criteria criteria = new Query(Emp.class).createCriteria();
		Criterion criterion = Restrictions.or(
			Restrictions.le("salary", 5000),
			Restrictions.gt("salary", 8000)
		);
		criteria.add(
			Restrictions.and(
				criterion,
				Restrictions.le("hiredate", "2014-10-15")
			)
		);
		List<Emp> emps = Context.getSession().queryList(criteria);
		Testing.printlnObject(emps);
	}
	
}