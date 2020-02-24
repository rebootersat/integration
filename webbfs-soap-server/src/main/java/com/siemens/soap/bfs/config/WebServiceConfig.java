package com.siemens.soap.bfs.config;

import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.ws.config.annotation.EnableWs;
import org.springframework.ws.config.annotation.WsConfigurerAdapter;
import org.springframework.ws.transport.http.MessageDispatcherServlet;
import org.springframework.ws.wsdl.wsdl11.DefaultWsdl11Definition;
import org.springframework.xml.xsd.SimpleXsdSchema;
import org.springframework.xml.xsd.XsdSchema;

@EnableWs
@Configuration
public class WebServiceConfig extends WsConfigurerAdapter{

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Bean
	public ServletRegistrationBean messageDispatcherServlet(ApplicationContext applicationContext) {
		MessageDispatcherServlet servlet = new MessageDispatcherServlet();
		servlet.setApplicationContext(applicationContext);
		servlet.setTransformWsdlLocations(true);
		return new ServletRegistrationBean(servlet, "/bfs/*");
	}
	
	@Bean(name = "webbfs")
	public DefaultWsdl11Definition defaultWsdl11Definition(XsdSchema schema) {
		DefaultWsdl11Definition definition = new DefaultWsdl11Definition();
		definition.setPortTypeName("WebBFSSoapServerPort");
		definition.setLocationUri("/bfs");
		definition.setSchema(schema);
		definition.setTargetNamespace("http://com.siemens.soap.bfs/webbfs-soap-server");
		return definition;
	}

	@Bean
	public XsdSchema webbfsSchema() {
		return new SimpleXsdSchema(new ClassPathResource("webbfs.xsd"));
	}

}
