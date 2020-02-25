package com.siemens.becs.objects.utils;

import java.util.ArrayList;
import java.util.List;

public class Row {

	List<Column> columnValues;

	public Row() {
		columnValues = new ArrayList<Column>();
	}

	public List<Column> getColumnValues() {
		return columnValues;
	}

	public void addColumnValue(Column col) {
		this.columnValues.add(col);
	}

	@Override
	public String toString() {

		StringBuilder builder = new StringBuilder();
		builder.append("[");
		for (int i = 0; i < this.columnValues.size(); i++) {
			builder.append(columnValues.get(i));
			if (i < this.columnValues.size() - 1)
				builder.append(",");
		}
		builder.append("]");
		return builder.toString();
	}

}
