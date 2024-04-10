<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page
	import="business.gestorDeEspectaculos.GestorDeEspectaculos, business.espectador.Espectador, java.util.Date"%>
<jsp:useBean id="customerBean" scope="session"
	class="display.CustomerBean"></jsp:useBean>


<%
	// Si el usuario no ha sido validado o no es un usuario administrador, le mando al menú de login para que se logee correctamente
	// Así evitamos que se pueda introducir la ruta manualmente en el navegador y que se pueda acceder

	if (customerBean.getValidado() == false || customerBean.getEsAdmin() != 1) {
	%>
<jsp:forward page="/showit/mvc/view/login/loginView.jsp">
	<jsp:param value="Haga inicio previamente" name="message" />
</jsp:forward>
<%
	}
%>


<%
String mensaje = "";
String nextPage = "../../view/adminMenu/modificarDatos/modificarDatosAdminView.jsp";



// El usuario ha introducido un nickname para iniciar sesión y un password para iniciar sesión

GestorDeEspectaculos gestor = null;
String url = application.getInitParameter("BDdriver");
String user = application.getInitParameter("BDuser");
String passwd = application.getInitParameter("BDpassword");
String nombreApellidos = request.getParameter("nombreApellidos");
String file = application.getInitParameter("RutaConsultas");

java.io.InputStream inputFile = application.getResourceAsStream(file);

try {

	gestor = new GestorDeEspectaculos(url, user, passwd, inputFile);
} catch (Exception e1) {
	mensaje = e1.toString();
%>
<jsp:forward page="<%=nextPage%>">
	<jsp:param value="<%=mensaje%>" name="message" />
</jsp:forward>
<%
}

try {

gestor.actualizarNombreApellidosUsuario(customerBean.getNick(), nombreApellidos);

} catch (Exception e2) {
mensaje = e2.toString();
%>
<jsp:forward page="<%=nextPage%>">
	<jsp:param value="<%=mensaje%>" name="message" />
</jsp:forward>
<%
}
%>
<jsp:forward page="<%=nextPage%>">
	<jsp:param
		value="El nombre y los apellidos han sido correctamente actualizados"
		name="message" />
</jsp:forward>
