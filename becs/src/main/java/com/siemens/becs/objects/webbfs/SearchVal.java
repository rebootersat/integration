package com.siemens.becs.objects.webbfs;

public class SearchVal {

	private String operator;
	private String tabAndCol;
	private String value;

	public SearchVal() {
	}

	public SearchVal(String operator, String tabAndCol, String value) {
		super();
		this.operator = operator;
		this.tabAndCol = tabAndCol;
		this.value = value;
	}

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	public String getTabAndCol() {
		return tabAndCol;
	}

	public void setTabAndCol(String tabAndCol) {
		this.tabAndCol = tabAndCol;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

}
