package com.siemens.becs.objects.SqlServer;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.siemens.becs.objects.Log;
import com.siemens.becs.toolbox.ForEach;

public class Test {

	public static void main(String[] args) throws SQLException {
		SqlServerDB db = new SqlServerDB("sa", "Jain@Mandir1989", "WebBFS");
		db.createConnection();
		db.getColumnsMetadata("ANL");

		List<String> columns = new ArrayList<>();
		columns.add("ANLNUM");
		columns.add("ANLKNZ");
		columns.add("KLAKNZ");
		columns.add("ANLSTA");

		List<FilterColumn> filters = new ArrayList<>();
		filters.add(new FilterColumn("ANLSTA", "AND", "1"));

		db.select("ANL", columns, filters);
		
		SqlTable table = new SqlTable(db);
		table.setName("Anl");
		table.getColumnNames().addAll(columns);
		
		
		ForEach loop = new ForEach(table);
		
		/*
		loop.read(row->{
			System.out.println();
			row.getColumnValues().forEach(col->{
				System.out.println(col);
			});
		});*/
		
		List<Log> logs = new ArrayList<>();
		Log log = new Log();
		log.setMessages("Plant item no: (ANLNUM)");
		logs.add(log);
		log = new Log();
		//log.setMessages("Plant item code: {(ANLKNZ)}, WebBFS class : {(KLAKNZ)} and Status : {(ANLSTA)}");
	//	logs.add(log);
		
		loop.read(logs);
		
	}
	
}
