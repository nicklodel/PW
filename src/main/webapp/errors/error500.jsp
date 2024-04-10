<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page isErrorPage="true"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>500 ERROR!!</title>
</head>
<body>
	<h3>Excepción de server cazada!!</h3>
	Si estás viendo esto, es porque hay un fallo de server!! Fallo:
	<br>
	<%= exception %>
</body>
</html>