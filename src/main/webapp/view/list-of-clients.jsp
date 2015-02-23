<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<?xml version="1.0" encoding="ISO-8859-1" ?>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
<title>List of clients</title>
</head>
<body>
	<h1>List of clients</h1>
	<a href="${pageContext.request.contextPath}/index.html">Index</a>
	<br/>
	${message}
	<br />
	<p>Here you can see the list of clients, add a new client, edit or delete an existing one.</p>
	<table border="1px" cellpadding="0" cellspacing="0">
		<thead>
			<tr>
				<th width="10%">Id</th>
				<th width="25%">Name</th>
				<th width="15%">Id Number</th>
				<th width="15%">Contact Number</th>
				<th width="20%">Email</th>
				<th width="20%">Address</th>
				<th width="10%">Edit</th>
				<th width="10%">Delete</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="client" items="${clients}">
				<tr>
					<td>${client.id}</td>
					<td>${client.name}</td>
					<td>${client.idNumber}</td>
					<td>${client.contactNumber}</td>
					<td>${client.email}</td>
					<td>${client.address}</td>
					<td><a
						href="${pageContext.request.contextPath}/clients/edit/${client.id}.html">Edit</a></td>
					<td><a
						href="${pageContext.request.contextPath}/clients/delete/${client.id}.html">Delete</a></td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	<p>
		<a href="${pageContext.request.contextPath}/clients/add.html">Add
			new client</a>
	</p>
</body>
</html>