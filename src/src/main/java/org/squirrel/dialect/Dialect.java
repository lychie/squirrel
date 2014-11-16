package org.squirrel.dialect;

import org.squirrel.Criteria;
import org.squirrel.Pagination;
import org.squirrel.Query;

public abstract class Dialect {
	protected String sql;
	
	public abstract Dialect pagination(Query query);
	
	public abstract Dialect pagination(Criteria criteria);
	
	public String build(boolean build){
		if(build)
			System.out.println(sql);
		return sql;
	}
	
	protected int paginateStartIndex(Pagination page) {
		int totalPages = page.getTotalAmount() / page.getPageItems();
		if(page.getTotalAmount() % page.getPageItems() != 0)
			totalPages += 1;
		if(page.getCurrentPage() < 1)
			page.setCurrentPage(1);
		if(page.getCurrentPage() > totalPages)
			page.setCurrentPage(totalPages);
		return (page.getCurrentPage() - 1) * page.getPageItems();
	}
	
}