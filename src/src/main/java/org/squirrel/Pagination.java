package org.squirrel;

public class Pagination {

	// 分页结果集
	private ResultSet resultSet;
	// 每页显示的数据条数
	private int pageItems;
	// 总记录条数
	private int totalAmount;
	// 当前的页码
	private int currentPage;
	// 每页显示的条目缺省值
	public static final int DEFAULT_PAGE_SIZE = 10;
	
	public Pagination() {
		this(1, DEFAULT_PAGE_SIZE);
	}
	
	public Pagination(int currentPage, int pageItems) {
		this.pageItems = pageItems;
		this.currentPage = currentPage;
	}

	public ResultSet getResultSet() {
		return resultSet;
	}

	public void setResultSet(ResultSet resultSet) {
		this.resultSet = resultSet;
	}

	public int getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(int totalAmount) {
		this.totalAmount = totalAmount;
	}

	public int getCurrentPage() {
		return currentPage <= 0 ? 1 : currentPage;
	}

	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}

	public int getPageItems() {
		return pageItems <= 0 ? DEFAULT_PAGE_SIZE : pageItems;
	}

	public void setPageItems(int pageItems) {
		this.pageItems = pageItems;
	}

}