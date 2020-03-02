package com.siemens.becs.objects.SqlServer;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.springframework.stereotype.Service;

import com.siemens.becs.objects.Column;
import com.siemens.becs.objects.Row;

@Service
public class SqlServerDB {

	private Connection connection;
	private String username;
	private String password;
	private String dbName;

	public SqlServerDB(String username, String password, String dbName) {
		this.username = username;
		this.password = password;
		this.dbName = dbName;
	}

	public void createConnection() {
		if (Objects.nonNull(connection))
			return;
		try {
			String driverName = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
			Class.forName(driverName);
			String url = "jdbc:sqlserver://localhost;databaseName=" + dbName;
			connection = DriverManager.getConnection(url, username, password);

		} catch (ClassNotFoundException e) {

			System.out.println("Could not find the database driver " + e.getMessage());
		} catch (SQLException e) {

			System.out.println("Could not connect to the database " + e.getMessage());
		}
	}

	public int insert(String tableName, String[] colNames, String[] values) throws SQLException {
		Objects.requireNonNull(connection, "Connection cannot be null");
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

		PreparedStatement stmt = connection.prepareStatement(query.toString());
		for (int i = 0; i < values.length; i++) {
			stmt.setString(i + 1, values[i]);
		}
		return stmt.executeUpdate();
	}

	public List<Row> select(String tableName, List<String> columnNames, List<FilterColumn> filters)
			throws SQLException {
		Objects.requireNonNull(connection, "Connection cannot be null");
		StringBuilder query = new StringBuilder("SELECT ");
		for (int i = 0; i < columnNames.size(); i++) {
			query.append(columnNames.get(i));
			if (i < columnNames.size() - 1)
				query.append(",");
		}
		query.append(" FROM ").append(tableName);

		if (filters.size() > 0) {
			query.append(" WHERE ");
			for (int i = 0; i < filters.size(); i++) {
				FilterColumn filterColumn = filters.get(i);
				if (i > 0)
					query.append(" ").append(filterColumn.getOperator()).append(" ");
				query.append(filterColumn.getName()).append(" = ").append("?");
			}
		}

		System.out.println("Query : " + query);
		PreparedStatement stmt = connection.prepareStatement(query.toString());
		stmt.setString(1, filters.get(0).getValue());

		ResultSet result = stmt.executeQuery();
		List<Row> rows = new ArrayList<>();
		while (result.next()) {
			Row row = new Row();
			for (int i = 0; i < columnNames.size(); i++) {
				Column col = new Column(columnNames.get(i), result.getString(i + 1));
				row.addColumnValue(col);
			}
			rows.add(row);
		}
		System.out.println(rows);
		return rows;
	}

	public List<String> getColumnsMetadata(String tableName) throws SQLException {
		Objects.requireNonNull(connection, "Connection cannot be null");
		List<String> columnNames = new ArrayList<>();
		DatabaseMetaData metaData = connection.getMetaData();
		ResultSet columns = metaData.getColumns(null, null, tableName, null);
		while (columns.next()) {
			columnNames.add(columns.getString("COLUMN_NAME"));
		}
		return columnNames;
	}

	public void closeConnection(Connection conn) {
		if (conn != null)
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
	}

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
	}

}
