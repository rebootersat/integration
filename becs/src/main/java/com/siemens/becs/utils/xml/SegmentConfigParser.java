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
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.siemens.becs.objects.DataTable;
import com.siemens.becs.objects.DataTableImpl;
import com.siemens.becs.objects.memory.Memory;
import com.siemens.becs.transformation.Mapping;
import com.siemens.becs.transformation.ObjectInfo;
import com.siemens.becs.transformation.Transformation;
import com.siemens.becs.variables.Variable;

public class SegmentConfigParser {

	private static XmlProcessor xmlProcessor;

	public SegmentConfigParser(String apth) throws ParserConfigurationException, IOException, SAXException {
		xmlProcessor = new XmlProcessor(apth);
	}

	public Transformation getTransformationByID(String id) {
		String expression = "Workflow/Transformations/Transformation[@ID='" + id + "']";
		NodeList transfrNodes = getNodesByExpression(expression);
		Objects.requireNonNull(transfrNodes);
		System.out.println("transformation count : " + transfrNodes.getLength());
		Transformation trn = new Transformation();
		for (int i = 0; i < transfrNodes.getLength(); i++) {
			List<Mapping> mappings = new ArrayList<>();
			Node transfrmNode = transfrNodes.item(0);
			NamedNodeMap attributes = transfrmNode.getAttributes();
			String name = attributes.getNamedItem("Name").getNodeValue();
			trn.setName(name);
			String sourceID = attributes.getNamedItem("sourceObjectID").getNodeValue();
			trn.setSource(getObject(sourceID));
			String destID = attributes.getNamedItem("destintationObjectID").getNodeValue();
			trn.setDestination(getObject(destID));
			NodeList mappingNodes = transfrNodes.item(i).getChildNodes();
			for (int j = 0; j < mappingNodes.getLength(); j++) {
				NodeList mappingNode = mappingNodes.item(j).getChildNodes();
				if (mappingNodes.item(j).getNodeType() == Node.ELEMENT_NODE) {
					Mapping mapping = new Mapping();
					for (int k = 0; k < mappingNode.getLength(); k++) {
						Node item = mappingNode.item(k);
						if (item.getNodeType() == Node.ELEMENT_NODE) {
							if (item.getNodeName().equals("SourceColName")) {
								mapping.setSrcCol(item.getTextContent().trim());
							} else if (item.getNodeName().equals("DestinationColName")) {
								mapping.setDestCol(item.getTextContent().trim());
							}
						}
					}
					mappings.add(mapping);
				}
			}
			trn.setMapping(mappings);
		}
		return trn;
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

	private ObjectInfo getObject(String objectID) {

		ObjectInfo object = getObject("DataTable", objectID);
		if (Objects.isNull(object)) {
			object = getObject("Memory", objectID);
			if (Objects.isNull(object)) {
				return null;
			}
		}
		return object;
	}

	private ObjectInfo getObject(String objectVal, String objectID) {
		String expression = "//" + objectVal + "[@ID='" + objectID + "']";
		NodeList objectNodes = getNodesByExpression(expression);
		Objects.requireNonNull(objectNodes);
		ObjectInfo info = new ObjectInfo();
		for (int i = 0; i < objectNodes.getLength(); i++) {
			String name = objectNodes.item(i).getAttributes().getNamedItem("Name").getNodeValue();
			info.setName(name);
			List<String> columnNames = getColumnNames(objectNodes.item(i).getChildNodes());
			info.getColumns().addAll(columnNames);
		}
		return info.getName() == null ? null: info;
	}

	public List<DataTable> getDataTablesForSearchObject(String id) {
		List<DataTable> dTables = new ArrayList<>();
		String expression = "Workflow/Connectors/Connector/SearchObjects/SearchObject[@ID='" + id
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
				"C:\\Users\\SANDEEP\\git\\integration\\becs\\sync-segment.xml");

		/**
		 * List<DataTable> tabs =
		 * processor.getDataTablesForSearchObject("FetchPlantItems");
		 * 
		 * tabs.forEach(table -> { System.out.println("Table Name : " +
		 * table.getName()); System.out.println(table.getColumnNames()); });
		 * 
		 * Memory memory = processor.getMemoryByID("AsrMemory");
		 * System.out.println("Name : " + memory.getName());
		 * System.out.println(memory.getColumnNames());
		 * 
		 * Variables.getVariables().addAll(processor.getVariables());
		 * 
		 * Variables.forEach(var -> { System.out.println(var); });
		 **/
		 Transformation transformationByID =
		 processor.getTransformationByID("SearchAnlToAnlMemory");
		 transformationByID.validate();
		System.out.println(transformationByID);
	//	ObjectInfo object = processor.getObject("AnlMemory");
		// System.out.println(object);
	}

}
