package servlet.userMenu;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLEncoder;

/**
 * Clase que permite conocer cuales serán las siguientes sesiones que ocurrirán
 * de un espectáculo en concreto del sistema
 * 
 * @author Ricardo Espantaleón Pérez
 * @author Nicolás López
 *
 */
public class UserFiltrarSesionesServlet extends HttpServlet {

	private static final long serialVersionUID = 55L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String fecha = request.getParameter("fecha");

		response.sendRedirect("/showit/mvc/view/userMenu/verProximosEspectaculos/verProximosEspectaculosView.jsp?fecha="
				+ URLEncoder.encode(fecha, "UTF-8"));
	}

}