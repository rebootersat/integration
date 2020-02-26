package com.siemens.becs.objects.webbfs;

import java.util.ArrayList;
import java.util.List;

import com.siemens.becs.objects.Row;

public class ResultSet {

	private List<Row> rows = new ArrayList<Row>();
	
	public void addRow(Row row) {
		this.rows.add(row);
	}
	
	public List<Row> getRows() {
		return rows;
	}
}
