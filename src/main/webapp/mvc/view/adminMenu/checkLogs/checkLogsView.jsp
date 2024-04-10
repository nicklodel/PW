<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ page
	import="business.gestorDeEspectaculos.GestorDeEspectaculos, business.espectador.Espectador, java.util.Date, java.util.ArrayList, business.log.Log"%>
<jsp:useBean id="customerBean" scope="session"
	class="display.CustomerBean"></jsp:useBean>

<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet"
	href="http://localhost:8080/showit/styles/mainStyle.css">
<link rel="stylesheet"
	href="http://localhost:8080/showit/styles/subMenuStyle.css">
<meta charset="UTF-8">
<title>Panel de control</title>
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
	Bienvenido usuario administrador
	<%=customerBean.getNick()%><br></br> Aqui tiene una lista de todos los
	usuarios registrados y su última hora de conexión
	<br>
	<h1>Log de conexiones</h1>
	<br></br>
	<table>

		<%
		GestorDeEspectaculos gestor = null;

		// Implementar la lectura del xml directamente
		String url = application.getInitParameter("BDdriver");
		String user = application.getInitParameter("BDuser");
		String passwd = application.getInitParameter("BDpassword");
		String file = application.getInitParameter("RutaConsultas");

		java.io.InputStream inputFile = application.getResourceAsStream(file);

		// Como es una vista, no debo poner try catchs para no mostrar lógica de negocio en las vistas del modelo

		// En caso de error, redireccionar a la página de error correspondiente del gestor
		gestor = new GestorDeEspectaculos(url, user, passwd, inputFile);

		ArrayList<Log> registros = gestor.getRegistrosLog();
		%>
		<tr>
			<th>Nickname</th>
			<th>Última conexión</th>
			<th>Fecha de registro</th>
			<th>Usuario administrador</th>

		</tr>
		<%
		for (int i = 0; i < registros.size(); ++i) {
		%>

		<tr>
			<td><%=registros.get(i).getNick()%></td>
			<td><%=registros.get(i).getUltimaConexion().toString()%></td>
			<td><%=registros.get(i).getFechaCreacion().toString()%></td>
			<td><%=registros.get(i).getEsAdmin() == 0 ? "No" : "Sí"%></td>
		</tr>
		<%
		}
		%>
	</table>
	<br></br>
	<%@ include file="/headers/volverAdmin.html"%>
</body>
</html>