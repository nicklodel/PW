<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet" href="../../../styles/mainStyle.css">
<link rel="stylesheet" href="../../../styles/subMenuStyle.css">
<link rel="stylesheet" href="../../../styles/loginStyle.css">
<meta charset="UTF-8">
<title>Registro</title>
</head>
<body>

	<%
	String messageNextPage = request.getParameter("message");
	if (messageNextPage == null)
		messageNextPage = "";
	String nextPage = "../../controller/register/registerController.jsp";
	%>
	<br>
	<!-- Formulario para registrar a un usuario en el sistema -->	
	<form method="post"
		action="../../controller/register/registerController.jsp">
		<!-- Pido datos al usuario. LAS PROPIEDADES SIEMPRE VAN EN EL LADO IZQUIERDO -->
		<fieldset>
			<h1>Formulario de registro</h1>
			<br> <br> <br>
			<!-- Cajita para agrupar el formulario -->
			<!-- Le coloca el texto a fieldset -->

			<!--Etiquetas asociadas al boton. placeholder me coloca un texto dentro de la caja-->
			<input class="un" type="text" name="nombreApellidos"
				placeholder="Nombre y Apellidos" required>
			<!-- No requiere el cierre del slatch. Puedo pasar propiedades a las etiquetas -->
			<br> <br> <input class="un" type="text" name="nickName"
				placeholder="Nickname" required> <br> <br> <input
				class="pass" type="password" name="password"
				placeholder="Contraseña" required> <br> <br> <input
				class="un" type="email" name="email"
				placeholder="Email: john@doe.com" required> <br> <br>
			<label>Usuario administrador</label> <select class="en" name="esAdmin">
				<!-- switch case -->
				<option value="0">No</option>
				<!-- value me indica el valor real de la variable al seleccionarlo-->
				<option value="1">Sí</option>
			</select> <br> <br> <input class="submit" type="submit"
				value="Registrarse">
			<!-- Boton para mandar formulario, con value elijo el texto del boton -->
			<br> <br>
		</fieldset>
	</form>
	<%=messageNextPage%><br />
	<br />
	<%@ include file="../../../headers/volver.html"%>
</body>
</html>