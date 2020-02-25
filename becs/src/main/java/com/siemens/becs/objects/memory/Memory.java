package com.siemens.soap.bfs.objects.memory;

import java.util.ArrayList;
import java.util.List;

import com.siemens.soap.bfs.objects.utils.Destination;
import com.siemens.soap.bfs.objects.utils.Row;
import com.siemens.soap.bfs.objects.utils.Source;

public class Memory implements Source, Destination {

	private String name;
	private List<String> columnsNames = new ArrayList<>();
	private List<Row> rows = new ArrayList<>();

	public Memory(String... cols) {
		for (int i = 0; i < cols.length; i++) {
			columnsNames.add(cols[i]);
		}
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<String> getColumnsNames() {
		return columnsNames;
	}

	public void setColumnsNames(List<String> columnsNames) {
		this.columnsNames = columnsNames;
	}

	public List<Row> getRows() {
		return rows;
	}

	public void setRows(List<Row> rows) {
		this.rows = rows;
	}

	@Override
	public List<String> getColumnNames() {
		return columnsNames;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("{");
		
		for (int i = 0; i < rows.size(); i++) {
			builder.append(rows.get(i));
			if(i< rows.size() -1)
				builder.append(",");
		}
		builder.append("}");
		return builder.toString();
	}

}
