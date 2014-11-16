package org.squirrel;

import java.util.Map;
import java.util.List;
import java.util.Iterator;

public class ResultSet implements Iterator<Map<String, Object>> {

	private int size;
	private int index;
	private List<Map<String, Object>> list;
	
	public ResultSet() {}
	
	public ResultSet(List<Map<String, Object>> list) {
		this.list = list;
		this.size = list.size();
	}

	public List<Map<String, Object>> getList() {
		return list;
	}

	public void setList(List<Map<String, Object>> list) {
		this.list = list;
		this.size = list.size();
	}

	public int size() {
		return size;
	}

	@Override
	public boolean hasNext() {
		return index < size;
	}

	@Override
	public Map<String, Object> next() {
		return list.get(index++);
	}

	@Override
	public void remove() {
		throw new UnsupportedOperationException("Not support remove operation.");
	}
	
}