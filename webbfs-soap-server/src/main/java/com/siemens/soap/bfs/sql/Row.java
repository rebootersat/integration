package com.siemens.soap.bfs.sql;

import java.util.ArrayList;
import java.util.List;

public class Row {

	private List<Column> columns;

	public Row() {
		columns = new ArrayList<Column>();
	}

	public List<Column> getColumns() {
		return columns;
	}

	@Override
	public String toString() {
		return "Row [columns=" + columns + "]";
	}

	public List<String> getValues() {
		List<String> values = new ArrayList<>();
		columns.forEach(column->{
			values.add(column.getValue());
		});
		return values;
	}
	
	public List<String> getColumnNames() {
		List<String> values = new ArrayList<>();
		columns.forEach(column->{
			values.add(column.getName());
		});
		return values;
	}
	
	

}
