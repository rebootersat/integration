package com.siemens.soap.bfs.objects.webbfs;

import java.util.ArrayList;
import java.util.List;

public class ResultSet {

	private List<WebBFSRow> rows = new ArrayList<WebBFSRow>();
	
	public void addRow(WebBFSRow row) {
		this.rows.add(row);
	}
	
	public List<WebBFSRow> getRows() {
		return rows;
	}
}
