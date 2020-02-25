package com.siemens.becs.system;

import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.ws.client.core.WebServiceTemplate;
import org.springframework.ws.client.core.support.WebServiceGatewaySupport;

public class WebBFSSoapEndPoint extends WebServiceGatewaySupport {

	private WebServiceTemplate server;
	
	public void intIt() {
		this.server = getWebServiceTemplate();
		configureJaxb2Marshaller();
	}

	private void configureJaxb2Marshaller() {
		Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
		marshaller.setContextPath("bfs.soap.siemens.com.webbfs_soap_server");
		setMarshaller(marshaller);
		setUnmarshaller(marshaller);
		setDefaultUri("http://localhost:8082/bfs");
	}
	
	public Object sendRequest(Object request) {
		return server.marshalSendAndReceive(request);
	}
}
