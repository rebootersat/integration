package com.siemens.becs.objects.SqlServer;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.siemens.becs.objects.DataTableImpl;
import com.siemens.becs.objects.ObjectService;
import com.siemens.becs.objects.Row;
import com.siemens.becs.transformation.Transformation;

public class SqlTable extends DataTableImpl implements ObjectService {

	private String objectName;
	private List<ObjectService> destinationObjects;
	private Transformation transformation;
	private SqlServerDB db;

	public SqlTable(SqlServerDB db) {
		this.db = db;
	}

	public void setTransformation(Transformation transformation) {
		this.transformation = transformation;
	}

	public Transformation getTransformation() {
		return transformation;
	}

	public List<ObjectService> getDestinationObjects() {
		return destinationObjects;
	}

	public void setDestinationObjects(List<ObjectService> destinationObjects) {
		this.destinationObjects = destinationObjects;
	}

	public void setObjectName(String objectName) {
		this.objectName = objectName;
	}

	public String getObjectName() {
		return objectName;
	}

	@Override
	public void execute() {
		destinationObjects.forEach(targetObject -> {
			targetObject.receiveData(getData());
		});
	}

	@Override
	public void receiveData(List<Row> rows) {
		rows.addAll(rows);
	}

	@Override
	public void receiveData(Row row) {
		getData().add(row);
	}

	@Override
	public List<Row> getData() {
		db.createConnection();
		try {
			List<String> columns = db.getColumnsMetadata(getName());
			boolean containsAll = columns.containsAll(getColumnNames());
			if (containsAll) {
				return db.select(getName(), getColumnNames(), null);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return new ArrayList<Row>();
	}
}
