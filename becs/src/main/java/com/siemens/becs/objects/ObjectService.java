package com.siemens.becs.objects;

import java.util.List;

public interface ObjectService {

	void setName(String name);

	String getName();

	void execute();

	default List<Row> getData() {
		System.out.println("Method implementation missing in child class");
		return null;
	}

	default void receiveData(Row row) {
		System.out.println("Method implementation missing in child class, Method name : receiveData(row)");
	}

	
	default void receiveData(List<Row> row) {
		System.out.println("Method implementation missing in child class, Method name : receiveData(List<Row> row)");
	}
	
}
