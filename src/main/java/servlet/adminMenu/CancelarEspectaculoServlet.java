package servlet.adminMenu;

import javax.servlet.*;

import javax.servlet.http.*;

import business.gestorDeEspectaculos.GestorDeEspectaculos;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLEncoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import business.sesion.*;
import data.dao.*;

/**
 * Clase que cancela un espectáculo en concreto del sistema
 * 
 * @author Ricardo Espantaleón Pérez
 *
 */
public class CancelarEspectaculoServlet extends HttpServlet {

	private static final long serialVersionUID = 12L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();

		// Recibo la instancia al objeto que referencia a las variables de contexto del
		// servidor
		ServletContext app = getServletContext();

		String tituloEspectaculo = request.getParameter("tituloEspectaculo");

		// Procesamos la cadena con encodificado URL
		tituloEspectaculo = tituloEspectaculo.replace('+', ' ');

		GestorDeEspectaculos gestor = null;
		String url = app.getInitParameter("BDdriver");
		String user = app.getInitParameter("BDuser");
		String passwd = app.getInitParameter("BDpassword");
		String file = app.getInitParameter("RutaConsultas");

		java.io.InputStream inputFile = app.getResourceAsStream(file);
		gestor = new GestorDeEspectaculos(url, user, passwd, inputFile);

		// Mensaje a devolver a la página de creaciones de espectáculos, en caso de
		// éxito o error
		String message = "";

		// Booleano para comprobar si entré previamente en el catch y hubo un error, por
		// tanto no indico un mensaje de éxito
		boolean isError = false;

		try {
			gestor.cancelarEspectaculo(tituloEspectaculo);

		} catch (Exception error) {

			// En caso de capturar una excepción, retorno el error capturado y lo imprimo,
			// dado que estos errores son por que existe el espectáculo o por que la fecha
			// es anterior
			// por tanto podemos tratar estos errores en tiempo de ejecución
			message = error.toString();
			isError = true;

		}

		if (!isError)
			message = "Espectáculo cancelado correctamente";

		// Si estamos fuera del try catch, la fecha ha sido creada correctamente, y
		// redireccionamos

		// IMPORTANTE, SOLAMENTE PODREMOS LANZAR UN ÚNICO REDIRECT, SINO TENDREMOS
		// PROBLEMAS PARA CONFIRMAR LOS COMMITS POR PARTE DEL PROTOCOLO HTTP
		response.sendRedirect("/showit/mvc/view/adminMenu/mostrarDatosEspectaculo/imprimirEspectaculosView.jsp");

	}

}