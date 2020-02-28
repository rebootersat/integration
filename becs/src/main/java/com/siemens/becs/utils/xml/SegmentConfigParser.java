package com.siemens.becs.utils.xml;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.springframework.expression.ExpressionException;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.siemens.becs.objects.DataTable;
import com.siemens.becs.objects.DataTableImpl;
import com.siemens.becs.objects.memory.Memory;
import com.siemens.becs.variables.Variable;
import com.siemens.becs.variables.Variables;

public class SegmentConfigParser {

	private static XmlProcessor xmlProcessor;

	public SegmentConfigParser(String apth) throws ParserConfigurationException, IOException, SAXException {
		xmlProcessor = new XmlProcessor(apth);
	}

	public List<Variable> getVariables() {
		String expression = "Workflow/Variables/Variable";
		NodeList variableNodes = getNodesByExpression(expression);
		Objects.requireNonNull(variableNodes);
		List<Variable> variables = new ArrayList<>();
		for (int i = 0; i < variableNodes.getLength(); i++) {
			Variable var = new Variable();
			NodeList variableNode = variableNodes.item(i).getChildNodes();
			for (int j = 0; j < variableNode.getLength(); j++) {
				Node item = variableNode.item(j);
				if (item.getNodeType() == Node.ELEMENT_NODE) {
					if (item.getNodeName().equals("Name"))
						var.setName(item.getTextContent().trim());
					else if (item.getNodeName().equals("Value"))
						var.setValue(item.getTextContent().trim());
				}

			}
			variables.add(var);
		}
		return variables;
	}

	public Memory getMemoryByID(String id) {
		String expression = "Workflow/Memories/Memory[@ID='" + id + "']";
		NodeList memoryNodes = getNodesByExpression(expression);
		Objects.requireNonNull(memoryNodes);
		Memory memory = new Memory();
		for (int i = 0; i < memoryNodes.getLength(); i++) {
			String name = memoryNodes.item(i).getAttributes().getNamedItem("Name").getNodeValue();
			memory.setName(name);
			List<String> columnNames = getColumnNames(memoryNodes.item(i).getChildNodes());
			memory.getColumnNames().addAll(columnNames);
		}
		return memory != null ? memory : null;
	}

	public List<DataTable> getDataTablesForSearchObject(String searchObjectName) {
		List<DataTable> dTables = new ArrayList<>();
		String expression = "Workflow/Connectors/Connector/SearchObjects/SearchObject[@Name='" + searchObjectName
				+ "']/ListOf_DataTables/DataTable";
		NodeList dataTableNodes = getNodesByExpression(expression);
		Objects.requireNonNull(dataTableNodes);
		for (int i = 0; i < dataTableNodes.getLength(); i++) {
			String nodeName = dataTableNodes.item(i).getAttributes().getNamedItem("Name").getNodeValue();
			DataTable dataTable = new DataTableImpl(nodeName);
			List<String> columnNames = getColumnNames(dataTableNodes.item(i).getChildNodes());
			dataTable.getColumnNames().addAll(columnNames);
			dTables.add(dataTable);
		}
		return dTables;
	}

	private List<String> getColumnNames(NodeList columnsNodes) {
		List<String> colNames = new ArrayList<>();
		for (int j = 0; j < columnsNodes.getLength(); j++) {
			if (columnsNodes.item(j).getNodeType() == Node.ELEMENT_NODE) {
				NodeList columnNode = columnsNodes.item(j).getChildNodes();
				for (int k = 0; k < columnNode.getLength(); k++) {
					if (columnNode.item(k).getNodeType() == Node.ELEMENT_NODE) {
						colNames.add(columnNode.item(k).getTextContent());
					}
				}
			}
		}
		return colNames;
	}

	private NodeList getNodesByExpression(String expression) {
		XPathFactory factory = XPathFactory.newInstance();
		XPath xpath = factory.newXPath();
		NodeList nodeList = null;
		try {
			XPathExpression expr = xpath.compile(expression);
			nodeList = (NodeList) expr.evaluate(xmlProcessor.getDocument(), XPathConstants.NODESET);
		} catch (ExpressionException | XPathExpressionException e) {
			e.printStackTrace();
		}
		return nodeList;
	}

	public static void main(String[] args) throws ParserConfigurationException, IOException, SAXException {
		SegmentConfigParser processor = new SegmentConfigParser(
				"D:\\WebBFS-APM-integration\\integration\\becs\\sync-segment.xml");
		List<DataTable> tabs = processor.getDataTablesForSearchObject("FetchPlantItems");

		tabs.forEach(table -> {
			System.out.println("Table Name : " + table.getName());
			System.out.println(table.getColumnNames());
		});

		Memory memory = processor.getMemoryByID("AsrMemory");
		System.out.println("Name : " + memory.getName());
		System.out.println(memory.getColumnNames());

		Variables.getVariables().addAll(processor.getVariables());

		Variables.forEach(var -> {
			System.out.println(var);
		});
	}

}
