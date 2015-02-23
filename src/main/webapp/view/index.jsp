<?xml version="1.0" encoding="ISO-8859-1" ?>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
<title>Index</title>
</head>
<body style="background-color: azure; font-family: Trebuchet MS">
	<h1 style="color: cornflowerblue">Index</h1>
	<p style = "color: darkslateblue">${message} </p>
	<br />
	<ul>
		<li><a style="font-size: 18px; color: dodgerblue"
			href="${pageContext.request.contextPath}/clients/list.html">Clients
				Module</a></li>
	</ul>
</body>
</html>