package com.siemens.becs.utils.xml;

import java.io.IOException;
import java.util.List;

import javax.xml.bind.Element;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.siemens.becs.objects.DataTable;

public class SegmentConfigParser {

	private static XmlProcessor xmlProcessor;

	public SegmentConfigParser(String apth) throws ParserConfigurationException, IOException, SAXException {
		xmlProcessor = new XmlProcessor(apth);
	}

	public List<DataTable> getDataTablesForSearchObject(String objectName) {

		XPathFactory factory = XPathFactory.newInstance();
		XPath xpath = factory.newXPath();
		XPathExpression expr;
		try {
			String expression = "//*[@Name='" + objectName + "']";
			expression = "Workflow/Connectors/Connector/SearchObjects/SearchObject[@Name='" + objectName
					+ "']/ListOf_DataTables/DataTable";
			expr = xpath.compile(expression);

			NodeList dataTables = (NodeList) expr.evaluate(xmlProcessor.getDocument(), XPathConstants.NODESET);
			System.out.println("No of DataTables  : " + dataTables.getLength());
			for (int i = 0; i < dataTables.getLength(); i++) {
				String nodeName = dataTables.item(i).getNodeName();
				Node namedItem = dataTables.item(i).getAttributes().getNamedItem("Name");
				System.out.println("Node name : " + nodeName+" name "+namedItem.getNodeValue());
				NodeList columns = dataTables.item(i).getChildNodes();
				System.out.println("No of columns : " + columns.getLength());
				for (int j = 0; j < columns.getLength(); j++) {
					nodeName = columns.item(j).getNodeName();
					if (nodeName == "Columns") {
						NodeList Column = columns.item(j).getChildNodes();
						for (int j2 = 0; j2 < Column.getLength(); j2++) {
							nodeName = Column.item(j2).getNodeName();
							if (nodeName == "ColumnName")
								System.out.println("in j2 loop Node name : " + nodeName + " val : "
										+ Column.item(j2).getTextContent());
						}
					}

				}
			}
		} catch (XPathExpressionException e) {
			e.printStackTrace();
		}

		return null;
	}

	public static void main(String[] args) throws ParserConfigurationException, IOException, SAXException {
		SegmentConfigParser processor = new SegmentConfigParser(
				"D:\\WebBFS-APM-integration\\integration\\becs\\sync-segment.xml");
		processor.getDataTablesForSearchObject("FetchPlantItems");
	}

}
