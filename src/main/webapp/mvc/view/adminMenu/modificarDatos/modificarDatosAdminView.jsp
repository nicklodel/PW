<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<jsp:useBean id="customerBean" scope="session"
	class="display.CustomerBean"></jsp:useBean>
<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet"
	href="/showit/styles/mainStyle.css">
<link rel="stylesheet"
	href="/showit/styles/subMenuStyle.css">
<link rel="stylesheet"
	href="/showit/styles/loginStyle.css">
<meta charset="UTF-8">
<title>Modificar datos</title>
</head>
<body>

	<%
	// Si el usuario no ha sido validado o no es un usuario administrador, le mando al menú de login para que se logee correctamente
	// Así evitamos que se pueda introducir la ruta manualmente en el navegador y que se pueda acceder

	if (customerBean.getValidado() == false || customerBean.getEsAdmin() != 1) {
	%>
	<jsp:forward page="../../view/login/loginView.jsp">
		<jsp:param value="Haga inicio previamente" name="message" />
	</jsp:forward>
	<%
	}
	%>

	<%
	String messageNextPage = request.getParameter("message");
	if (messageNextPage == null)
		messageNextPage = "";
	String nextPage = "/showit/mvc/controller/modificarDatos/modificarDatosAdminController.jsp";
	%>

	Bienvenido al menu de modificar de datos
	<br></br>

	<form method="post"
		action="/showit/mvc/controller/modificarDatos/modificarDatosAdminController.jsp">

		<!--<form method="post" action="../../controller/modificarDatos/modificarDatosController.jsp"> <!-- Pido datos al usuario. LAS PROPIEDADES SIEMPRE VAN EN EL LADO IZQUIERDO -->
		<fieldset>
		<h2>Modificación de datos</h2>
		<br>
		<br>
		<br>
			<!-- Cajita para agrupar el formulario -->
			<!--Etiquetas asociadas al boton. placeholder me coloca un texto dentro de la caja-->
			<input type="text" class="un" name="nombreApellidos"
				placeholder="Nuevo nombre y apellidos" required> <br> <br>
			<input type="submit" class="submit" name="login" value="Actualizar">
			<!-- Boton para mandar formulario, con value elijo el texto del boton -->
			<br> <br>
		</fieldset>
	</form>
	<%=messageNextPage%><br />
	<!--</form> -->

	<%@ include file="../../../../headers/volverAdmin.html"%>

</body>
</html>