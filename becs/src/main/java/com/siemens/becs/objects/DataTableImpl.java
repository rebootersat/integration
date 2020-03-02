package com.siemens.becs.objects;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class DataTableImpl implements DataTable {

	private String tableName;
	private List<Row> rows = new ArrayList<Row>();
	private List<String> columnNames = new ArrayList<String>();

	public DataTableImpl() {
	}

	public DataTableImpl(String name) {
		this.tableName = name;
	}

	@Override
	public void setName(String name) {
		this.tableName = name;
	}

	@Override
	public List<String> getColumnNames() {
		return columnNames;
	}
	
	@Override
	public String getName() {
		return this.tableName;
	}

	@Override
	public void addRow(Row row) {
		rows.add(row);
	}

	@Override
	public void forEach(Consumer<Row> consumer) {
		requireNonNull(consumer);
		rows.forEach(row -> {
			consumer.accept(row);
		});
	}

}
