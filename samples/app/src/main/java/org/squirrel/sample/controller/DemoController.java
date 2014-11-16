package org.squirrel.sample.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.squirrel.Pagination;
import org.squirrel.Query;
import org.squirrel.Session;

@Controller
public class DemoController {

	@Autowired
	private Session session;
	
	@RequestMapping("query/page/{index}")
	public String query(@PathVariable Integer index, ModelMap model) {
		Query query = new Query().select("E.*, D.name AS deptName")
			  .from("Emp E LEFT JOIN Dept D ON E.deptId = D.id")
			  .orderBy("E.id")
			  .pagination(index, 2); // 每页显示2条记录
		Pagination pagination = session.queryPage(query);
		model.put("p", pagination);
		return "list";
	}
	
}