package com.siemens.becs.objects.memory;

import java.util.ArrayList;
import java.util.List;

import com.siemens.becs.objects.ObjectService;
import com.siemens.becs.objects.Row;

public class Memory implements ObjectService {

	private String name;
	private List<String> columnsNames = new ArrayList<>();
	private List<Row> rows = new ArrayList<>();

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
}
