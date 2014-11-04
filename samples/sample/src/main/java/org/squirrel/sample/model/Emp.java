package org.squirrel.sample.model;

import java.util.Date;
import java.text.SimpleDateFormat;

public class Emp extends Model {

	private int deptId;
	private int salary;
	private String job;
	private String sex;
	private String name;
	private Date hiredate;

	public int getDeptId() {
		return deptId;
	}

	public void setDeptId(int deptId) {
		this.deptId = deptId;
	}

	public int getSalary() {
		return salary;
	}

	public void setSalary(int salary) {
		this.salary = salary;
	}

	public String getJob() {
		return job;
	}

	public void setJob(String job) {
		this.job = job;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public Date getHiredate() {
		return hiredate;
	}

	public void setHiredate(Date hiredate) {
		this.hiredate = hiredate;
	}

	@Override
	public String toString() {
		String date = new SimpleDateFormat("yyyy-MM-dd").format(hiredate);
		return id + "\t" + name + "\t" + sex + "\t" + job + "\t" + salary + "\t" + date;
	}

}