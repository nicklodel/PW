<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<jsp:useBean id="customerBean" scope="session"
	class="display.CustomerBean"></jsp:useBean>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Iniciar sesión</title>
</head>
<link rel="stylesheet" href="../../../styles/mainStyle.css">
<link rel="stylesheet" href="../../../styles/subMenuStyle.css">
<link rel="stylesheet" href="../../../styles/loginStyle.css">
<body>

	<%
	// Si el usuario se ha logado previamente le redirecciono a su vista correspondiente

	if (customerBean.getValidado() == true) {

		// Es un usuario administrador
		if (customerBean.getEsAdmin() == 1) {
	%>
	<jsp:forward page="../../view/adminMenu/adminMenuView.jsp">
		<jsp:param value="CORRECTO" name="message" />
	</jsp:forward>
	<%
	}

	else {
	%>
	<jsp:forward page="../../view/userMenu/userMenuView.jsp">
		<jsp:param value="CORRECTO" name="message" />
	</jsp:forward>
	<%
	}

	}

	String nextPage = "../../controller/login/loginController.jsp";
	String messageNextPage = request.getParameter("message");
	if (messageNextPage == null)
	messageNextPage = "";
	%>

	<br>
	<!-- Formulario para iniciar sesión con un usuario creado en el sistema -->	
	<form class="form1" method="post"
		action="../../controller/login/loginController.jsp">
		<!-- Pido datos al usuario. LAS PROPIEDADES SIEMPRE VAN EN EL LADO IZQUIERDO -->
		<fieldset>
			<h2>Iniciar Sesión</h2>
			<br> <br> <br>
			<!-- Cajita para agrupar el formulario -->

			<!-- Le coloca el texto a fieldset -->

			<!--Etiquetas asociadas al boton. placeholder me coloca un texto dentro de la caja-->
			<input class="un" type="text" name="nickname"
				placeholder="Nombre de usuario" required> <br> <br>
			<input class="pass" type="password" name="password"
				placeholder="Contraseña" required> <br> <br> <input
				class="submit" type="submit" name="login" value="Iniciar sesión">
			<!-- Boton para mandar formulario, con value elijo el texto del boton -->
			<br> <br>
		</fieldset>
	</form>
	<%=messageNextPage%><br />
	<br />
	<%@ include file="../../../headers/volver.html"%>
</body>
</html>