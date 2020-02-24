package com.siemens.soap.bfs.objects.webbfs;

import java.util.ArrayList;
import java.util.List;

public class WebBFSRow {

	private List<WebBFSColumn> columnValues = new ArrayList<WebBFSColumn>();;

	public void add(WebBFSColumn col) {
		this.columnValues.add(col);
	}

	public List<WebBFSColumn> getColumnValues() {
		return columnValues;
	}
}
