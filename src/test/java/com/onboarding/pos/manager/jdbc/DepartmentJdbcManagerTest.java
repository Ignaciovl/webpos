package com.onboarding.pos.manager.jdbc;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.onboarding.pos.exception.entity.EntityCreateException;
import com.onboarding.pos.exception.entity.EntityDeleteException;
import com.onboarding.pos.exception.entity.EntityUpdateException;
import com.onboarding.pos.model.Department;
import com.onboarding.pos.util.JdbcHelper;

public class DepartmentJdbcManagerTest {

	private JdbcHelper jdbcHelperMock;
	private Connection connectionMock;
	private PreparedStatement preparedStatementMock;
	private ResultSet resultSetMock;
	
	private DepartmentJdbcManager departmentManager;

	@Before
	public void setUp() throws Exception {
		jdbcHelperMock = mock(JdbcHelper.class);
		connectionMock = mock(Connection.class);
		preparedStatementMock = mock(PreparedStatement.class);
		resultSetMock = mock(ResultSet.class);

		when(jdbcHelperMock.getConnection()).thenReturn(connectionMock);
		
		departmentManager = new DepartmentJdbcManager(jdbcHelperMock);
	}

	@After
	public void tearDown() throws Exception {
		jdbcHelperMock = null;
		connectionMock = null;
		preparedStatementMock = null;
		resultSetMock = null;
		departmentManager = null;
	}

	@Test
	public void testCountAllSuccessfully() throws Exception {
		when(connectionMock.prepareStatement(anyString())).thenReturn(preparedStatementMock);
		when(preparedStatementMock.executeQuery()).thenReturn(resultSetMock);
		
		long expectedCount = 10L;
		
		when(resultSetMock.next()).thenReturn(true);
		when(resultSetMock.getLong(1)).thenReturn(10L);
		
		long resultCount = departmentManager.countAll();

		verify(connectionMock).prepareStatement(anyString());
		verify(preparedStatementMock).executeQuery();
		assertEquals(expectedCount, resultCount);
	}

