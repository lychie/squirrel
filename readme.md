## 常用类 ##

* **会话**  
**|— Session**

* **查询**  
**|— Query**  
**|— Criteria**  
**|— Restrictions**  
**|— ResultSet**

* **排序**  
**|— Order**

* **分页**  
**|— Pagination**

* **主键策略**  
**|— UUID**  
**|— INCREMENT**

*注：Hibernate 有冬眠之意，Squirrel ( 松鼠 ) 具有冬眠的习性，项目中众多类名称沿袭自 Hibernate，如 Criteria、Criterion、Restrictions 等。*

----------

## Query ##

```java
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
	Query query = new Query(Emp.class).where("salary < ?", 5000)
		  .orderBy("salary, id ASC");
	List<Emp> emps = session.queryList(query);
	Testing.printlnObject(emps);
}

/**
 * 关联表查询
 */
@Test
public void joinQuery() {
	Query query = new Query().select("e.*, d.name AS deptName")
		  .from("Emp e LEFT JOIN dept d ON e.deptId = d.id")
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
		  .from("Emp e LEFT JOIN dept d ON e.deptId = d.id")
		  .orderBy("e.id ASC")
		  .pagination(2, 5); // 从第2页开始, 每页5条记录, 即 6-10.
	Pagination pagination = session.queryPage(query);
	ResultSet rs = pagination.getResultSet();
	while(rs.hasNext())
		print(rs.next());
}

private void print(Map<String, Object> map) {
	for(String key : map.keySet())
		System.out.print(map.get(key) + "\t");
	System.out.println();
}
```

----------

## Criteria ##

```java
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
```

## Other ##

```java
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
```