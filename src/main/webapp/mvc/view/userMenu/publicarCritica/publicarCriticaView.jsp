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
<link rel="stylesheet" href="/showit/styles/mixStyle.css">
<meta charset="UTF-8">
<title>Publicar Critica</title>
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

	<br>
	<!-- Formulario para crear una crítica de un espectáculo -->
	<form method="post" action="/showit/servlet/CrearCriticaServlet">
		<!-- Pido datos al usuario. LAS PROPIEDADES SIEMPRE VAN EN EL LADO IZQUIERDO -->
		<fieldset>
			<h2>Crear una crítica</h2>
			<br> <br> <br>
			<!-- Cajita para agrupar el formulario -->

			<!-- Le coloca el texto a fieldset -->
			<!--Etiquetas asociadas al boton. placeholder me coloca un texto dentro de la caja-->
			<!-- <input class="un" type="text" name="titulo" id="titulo"
				placeholder="Título espectaculo" required> <br> <br>-->

			<%
			// Solamente podrá hacer críticas de aquellos espectáculos que no haya valorado previamente

			GestorDeEspectaculos gestor = null;

			String url = application.getInitParameter("BDdriver");
			String user = application.getInitParameter("BDuser");
			String passwd = application.getInitParameter("BDpassword");
			String file = application.getInitParameter("RutaConsultas");

			java.io.InputStream inputFile = application.getResourceAsStream(file);

			gestor = new GestorDeEspectaculos(url, user, passwd, inputFile);

			ArrayList<Espectaculo> espectaculosAValorar = null;

			// Obtengo aquellos espectáculos los cuales un usuario puede valorar
			espectaculosAValorar = gestor.getEspectaculosAValorarPorUsuario(customerBean.getNick());
			%>

			<label>Criticar espectáculo</label> <select class="en"
				name="espectaculo" required>
				<option value="" hidden disabled selected>Seleccione un
					espectáculo</option>

				<%
				for (int i = 0; i < espectaculosAValorar.size(); ++i) {
				%>
				<option id="tituloEspectaculo"
					value=<%=URLEncoder.encode(espectaculosAValorar.get(i).getTitulo(), "UTF-8")%>><%=espectaculosAValorar.get(i).getTitulo()%></option>
				<%
				}
				%>

			</select> <input class="un" type="number" name="puntuacion"
				placeholder="Puntuación" min=0 max=10 required> <br> <br>
			<textarea class="un" name="comentario" id="comentario"
				placeholder="Comentario" required> </textarea>
			<br> <br> <input class="un" type="hidden" name="nick"
				id="nick" value=<%=customerBean.getNick()%>> <input
				class="submit" id="buttonSubmit" type="submit" name="create"
				value="Publicar Crítica">
			<!-- Boton para mandar formulario, con value elijo el texto del boton -->
			<br> <br>
		</fieldset>
	</form>
	<br>
	<br>
	<p>${param.message}</p>
	<br></br>
	<%@ include file="/headers/volverUser.html"%>
	<br></br>
</body>
</html>