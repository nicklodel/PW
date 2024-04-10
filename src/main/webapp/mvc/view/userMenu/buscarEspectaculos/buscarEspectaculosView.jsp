<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ page
	import="business.gestorDeEspectaculos.GestorDeEspectaculos, business.espectaculo.*,business.espectador.Espectador, java.util.Date, java.util.ArrayList, business.log.Log, java.net.URLEncoder"%>
<jsp:useBean id="customerBean" scope="session"
	class="display.CustomerBean"></jsp:useBean>
<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet" href="/showit/styles/mainStyle.css">
<link rel="stylesheet" href="/showit/styles/subMenuStyle.css">
<link rel="stylesheet" href="/showit/styles/formStyle.css">
<meta charset="UTF-8">
<title>Panel de espectáculos</title>
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
	%>

	<%
	GestorDeEspectaculos gestor = null;

	String url = application.getInitParameter("BDdriver");
	String user = application.getInitParameter("BDuser");
	String passwd = application.getInitParameter("BDpassword");
	String file = application.getInitParameter("RutaConsultas");

	java.io.InputStream inputFile = application.getResourceAsStream(file);

	gestor = new GestorDeEspectaculos(url, user, passwd, inputFile);

	ArrayList<Espectaculo> espectaculos = null;

	boolean flag = false;

	try {
		if (request.getParameter("filtro") == null || request.getParameter("filtro") == "")
			espectaculos = gestor.getEspectaculos();

		else
			espectaculos = gestor.getEspectaculosCategoria(request.getParameter("filtro"));

	} catch (Exception e) {
		flag = true;
	%>
	<h1>No existe ningún espectáculo en el sistema</h1>
	<%
	}

	if (!flag && espectaculos != null) {
	// Existen espectáculos
	%>

	<!-- Formulario para filtrar los tipos de espectáculos -->

	<form method="get"
		action="/showit/servlet/UserFiltrarEspectaculoServlet">
		<label>Filtrar espectáculos</label> <input class="en" type="text"
			name="categoria" placeholder="Categoría a filtrar"> <input
			class="submit" type="submit" value="Filtrar categoría">
	</form>

	<br>

	<!-- Formulario para elegir que espectáculo consultar -->

	<form method="get"
		action="/showit/servlet/BuscarCriticasEspectaculoServlet">
		<label>Consulta espectáculo</label> <select class="en"
			name="espectaculo" required>
			<option value="" hidden disabled selected>Seleccione un
				espectáculo</option>
			<%
			for (int i = 0; i < espectaculos.size(); ++i) {
			%>
			<option id="tituloEspectaculo"
				value=<%=URLEncoder.encode(espectaculos.get(i).getTitulo(), "UTF-8")%>><%=espectaculos.get(i).getTitulo()%></option>
			<%
			}
			%>
		</select> <input class="submit" type="submit" value="Consultar críticas">
	</form>
	<%
	if (espectaculos.size() == 0) {
	%>
	<br>
	<br>
	<p>No se ha encontrado ningún espectáculo</p>
	<%
	}

	else {
	%>
	<table>
		<tr>
			<th>Título espectáculo</th>
			<th>Categoría</th>
		</tr>
		<%
		for (int i = 0; i < espectaculos.size(); ++i) {
		%>
		<tr>
			<td><%=espectaculos.get(i).getTitulo()%></td>
			<td><%=espectaculos.get(i).getCategorias()%></td>
		</tr>
		<%
		}
		%>
	</table>
	<%
	}
	}
	%>
	<br></br>
	<%@ include file="/headers/volverUser.html"%>
</body>
</html>