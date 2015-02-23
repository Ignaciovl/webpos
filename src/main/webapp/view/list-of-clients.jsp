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
<body style = "background-color: azure; font-family: Trebuchet MS">
	<h1 style = "color: cornflowerblue">List of clients</h1>
	<a style = "color: dodgerblue" href="${pageContext.request.contextPath}/index.html">Index</a>
	<br/>
	<p style = "color: darkslateblue"> ${message} </p>
	<br />
	<p style = "color: darkslateblue">Here you can see the list of clients, add a new client, edit or delete an existing one.</p>
	<table border="1px" cellpadding="0" cellspacing="0">
		<thead>
			<tr>
				<th style = "color: darkslateblue" width="10%">Id</th>
				<th style = "color: darkslateblue" width="25%">Name</th>
				<th style = "color: darkslateblue" width="15%">Id Number</th>
				<th style = "color: darkslateblue" width="15%">Contact Number</th>
				<th style = "color: darkslateblue" width="20%">Email</th>
				<th style = "color: darkslateblue" width="20%">Address</th>
				<th style = "color: darkslateblue" width="10%">Edit</th>
				<th style = "color: darkslateblue" width="10%">Delete</th>
			</tr>
		</thead>
		<tbody style = "color: darkslateblue">
			<c:forEach var="client" items="${clients}">
				<tr>
					<td >${client.id}</td>
					<td>${client.name}</td>
					<td>${client.idNumber}</td>
					<td>${client.contactNumber}</td>
					<td>${client.email}</td>
					<td>${client.address}</td>
					<td><a style = "color: dodgerblue"
						href="${pageContext.request.contextPath}/clients/edit/${client.id}.html">Edit</a></td>
					<td><a style = "color: dodgerblue"
						href="${pageContext.request.contextPath}/clients/delete/${client.id}.html">Delete</a></td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	<p>
		<a style = "color: dodgerblue" href="${pageContext.request.contextPath}/clients/add.html">Add
			new client</a>
	</p>
</body>
</html>