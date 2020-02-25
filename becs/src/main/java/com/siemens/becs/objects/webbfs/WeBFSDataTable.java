package com.siemens.becs.objects.webbfs;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import com.siemens.becs.objects.utils.Source;

public class WeBFSDataTable implements Source {

	private ResultSet data;
	private String name;
	private List<String> cols = new ArrayList<String>();

	public WeBFSDataTable(String name) {
		this.name = name;
	}

	public void addColumn(String colName) {
		this.cols.add(colName);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public List<String> getColumnNames() {
		return this.cols;
	}

	public ResultSet getData() {
		return data;
	}

	public void setData(ResultSet data) {
		this.data = data;
	}

	public void forEachRow(Consumer<WebBFSRow> consumer) {
		data.getRows().forEach(row -> {
			consumer.accept(row);
		});
	}

	@Override
	public String toString() {
		return "DataTable [data=" + data + ", name=" + name + ", cols=" + cols + "]";
	}
	
	
}
