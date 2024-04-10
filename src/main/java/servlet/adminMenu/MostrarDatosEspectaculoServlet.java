package servlet.adminMenu;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Clase que muestra los datos de un espectáculo en concreto del sistema
 * 
 * @author Ricardo Espantaleón Pérez
 *
 */
public class MostrarDatosEspectaculoServlet extends HttpServlet {

	private static final long serialVersionUID = 12L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String tituloEspectaculo = request.getParameter("espectaculo");

		response.sendRedirect(
				"/showit/mvc/view/adminMenu/mostrarDatosEspectaculo/imprimirDatosEspectaculoView.jsp?titulo="
						+ tituloEspectaculo);
	}

}