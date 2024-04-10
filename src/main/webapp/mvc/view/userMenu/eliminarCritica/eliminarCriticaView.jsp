<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page
	import="business.gestorDeEspectaculos.GestorDeEspectaculos, business.critica.Critica,business.espectaculo.*,business.espectador.Espectador, java.util.Date, java.util.ArrayList, business.log.Log, business.sesion.Sesion, java.net.URLEncoder"%>
<jsp:useBean id="customerBean" scope="session"
	class="display.CustomerBean"></jsp:useBean>
<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet" href="/showit/styles/mainStyle.css">
<link rel="stylesheet" href="/showit/styles/subMenuStyle.css">
<link rel="stylesheet" href="/showit/styles/CritStyle.css">
<meta charset="UTF-8">
<title>Eliminar crítica</title>
</head>
<body>

	<%
	// Si el usuario no ha sido validado o es un usuario administrador, le mando al menú de login para que se logee correctamente
	// Así evitamos que se pueda introducir la ruta manualmente en el navegador y que se pueda acceder

	if (customerBean.getValidado() == false || customerBean.getEsAdmin() != 0) {
	%>
	<jsp:forward page="../../view/login/loginView.jsp">
		<jsp:param value="Haga inicio previamente" name="message" />
	</jsp:forward>
	<%
	}
	%>

	<h1>
		Bienvenido usuario
		<%=customerBean.getNick()%></h1>

	<br>
	<!-- Impresión de mensajes de éxito fallo en posibles redirect -->
	<h4>${param.message}</h4>
	<br>

	<div>Sus críticas son las siguientes:</div>
	<br>

	<!-- Impresión de las críticas -->

	<%
			// Solamente podrá hacer críticas de aquellos espectáculos que no haya valorado previamente

			GestorDeEspectaculos gestor = null;

			String url = application.getInitParameter("BDdriver");
			String user = application.getInitParameter("BDuser");
			String passwd = application.getInitParameter("BDpassword");
			String file = application.getInitParameter("RutaConsultas");

			java.io.InputStream inputFile = application.getResourceAsStream(file);

			gestor = new GestorDeEspectaculos(url, user, passwd, inputFile);

			ArrayList<Critica> criticasUsuario = gestor.getCriticasUsuario(customerBean.getNick());
			
			for(int i = 0; i < criticasUsuario.size(); ++i) {
				// Formulario para cada crítica a borrar por parte del usuario
				
				%>
	<!-- Formulario para borrar una critica -->
	<form method="post" action="/showit/servlet/BorrarCriticaServlet">
		<h4><%=criticasUsuario.get(i).getTitulo()%>. Puntuación:
			<%=criticasUsuario.get(i).getPuntuacion()%></h4>
		<div class="un"><%=criticasUsuario.get(i).getComentario()%></div>
		<input type="hidden" name="nick" id="nick"
			value=<%=customerBean.getNick()%>> <br> <input
			type="hidden" name="tituloEspectaculo" id="tituloEspectaculo"
			value=<%=URLEncoder.encode(criticasUsuario.get(i).getTitulo(), "UTF-8")%>>
		<input class="submit" id="buttonSubmit" type="submit" name="delete"
			value="Eliminar Crítica">
	</form>

	<br>

	<%
			}
	%>


	<br></br>
	<%@ include file="/headers/volverUser.html"%>
</body>
</html>