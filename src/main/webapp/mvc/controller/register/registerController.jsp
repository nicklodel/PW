<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page
	import="business.gestorDeEspectaculos.GestorDeEspectaculos, business.espectador.Espectador, java.util.Date"%>
<jsp:useBean id="customerBean" scope="session"
	class="display.CustomerBean"></jsp:useBean>
<%
String mensaje = "";
String nextPage = "../../view/register/registerView.jsp";

// El usuario ha introducido un nickname para iniciar sesión y un password para iniciar sesión

GestorDeEspectaculos gestor = null;
String url = application.getInitParameter("BDdriver");
String user = application.getInitParameter("BDuser");
String passwd = application.getInitParameter("BDpassword");

String nick = request.getParameter("nickName");
String email = request.getParameter("email");
String nombreApellidos = request.getParameter("nombreApellidos");
String password = request.getParameter("password");
String value = request.getParameter("esAdmin");
int esAdmin = Integer.parseInt(value);
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
boolean existeNick = false;
boolean existeEmail = false;
try {
existeNick = gestor.existeNick(nick);
existeEmail = gestor.existeEmail(email);

} catch (Exception e2) {
mensaje = e2.toString();
%>
<jsp:forward page="<%=nextPage%>">
	<jsp:param value="<%=mensaje%>" name="message" />
</jsp:forward>
<%
}

if (existeNick) {
%>
<jsp:forward page="<%=nextPage%>">
	<jsp:param
		value="Error, el nickname introducido ya existe en el sistema"
		name="message" />
</jsp:forward>
<%
}
if (existeEmail) {
%>
<jsp:forward page="<%=nextPage%>">
	<jsp:param value="Error, el email introducido ya existe en el sistema"
		name="message" />
</jsp:forward>
<%
}

else if (!existeNick && !existeEmail) {
Espectador espectador = new Espectador(nick, nombreApellidos, email, esAdmin, new Date());
gestor.crearUsuario(espectador, password);
}
%>
<jsp:forward page="<%=nextPage%>">
	<jsp:param value="El usuario ha sido creado correctamente"
		name="message" />
</jsp:forward>