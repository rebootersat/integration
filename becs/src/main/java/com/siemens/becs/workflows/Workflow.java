package com.siemens.becs.workflows;

public interface Workflow {

	void setXslt();
	
	void execute();
	
	void setRabbitMQ();
	
}
