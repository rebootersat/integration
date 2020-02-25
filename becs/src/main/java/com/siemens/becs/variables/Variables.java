package com.siemens.becs.variables;

import java.util.ArrayList;
import java.util.List;

public class Variables {

	static List<Variable> variables;

	public static List<Variable> getVariables() {
		if (variables == null)
			return new ArrayList<Variable>();
		return variables;
	}

	public void addVariable(Variable var) {
		getVariables().add(var);
	}

	public void addVariable(String name, String value) {
		addVariable(new Variable(name, value));
	}

	public static void updateValue(String varName, String value) {
		variables.forEach(var -> {
			if (var.getName().equals(varName))
				var.setValue(value);
		});
	}

}
