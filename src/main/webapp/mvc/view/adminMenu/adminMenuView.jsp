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
<body>
	<div class="container">
		<div class="card">
			<div class="header">
				<h3>Menu administrador</h3>
			</div>
			<div class="body">
				<a href="/showit/mvc/view/login/loginView.jsp">Menu
					administrador</a> <a
					href="/showit/mvc/view/adminMenu/checkLogs/checkLogsView.jsp">Consultar
					registros</a> <a
					href="/showit/mvc/view/adminMenu/modificarDatos/modificarDatosAdminView.jsp">Modificar
					datos</a> <a
					href="/showit/mvc/view/adminMenu/mostrarDatosEspectaculo/imprimirEspectaculosView.jsp">Mostrar
					espectaculos</a>
				<li><i>Crear espectaculos</i>
					<ul>
						<li><a
							href="/showit/mvc/view/adminMenu/crearEspectaculo/createEspectaculoPuntualView.jsp">Crear
								espectaculo puntual</a>
						<li><a
							href="/showit/mvc/view/adminMenu/crearEspectaculo/createEspectaculoMultipleView.jsp">Crear
								espectaculo múltiple</a>
						<li><a
							href="/showit/mvc/view/adminMenu/crearEspectaculo/createEspectaculoTemporadaView.jsp">Crear
								espectaculo Temporada</a>
					</ul></li> <a href="/showit/mvc/controller/logout/logoutController.jsp">Desconectar</a>

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