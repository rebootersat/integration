package com.siemens.soap.bfs.sql;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class SqlServerDB {

	private List<String> cols;

	public SqlServerDB() {
		cols = new ArrayList<>();
	}

	public Connection getConnection(String username, String password, String dbName) {
		try {
			String driverName = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
			Class.forName(driverName);
			String url = "jdbc:sqlserver://localhost;databaseName=" + dbName;
			return DriverManager.getConnection(url, username, password);

		} catch (ClassNotFoundException e) {

			System.out.println("Could not find the database driver " + e.getMessage());
		} catch (SQLException e) {

			System.out.println("Could not connect to the database " + e.getMessage());
		}
		return null;
	}

	public int insert(Connection conn, String tableName, String[] colNames, String[] values) throws SQLException {
		StringBuilder query = new StringBuilder("INSERT INTO ");
		query.append(tableName).append("(");
		for (int i = 0; i < colNames.length; i++) {
			query.append(colNames[i]);
			if (i < colNames.length - 1)
				query.append(",");
		}
		query.append(") values (");
		for (int i = 0; i < colNames.length; i++) {
			query.append("?");
			if (i < colNames.length - 1)
				query.append(",");
		}
		query.append(")");

		System.out.println("Query : " + query);

		PreparedStatement stmt = conn.prepareStatement(query.toString());
		for (int i = 0; i < values.length; i++) {
			stmt.setString(i + 1, values[i]);
		}
		return stmt.executeUpdate();
	}

	public List<Row> select(Connection conn, String tableName, String[] colNames) throws SQLException {
		StringBuilder query = new StringBuilder("SELECT  ");
		for (int i = 0; i < colNames.length; i++) {
			query.append(colNames[i]);
			if (i < colNames.length - 1)
				query.append(",");
		}
		query.append(" FROM ").append(tableName);
		PreparedStatement prepareStatement = conn.prepareStatement(query.toString());
		prepareStatement.execute();
		ResultSet resultSet = prepareStatement.getResultSet();
		List<Row> rows = new ArrayList<>();
		while (resultSet.next()) {
			Row row = new Row();
			for (int i = 0; i < colNames.length; i++) {
				Column cl = new Column(colNames[i],resultSet.getString(colNames[i]));
				row.getColumns().add(cl);
			}
			rows.add(row);
		}
		return rows;
	}

	public void getColumnsMetadata(Connection conn, String tableName) throws SQLException {
		DatabaseMetaData metaData = conn.getMetaData();
		ResultSet columns = metaData.getColumns(null, null, tableName, null);
		while (columns.next()) {
			cols.add(columns.getString("COLUMN_NAME"));
		}
		System.out.println(cols);
	}

	public void closeConnection(Connection conn) {
		if (conn != null)
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
	}

	public static void test(String[] args) throws SQLException {

		SqlServerDB DbServer = new SqlServerDB();

		Connection conn = DbServer.getConnection("sa", "Jain@Mandir1989", "WebBFS");

		String[] colNames = new String[] { "AnlKnz", "KlaKnz" };

		List<Row> result = DbServer.select(conn, "Anl", colNames);
		
		result.forEach(row -> {
			System.out.println(row.getValues());
		});
		
		DbServer.closeConnection(conn);

	}

}
