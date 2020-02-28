package com.siemens.becs.variables;

public class Variable {

	private String name;
	private String value;

	public Variable(String name, String values) {
		super();
		this.name = name;
		this.value = values;
	}

	public Variable() {
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String values) {
		this.value = values;
	}

	@Override
	public String toString() {
		return "Variable [name=" + name + ", values=" + value + "]";
	}

}
