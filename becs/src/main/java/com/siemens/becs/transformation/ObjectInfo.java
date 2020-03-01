package com.siemens.becs.transformation;

import java.util.ArrayList;
import java.util.List;

public class ObjectInfo {

	private String name;
	private List<String> columns = new ArrayList<>();

	public ObjectInfo() {
	}

	public ObjectInfo(String name) {
		super();
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<String> getColumns() {
		return columns;
	}

	public void setColums(List<String> columns) {
		this.columns = columns;
	}

	@Override
	public String toString() {
		return "ObjectInfo [name=" + name + ", columns=" + columns + "]";
	}
}
