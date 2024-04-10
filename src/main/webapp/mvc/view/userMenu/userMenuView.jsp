<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ page
	import="business.gestorDeEspectaculos.GestorDeEspectaculos, business.espectador.Espectador, java.util.Date, java.util.ArrayList, business.log.Log"%>
<jsp:useBean id="customerBean" scope="session"
	class="display.CustomerBean"></jsp:useBean>

<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet" href="../../../styles/mainStyle.css">
<link rel="stylesheet" href="../../../styles/adminMenuStyle.css">
<meta charset="UTF-8">
<title>Panel de control</title>
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

	<div class="container">
		<div class="card">
			<div class="header">
				<h3>Menu espectador</h3>
			</div>
			<div class="body">
				<a href="/showit/mvc/view/login/loginView.jsp">Menu espectador</a> <a
					href="/showit/mvc/view/userMenu/modificarDatos/modificarDatosUserView.jsp">Modificar
					datos</a> <a
					href="/showit/mvc/view/userMenu/buscarEspectaculos/buscarEspectaculosView.jsp">Buscar
					espectáculos</a> <a
					href="/showit/mvc/view/userMenu/publicarCritica/publicarCriticaView.jsp">Publicar
					crítica</a><a
					href="/showit/mvc/view/userMenu/eliminarCritica/eliminarCriticaView.jsp">Consultar/Eliminar
					críticas</a> <a
					href="/showit/mvc/view/userMenu/verProximosEspectaculos/verProximosEspectaculosView.jsp">Ver
					Próximos Espectáculos</a> <a
					href="/showit/mvc/controller/logout/logoutController.jsp">Desconectar</a>
				<!-- Formulario para dar de baja a un usuario -->
				<form method="post" action="/showit/servlet/DarBajaUsuarioServlet">
					<input class="submit" type="submit" value="Darse de baja"
						onclick="showAlert()"> <input type="hidden"
						value=<%=customerBean.getNick()%> name="nick">
				</form>
			</div>
		</div>
	</div>
	<script src="/showit/scripts/showAlertWindow.js"></script>
</body>
</html>