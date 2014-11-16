package org.squirrel.sample;

import java.util.Date;

import org.junit.BeforeClass;
import org.junit.Test;
import org.squirrel.Criteria;
import org.squirrel.Query;
import org.squirrel.Restrictions;
import org.squirrel.Session;
import org.squirrel.sample.model.Emp;
import org.squirrel.sample.util.Context;
import org.squirrel.sample.util.Testing;

public class OtherSample {

	private static Session session;
	
	@BeforeClass
	public static void prepare() {
		session = Context.getSession();
	}
	
	/**
	 * 保存
	 */
	@Test
	public void save(){
		Emp emp = new Emp();
		emp.setDeptId(3);
		emp.setHiredate(new Date());
		emp.setJob("人事专员");
		emp.setName("小春子");
		emp.setSalary(4000);
		emp.setSex("男");
		int result = session.save(emp);
		Testing.printlnObject(result == 1 ? "保存成功" : "保存失败");
	}
	
	/**
	 * 更新
	 */
	@Test
	public void update(){
		Criteria criteria = new Query(Emp.class).createCriteria();
		criteria.add(Restrictions.eq("name", "小春子"));
		Emp emp = session.queryObject(criteria);
		emp.setSalary(emp.getSalary() + 1000);
		int result = session.update(emp);
		Testing.printlnObject(result == 1 ? "更新成功" : "更新失败");
	}
	
	/**
	 * 删除
	 */
	@Test
	public void delete(){
		Criteria criteria = new Query(Emp.class).createCriteria();
		criteria.add(Restrictions.eq("name", "小春子"));
		Emp emp = session.queryObject(criteria);
		int result = session.delete(emp);
		Testing.printlnObject(result == 1 ? "删除成功" : "删除失败");
	}
	
}