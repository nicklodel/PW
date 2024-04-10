package tests;

import java.io.IOException;

import java.util.Date;
import java.sql.SQLException;

import business.espectador.Espectador;
import business.gestorDeEspectaculos.GestorDeEspectaculos;
import data.common.BDConnection;
import data.dao.UserDAO;

public class Test {

	public static void main(String[] args) {
		// Testeando conexión a la base de datos
		insertUser();
		gestorInsertUser();
		compruebaUsuario();
		getUsuario();
		insertarLog();

		System.out.print("PASSED!");
	}

	public static void testBD() { // DONE
		UserDAO prueba = null;
	}

	public static void insertUser() { // DONE
		UserDAO prueba = null;

		try {
			prueba = new UserDAO();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.exit(-1);
		}

		String nick = "rick";
		String nombreApellidos = "Ricardo Espantaleón";
		String email = "i92esper@uco.es";
		int esAdmin = 1;
		String password = "pw";
		Espectador u = null;

		try {
			u = new Espectador(nick, nombreApellidos, email, esAdmin);
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			System.exit(-1);
		}

		try {
			prueba.insertarUsuario(u, password);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.exit(-1);
		}
	}

	public static void gestorInsertUser() { // DONE
		String nick = "enrique2001";
		String nombreApellidos = "Enrique Estévez";
		String email = "i92esmae@uco.es";
		int esAdmin = 0;
		String password = "1234";
		Espectador u = null;

		GestorDeEspectaculos g = null;

		try {
			g = new GestorDeEspectaculos("jdbc:mysql://oraclepr.uco.es:3306/i92esmae", "i92esmae", "bdpw2021");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.exit(-1);
		}

		try {
			u = new Espectador(nick, nombreApellidos, email, esAdmin);
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			System.exit(-1);
		}

		try {
			g.crearUsuario(u, password);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.exit(-1);
		}
	}

	public static void compruebaUsuario() { // DONE
		GestorDeEspectaculos g = null;

		try {
			g = new GestorDeEspectaculos("jdbc:mysql://oraclepr.uco.es:3306/i92esmae", "i92esmae", "bdpw2021");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.exit(-1);
		}

		Espectador e = new Espectador();
		String password = "pw";
		e.setNick("rick");

		System.out.println(e.getNick() + " " + password);

		try {
			System.out.println(g.compruebaUsuarioYPassword(e.getNick(), password));
		} catch (Exception e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
	}

	public static void getUsuario() {
		GestorDeEspectaculos g = null;

		try {
			g = new GestorDeEspectaculos();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.exit(-1);
		}

		Espectador e = null;

		try {
			e = g.getUsuario("rick");
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		System.out
				.println(e.getNick() + " " + e.getEmail() + " " + e.getNombreApellidos() + " " + e.getFechaCreacion());

		try {
			e = g.getUsuario("nick");
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		System.out
				.println(e.getNick() + " " + e.getEmail() + " " + e.getNombreApellidos() + " " + e.getFechaCreacion());
	}

	public static void insertarLog() { // DONE
		GestorDeEspectaculos g = null;

		try {
			g = new GestorDeEspectaculos();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.exit(-1);
		}

		try {
			g.actualizarLog("rick", new Date(System.currentTimeMillis()));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
