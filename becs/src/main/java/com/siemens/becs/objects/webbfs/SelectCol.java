package com.siemens.becs.objects.webbfs;

public class SelectCol {

	private String orderBy;
	private String tabAndCol;

	public SelectCol() {
	}

	public SelectCol(String orderBy, String tabAndCol) {
		super();
		this.orderBy = orderBy;
		this.tabAndCol = tabAndCol;
	}

	public String getOrderBy() {
		return orderBy;
	}

	public void setOrderBy(String orderBy) {
		this.orderBy = orderBy;
	}

	public String getTabAndCol() {
		return tabAndCol;
	}

	public void setTabAndCol(String tabAndCol) {
		this.tabAndCol = tabAndCol;
	}
}
