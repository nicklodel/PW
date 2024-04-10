package servlet.userMenu;

import javax.servlet.http.HttpServlet;

import java.io.IOException;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import business.gestorDeEspectaculos.GestorDeEspectaculos;
import business.critica.*;

/**
 * Clase que permite buscar todas las críticas de un espectáculo en concreto del
 * sistema
 * 
 * @author Ricardo Espantaleón Pérez
 *
 */
public class BuscarCriticasEspectaculoServlet extends HttpServlet {
	private static final long serialVersionUID = 21L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String tituloEspectaculo = request.getParameter("espectaculo");

		response.sendRedirect("/showit/mvc/view/userMenu/buscarEspectaculos/imprimirCriticasEspectaculoView.jsp?titulo="
				+ tituloEspectaculo);
	}
}
