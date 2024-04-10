<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page
	import="business.gestorDeEspectaculos.GestorDeEspectaculos, business.espectaculo.*,business.espectador.Espectador, java.util.Date, java.util.ArrayList, business.log.Log, business.sesion.Sesion, java.net.URLEncoder"%>
<jsp:useBean id="customerBean" scope="session"
	class="display.CustomerBean"></jsp:useBean>
<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet" href="/showit/styles/mainStyle.css">
<link rel="stylesheet" href="/showit/styles/subMenuStyle.css">
<link rel="stylesheet" href="/showit/styles/loginStyle.css">

<meta charset="UTF-8">
<title>Modificar Sesión</title>
</head>
<body>
	<form method="post" action="/showit/servlet/ModificarSesionServlet">
		<!-- Pido datos al usuario. LAS PROPIEDADES SIEMPRE VAN EN EL LADO IZQUIERDO -->
		<!-- Cajita para agrupar el formulario -->
		<fieldset>
			<h2>Modificar una sesión</h2>
			<br> <br> <br	
			<%
			int id = Integer.parseInt(request.getParameter("id"));
			GestorDeEspectaculos gestor = null;
			String url = application.getInitParameter("BDdriver");
			String user = application.getInitParameter("BDuser");
			String passwd = application.getInitParameter("BDpassword");
			String file = application.getInitParameter("RutaConsultas");
			String message = "";
			boolean error = false;
			java.io.InputStream inputFile = application.getResourceAsStream(file);
			gestor = new GestorDeEspectaculos(url, user, passwd, inputFile);

			Sesion sesion = null;
			try {
				sesion = gestor.getSesion(id);
			} catch (Exception e) {
				message = "Error recogiendo la sesión";
				error = true;
			}
			if (error == true) {
			%>
			<h3>Error recogiendo la sesión</h3>
			><%
			}
			%>
			<form method="post" action="/showit/servlet/ModificarSesionServlet">
				<!-- Pido datos al usuario. LAS PROPIEDADES SIEMPRE VAN EN EL LADO IZQUIERDO -->

				<!-- Le coloca el texto a fieldset -->
				<!--Etiquetas asociadas al boton. placeholder me coloca un texto dentro de la caja-->
				<input class="un" type="number" name="localidades"
					value=<%=sesion.getLocalidades()%> required> <br> <br>
				<br> <br> <label>Fecha</label> <input type="date"
					class="en" name="fecha" value=<%=sesion.getFecha().toString()%>
					id="fecha" min="" required> <br> <br> <label>Hora</label>
				<input type="time" class="en" name="tiempo" id="hora" required>
				<br> <br> <input class="submit" type="submit"
					name="create" value="Modificar Sesion">
				<!-- Boton para mandar formulario, con value elijo el texto del boton -->
				<input type="hidden" value=<%=sesion.getId()%> name="idSesion">
				<br> <br>
		</fieldset>
	</form>
	<br>
	<p>${param.message}</p>
	<br>
	<br>
	<div id="header">
		<ul class="nav">
			<li><a
				href=<%="/showit/mvc/view/adminMenu/mostrarDatosEspectaculo/imprimirDatosEspectaculoView.jsp?titulo="
		+ URLEncoder.encode(sesion.getEspectaculo(), "UTF-8")%>>Volver</a></li>
		</ul>
	</div>
	<script src="/showit/scripts/checkMinDate.js"></script>
</body>
</html>