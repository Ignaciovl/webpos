package com.onboarding.pos.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class JdbcHelper {
	
private SystemHelper systemHelper;
	
	public JdbcHelper(SystemHelper systemHelper) {
		this.systemHelper = systemHelper;
	}
	
	public SystemHelper getSystemHelper() {
		return systemHelper;
	}

	public void setSystemHelper(SystemHelper systemHelper) {
		this.systemHelper = systemHelper;
	}

	public Connection getConnection() throws Exception {
		String databaseDriver = systemHelper.getAppProperty("database.driver");
		String databaseUrl = systemHelper.getAppProperty("database.url");
		String username = systemHelper.getAppProperty("database.username");
		String password = systemHelper.getAppProperty("database.password");

		Class.forName(databaseDriver);
		return DriverManager.getConnection(databaseUrl, username, password);
	}
	
	public void closeConnection(Connection conn, Statement stmt) throws SQLException {
		if (stmt != null)
			stmt.close();
		if (conn != null)
			conn.close();
	}
	
	public void closeConnection(Connection conn, Statement stmt, ResultSet rs) throws SQLException {
		if (rs != null)
			rs.close();
		closeConnection(conn, stmt);
	}
}
