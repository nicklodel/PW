<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ page
	import="business.gestorDeEspectaculos.GestorDeEspectaculos, business.espectaculo.*,business.espectador.Espectador, java.util.Date, java.util.ArrayList, business.log.Log, business.sesion.Sesion, java.net.URLEncoder, business.critica.*"%>
<jsp:useBean id="customerBean" scope="session"
	class="display.CustomerBean"></jsp:useBean>
<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet" href="/showit/styles/mainStyle.css">
<link rel="stylesheet" href="/showit/styles/subMenuStyle.css">
<link rel="stylesheet" href="/showit/styles/CritStyle.css">

<meta charset="UTF-8">
<title>Datos del Espectáculo:</title>
</head>
<body>

	<%
	// Si el usuario no ha sido validado o no es un usuario administrador, le mando al menú de login para que se logee correctamente
	// Así evitamos que se pueda introducir la ruta manualmente en el navegador y que se pueda acceder

	if (customerBean.getValidado() == false || customerBean.getEsAdmin() != 0) {
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
	ArrayList<Critica> criticas = null;
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

	<h2>
		Descripción:
		<%=espectaculo.getDescripcion()%></h2>

	<%
	criticas = gestor.getCriticasTitulo(espectaculo.getTitulo());
	%>

	<h2>
		Puntuación:
		<%=gestor.getValoracionEspectaculo(espectaculo.getTitulo()) == 0.0 && criticas.size() == 0 ? "No valorado"
		: gestor.getValoracionEspectaculo(espectaculo.getTitulo())%></h2>
	<br>
	<br>
	<br>
	<%
	for (int i = 0; i < criticas.size(); ++i) {
	%>

	<!-- Formulario para valorar una crítica -->
	<form method="post" action="/showit/servlet/ValorarCriticaServlet">
		<h4>
			Puntuación al espectáculo:
			<%=criticas.get(i).getPuntuacion()%></h4>
		<h4>
			Nick del autor:
			<%=criticas.get(i).getNickAutor()%></h4>
		<h4>
			Valoración media a la crítica:
			<%=gestor.getValoracionCritica(espectaculo.getTitulo(), criticas.get(i).getNickAutor())%></h4>
		<div class="un"><%=criticas.get(i).getComentario()%></div>
		<input type="hidden" name="nick" id="nick"
			value=<%=customerBean.getNick()%>> <br> <input
			type="hidden" name="tituloEspectaculo" id="tituloEspectaculo"
			value=<%=URLEncoder.encode(criticas.get(i).getTitulo(), "UTF-8")%>>
		<input type="hidden" name="nickAutor" id="nickAutor"
			value=<%=criticas.get(i).getNickAutor()%>>

		<%
		// Si el usuario ya ha valorado la crítica, no podrá volver a votarla. O si la crítica es del usuario actual de la sesión
		// no podrá valorarse a si mismo
		if (gestor.estaValorado(customerBean.getNick(), criticas.get(i).getNickAutor(), criticas.get(i).getTitulo()) > 0
				|| customerBean.getNick().equalsIgnoreCase(criticas.get(i).getNickAutor())) {
		%>
		<input class="submit" type="button" value="Valorado ✔"> <br>
		<br>
		<%
		}

		// El usuario puede valorar la crítica dado que no la ha valorado antes

		else {
		%>

		<input class="en" type="number" name="puntuacion"
			placeholder="Puntuacion" min=0 max=10
			style="width: 200px; height: 50px; border: 1px solid #000;" required>
		<input class="submit" id="buttonSubmit" type="submit"
			name="valorar Crítica" value="Valorar Crítica">

		<%
		}
		%>
	</form>
	<br>
	<%
	}
	}
	%>


	<br>
	<br>

	<br></br>
	<%@ include file="/headers/volverMostrarUser.html"%>

	<script src="/showit/scripts/showAlertWindow.js"></script>