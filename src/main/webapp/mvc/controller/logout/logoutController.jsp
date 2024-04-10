<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<jsp:useBean id="customerBean" scope="session"
	class="display.CustomerBean"></jsp:useBean>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Desconectar</title>
</head>
<body>
	<%
	//Se comprueba primero que el usuario no está logado
	if (customerBean.getNick().equals("") || customerBean == null) {

	} else {
		// Para desconectar al usuario, se accede al método removeAttribute() de la sesión
		request.getSession().removeAttribute("customerBean");
	}

	response.sendRedirect("/showit/index.jsp");
	%>
</body>
</html>