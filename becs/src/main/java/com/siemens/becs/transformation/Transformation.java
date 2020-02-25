package com.siemens.becs.transformation;

import com.siemens.becs.objects.DataTable;
import com.siemens.becs.objects.utils.Source;

public interface Transformation {

	void setName(String name);

	String getName();

	void addMapping(Mapping mapping);

	void setSource(Source src);

	Source getSource();

	void setDestination(DataTable dest);

	DataTable getDestination();

	void transferData();
}
