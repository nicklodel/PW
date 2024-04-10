package servlet.adminMenu;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLEncoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import business.gestorDeEspectaculos.GestorDeEspectaculos;

/**
 * Clase servlet que implementa la creación de un espectáculo de temporada en el
 * sistema mediante método post
 * 
 * @author Ricardo Espantaleón Pérez
 *
 */
public class CreateEspectaculoTemporadaServlet extends HttpServlet {
	// Serial ID a inicializar
	private static final long serialVersionUID = 2L;

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
		int nEspectaculos = Integer.parseInt(request.getParameter("nEspec"));

		String stringFecha = request.getParameter("fecha");
		String stringHora = request.getParameter("tiempo");
		java.util.Date fechaEspectaculo = null;
		java.util.Date horaEspectaculo = null;

		try {
			fechaEspectaculo = new SimpleDateFormat("yyyy-MM-dd").parse(stringFecha);
			horaEspectaculo = new SimpleDateFormat("HH:mm").parse(stringHora);
			fechaEspectaculo = new Date(fechaEspectaculo.getTime() + horaEspectaculo.getTime() + 3600000);

		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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
			gestor.crearEspectaculoTemporada(tituloEspectaculo, categoriaEspectaculo, descripcionEspectaculo,
					fechaEspectaculo, nEspectaculos, localidadesEspectaculo);
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
		response.sendRedirect("/showit/mvc/view/adminMenu/crearEspectaculo/createEspectaculoTemporadaView.jsp?message="
				+ URLEncoder.encode(message, "UTF-8"));

	}

}
