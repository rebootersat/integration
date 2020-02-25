package com.siemens.soap.bfs.workflows;

public interface Workflow {

	void setXslt();
	
	void execute();
	
	void setRabbitMQ();
	
}