	@Test
	public void testCountAllNoResultsFound() throws Exception {
		when(connectionMock.prepareStatement(anyString())).thenReturn(preparedStatementMock);
		when(preparedStatementMock.executeQuery()).thenReturn(resultSetMock);
		
		long expectedCount = 0L;
		
		when(resultSetMock.next()).thenReturn(true);
		when(resultSetMock.getLong(1)).thenReturn(0L);
		
		long resultCount = departmentManager.countAll();

		verify(connectionMock).prepareStatement(anyString());
		verify(preparedStatementMock).executeQuery();
		assertEquals(expectedCount, resultCount);
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void testCountAllCatchingSqlException() throws Exception {
		when(connectionMock.prepareStatement(anyString())).thenThrow(SQLException.class);
		
		long expectedCount = 0L;
		
		long resultCount = departmentManager.countAll();

		verify(connectionMock).prepareStatement(anyString());
		verify(preparedStatementMock, never()).executeQuery();
		assertEquals(expectedCount, resultCount);
	}
	
	@Test
	public void testFindAllSuccessfully() throws Exception {
		when(connectionMock.prepareStatement(anyString())).thenReturn(preparedStatementMock);
		when(preparedStatementMock.executeQuery()).thenReturn(resultSetMock);
		
		Department department1 = new Department(1, "01", "Department 1");
		Department department2 = new Department(2, "02", "Department 2");
		Department department3 = new Department(3, "03", "Department 3");
		
		when(resultSetMock.next())
				.thenReturn(true)
				.thenReturn(true)
				.thenReturn(true)
				.thenReturn(false);
		
		when(resultSetMock.getInt("id"))
				.thenReturn(department1.getId())
				.thenReturn(department2.getId())
				.thenReturn(department3.getId());
		
		when(resultSetMock.getString("code"))
				.thenReturn(department1.getCode())
				.thenReturn(department2.getCode())
				.thenReturn(department3.getCode());
		
		when(resultSetMock.getString("name"))
				.thenReturn(department1.getName())
				.thenReturn(department2.getName())
				.thenReturn(department3.getName());

		List<Department> expectedDepartments = new ArrayList<Department>();
		expectedDepartments.add(department1);
		expectedDepartments.add(department2);
		expectedDepartments.add(department3);
		
		List<Department> resultDepartments = departmentManager.findAll();

		verify(connectionMock).prepareStatement(anyString());
		verify(preparedStatementMock).executeQuery();
		assertEquals(expectedDepartments, resultDepartments);
	}

	@Test
	public void testFindAllNoResultsFound() throws Exception {
		when(connectionMock.prepareStatement(anyString())).thenReturn(preparedStatementMock);
		when(preparedStatementMock.executeQuery()).thenReturn(resultSetMock);
		
		when(resultSetMock.next()).thenReturn(false);
		
		List<Department> expectedDepartments = new ArrayList<Department>();
		
		List<Department> resultDepartments = departmentManager.findAll();

		verify(connectionMock).prepareStatement(anyString());
		verify(preparedStatementMock).executeQuery();
		assertEquals(expectedDepartments, resultDepartments);
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void testFindAllCatchingSqlException() throws Exception {
		when(connectionMock.prepareStatement(anyString())).thenThrow(SQLException.class);
		
		List<Department> expectedDepartments = new ArrayList<Department>();
		
		List<Department> resultDepartments = departmentManager.findAll();

		verify(connectionMock).prepareStatement(anyString());
		verify(preparedStatementMock, never()).executeQuery();
		assertEquals(expectedDepartments, resultDepartments);
	}
	
	@Test
	public void testFindByIdSuccessfully() throws Exception {
		when(connectionMock.prepareStatement(anyString())).thenReturn(preparedStatementMock);
		when(preparedStatementMock.executeQuery()).thenReturn(resultSetMock);
		
		Department expectedDepartment = new Department(1, "01", "Department 1");
		
		when(resultSetMock.next()).thenReturn(true).thenReturn(false);
		when(resultSetMock.getInt("id")).thenReturn(expectedDepartment.getId());
		when(resultSetMock.getString("code")).thenReturn(expectedDepartment.getCode());
		when(resultSetMock.getString("name")).thenReturn(expectedDepartment.getName());
		
		Department resultDepartment = departmentManager.findById(1);

		verify(connectionMock).prepareStatement(anyString());
		verify(preparedStatementMock).setInt(1, 1);
		verify(preparedStatementMock).executeQuery();
		assertEquals(expectedDepartment, resultDepartment);
	}

	@Test
	public void testFindByIdNoResultsFound() throws Exception {
		when(connectionMock.prepareStatement(anyString())).thenReturn(preparedStatementMock);
		when(preparedStatementMock.executeQuery()).thenReturn(resultSetMock);
		
		Department expectedDepartment = null;
		
		when(resultSetMock.next()).thenReturn(false);
		
		Department resultDepartment = departmentManager.findById(1);

		verify(connectionMock).prepareStatement(anyString());
		verify(preparedStatementMock).setInt(1, 1);
		verify(preparedStatementMock).executeQuery();
		assertEquals(expectedDepartment, resultDepartment);
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void testFindByIdCatchingSqlException() throws Exception {
		when(connectionMock.prepareStatement(anyString())).thenThrow(SQLException.class);
		
		Department expectedDepartment = null;
		
		Department resultDepartment = departmentManager.findById(1);

		verify(connectionMock).prepareStatement(anyString());
		verify(preparedStatementMock, never()).setInt(1, 1);
		verify(preparedStatementMock, never()).executeQuery();
		assertEquals(expectedDepartment, resultDepartment);
	}
	
	@Test
	public void testFindByCodeSuccessfully() throws Exception {
		when(connectionMock.prepareStatement(anyString())).thenReturn(preparedStatementMock);
		when(preparedStatementMock.executeQuery()).thenReturn(resultSetMock);
		
		Department expectedDepartment = new Department(1, "01", "Department 1");
		
		when(resultSetMock.next()).thenReturn(true).thenReturn(false);
		when(resultSetMock.getInt("id")).thenReturn(expectedDepartment.getId());
		when(resultSetMock.getString("code")).thenReturn(expectedDepartment.getCode());
		when(resultSetMock.getString("name")).thenReturn(expectedDepartment.getName());
		
		Department resultDepartment = departmentManager.findByCode("01");

		verify(connectionMock).prepareStatement(anyString());
		verify(preparedStatementMock).setString(1, "01");
		verify(preparedStatementMock).executeQuery();
		assertEquals(expectedDepartment, resultDepartment);
	}

	@Test
	public void testFindByCodeNoResultsFound() throws Exception {
		when(connectionMock.prepareStatement(anyString())).thenReturn(preparedStatementMock);
		when(preparedStatementMock.executeQuery()).thenReturn(resultSetMock);
		
		Department expectedDepartment = null;
		
		when(resultSetMock.next()).thenReturn(false);
		
		Department resultDepartment = departmentManager.findByCode("01");

		verify(connectionMock).prepareStatement(anyString());
		verify(preparedStatementMock).setString(1, "01");
		verify(preparedStatementMock).executeQuery();
		assertEquals(expectedDepartment, resultDepartment);
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void testFindByCodeCatchingSqlException() throws Exception {
		when(connectionMock.prepareStatement(anyString())).thenThrow(SQLException.class);
		
		Department expectedDepartment = null;
		
		Department resultDepartment = departmentManager.findByCode("01");

		verify(connectionMock).prepareStatement(anyString());
		verify(preparedStatementMock, never()).setString(1, "01");
		verify(preparedStatementMock, never()).executeQuery();
		assertEquals(expectedDepartment, resultDepartment);
	}
	
	@Test
	public void testFindByNameSuccessfully() throws Exception {
		when(connectionMock.prepareStatement(anyString())).thenReturn(preparedStatementMock);
		when(preparedStatementMock.executeQuery()).thenReturn(resultSetMock);
		
		Department department1 = new Department(1, "01", "Men Clothing");
		Department department2 = new Department(2, "02", "Women Clothing");
		
		when(resultSetMock.next())
				.thenReturn(true)
				.thenReturn(true)
				.thenReturn(false);
		
		when(resultSetMock.getInt("id"))
				.thenReturn(department1.getId())
				.thenReturn(department2.getId());
		
		when(resultSetMock.getString("code"))
				.thenReturn(department1.getCode())
				.thenReturn(department2.getCode());
		
		when(resultSetMock.getString("name"))
				.thenReturn(department1.getName())
				.thenReturn(department2.getName());
		
		List<Department> expectedDepartments = new ArrayList<Department>();
		expectedDepartments.add(department1);
		expectedDepartments.add(department2);
		
		List<Department> resultDepartments = departmentManager.findByName("clothing");

		verify(connectionMock).prepareStatement(anyString());
		verify(preparedStatementMock).setString(1, "%clothing%");
		verify(preparedStatementMock).executeQuery();
		assertEquals(expectedDepartments, resultDepartments);
	}

	@Test
	public void testFindByNameNoResultsFound() throws Exception {
		when(connectionMock.prepareStatement(anyString())).thenReturn(preparedStatementMock);
		when(preparedStatementMock.executeQuery()).thenReturn(resultSetMock);
		
		when(resultSetMock.next()).thenReturn(false);
		
		List<Department> expectedDepartments = new ArrayList<Department>();
		
		List<Department> resultDepartments = departmentManager.findByName("clothing");

		verify(connectionMock).prepareStatement(anyString());
		verify(preparedStatementMock).setString(1, "%clothing%");
		verify(preparedStatementMock).executeQuery();
		assertEquals(expectedDepartments, resultDepartments);
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void testFindByNameCatchingSqlException() throws Exception {
		when(connectionMock.prepareStatement(anyString())).thenThrow(SQLException.class);
		
		List<Department> expectedDepartments = new ArrayList<Department>();
		
		List<Department> resultDepartments = departmentManager.findByName("clothing");

		verify(connectionMock).prepareStatement(anyString());
		verify(preparedStatementMock, never()).setString(1, "%clothing%");
		verify(preparedStatementMock, never()).executeQuery();
		assertEquals(expectedDepartments, resultDepartments);
	}

	@Test
	public void testCreateSuccessfully() throws Exception {
		when(connectionMock.prepareStatement(anyString(), eq(Statement.RETURN_GENERATED_KEYS))).thenReturn(preparedStatementMock);
		
		when(preparedStatementMock.executeUpdate()).thenReturn(1);
		when(preparedStatementMock.getGeneratedKeys()).thenReturn(resultSetMock);
		
		when(resultSetMock.next()).thenReturn(true);
		when(resultSetMock.getInt(1)).thenReturn(1);
		
		Department expectedDepartment = new Department(1, "01", "Department 1");
		
		Department resultDepartment = departmentManager.create(new Department("01", "Department 1"));
		
		verify(connectionMock).setAutoCommit(false);
		verify(preparedStatementMock).executeUpdate();
		verify(preparedStatementMock).getGeneratedKeys();
		verify(connectionMock).commit();
		assertEquals(expectedDepartment, resultDepartment);
	}
	
	@SuppressWarnings({"unchecked", "unused"})
	@Test
	public void testCreateCatchingSqlExceptionExpectingEntityCreateException() throws Exception {
		when(connectionMock.prepareStatement(anyString(), eq(Statement.RETURN_GENERATED_KEYS))).thenReturn(preparedStatementMock);
		
		when(preparedStatementMock.executeUpdate()).thenThrow(SQLException.class);
		
		try {
			Department resultDepartment = departmentManager.create(new Department("01", "Department 1"));
			fail("An Exception should been thrown");
		} catch(EntityCreateException ex) {
			verify(connectionMock).rollback();
			verify(jdbcHelperMock).closeConnection(connectionMock, preparedStatementMock);
		}
	}
	
	@SuppressWarnings("unused")
	@Test
	public void testCreateNotInsertedExpectingEntityCreateException() throws Exception {
		when(connectionMock.prepareStatement(anyString(), eq(Statement.RETURN_GENERATED_KEYS))).thenReturn(preparedStatementMock);
		
		when(preparedStatementMock.executeUpdate()).thenReturn(0);
		
		try {
			Department resultDepartment = departmentManager.create(new Department("01", "Department 1"));
			fail("An Exception should been thrown");
		} catch(EntityCreateException ex) {
			verify(connectionMock).rollback();
			verify(jdbcHelperMock).closeConnection(connectionMock, preparedStatementMock);
		}
	}
	
	@SuppressWarnings("unused")
	@Test
	public void testCreateNoReturnedKeysExpectingEntityCreateException() throws Exception {
		when(connectionMock.prepareStatement(anyString(), eq(Statement.RETURN_GENERATED_KEYS))).thenReturn(preparedStatementMock);
		
		when(preparedStatementMock.executeUpdate()).thenReturn(0);
		when(preparedStatementMock.getGeneratedKeys()).thenReturn(resultSetMock);
		
		when(resultSetMock.next()).thenReturn(false);
		
		try {
			Department resultDepartment = departmentManager.create(new Department("01", "Department 1"));
			fail("An Exception should been thrown");
		} catch(EntityCreateException ex) {
			verify(connectionMock).rollback();
			verify(jdbcHelperMock).closeConnection(connectionMock, preparedStatementMock);
		}
	}
	
	@Test
	public void testUpdateSuccessfully() throws Exception {
		when(connectionMock.prepareStatement(anyString())).thenReturn(preparedStatementMock);
		
		when(preparedStatementMock.executeUpdate()).thenReturn(1);
		
		when(resultSetMock.next()).thenReturn(true);
		
		Department departmentToUpdate = new Department(1, "01", "Department 1 Updated");
		
		Department resultDepartment = departmentManager.update(departmentToUpdate);
		
		verify(connectionMock).setAutoCommit(false);
		verify(preparedStatementMock).executeUpdate();
		verify(connectionMock).commit();
		assertEquals(departmentToUpdate, resultDepartment);
	}
	
	@SuppressWarnings({"unchecked", "unused"})
	@Test
	public void testUpdateCatchingSqlExceptionExpectingEntityUpdateException() throws Exception {
		when(connectionMock.prepareStatement(anyString())).thenReturn(preparedStatementMock);
		
		when(preparedStatementMock.executeUpdate()).thenThrow(SQLException.class);
		
		Department departmentToUpdate = new Department(1, "01", "Department 1 Updated");
		
		try {
			Department resultDepartment = departmentManager.update(departmentToUpdate);
			fail("An Exception should been thrown");
		} catch(EntityUpdateException ex) {
			verify(connectionMock).rollback();
			verify(jdbcHelperMock).closeConnection(connectionMock, preparedStatementMock);
		}
	}
	
	@SuppressWarnings("unused")
	@Test
	public void testUpdateNotInsertedExpectingEntityUpdateException() throws Exception {
		when(connectionMock.prepareStatement(anyString())).thenReturn(preparedStatementMock);
		
		when(preparedStatementMock.executeUpdate()).thenReturn(0);
		
		Department departmentToUpdate = new Department(1, "01", "Department 1 Updated");
		
		try {
			Department resultDepartment = departmentManager.update(departmentToUpdate);
			fail("An Exception should been thrown");
		} catch(EntityUpdateException ex) {
			verify(connectionMock).rollback();
			verify(jdbcHelperMock).closeConnection(connectionMock, preparedStatementMock);
		}
	}
	
	@Test
	public void testDeleteSuccessfully() throws Exception {
		when(connectionMock.prepareStatement(anyString())).thenReturn(preparedStatementMock);
		
		when(preparedStatementMock.executeUpdate()).thenReturn(1);
		
		when(resultSetMock.next()).thenReturn(true);
		
		Department departmentToDelete = new Department(1, "01", "Department 1 Deleted");
		
		departmentManager.delete(departmentToDelete);
		
		verify(connectionMock).setAutoCommit(false);
		verify(preparedStatementMock).executeUpdate();
		verify(connectionMock).commit();
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void testDeleteCatchingSqlExceptionExpectingEntityDeleteException() throws Exception {
		when(connectionMock.prepareStatement(anyString())).thenReturn(preparedStatementMock);
		
		when(preparedStatementMock.executeUpdate()).thenThrow(SQLException.class);
		
		Department departmentToDelete = new Department(1, "01", "Department 1 Deleted");
		
		try {
			departmentManager.delete(departmentToDelete);
			fail("An Exception should been thrown");
		} catch(EntityDeleteException ex) {
			verify(connectionMock).rollback();
			verify(jdbcHelperMock).closeConnection(connectionMock, preparedStatementMock);
		}
	}
	
	@Test
	public void testDeleteNotInsertedExpectingEntityDeleteException() throws Exception {
		when(connectionMock.prepareStatement(anyString())).thenReturn(preparedStatementMock);
		
		when(preparedStatementMock.executeUpdate()).thenReturn(0);
		
		Department departmentToDelete = new Department(1, "01", "Department 1 Deleted");
		
		try {
			departmentManager.delete(departmentToDelete);
			fail("An Exception should been thrown");
		} catch(EntityDeleteException ex) {
			verify(connectionMock).rollback();
			verify(jdbcHelperMock).closeConnection(connectionMock, preparedStatementMock);
		}
	}
	
}