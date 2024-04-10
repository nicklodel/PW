package servlet.userMenu;

import javax.servlet.*;
import javax.servlet.http.*;

import business.gestorDeEspectaculos.GestorDeEspectaculos;
import business.sesion.Sesion;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLEncoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Clase que permite comprar las entradas de una sesión de un espectáculo en
 * concreto del sistema
 * 
 * @author Ricardo Espantaleón Pérez
 *@author Nicolás López
 */
public class ComprarEntradaSesionServlet extends HttpServlet {

	private static final long serialVersionUID = 22L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		ServletContext app = getServletContext();

		int idSesion = Integer.parseInt(request.getParameter("idSesion"));

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
			gestor.venderEntradas(idSesion);

		} catch (Exception error) {

			// En caso de capturar una excepción, retorno el error capturado y lo imprimo,
			// dado que estos errores son por que existe el espectáculo o por que la fecha
			// es anterior
			// por tanto podemos tratar estos errores en tiempo de ejecución
			message = error.toString();
			isError = true;

		}
		if (!isError)
			message = "Entrada correctamente comprada";

		response.sendRedirect(
				"http://localhost:8080/showit/mvc/view/userMenu/verProximosEspectaculos/verProximosEspectaculosView.jsp?message="
						+ message);
	}
}
