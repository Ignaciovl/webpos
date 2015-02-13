package com.onboarding.pos.manager.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Level;

import com.onboarding.pos.exception.entity.EntityCreateException;
import com.onboarding.pos.exception.entity.EntityDeleteException;
import com.onboarding.pos.exception.entity.EntityException;
import com.onboarding.pos.exception.entity.EntityNotFoundException;
import com.onboarding.pos.exception.entity.EntityUpdateException;
import com.onboarding.pos.manager.DepartmentManager;
import com.onboarding.pos.model.Department;
import com.onboarding.pos.util.JdbcHelper;

public class DepartmentJdbcManager extends AbstractJdbcManager<Department> implements DepartmentManager {

	public DepartmentJdbcManager(JdbcHelper jdbcHelper) {
		super(jdbcHelper);
	}

	@Override
	public long countAll() {
		long count = 0;
		
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			conn = getConnection();
			stmt = conn.prepareStatement("SELECT COUNT(id) FROM department");
			rs = stmt.executeQuery();
			if (rs.next()) {
				count = rs.getLong(1);
			}
		} catch (Exception e) {
			logger.log(Level.ERROR, e.getMessage(), e.getCause());
			count = 0;
		} finally {
			try {
				closeConnection(conn, stmt, rs);
			} catch (SQLException ex) {
				logger.log(Level.ERROR, ex.getMessage(), ex.getCause());
			}
		}
		
