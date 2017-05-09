package cn.gomro.mid.core.biz.base;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 类名称：Paging
 * 用于easyUI的分页显示
 */
public class Paging<T> implements Serializable {

	private int total = 0;
	private List<T> rows = new ArrayList<T>();

	public Paging() {
	}

	public Paging(int total, List<T> rows) {
		this.total = total;
		this.rows = rows;
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public List<T> getRows() {
		return rows;
	}

	public void setRows(List<T> rows) {
		this.rows = rows;
	}
	
}
