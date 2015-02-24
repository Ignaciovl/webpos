<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<?xml version="1.0" encoding="ISO-8859-1" ?>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
<link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/css/stylesheet.css" />
<link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/css/table.css" />
<title>List of clients</title>
</head>
<body>
	<img
		src="https://getonbrd-prod.s3.amazonaws.com/uploads/users/logo/1804/Logo.png"
		height="120" width="120" />
	<h1>List of clients</h1>
	<a href="${pageContext.request.contextPath}/index.html">Index</a>
	<br />
	<p>${message}</p>
	<br />
	<p>Here you can see the list of clients, add a new client, edit or
		delete an existing one.</p>
	<table>
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
			<c:forEach var="client" items="${clients}" varStatus="loopStatus">
				<tr class="${loopStatus.index % 2 == 0 ? 'even' : 'odd'}">
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