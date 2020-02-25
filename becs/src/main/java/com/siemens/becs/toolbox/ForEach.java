package com.siemens.soap.bfs.tools;

import java.util.List;
import java.util.function.Consumer;

import com.siemens.soap.becs.transformation.Mapping;
import com.siemens.soap.bfs.objects.utils.Row;

public class ForEach {

	List<Row> rows;
	List<Mapping> mappings;

	public ForEach(List<Row> rows, List<Mapping> mappings) {
		this.rows = rows;
		this.mappings = mappings;
	}

	public ForEach(Consumer<String> consumer) {
		rows.forEach(row -> {
			row.getColumnValues().forEach(col -> {
				Mapping mapping = getMapping(col.getName());
				Variables.updateValue(mapping.getSrcCol(), col.getValue());
			});
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
