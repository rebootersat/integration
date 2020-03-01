package com.siemens.becs.objects.webbfs;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import com.siemens.becs.objects.Column;
import com.siemens.becs.objects.DataTable;
import com.siemens.becs.objects.ObjectService;
import com.siemens.becs.objects.Row;
import com.siemens.becs.system.WebBFSSoapEndPoint;
import com.siemens.becs.transformation.Transformation;

import bfs.soap.siemens.com.webbfs_soap_server.ArrayOfBfsObj;
import bfs.soap.siemens.com.webbfs_soap_server.BfsObj;
import bfs.soap.siemens.com.webbfs_soap_server.SearchObjects;
import bfs.soap.siemens.com.webbfs_soap_server.SearchObjectsResponse;
import bfs.soap.siemens.com.webbfs_soap_server.SearchObjectsResult;

public class SearchObject implements ObjectService {

	private String mainTableName;
	private List<SearchVal> searchVals = new ArrayList<>();
	private List<SelectCol> selectCols = new ArrayList<>();
	private String name;
	private List<Transformation> transformation;
	private List<DataTable> dataTables;
	private List<ObjectService> consumerObjects;
	private WebBFSSoapEndPoint webBFSEndPoint;

	public SearchObject(List<ObjectService> consumerObjects) {
		this.consumerObjects = consumerObjects;
	}

	public void setWebBFSEndPoint(WebBFSSoapEndPoint webBFSEndPoint) {
		this.webBFSEndPoint = webBFSEndPoint;
	}

	public List<Transformation> getTransformation() {
		return transformation;
	}

	public void setTransformation(List<Transformation> transformation) {
		this.transformation = transformation;
	}

	public void setDataTables(List<DataTable> dataTables) {
		this.dataTables = dataTables;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setMainTableName(String mainTableName) {
		this.mainTableName = mainTableName;
	}

	public String getMainTableName() {
		return this.mainTableName;
	}

	public void addSearchVal(SearchVal searchColumn) {
		searchVals.add(searchColumn);
	}

	public List<SearchVal> getSearchVals() {
		return this.searchVals;
	}

	public List<SelectCol> getSelectCols() {
		return this.selectCols;
	}

	public void addSelectCol(SelectCol selectCol) {
		selectCols.add(selectCol);
	}

	@Override
	public void execute() {
		SearchObjectsResponse response = (SearchObjectsResponse) webBFSEndPoint.sendRequest(createRequestObject());
		parseResponse(response);

		dataTables.forEach(dt -> {
			Transformation trns = getTransformationBySourceObjectName(dt.getName());
			ObjectService objectService = getConsumerObjectService(trns.getDestination().getName());
			dt.forEach(row -> {
				Row r = new Row();
				row.getColumnValues().forEach(col -> {
					String destiColName = trns.getDestinationColumnName(col.getName());
					r.addColumnValue(destiColName, col.getValue());
				});
				objectService.receiveData(r);
			});
		});
	}

	@Override
	public void receiveData(Row row, String dataTableName) {
		DataTable table = getDataTableByName(dataTableName);
		if (null == table)
			throw new NoSuchElementException("Datatable not found " + dataTableName);
		table.addRow(row);
	}

	@Override
	public void pushData(ObjectService next, Row row) {
		next.receiveData(row);
	}

	private void parseResponse(SearchObjectsResponse response) {
		SearchObjectsResult result = response.getSearchObjectsResult();
		ArrayOfBfsObj listOfBfsObj = result.getListOfBfsObj();
		listOfBfsObj.getBfsObj().forEach(bfsObject -> {
			createDataTable(bfsObject);
		});
	}

	private DataTable getDataTableByName(String tabName) {
		for (int i = 0; i < this.dataTables.size(); i++) {
			if (this.dataTables.get(i).getName().equals(tabName))
				return this.dataTables.get(i);
		}
		return null;
	}

	private void createDataTable(BfsObj bfsObject) {
		bfsObject.getObjVal().forEach(row -> {
			Row rw = new Row();
			row.getObjVal().forEach(column -> {
				rw.addColumnValue(new Column(column.getColNam(), column.getValue()));
			});
			receiveData(rw, bfsObject.getTabNam());
		});
	}

	private Transformation getTransformationBySourceObjectName(String name) {
		for (int i = 0; i < this.transformation.size(); i++) {
			if (this.transformation.get(i).getSource().getName().equals(name))
				return this.transformation.get(i);
		}
		return null;
	}

	private ObjectService getConsumerObjectService(String name) {
		for (int i = 0; i < consumerObjects.size(); i++) {
			if (consumerObjects.get(i).getName().equals(name))
				return consumerObjects.get(i);
		}
		return null;
	}

	private SearchObjects createRequestObject() {
		return new SearchObjects();
	}
}
