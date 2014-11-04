package org.squirrel;

public class Page <E> {

	// 分页结果集
	private E result;
	// 总记录条数
	private int totalCount;
	// 当前的页码
	private int pageIndex;
	// 上一页页码
	private int backPageIndex;
	// 下一页页码
	private int nextPageIndex;
	// 每页显示的数据条数
	private int pageSize;
	// 分页的总页数
	private int totalPages;
	// 是否是第一页
	private boolean isFirstPage;
	// 是否是最后一页
	private boolean isLastPage;
	// 每页显示的条目缺省值
	private static final int DEFAULT_PAGE_SIZE = 10;
	
	public Page(int pageIndex, int totalCount, E result){
		this(pageIndex, DEFAULT_PAGE_SIZE, totalCount, result);
	}
	
	public Page(int pageIndex, int pageSize, int totalCount, E result){
		this.result = result;
		this.pageSize = pageSize;
		this.setTotalCount(totalCount);
		this.setPageIndex(pageIndex);
	}

	private void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
		totalPages = totalCount / pageSize;
		if(totalCount % pageSize != 0)
			totalPages += 1;
	}

	private void setPageIndex(int pageIndex) {
		this.pageIndex = pageIndex;
		if(pageIndex <= 0)
			this.pageIndex = 1;
		if(pageIndex > totalPages)
			this.pageIndex = totalPages;
		reset();
	}

	public E getResult() {
		return result;
	}

	public int getTotalCount() {
		return totalCount;
	}

	public int getPageIndex() {
		return pageIndex;
	}

	public int getBackPageIndex() {
		return backPageIndex;
	}

	public int getNextPageIndex() {
		return nextPageIndex;
	}
	
	public int getPageSize() {
		return pageSize;
	}

	public int getTotalPages() {
		return totalPages;
	}

	public boolean isIsFirstPage() {
		return isFirstPage;
	}

	public boolean isIsLastPage() {
		return isLastPage;
	}
	
	private void reset(){
		this.isFirstPage = this.pageIndex == 1 ? true : false;
		this.isLastPage = this.pageIndex >= totalPages ? true : false;
		this.backPageIndex = isFirstPage ? 1 : this.pageIndex - 1;
		this.nextPageIndex = isLastPage ? totalPages : this.pageIndex + 1;
		this.nextPageIndex = isFirstPage && isLastPage ? 1 : this.nextPageIndex;
	}
	
}