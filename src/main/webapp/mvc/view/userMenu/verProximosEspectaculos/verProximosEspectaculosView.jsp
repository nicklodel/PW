<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ page
	import="business.gestorDeEspectaculos.GestorDeEspectaculos, business.espectaculo.*,business.espectador.Espectador, java.util.Date, java.util.ArrayList, 
	business.log.Log, business.sesion.Sesion, java.net.URLEncoder, java.text.SimpleDateFormat"%>
<jsp:useBean id="customerBean" scope="session"
	class="display.CustomerBean"></jsp:useBean>
<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet" href="/showit/styles/mainStyle.css">
<link rel="stylesheet" href="/showit/styles/subMenuStyle.css">
<link rel="stylesheet" href="/showit/styles/formStyle.css">

<meta charset="UTF-8">
<title>Próximos espectáculos</title>
</head>
<body>

	<%
	if (customerBean.getValidado() == false || customerBean.getEsAdmin() != 0) {
	%>
	<jsp:forward page="../../view/login/loginView.jsp">
		<jsp:param value="Haga inicio previamente" name="message" />
	</jsp:forward>
	<%
	}
	%>

	<%
	GestorDeEspectaculos gestor = null;

	String url = application.getInitParameter("BDdriver");
	String user = application.getInitParameter("BDuser");
	String passwd = application.getInitParameter("BDpassword");
	String file = application.getInitParameter("RutaConsultas");

	java.io.InputStream inputFile = application.getResourceAsStream(file);

	gestor = new GestorDeEspectaculos(url, user, passwd, inputFile);

	ArrayList<Sesion> sesiones = null;

	String messageError = "";

	try {
		if (request.getParameter("fecha") == null || request.getParameter("fecha") == "")
			sesiones = gestor.getSesionesPosteriores(new Date());

		else
			sesiones = gestor
			.getSesionesPosteriores(new SimpleDateFormat("yyyy-MM-dd").parse(request.getParameter("fecha")));

	} catch (Exception e) {
	%>
	<h1>No existe ninguna sesión a partir de la fecha indicada</h1>
	<%
	}
	%>

	<!-- Formulario para filtrar por fecha las siguientes sesiones disponibles -->

	<form method="get" action="/showit/servlet/UserFiltrarSesionesServlet">
		<label>Filtrar por fecha</label> <input class="en" type="date"
			name="fecha" id="fecha" placeholder="Fecha a seleccionar"> <input
			class="submit" type="submit" value="Filtrar por fecha">
	</form>

	<br>

	<p>${param.message}</p>

	<table>
		<tr>
			<th>Título</th>
			<th>Fecha</th>
			<th>Localidades</th>
			<th>Vendidas</th>
		</tr>
		<%
		for (int i = 0; i < sesiones.size(); ++i) {
		%>
		<form method="post"
			action="/showit/servlet/ComprarEntradaSesionServlet">
			<tr>
				<td><%=sesiones.get(i).getEspectaculo()%></td>
				<td><%=sesiones.get(i).getFecha()%></td>
				<td><%=sesiones.get(i).getLocalidades()%></td>
				<td><%=sesiones.get(i).getVendidas()%></td>
				<input type="hidden" name="idSesion" id="idSesion"
					value=<%=sesiones.get(i).getId()%>>
				<td><input class="submit" id="buttonSubmit" type="submit"
					name="comprar entrada" value="Comprar entrada"></td>
			</tr>
		</form>
		<%
		}
		%>
	</table>
	<br>
	<br>
	<%@ include file="/headers/volverUser.html"%>
	<script src="/showit/scripts/checkMinDate.js"></script>
</body>