<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page
	import="business.gestorDeEspectaculos.GestorDeEspectaculos, business.espectador.Espectador, java.util.Date, java.io.InputStream"%>
<jsp:useBean id="customerBean" scope="session"
	class="display.CustomerBean"></jsp:useBean>
<%
String mensaje = "";
String nextPage = "../../view/login/loginView.jsp";

// Si el usuario se ha logado previamente le redirecciono a su vista correspondiente

if(customerBean.getValidado() == true) {
	
	// Es un usuario administrador
	if(customerBean.getEsAdmin() == 1) {
		%>
<jsp:forward page="../../view/adminMenu/adminMenuView.jsp">
	<jsp:param value="" name="message" />
</jsp:forward>
<%
	}
	
	else {
		%>
<jsp:forward page="../../view/userMenu/userMenuView.jsp">
	<jsp:param value="" name="message" />
</jsp:forward>
<%
	}
	
}

// Compruebo si el customerBean no ha sido previamente validado para poder procesar su solicitud
if (customerBean.getValidado() == false) {
	String nickName = request.getParameter("nickname");
	String password = request.getParameter("password");

	// El usuario ha introducido un nickname para iniciar sesión y un password para iniciar sesión
	if (nickName != null && password != null) {
		customerBean.setNick(nickName);

		GestorDeEspectaculos gestor = null;
		String url = application.getInitParameter("BDdriver");
		String user = application.getInitParameter("BDuser");
		String passwd = application.getInitParameter("BDpassword");
		String file = application.getInitParameter("RutaConsultas");

		java.io.InputStream inputFile = application.getResourceAsStream(file);

		gestor = new GestorDeEspectaculos(url, user, passwd, inputFile);

		boolean status = false;

		status = gestor.compruebaUsuarioYPassword(customerBean.getNick(), password);

		// Le indicamos al customer bean si el usuario en cuestión es válido o no
		customerBean.setValidado(status);

		// El usuario es válido
		if (status) {
	customerBean.setEsAdmin(gestor.getUsuario(customerBean.getNick()).esAdmin());
	customerBean.setUltimaConexion(new Date());
	gestor.actualizarLog(customerBean.getNick(), customerBean.getUltimaConexion());

	// No es usuario administrador
	if (customerBean.getEsAdmin() == 0) {
%>
<jsp:forward page="../../view/userMenu/userMenuView.jsp">
	<jsp:param value="CORRECTO" name="message" />
</jsp:forward>
<%
}

	// Es usuario administrador
	else {
	%>
<jsp:forward page="../../view/adminMenu/adminMenuView.jsp">
	<jsp:param value="CORRECTO" name="message" />
</jsp:forward>
<%
	}
}

	// El usuario no es válido
	else {
	mensaje = "Error, la password o el usuario introducido no es correcto";
	}

	}
}
%>
<jsp:forward page="<%=nextPage%>">
	<jsp:param value="<%=mensaje%>" name="message" />
</jsp:forward>