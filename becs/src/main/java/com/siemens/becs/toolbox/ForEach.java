package com.siemens.becs.toolbox;

import java.util.List;
import java.util.function.Consumer;

import com.siemens.becs.objects.ObjectService;
import com.siemens.becs.objects.Row;
import com.siemens.becs.transformation.Mapping;
import com.siemens.becs.variables.Variable;
import com.siemens.becs.variables.Variables;

public class ForEach {

	private List<Mapping> mappings;
	private ObjectService objectService;

	public ForEach(ObjectService objectService, List<Row> rows, SetValue setVal) {
		this.mappings = setVal.getMappings();
		this.objectService = objectService;
	}

	public void iterate(Consumer<List<Variable>> consumer) {
		objectService.getData().forEach(row -> {
			row.getColumnValues().forEach(col -> {
				Mapping mapping = getMapping(col.getName());
				Variables.updateValue(mapping.getSrcCol(), col.getValue());
			});
			consumer.accept(Variables.getVariables());
		});
	}

	private Mapping getMapping(String srcName) {
		for (int i = 0; i < mappings.size(); i++) {
			if (mappings.get(i).getSrcCol().equals(srcName))
				return mappings.get(i);
		}
		return null;
	}
}
