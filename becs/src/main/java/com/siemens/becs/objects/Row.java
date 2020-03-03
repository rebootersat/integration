package com.siemens.becs.objects;

import java.util.ArrayList;
import java.util.Iterator;
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

	public void addColumnValue(String colName, String colValue) {
		this.columnValues.add(new Column(colName, colValue));
	}

	public String getColumnValue(String colName) {
		for (Iterator<Column> iterator = columnValues.iterator(); iterator.hasNext();) {
			Column column = (Column) iterator.next();
			if (column.getName().equals(colName))
				return column.getValue();
		}
		return null;
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
