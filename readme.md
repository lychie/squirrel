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
```