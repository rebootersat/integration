package com.siemens.becs.objects;

import java.util.List;
import java.util.function.Consumer;

public interface DataTable {

	void addRow(Row row);
	
	void setName(String name);

	List<String> getColumnNames();

	String getName();

	void forEach(Consumer<Row> consumer);
}
