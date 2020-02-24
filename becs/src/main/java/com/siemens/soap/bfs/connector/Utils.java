package com.siemens.soap.bfs.connector;

import java.util.List;

public class Utils {

	public static <T> void requireNonEmptyList(List<T> object, String message) {
		if (object.isEmpty())
			throw new IllegalArgumentException(message);
	}

	public static <T> void requireNonEmptyList(List<T> object) {
		if (object.isEmpty())
			throw new IllegalArgumentException("List is empty");
	}

}