		return count;
	}

	@Override
	public List<Department> findAll() {
		List<Department> departments = new ArrayList<Department>();
		
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			conn = getConnection();
			stmt = conn.prepareStatement("SELECT id, code, name FROM department ORDER BY id");
			rs = stmt.executeQuery();
			while (rs.next()) {
				Department department = new Department();
				department.setId(rs.getInt("id"));
				department.setCode(rs.getString("code"));
				department.setName(rs.getString("name"));
				departments.add(department);
			}
		} catch (Exception e) {
			logger.log(Level.ERROR, e.getMessage(), e.getCause());
			departments = new ArrayList<Department>();
		} finally {
			try {
				closeConnection(conn, stmt, rs);
			} catch (SQLException ex) {
				logger.log(Level.ERROR, ex.getMessage(), ex.getCause());
			}
		}

		return departments;
	}
	
	@Override
	public Department findById(int id) {
		Department department = null;
		
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			conn = getConnection();
			stmt = conn.prepareStatement("SELECT id, code, name FROM department WHERE id = ? ORDER BY id");
			stmt.setInt(1, id);
			rs = stmt.executeQuery();
			if (rs.next()) {
				department = new Department();
				department.setId(rs.getInt("id"));
				department.setCode(rs.getString("code"));
				department.setName(rs.getString("name"));
			}
		} catch (Exception e) {
			logger.log(Level.ERROR, e.getMessage(), e.getCause());
			department = null;
		} finally {
			try {
				closeConnection(conn, stmt, rs);
			} catch (SQLException ex) {
				logger.log(Level.ERROR, ex.getMessage(), ex.getCause());
			}
		}

		return department;
	}

	@Override
	public Department findByCode(String code) {
		Department department = null;
		
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			conn = getConnection();
			stmt = conn.prepareStatement("SELECT id, code, name FROM department WHERE code = ? ORDER BY id");
			stmt.setString(1, code);
			rs = stmt.executeQuery();
			if (rs.next()) {
				department = new Department();
				department.setId(rs.getInt("id"));
				department.setCode(rs.getString("code"));
				department.setName(rs.getString("name"));
			}
		} catch (Exception e) {
			logger.log(Level.ERROR, e.getMessage(), e.getCause());
			department = null;
		} finally {
			try {
				closeConnection(conn, stmt, rs);
			} catch (SQLException ex) {
				logger.log(Level.ERROR, ex.getMessage(), ex.getCause());
			}
		}

		return department;
	}

	@Override
	public List<Department> findByName(String name) {
		List<Department> departments = new ArrayList<Department>();
		
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			conn = getConnection();
			stmt = conn
					.prepareStatement("SELECT id, code, name FROM department WHERE name LIKE ? ORDER BY id");
			stmt.setString(1, "%" + name + "%");
			rs = stmt.executeQuery();
			while (rs.next()) {
				Department department = new Department();
				department.setId(rs.getInt("id"));
				department.setCode(rs.getString("code"));
				department.setName(rs.getString("name"));
				departments.add(department);
			}
		} catch (Exception e) {
			logger.log(Level.ERROR, e.getMessage(), e.getCause());
			departments = new ArrayList<Department>();
		} finally {
			try {
				closeConnection(conn, stmt, rs);
			} catch (SQLException ex) {
				logger.log(Level.ERROR, ex.getMessage(), ex.getCause());
			}
		}

		return departments;
	}

	@Override
	public Department create(Department department) throws EntityException {
		Connection conn = null;
		PreparedStatement stmt = null;
		try {
			conn = getConnection();
			conn.setAutoCommit(false);
			stmt = conn.prepareStatement("INSERT INTO department (code, name) VALUES (?, ?)", Statement.RETURN_GENERATED_KEYS);
			stmt.setString(1, department.getCode());
			stmt.setString(2, department.getName());
			int inserted = stmt.executeUpdate();
			if (inserted < 1)
				throw new EntityCreateException(Department.class, null);
			
			ResultSet generatedKeys = stmt.getGeneratedKeys();
			if (generatedKeys.next()) {
				department.setId(generatedKeys.getInt(1));
			} else {
				throw new EntityCreateException(Department.class, null);
			}
			conn.commit();
		} catch (Exception e) {
			logger.log(Level.ERROR, e.getMessage(), e.getCause());
			if (conn != null) {
				try {
					conn.rollback();
				} catch (SQLException ex) {
					logger.log(Level.ERROR, ex.getMessage(), ex.getCause());
				}
			}
			throw new EntityCreateException(Department.class, e);
		} finally {
			try {
				closeConnection(conn, stmt);
			} catch (SQLException ex) {
				logger.log(Level.ERROR, ex.getMessage(), ex.getCause());
			}
		}
		
		return department;
	}

	@Override
	public Department update(Department department) throws EntityException {
		Connection conn = null;
		PreparedStatement stmt = null;
		try {
			conn = getConnection();
			conn.setAutoCommit(false);
			stmt = conn.prepareStatement("UPDATE department SET name = ? WHERE id = ?");
			stmt.setString(1, department.getName());
			stmt.setInt(2, department.getId());
			int updated = stmt.executeUpdate();
			if (updated < 1)
				throw new EntityNotFoundException(Department.class);
			conn.commit();
		} catch (Exception e) {
			logger.log(Level.ERROR, e.getMessage(), e.getCause());
			if (conn != null) {
				try {
					conn.rollback();
				} catch (SQLException ex) {
					logger.log(Level.ERROR, ex.getMessage(), ex.getCause());
				}
			}
			throw new EntityUpdateException(Department.class, e);
		} finally {
			try {
				closeConnection(conn, stmt);
			} catch (SQLException ex) {
				logger.log(Level.ERROR, ex.getMessage(), ex.getCause());
			}
		}
		
		return department;
	}

	@Override
	public void delete(Department department) throws EntityException {
		Connection conn = null;
		PreparedStatement stmt = null;
		try {
			conn = getConnection();
			conn.setAutoCommit(false);
			stmt = conn.prepareStatement("DELETE FROM department WHERE id = ?");
			stmt.setInt(1, department.getId());
			int deleted = stmt.executeUpdate();
			if (deleted < 1)
				throw new EntityNotFoundException(Department.class);
			conn.commit();
		} catch (Exception e) {
			logger.log(Level.ERROR, e.getMessage(), e.getCause());
			if (conn != null) {
				try {
					conn.rollback();
				} catch (SQLException ex) {
					logger.log(Level.ERROR, ex.getMessage(), ex.getCause());
				}
			}
			throw new EntityDeleteException(Department.class, e);
		} finally {
			try {
				closeConnection(conn, stmt);
			} catch (SQLException ex) {
				logger.log(Level.ERROR, ex.getMessage(), ex.getCause());
			}
		}
	}
	
}
