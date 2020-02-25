package com.siemens.becs.objects;

import java.util.List;

public interface DataTable {

	void setName(String name);

	List<Row> read();

	List<String> getColumnNames();

}
