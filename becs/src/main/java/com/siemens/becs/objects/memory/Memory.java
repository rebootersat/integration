package com.siemens.becs.objects.memory;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import com.siemens.becs.objects.DataTable;
import com.siemens.becs.objects.ObjectService;
import com.siemens.becs.objects.Row;

public class Memory implements ObjectService, DataTable {

	private String name;
	private List<String> columnsNames = new ArrayList<>();
	private List<Row> rows = new ArrayList<>();

	public Memory() {
		// TODO Auto-generated constructor stub
	}
	
	public Memory(String name) {
		this.name = name;
	}
	
	public Memory(String... cols) {
		for (int i = 0; i < cols.length; i++) {
			columnsNames.add(cols[i]);
		}
	}
	
	@Override
	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String getName() {
		return this.name;
	}

	@Override
	public void execute() {

	}

	@Override
	public void receiveData(List<Row> rows) {
		this.rows.addAll(rows);
	}

	@Override
	public void pushData(ObjectService objectService, Row row) {
		objectService.receiveData(row);
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("{");

		for (int i = 0; i < rows.size(); i++) {
			builder.append(rows.get(i));
			if (i < rows.size() - 1)
				builder.append(",");
		}
		builder.append("}");
		return builder.toString();
	}

	@Override
	public void addRow(Row row) {
		rows.add(row);
	}

	@Override
	public List<String> getColumnNames() {
		return columnsNames;
	}

	@Override
	public void forEach(Consumer<Row> consumer) {
		
	}
}
