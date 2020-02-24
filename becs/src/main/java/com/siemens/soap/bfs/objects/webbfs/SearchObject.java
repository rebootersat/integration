package com.siemens.soap.bfs.objects.webbfs;

import java.util.ArrayList;
import java.util.List;

import com.siemens.soap.becs.transformation.Transformation;
import com.siemens.soap.bfs.connector.WebBFSSoapEndPoint;

import bfs.soap.siemens.com.webbfs_soap_server.ArrayOfBfsObj;
import bfs.soap.siemens.com.webbfs_soap_server.BfsObj;
import bfs.soap.siemens.com.webbfs_soap_server.SearchObjects;
import bfs.soap.siemens.com.webbfs_soap_server.SearchObjectsResponse;
import bfs.soap.siemens.com.webbfs_soap_server.SearchObjectsResult;

public class SearchObject {

	private String mainTableName;
	private List<SearchVal> searchVals = new ArrayList<>();
	private List<SelectCol> selectCols = new ArrayList<>();
	private String name;
	private List<ResultSet> result = new ArrayList<ResultSet>();
	private List<Transformation> transformation;
	private List<DataTable> dataTables;

	
	
	public List<Transformation> getTransformation() {
		return transformation;
	}

	public void setTransformation(List<Transformation> transformation) {
		this.transformation = transformation;
	}

	public List<DataTable> getDataTables() {
		return dataTables;
	}

	public void setDataTables(List<DataTable> dataTables) {
		this.dataTables = dataTables;
	}

	public SearchObject() {
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

	public void setSearchVal(SearchVal searchColumn) {
		searchVals.add(searchColumn);
	}

	public List<SearchVal> getSearchVals() {
		return this.searchVals;
	}

	public List<SelectCol> getSelectCols() {
		return this.selectCols;
	}

	public void setSelectCol(SelectCol selectCol) {
		selectCols.add(selectCol);
	}

	public void setResult(ResultSet result) {
		this.result.add(result);
	}

	public List<ResultSet> getResult() {
		return result;
	}
	

	public void execute(WebBFSSoapEndPoint webBFSEndPoint) {
		SearchObjectsResponse response = (SearchObjectsResponse) webBFSEndPoint.sendRequest(new SearchObjects());
		createDataTable(response);
	}

	private void createDataTable(SearchObjectsResponse response) {
		SearchObjectsResult result = response.getSearchObjectsResult();
		ArrayOfBfsObj listOfBfsObj = result.getListOfBfsObj();
		listOfBfsObj.getBfsObj().forEach(bfsObject -> {
			DataTable table = getDataTableByName(bfsObject.getTabNam());
			if (null != table) {
				convertBfsObjectToDataTable(bfsObject, table);
				Transformation trns = getTransformationByName(table.getName());
				trns.setSource(table);
				trns.transferData();
			}
		});
	}

	private DataTable getDataTableByName(String tabName) {
		for (int i = 0; i < this.dataTables.size(); i++) {
			if (this.dataTables.get(i).getName().equals(tabName))
				return this.dataTables.get(i);
		}
		return null;
	}

	private void convertBfsObjectToDataTable(BfsObj bfsObject, DataTable table) {
		table.setName(bfsObject.getTabNam());
		ResultSet data = new ResultSet();
		bfsObject.getObjVal().forEach(row -> {
			WebBFSRow rw = new WebBFSRow();
			row.getObjVal().forEach(column -> {
				rw.add(new WebBFSColumn(column.getColNam(), column.getValue()));
			});
			data.addRow(rw);
		});
		table.setData(data);
	}
	
	private Transformation getTransformationByName(String name) {
		for (int i = 0; i < this.transformation.size(); i++) {
			if(this.transformation.get(i).getName().equals(name))
				return this.transformation.get(i);
		}
		return null;
	}
}
