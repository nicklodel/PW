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
<title>Crear Espectáculo Puntal</title>
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
	<br>
	<!-- Pongo la url que indiqué en el mapping del fichero web.xml -->
	<form method="post" action="/showit/servlet/CreateEspectaculoPuntualServlet">
		<!-- Pido datos al usuario. LAS PROPIEDADES SIEMPRE VAN EN EL LADO IZQUIERDO -->
		<fieldset>
		<h2>Creación de espectáculo puntual</h2>
		<br>
		<br>
		<br>
			<!-- Cajita para agrupar el formulario -->
			
			<!-- Le coloca el texto a fieldset -->
			<!--Etiquetas asociadas al boton. placeholder me coloca un texto dentro de la caja-->
			<input class="un" type="text" name="titulo" placeholder="Título" required>
			<br> <br> <input class="un" type="text"
				name="categoria" placeholder="Categoría" required> <br> <br>
			<input class="un" type="number" name="localidades" placeholder="Localidades" required> <br> <br>
			<input class="un" type="text" name="descripcion" placeholder="Descripción" required>
			<br> <br> <label>Fecha</label> <input type="date" class="en"
				name="fecha" id="fecha" min="" required> <br> <br> 
				<label>Hora</label> <input type="time" class="en"
				name="tiempo" id="hora" min="" required> <br> <br><input class="submit"
				type="submit" name="create" value="Crear Espectáculo">
			<!-- Boton para mandar formulario, con value elijo el texto del boton -->
			<br> <br>
		</fieldset>
	</form>
	<br>
	<p>${param.message}</p>
	<br>
	<br>
	<%@ include file="../../../../headers/volverAdmin.html"%>
	<script src="/showit/scripts/checkMinDate.js"></script>
	<br>
	<br>
</body>
</html>