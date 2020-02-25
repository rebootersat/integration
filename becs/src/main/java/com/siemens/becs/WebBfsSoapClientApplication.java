package com.siemens.becs;

import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.xml.sax.SAXException;

import com.siemens.becs.connector.xml.parser.XmlProcessor;

@SpringBootApplication
public class WebBfsSoapClientApplication {

	public static void main(String[] args) throws IOException, SAXException, ParserConfigurationException {
		SpringApplication.run(WebBfsSoapClientApplication.class, args);

		XmlProcessor processor = new XmlProcessor();
		processor.loadXmlDocument("C:\\Users\\SANDEEP\\Desktop\\workflows\\plantitem\\sync-segment.xml");
		// List<DataTable> dataTables = processor.getDataTables();
		// System.out.println(dataTables);
	}

}
