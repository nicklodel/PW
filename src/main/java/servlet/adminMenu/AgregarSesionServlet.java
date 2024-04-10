package servlet.adminMenu;

import java.io.IOException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.*;
import java.text.ParseException;
import business.gestorDeEspectaculos.GestorDeEspectaculos;
import business.sesion.*;

/**
 * Clase que agrega una sesión a un espectáculo del sistema
 * 
 * @author Ricardo Espantaleón Pérez
 *
 */
public class AgregarSesionServlet extends HttpServlet {

	private static final long serialVersionUID = 5L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		ServletContext app = getServletContext();
		
		

		int localidades = Integer.parseInt(request.getParameter("localidades"));
		String stringFecha = request.getParameter("fecha");
		String stringHora = request.getParameter("tiempo");
		Date fechaEspectaculo, horaEspectaculo;
		String espectaculo = request.getParameter("tituloEspectaculo");
		
		// Proceso el encode de la URL
		espectaculo = espectaculo.replace('+', ' ');
		
		try {
			fechaEspectaculo = new SimpleDateFormat("yyyy-MM-dd").parse(stringFecha);
			horaEspectaculo = new SimpleDateFormat("HH:mm").parse(stringHora);
			fechaEspectaculo = new Date(fechaEspectaculo.getTime() + horaEspectaculo.getTime() + 3600000);

		} catch (ParseException e) {
			
			throw new ServletException (e);
		}

		GestorDeEspectaculos gestor = null;
		String url = app.getInitParameter("BDdriver");
		String user = app.getInitParameter("BDuser");
		String passwd = app.getInitParameter("BDpassword");
		String file = app.getInitParameter("RutaConsultas");
		String message = "";
		java.io.InputStream inputFile = app.getResourceAsStream(file);
		gestor = new GestorDeEspectaculos(url, user, passwd, inputFile);
		boolean isError = false;

		Sesion sesion = new Sesion(localidades, fechaEspectaculo, 0, 0, espectaculo);
		try {
			gestor.agregarSesion(sesion);
		} catch (Exception error) {

			// En caso de capturar una excepción, retorno el error capturado y lo imprimo,
			// dado que estos errores son por que existe el espectáculo o por que la fecha
			// es anterior
			// por tanto podemos tratar estos errores en tiempo de ejecución
			message = error.toString();
			isError = true;

		}
		if (!isError)
			message = "Sesión creada correctamente";
		
		response.sendRedirect("/showit/mvc/view/adminMenu/mostrarDatosEspectaculo/imprimirDatosEspectaculoView.jsp?titulo="
				+ espectaculo);
	}
}
