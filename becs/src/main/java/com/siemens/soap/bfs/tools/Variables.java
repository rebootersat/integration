package com.siemens.soap.bfs.tools;

import java.util.ArrayList;
import java.util.List;

public class Variables {

	static List<Variable> variables;

	public List<Variable> getVariables() {
		if (variables == null)
			return new ArrayList<Variable>();
		return variables;
	}

	public static void updateValue(String varName, String value) {
		variables.forEach(var -> {
			if (var.getName().equals(varName))
				var.setValue(value);
		});
	}

}
