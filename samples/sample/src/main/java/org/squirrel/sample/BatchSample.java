package org.squirrel.sample;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;
import org.squirrel.Criteria;
import org.squirrel.Query;
import org.squirrel.Restrictions;
import org.squirrel.Session;
import org.squirrel.sample.model.Emp;
import org.squirrel.sample.util.Context;
import org.squirrel.sample.util.Testing;

public class BatchSample {

	private static Session session;
	
	@BeforeClass
	public static void prepare() {
		session = Context.getSession();
	}

	/**
	 * 批量保存
	 */
	@Test
	public void batchInsert() {
		List<Emp> emps = new ArrayList<Emp>();
		final int count = 10;
		int deptId = 3;
		Date hiredate = new Date();
		for(int i = 0; i < count; i++){
			Emp emp = new Emp();
			emp.setDeptId(deptId);
			emp.setHiredate(hiredate);
			emp.setJob("人事专员");
			emp.setName("小春子-" + (i + 1));
			emp.setSalary(4000 + i);
			emp.setSex("男");
			emps.add(emp);
		}
		int[] results = session.batchInsert(emps);
		Testing.printlnObject(Arrays.toString(results));
	}
	
	/**
	 * 批量更新
	 */
	@Test
	public void batchUpdate(){
		Criteria criteria = new Query(Emp.class).createCriteria();
		criteria.add(Restrictions.like("name", "小春子%"));
		List<Emp> emps = session.queryList(criteria);
		for(Emp emp : emps)
			emp.setSalary(emp.getSalary() + 1000);
		int[] results = session.batchUpdate(emps);
		Testing.printlnObject(Arrays.toString(results));
	}
	
	/**
	 * 批量删除
	 */
	@Test
	public void batchDelete(){
		Criteria criteria = new Query(Emp.class).createCriteria();
		criteria.add(Restrictions.like("name", "小春子%"));
		List<Emp> emps = session.queryList(criteria);
		int[] results = session.batchDelete(emps);
		Testing.printlnObject(Arrays.toString(results));
	}
	
}