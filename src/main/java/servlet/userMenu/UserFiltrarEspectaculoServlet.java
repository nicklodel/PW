package servlet.userMenu;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;
import java.net.URLEncoder;

/**
 * Clase que permite usar un filtro sobre los espectáculos según sea su
 * categoría
 * 
 * @author Ricardo Espantaleón Pérez
 *
 */
public class UserFiltrarEspectaculoServlet extends HttpServlet {

	private static final long serialVersionUID = 50L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String filtro = request.getParameter("categoria");

		response.sendRedirect("/showit/mvc/view/userMenu/buscarEspectaculos/buscarEspectaculosView.jsp?filtro="
				+ URLEncoder.encode(filtro, "UTF-8"));
	}

}