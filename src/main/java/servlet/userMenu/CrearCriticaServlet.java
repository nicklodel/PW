package servlet.userMenu;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLEncoder;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import business.gestorDeEspectaculos.GestorDeEspectaculos;
import business.critica.*;

/**
 * Clase que permite crear una crítica de un espectáculo en el sistema
 * 
 * @author Ricardo Espantaleón Pérez
 *
 */
public class CrearCriticaServlet extends HttpServlet {

	private static final long serialVersionUID = 20L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// Recibo la instancia al objeto que referencia a las variables de contexto del
		// servidor
		ServletContext app = getServletContext();

		// Recopilamos los parámetros del form

		float puntuacion = Float.parseFloat(request.getParameter("puntuacion"));
		String comentario = request.getParameter("comentario");
		String espectaculo = request.getParameter("espectaculo");
		String nickAutor = request.getParameter("nick");

		// Elimino parametros del encodificado URL

		comentario = comentario.replace('+', ' ');
		espectaculo = espectaculo.replace('+', ' ');

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

		Critica critica = new Critica(espectaculo, puntuacion, comentario, nickAutor);

		try {
			gestor.crearCritica(critica);

		} catch (Exception e) {
			isError = true;
			message = e.toString();

		}

		if (!isError)
			message = "Crítica creada correctamente";

		response.sendRedirect("/showit/mvc/view/userMenu/publicarCritica/publicarCriticaView.jsp?message="
				+ URLEncoder.encode(message, "UTF-8"));
	}

}
