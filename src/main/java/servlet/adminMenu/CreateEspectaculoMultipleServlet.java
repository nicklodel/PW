package servlet.adminMenu;

import javax.servlet.*;

import java.util.ArrayList;
import business.sesion.*;
import java.util.Date;
import javax.servlet.annotation.*;
import javax.servlet.http.*;
import business.gestorDeEspectaculos.GestorDeEspectaculos;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLEncoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * Clase servlet que implementa la creación de un espectáculo múltiple en el
 * sistema mediante método post
 * 
 * @author Ricardo Espantaleón Pérez
 * 
 */

// Mirar la entrada en el web.xml para entender el mapeo y como se declara un servlet en el sistema
public class CreateEspectaculoMultipleServlet extends HttpServlet {

	// Serial ID a inicializar
	private static final long serialVersionUID = 10L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();

		// Recibo la instancia al objeto que referencia a las variables de contexto del
		// servidor
		ServletContext app = getServletContext();

		String tituloEspectaculo = request.getParameter("titulo");
		String categoriaEspectaculo = request.getParameter("categoria");
		int localidadesEspectaculo = Integer.parseInt(request.getParameter("localidades"));
		String descripcionEspectaculo = request.getParameter("descripcion");
		String fechas = request.getParameter("uriFechas");

		// out.write(fechas);

		ArrayList<Date> sesiones = new ArrayList<Date>();

		// Cuento cuantas fechas va a tener el vector

		int nFechas = 0;

		for (int i = 0; i < fechas.length(); ++i) {
			if (fechas.charAt(i) == ',')
				nFechas++;
		}

		nFechas++;

		String vectorFechasString[] = fechas.split(",");

		// Parseo, debemos considerar admitir la hora tambien, como teníamos en el
		// parseo del main

		java.util.Date fechaEspectaculo = null;

		for (int i = 0; i < nFechas; ++i) {

			try {
				fechaEspectaculo = new SimpleDateFormat("yyyy-MM-dd|HH:mm").parse(vectorFechasString[i]);

			} catch (ParseException e) {

				throw new IOException(e);
			}

			sesiones.add(fechaEspectaculo);

		}

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
			gestor.crearEspectaculoPaseMultiple(tituloEspectaculo, categoriaEspectaculo, descripcionEspectaculo,
					sesiones, localidadesEspectaculo);
		} catch (Exception error) {

			// En caso de capturar una excepción, retorno el error capturado y lo imprimo,
			// dado que estos errores son por que existe el espectáculo o por que la fecha
			// es anterior
			// por tanto podemos tratar estos errores en tiempo de ejecución
			message = error.toString();
			isError = true;

		}

		if (!isError)
			message = "Espectáculo creado correctamente";

		// Si estamos fuera del try catch, la fecha ha sido creada correctamente, y
		// redireccionamos

		// IMPORTANTE, SOLAMENTE PODREMOS LANZAR UN ÚNICO REDIRECT, SINO TENDREMOS
		// PROBLEMAS PARA CONFIRMAR LOS COMMITS POR PARTE DEL PROTOCOLO HTTP
		response.sendRedirect("/showit/mvc/view/adminMenu/crearEspectaculo/createEspectaculoMultipleView.jsp?message="
				+ URLEncoder.encode(message, "UTF-8"));

	}

}