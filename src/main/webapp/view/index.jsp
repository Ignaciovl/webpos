<?xml version="1.0" encoding="ISO-8859-1" ?>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
<link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/css/stylesheet.css" />
<title>Index</title>
</head>
<body>
	<img
		src="https://getonbrd-prod.s3.amazonaws.com/uploads/users/logo/1804/Logo.png"
		height="120" width="120" />
	<h1>Index</h1>
	<p>${message}</p>
	<br />
	<ul>
		<li><a style="font-size: 18px"
			href="${pageContext.request.contextPath}/clients/list.html">Clients
				Module</a></li>
	</ul>
</body>
</html>