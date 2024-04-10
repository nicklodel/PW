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
<link rel="stylesheet" href="/showit/styles/showStyle.css">

<meta charset="UTF-8">
<title>Datos del Espectáculo:</title>
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

	GestorDeEspectaculos gestor = null;

	String url = application.getInitParameter("BDdriver");
	String user = application.getInitParameter("BDuser");
	String passwd = application.getInitParameter("BDpassword");
	String file = application.getInitParameter("RutaConsultas");

	java.io.InputStream inputFile = application.getResourceAsStream(file);

	gestor = new GestorDeEspectaculos(url, user, passwd, inputFile);

	Espectaculo espectaculo = null;

	String messageError = "";

	boolean flag = false;

	// Mensaje si no existe el espectáculo en el sistema
	try {
	if (request.getParameter("titulo") == null || request.getParameter("titulo") == "") {
		flag = true;
		messageError = "No existe el espectáculo en el sistema";
	}

	if (!gestor.existeEspectaculo(request.getParameter("titulo"))) {
	%><h1>No existe el espectáculo</h1>
	<%
	return;
	}
	String titulo = request.getParameter("titulo");
	%><h1><%=titulo%></h1>
	<br>
	<h1>
		Tipo de Espectáculo:
		<%=gestor.getTipoEspectaculo(titulo)%></h1>
	<br>
	<%
	espectaculo = gestor.buscarEspectaculoTitulo(titulo);

	} catch (Exception e) {
	flag = true;
	messageError = e.toString();
	}

	if (flag) {
	%><h1><%=messageError%></h1>
	<%
	} else {
	%>

<body>
	<h2>
		Título espectáculo:
		<%=espectaculo.getTitulo()%>
		<br> Categoría:
		<%=espectaculo.getCategorias()%></h2>

	<table>
		<tr>
			<th>Fecha</th>
			<th>Localidades</th>
			<th>Vendidas</th>
		</tr>
		<%
		ArrayList<Sesion> sesiones = espectaculo.getSesiones();
		for (int i = 0; i < espectaculo.getSesiones().size(); ++i) {
		%>
		<tr>

			<td><%=sesiones.get(i).getFecha()%></td>
			<td><%=sesiones.get(i).getLocalidades()%></td>
			<td><%=sesiones.get(i).getVendidas()%></td>


		</tr>
		<%
		}
		%>
	</table>
	<br>
	<br>
	<!-- Formulario para modificar una sesión -->
	<form method="get" action="/showit/servlet/ModificarSesionServlet">
		<select class="en" name="idSesion" required>
			<option value="" disabled selected>Seleccione una sesión</option>
			<%
			sesiones = espectaculo.getSesiones();
			for (int i = 0; i < sesiones.size(); ++i) {
			%>
			<option value=<%=sesiones.get(i).getId()%>><%=sesiones.get(i).getFecha()%></option>
			<%
			}
			%>
		</select><input class="submit" type="submit" value="Modificar sesión">
	</form>
	<br>
	<%
	// Solamente se podrán borrar espectáculos si son múltiples o de temporada

	if (gestor.getTipoEspectaculo(request.getParameter("titulo")).equals("Múltiple")
			|| gestor.getTipoEspectaculo(request.getParameter("titulo")).equals("Temporada")) {
	%>
	<!-- Formulario para borrar una sesión -->
	<form method="post" action="/showit/servlet/BorrarSesionServlet">
		<select class="en" name="id" required>
			<option value="" disabled selected>Seleccione un espectáculo</option>
			<%
			sesiones = espectaculo.getSesiones();
			for (int i = 0; i < sesiones.size(); ++i) {
			%>
			<option id="Sesiones" value=<%=sesiones.get(i).getId()%>><%=sesiones.get(i).getFecha()%></option>
			<%
			}
			%>
		</select><input class="submit" type="submit" value="Eliminar sesión"
			onclick="showAlert()"> <input type="hidden"
			value=<%=URLEncoder.encode(request.getParameter("titulo"), "UTF-8")%>
			name="tituloEspectaculo">
		<%
		}

		// Solamente podré agregar espectáculos si son de pase múltiple o de temporada

		if (gestor.getTipoEspectaculo(request.getParameter("titulo")).equals("Múltiple")
				|| gestor.getTipoEspectaculo(request.getParameter("titulo")).equals("Temporada")) {
		%>
	</form>

	<br>

	<!-- Formulario para cancelar un espectáculo -->
	<form method="post" action="/showit/servlet/CancelarEspectaculoServlet">
		<input type="hidden"
			value=<%=URLEncoder.encode(request.getParameter("titulo"), "UTF-8")%>
			name="tituloEspectaculo"> <input class="submit" type="submit"
			value="Cancelar Espectáculo" onclick="showAlert()">
	</form>

	<br>


	<div id="header">
		<ul class="nav">
			<li><a
				href=<%="/showit/mvc/view/adminMenu/modificarSesiones/agregarSesion/agregarSesionView.jsp?titulo="
		+ URLEncoder.encode(request.getParameter("titulo"), "UTF-8")%>>Agregar
					Sesión al Espectáculo</a></li>
		</ul>
	</div>

	<%
}
}
%>

	<br></br>
	<br>
	<%@ include file="/headers/volverMostrar.html"%>

	<script src="/showit/scripts/showAlertWindow.js"></script>