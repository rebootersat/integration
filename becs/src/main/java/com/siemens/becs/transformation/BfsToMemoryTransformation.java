package com.siemens.becs.transformation;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.siemens.becs.objects.memory.Memory;
import com.siemens.becs.objects.utils.Column;
import com.siemens.becs.objects.utils.Destination;
import com.siemens.becs.objects.utils.Row;
import com.siemens.becs.objects.utils.Source;
import com.siemens.becs.objects.webbfs.DataTable;

public class BfsToMemoryTransformation implements Transformation {

	List<Mapping> mappings = new ArrayList<>();
	private Source source;
	private Destination dest;
	private String name;

	@Override
	public void setName(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
	
	private String getMappingBySourceColumnName(String ColName) {
		for (Iterator<Mapping> iterator = mappings.iterator(); iterator.hasNext();) {
			Mapping mapping = (Mapping) iterator.next();
			if(mapping.getSrcCol().equals(ColName))
				return mapping.getDestCol();
		}
		return null;
	}
	
	@Override
	public void setSource(Source source) {
		this.source = source;
	}

	@Override
	public void setDestination(Destination dest) {
		this.dest = dest;
	}

	public void addMapping(Mapping mapping) {
		this.mappings.add(mapping);
	}

	public List<Mapping> getMappings() {
		return mappings;
	}

	@Override
	public void transferData() {
		DataTable dataTable = (DataTable) source;
		Memory memory = (Memory) dest;
		dataTable.forEachRow(row -> {
			Row mRow = new Row();
			row.getColumnValues().forEach(columnVal -> {
				String mappedColName = getMappingBySourceColumnName(columnVal.getName());
				mRow.addColumnValue(new Column(mappedColName, columnVal.getValue()));
			});
			memory.getRows().add(mRow);
		});
	}

	@Override
	public Source getSource() {
		return source;
	}

	@Override
	public Destination getDestination() {
		return dest;
	}
}
