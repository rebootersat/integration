package com.siemens.soap.becs.transformation;

import com.siemens.soap.bfs.objects.utils.Destination;
import com.siemens.soap.bfs.objects.utils.Source;

public interface Transformation {

	void setName(String name);
	
	String getName();
	
	void addMapping(Mapping mapping);
	
	void setSource(Source src);
	
	Source getSource();
	
	void setDestination(Destination dest);
	
	Destination getDestination();
	
	void transferData();
}
