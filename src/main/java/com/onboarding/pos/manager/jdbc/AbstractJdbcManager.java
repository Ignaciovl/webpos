package com.onboarding.pos.manager.jdbc;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.log4j.Logger;

import com.onboarding.pos.manager.EntityManager;
import com.onboarding.pos.model.EntityWithIntId;
import com.onboarding.pos.util.JdbcHelper;

public abstract class AbstractJdbcManager<T extends EntityWithIntId<T>> implements EntityManager<T> {

	protected final Logger logger = Logger.getLogger(this.getClass());
	
	private JdbcHelper jdbcHelper;
	
	public AbstractJdbcManager(JdbcHelper jdbcHelper) {
		this.jdbcHelper = jdbcHelper;
	}
	
	public JdbcHelper getJdbcHelper() {
		return jdbcHelper;
	}

	public void setJdbcHelper(JdbcHelper jdbcHelper) {
		this.jdbcHelper = jdbcHelper;
	}

	protected Connection getConnection() throws Exception {
		return jdbcHelper.getConnection();
	}

	protected void closeConnection(Connection conn, Statement stmt) throws SQLException {
		jdbcHelper.closeConnection(conn, stmt);
	}

	protected void closeConnection(Connection conn, Statement stmt, ResultSet rs) throws SQLException {
		jdbcHelper.closeConnection(conn, stmt, rs);
	}
	
}
