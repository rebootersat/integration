package com.siemens.becs.toolbox;

import java.util.ArrayList;
import java.util.List;

import com.siemens.becs.transformation.Mapping;

public class SetValue {

	private String name;
	private List<Mapping> mappings = new ArrayList<>();

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Mapping> getMappings() {
		return mappings;
	}

	public void setMappings(List<Mapping> mappings) {
		this.mappings = mappings;
	}

	public void addMapping(Mapping mapping) {
		this.mappings.add(mapping);
	}

	public void addMapping(String variableName, String sourceVariableName) {
		this.mappings.add(new Mapping(variableName, sourceVariableName));
	}
}
