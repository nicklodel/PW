/**
 * 
 */
package servlet.genericMethods;

import javax.servlet.*;
import java.text.ParseException;
import business.gestorDeEspectaculos.GestorDeEspectaculos;
import business.sesion.*;
import java.io.IOException;
import java.net.URLEncoder;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * Clase que permite dar de baja a un usuario del sistema
 * 
 * @author Ricardo Espantaleón Pérez
 * @author Nicolás López
 */
public class DarBajaUsuarioServlet extends HttpServlet {

	private static final long serialVersionUID = 33L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		ServletContext app = getServletContext();

		String nick = request.getParameter("nick");
		GestorDeEspectaculos gestor = null;
		String url = app.getInitParameter("BDdriver");
		String user = app.getInitParameter("BDuser");
		String passwd = app.getInitParameter("BDpassword");
		String file = app.getInitParameter("RutaConsultas");
		String message = "";
		java.io.InputStream inputFile = app.getResourceAsStream(file);
		gestor = new GestorDeEspectaculos(url, user, passwd, inputFile);
		boolean isError = false;

		try {
			gestor.eliminarUsuario(nick);
		} catch (Exception error) {

			// En caso de capturar una excepción, retorno el error capturado y lo imprimo,
			// dado que estos errores son por que existe el espectáculo o por que la fecha
			// es anterior
			// por tanto podemos tratar estos errores en tiempo de ejecución
			message = error.toString();
			isError = true;

		}
		if (!isError)
			message = "Usuario modificado correctamente";

		response.sendRedirect("/showit/mvc/controller/logout/logoutController.jsp");
	}
}
