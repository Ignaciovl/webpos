<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<?xml version="1.0" encoding="ISO-8859-1" ?>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
<link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/css/stylesheet.css" />
<title>Add client page</title>
</head>
<body>
	<img
		src="https://getonbrd-prod.s3.amazonaws.com/uploads/users/logo/1804/Logo.png"
		height="120" width="120" />
	<h1>Add client page</h1>
	<a href="${pageContext.request.contextPath}/index.html">Index</a>
	<br />
	<p>Here you can add a new client</p>
	<form:form method="POST" commandName="client"
		action="${pageContext.request.contextPath}/clients/add.html"
		onsubmit="return confirm('Add Client ${client.idNumber}?');">
		<table>
			<tbody>
				<tr>
					<td>Name:</td>
					<td><form:input path="name" /></td>
					<td><form:errors path="name" cssClass="error" /></td>
				</tr>
				<tr>
					<td>Id Number:</td>
					<td><form:input path="idNumber" /></td>
					<td><form:errors path="idNumber" cssClass="error" /></td>
				</tr>
				<tr>
					<td>Contact Number:</td>
					<td><form:input path="contactNumber" /></td>
					<td><form:errors path="contactNumber" cssClass="error" /></td>
				</tr>
				<tr>
					<td>Email:</td>
					<td><form:input path="email" /></td>
					<td><form:errors path="email" cssClass="error" /></td>
				</tr>
				<tr>
					<td>Address:</td>
					<td><form:input path="address" /></td>
					<td><form:errors path="address" cssClass="error" /></td>
				</tr>
				<tr>
					<td style="text-align: right"><input type="submit" value="Add" /></td>
					<td></td>
				</tr>
			</tbody>
		</table>
	</form:form>
	<p>
		<a href="${pageContext.request.contextPath}/clients/list.html">Return</a>
	</p>
</body>
</html>