package business.gestorDeEspectaculos;

import java.io.BufferedReader;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Properties;
import javax.swing.JOptionPane;
import business.abstractFactory.ConcreteFactory;
import business.critica.Critica;
import business.espectaculo.Espectaculo;
import business.espectador.Espectador;
import business.log.Log;
import business.sesion.Sesion;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import data.dao.*;

/**
 * Clase con patrón de diseño Singleton dedicada a la gestión del sistema
 * 
 * @author Ricardo Espantaleón Pérez
 * @author Nicolás López Delgado
 */
public class GestorDeEspectaculos {

	public enum tipo_espectaculo {
		PUNTUAL, MULTIPLE, TEMPORADA;
	};

	static final Properties ficheroPropiedades = new Properties();
	// factory
	ConcreteFactory factoria = new ConcreteFactory();
	UserDAO dao = null;

	/**
	 * Constructor sin parámetros el cual lee el fichero de propiedades del programa
	 * para determinar la ruta de los datos de los usuarios y de las críticas del
	 * sistema
	 * 
	 * @throws FileNotFoundException Lanzará una excepción en caso de no encontrar
	 *                               la ruta indicada
	 * @throws IOException           Se ha producido un error en la entrada/salida
	 * @author Ricardo Espantaleón Pérez
	 */
	public GestorDeEspectaculos() throws IOException {
		try {
			dao = new UserDAO();

		} catch (IOException e) {
			throw new IOException(e);

		}

	}

	/**
	 * Constructor de la clase GestorDeEspectaculos
	 *
	 * @param url      la url de la bd
	 * @param user     el usuario propietario de la bd
	 * @param password la contraseña del propietario de la bd
	 * @throws IOException Si no puede abrir el DAO
	 */
	public GestorDeEspectaculos(String url, String user, String password) throws IOException {
		try {
			dao = new UserDAO(url, user, password);

		} catch (IOException e) {
			throw new IOException(e);

		}

	}

	/**
	 * Constructor de la clase GestorDeEspectaculos
	 *
	 * @param url      la url de la bd
	 * @param user     el usuario propietario de la bd
	 * @param password la contraseña del propietario de la bd
	 * @param fichero  InputStream del fichero de consultas SQL a inicializar en el
	 *                 DAO
	 * @throws IOException Si no puede abrir el DAO
	 */
	public GestorDeEspectaculos(String url, String user, String password, InputStream fichero) throws IOException {
		try {
			dao = new UserDAO(url, user, password, fichero);

		} catch (IOException e) {
			throw new IOException(e);

		}

	}

	/**
	 * Función que comprueba que exista un email ya dado de alta en el sistema
	 * 
	 * @param email Email a comprobar si existe en el sistema
	 * @return Retorna verdadero en caso de que exista y falso en caso contrario
	 * @throws Exception Lanza una excepción genéerica
	 */
	public boolean existeEmail(String email) throws Exception {
		String emailAConsultar;

		try {
			emailAConsultar = dao.getEmail(email);

		} catch (Exception e) {
			throw new Exception(e);

		}

		if (emailAConsultar != null)
			return true;

		return false;
	}

	/**
	 * Función que comprueba que exista un usuario en el sistema
	 * 
	 * @param nick Nickname del usuario
	 * @return Verdadero si existe el usuario y falso si no existe
	 * @author Ricardo Espantaleón Pérez
	 * @throws Exception Lanza una excepción genérica
	 */
	public boolean existeNick(String nick) throws Exception {
		Espectador nickConsulta;

		try {
			nickConsulta = dao.getUsuario(nick);

		} catch (Exception e) {
			throw new Exception(e);
		}

		if (nickConsulta != null)
			return true;

		return false;
	}

	/**
	 * Función que comprueba si está vacía la lista usuarios
	 * 
	 * @return Devuelve verdadero si la lista de usuarios está vacía
	 * @author Ricardo Espantaleón Pérez
	 */
	public boolean listaUsuariosVacia() {
		try {
			if (dao.getEspectaculos() == null)
				return false;
			return true;
		} catch (Exception e) {
			System.err.println(e);
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * Función que comprueba si está vacía la lista críticas
	 * 
	 * @return Devuelve verdadero si la lista de críticas está vacía
	 * @author Ricardo Espantaleón Pérez
	 */
	public boolean listaCriticasVacia() {
		try {
			if (dao.getCriticas() == null)
				return true;

		} catch (Exception e) {
			System.err.println(e);
			e.printStackTrace();
		}

		return false;
	}

	/**
	 * Función que devuelve un usuario si existe en el sistema
	 * 
	 * @throws Exception Lanza una excepción en caso de que no exista el usuario en
	 *                   el sistema
	 * 
	 * @param nick Nickname del usuario
	 * @return Devuelve un usuario existente en el sistema
	 * @author Ricardo Espantaleón Pérez
	 */
	public Espectador getUsuario(String nick) throws Exception {
		if (!existeNick(nick))
			throw new Exception("Usuario no existente en el sistema");
		Espectador e = dao.getUsuario(nick);
		return e;
	}

	/**
	 * Registra un usuario en el sistema, siempre y cuando no exista previamente
	 * 
	 * @param espectador El espectador a insertar en la base de datos
	 * @param password   La contraseña asociada al espectador
	 * @throws Exception Lanza una excepción en caso de que ya exista el usuario en
	 *                   el sistema
	 * @author Ricardo Espantaleón Pérez
	 */
	public void crearUsuario(Espectador espectador, String password) throws Exception {
		if (existeNick(espectador.getNick()))
			throw new Exception("Nick ya registrado");

		if (existeEmail(espectador.getEmail()))
			throw new Exception("Email ya registrado");

		dao.insertarUsuario(espectador, password);
	}

	/**
	 * Función que elimina un usuario (retorna falso si no existe y true en caso
	 * contrario)
	 * 
	 * @throws Exception Lanza una excepción en caso de que no exista el usuario en
	 *                   el sistema
	 * @param nick Nickname del usuario
	 * @author Ricardo Espantaleón Pérez
	 */
	public void eliminarUsuario(String nick) throws Exception {
		if (!existeNick(nick))
			throw new Exception("Usuario no existente en el sistema");
		dao.borrarUsuario(nick);// FALTA QUITAR FUNCIONES
	}

	/**
	 * La función cambia el nombre y apellidos a un usuario
	 * 
	 * @param nick            Identificador del usuario
	 * @param nombreApellidos El nombre nuevo que se quiere cambiar
	 * @throws Exception Lanza una excepción en caso de que no exista el usuario en
	 *                   el sistema
	 * @author Nicolás López Delgado
	 */
	public void actualizarNombreApellidosUsuario(String nick, String nombreApellidos) throws Exception {
		if (!existeNick(nick))
			throw new Exception("Usuario no existente en el sistema");
		dao.actualizarNombreApellidos(nick, nombreApellidos);
	}

	/**
	 * La función que actualiza el email de un usuario
	 * 
	 * @param nick  Identificador de usuario
	 * @param email El email que se quiere cambiar
	 * @throws Exception Lanza una excepción en caso de que no exista el usuario en
	 *                   el sistema
	 * @author Nicolás López
	 */
	public void actualizarEmailUsuario(String nick, String email) throws Exception {
		if (!existeNick(nick))
			throw new Exception("Usuario no existente en el sistema");

		if (existeEmail(email))
			throw new Exception("Email ya existente en el sistema");

		try {
			dao.actualizarEmail(nick, email);
		} catch (Exception error) {
			throw new Exception("El formato del email es incorrecto");
		}
	}

	/**
	 * Función que comprueba si un usuario ha creado una crítica
	 * 
	 * @param critica Critica a comprobar si ha sido creada previamente en el
	 *                sistema por parte de un usuario
	 * @return Verdadero si el usuario ya ha creado dicha crítica y falso en caso
	 *         contrario
	 * @author Ricardo Espantaleón
	 */
	private boolean criticaCreada(Critica critica) {
		try {
			ArrayList<Critica> criticasUsuario = dao.getCriticasAutor(critica.getNickAutor());
			if (criticasUsuario.isEmpty())
				return false;

			for (int i = 0; i < criticasUsuario.size(); ++i) {
				if (criticasUsuario.get(i).getTitulo().equalsIgnoreCase(critica.getTitulo()))
					return true;
			}
		} catch (Exception e) {
			System.err.println(e);
			e.printStackTrace();
		}

		return false;
	}

	/**
	 * La función borra una crítica del vector críticas
	 * 
	 * @param nick   El nick del espectador al que pertenece la crítica
	 * @param titulo El título de la crítica
	 * @throws Exception Salta si no existe una crítica de ese espectador con ese
	 *                   título
	 * @author Nicolás López
	 */
	public void borrarCritica(String nick, String titulo) throws Exception {
		if (!existeCritica(nick, titulo))
			throw new Exception("La crítica no existe");
		dao.borrarCritica(titulo, nick);// HAY QUE CAMBIARLO
	}

	/**
	 * Devuelve todas las críticas del gestor
	 * 
	 * @throws Exception Se lanza si la lista de críticas está vacía
	 * @author Nicolás López
	 * @return un vector con las todas las críticas del sistema
	 */
	public ArrayList<Critica> getCriticas() throws Exception {
		if (listaCriticasVacia())
			throw new Exception("Lista de crítica vacía");

		return dao.getCriticas();
	}

	private boolean existeCritica(String nick, String titulo) {
		if (listaCriticasVacia())
			return false;
		try {
			Critica c = dao.getCriticaAutor(titulo, nick);
			if (c != null) {
				return true;
			}
			return false;
		} catch (Exception e) {
			System.err.println(e);
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * Vota una crítica de otro autor
	 * 
	 * @param nick       Autor de la crítica
	 * @param titulo     Título a la que va diriga la crítica
	 * @param valoracion valor de la nota
	 * @throws Exception Se lanza si no existe el nick inicializado
	 * @throws Exception Se lanza si las valoraciones no están en rangos del 0 al 10
	 * @author Ricardo Espantaleón
	 * @author Nicolás López
	 */
	public void votarCritica(String nickAutorCritica, String nickValoranteCritica, String titulo, float valoracion)
			throws Exception {
		if (!existeNick(nickValoranteCritica) || !existeNick(nickAutorCritica))
			throw new Exception("Usuario no existente en el sistema");

		if (valoracion < 0 || valoracion > 10)
			throw new Exception("Valoraciones deben ser un flotante entre 0 y 10");

		dao.valorarCritica(nickValoranteCritica, nickAutorCritica, titulo, valoracion);
	}

	/**
	 * Función que devuelve las críticas de un usuario específico
	 * 
	 * @param nick El identificador de usuario
	 * @return Un vector con las críticas del usuario
	 * @author Nicolás López
	 */
	public ArrayList<Critica> getCriticasUsuario(String nick) {
		try {
			ArrayList<Critica> criticasUsuario = dao.getCriticasAutor(nick);
			return criticasUsuario;

		} catch (Exception e) {
			System.err.println(e);
			e.printStackTrace();

		}

		return null;
	}

	/**
	 * Función que devuelve la lista de todos los usuarios registrados en el sistema
	 * 
	 * @throws Exception Lanza Una excepción en caso de que la lista de usuarios en
	 *                   el sistema está vacía
	 * @return Devuelve la lista de usuarios del sistema
	 * @author Ricardo Espantaleón
	 */
	public ArrayList<Espectador> getUsuarios() throws Exception {
		if (listaUsuariosVacia())
			throw new Exception("Lista de usuarios vacía");

		return dao.getUsuarios();
	}

	/**
	 * Función que exporta al fichero de propiedades las rutas de los ficheros que
	 * contienen tanto los usuarios como las críticas del gestor
	 * 
	 * @param rutaDatosUsuarios     Ruta virtual del fichero de usuarios
	 * @param rutaDatosCriticas     Ruta virtual del fichero de críticas
	 * @param rutaDatosEspectaculos Ruta virtual del fichero de espectáculos
	 * @throws Exception             Lanzará una excepción genérica en caso de no
	 *                               exportar el fichero de propiedades
	 * @throws FileNotFoundException Lanzará una excepción en caso de no encontrar
	 *                               la ruta indicada
	 * @author Ricardo Espantaleón
	 */

	static public void exportarFicheroPropiedades(String rutaDatosUsuarios, String rutaDatosCriticas,
			String rutaDatosEspectaculos) throws Exception, FileNotFoundException {
		ficheroPropiedades.setProperty("rutaDatosUsuarios", rutaDatosUsuarios);
		ficheroPropiedades.setProperty("rutaDatosCriticas", rutaDatosCriticas);
		ficheroPropiedades.setProperty("rutaDatosEspectaculos", rutaDatosEspectaculos);

		try (final OutputStream outputstream = new FileOutputStream("./config/gestorCriticas.properties");) {
			ficheroPropiedades.store(outputstream, "Archivo actualizado");
			outputstream.close();
		} catch (FileNotFoundException error) {
			throw new Exception("Error, el fichero de propiedades no ha sido encontrado");
		} catch (Exception error) {
			throw new Exception("Error, al exportar el fichero de propiedades");
		}

	}

	/**
	 * Función que importa el fichero de propiedades al gestor
	 * 
	 * @throws IOException Lanzará la excepción en caso de que no se haya podido
	 *                     importar
	 * @author Ricardo Espantaleón
	 */
	static public void importarFicheroPropiedades() throws IOException {
		InputStream entrada = null;

		try {
			entrada = new FileInputStream("./config/gestorCriticas.properties");

			ficheroPropiedades.load(entrada);

		} catch (IOException error) {
			throw new IOException("Error al importar el fichero de propiedades");

		}
	}

	/**
	 * Función que comprueba si está vacía la lista espectáculos
	 * 
	 * @return Devuelve true si está vacía, false si no
	 * @author Nicolás López
	 */
	public boolean listaEspectaculosVacia() {
		try {
			return dao.getEspectaculos().isEmpty();
		} catch (Exception e) {
			System.err.println(e);
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * Función que devuelve todos los espectaculos
	 * 
	 * @return Un array de espectáculos
	 * @throws Exception Excepción genérica que se lanza si la lista está vacía
	 * @author Nicolás López
	 */
	public ArrayList<Espectaculo> getEspectaculos() throws Exception {
		if (listaEspectaculosVacia())
			throw new Exception("Lista vacía");
		return dao.getEspectaculos();
	}

	/**
	 * Función que crea un espectáculo puntual
	 * 
	 * @param titulo      El titulo del espectaculo
	 * @param categorias  La categoria del espectÃ¡culo
	 * @param descripcion Descripcion del espectaculo
	 * @param fecha       La fecha de la sesión del espectáculo
	 * @param localidades Las localidades de la sesión del espectáculo
	 * @author Nicolás López
	 * @throws Exception Lanza una excepción genérica
	 */
	public void crearEspectaculoPuntual(String titulo, String categorias, String descripcion, Date fecha,
			int localidades) throws Exception {
		if (buscarEspectaculoTitulo(titulo) != null)
			throw new Exception("Error, ya existe el espectáculo");

		Espectaculo espectaculo = factoria.crearEspectaculoPuntual(titulo, categorias, descripcion, fecha, localidades);

		try {

			dao.insertarEspectaculo(espectaculo, "PUNTUAL");

		} catch (Exception e) {
			throw new Exception(e);
		}

	}

	/**
	 * Función que crea un espectáculo de pase múltiple
	 * 
	 * @param titulo      El titulo del espectáculo
	 * @param categorias  La categoria del espectáculo
	 * @param descripcion Descripcion del espectáculo
	 * @param fechas      La lista de fechas del espectáculo
	 * @param localidades Las localidades que tiene el sitio donde se celebra
	 * @author Nicolás López
	 * @throws Exception Lanza una excepción genérica
	 */
	public void crearEspectaculoPaseMultiple(String titulo, String categorias, String descripcion,
			ArrayList<Date> fechas, int localidades) throws Exception {
		if (buscarEspectaculoTitulo(titulo) != null)
			throw new Exception("Error, ya existe el espectáculo");

		Espectaculo e = factoria.crearEspectaculoPaseMultiple(titulo, categorias, descripcion, fechas, localidades);
		try {
			dao.insertarEspectaculo(e, "MULTIPLE");
		} catch (Exception error) {
			throw new Exception(error);
		}

	}

	/**
	 * Función que crea un espectáculo de temporada
	 * 
	 * @param titulo        El titulo del espectáculo
	 * @param categorias    La categoria del espectáculo
	 * @param descripcion   Descripcion del espectáculo
	 * @param fechaInicio   La fecha de inicio del espectáculo
	 * @param nEspectaculos Número de espectáculos con los que contará el pase
	 * @param localidades   Las localidades que tiene el sitio donde se celebra
	 * @author Nicolás López
	 * @throws Exception Lanza una excepción genérica
	 */
	public void crearEspectaculoTemporada(String titulo, String categorias, String descripcion, Date fechaInicio,
			int nEspectaculos, int localidades) throws Exception {
		if (buscarEspectaculoTitulo(titulo) != null)
			throw new Exception("Error, ya existe el espectáculo");

		Espectaculo espectaculo = factoria.crearEspectaculoTemporada(titulo, categorias, descripcion, fechaInicio,
				nEspectaculos, localidades);

		try {

			dao.insertarEspectaculo(espectaculo, "TEMPORADA");
		} catch (Exception e) {
			throw new Exception(e);
		}
	}

	/**
	 * Función que vende las entradas de una sesión
	 * 
	 * @param id El identificador de la sesión
	 * @author Nicolás López
	 * @throws Exception Lanza una excepción genérica en caso de error en la base de
	 *                   datos, o en caso de que no pueda contabilizarse la venta de
	 *                   la entrada
	 */
	public void venderEntradas(int id) throws Exception {
		if (dao.getSesion(id).getLocalidades() == 0)
			throw new Exception("No quedan entradas disponibles");

		try {
			dao.actualizarVentaEntrada(id);
		} catch (Exception e) {
			throw new Exception(e);
		}
	}

	/**
	 * Función que busca un espectaculo por su título
	 * 
	 * @param titulo El título del espectaculo
	 * @return Retorna el espectáculo asociado al título
	 * @author Ricardo Espantaleón
	 * @throws Exception Lanza una excepción genérica
	 */
	public Espectaculo buscarEspectaculoTitulo(String titulo) throws Exception {
		Espectaculo espectaculo;

		try {
			espectaculo = dao.getEspectaculo(titulo);
		} catch (Exception e) {
			throw new Exception(e);
		}

		return espectaculo;
	}

	/**
	 * Función que busca un espectáculo por su título
	 * 
	 * @param categorias La categoría del espectÃ¡clo
	 * @return El indice del vector donde se encuentra o -1 si no se encuentra
	 * @author Nicolás López
	 * @throws Exception Lanza una excepción genérica si no existe ningún
	 *                   espectáculo con esa categoria
	 */
	public ArrayList<Espectaculo> buscarEspectaculoCategoria(String categorias) throws Exception {

		if (dao.getEspectaculosCategoria(categorias) == null)
			throw new Exception("Error, no existe ningún espectáculo con esa categoria");

		return dao.getEspectaculosCategoria(categorias);
	}

	/**
	 * Función que busca una sesion en concreto dentro de un espectáculo a partir de
	 * su fecha y hora
	 * 
	 * @param indice El indice en el que buscar
	 * @param fecha  La fecha y hora del espectáculo
	 * @return El indice del vector donde se encuentra o -1 si no se encuentra
	 * @author Nicolás López
	 */

	/*
	 * public int buscarFecha(int indice, Date fecha) { ArrayList<Espectaculo>
	 * espectaculos= dao.devolverEspectaculos(); for (int i = 0; i <
	 * espectaculos.get(indice).getSesiones().size(); i++) { if
	 * (fecha.compareTo(espectaculos.get(indice).getSesiones().get(i).getFecha()) ==
	 * 0) { return i; } } return -1; }
	 */

	/**
	 * Función que consulta las entradas vendidas de una sesión
	 *
	 * @param sesion Clase de tipo Sesion que contiene una sesión de un espectáculo
	 * @return Las entradas vendidas de la sesión
	 * @author Nicolás López
	 */
	public int consultarEntradasVendidas(Sesion sesion) {
		return sesion.getVendidas();
	}

	/**
	 * Función que comprueba que existe un espectáculo en el gestor
	 * 
	 * @param titulo Título del espectáculo
	 * @return Retorna verdadero en caso de que exista el espectáculo y falso en
	 *         caso contrario
	 * @author Nicolás López
	 */
	public boolean existeEspectaculo(String titulo) {
		try {
			if (dao.getEspectaculo(titulo) == null)
				return false;
		} catch (Exception e) {
			System.err.println(e);

		}
		return true;

	}

	/**
	 * Función que elimina un espectáculo y todas sus sesiones en cuestión
	 * 
	 * @param titulo Título del espectáculo a eliminar
	 * @throws Exception Lanza una excepción en caso de que el espectáculo no exista
	 * 
	 * @author Ricardo Espantaleón
	 */
	public void cancelarTodasSesionesEspectaculo(String titulo) throws Exception {
		if (dao.getEspectaculo(titulo) == null)
			throw new Exception("El espectáculo no existe en el sistema\n\n");
		dao.cancelarTodasSesionesEspectaculo(titulo);

	}

	/**
	 * Función que comprueba si existe una sesión en concreto en el sistema
	 * 
	 * @param sesion Sesión a comprobar si existe en el sistema
	 * @return Verdadero en caso de que exista y falso en caso contrario
	 * @throws Exception Lanza una excepción en caso de que la sesión no exista
	 * @author Ricardo Espantaleón
	 */
	public boolean existeSesion(Sesion sesion) throws Exception {
		ArrayList<Sesion> sesiones = null;
		try {
			for (int i = 0; i < dao.getEspectaculos().size(); ++i) {
				sesiones = dao.getEspectaculos().get(i).getSesiones();

				for (int j = 0; j < sesiones.size(); ++j) {
					if (sesiones.get(j).equals(sesion))
						return true;
				}
			}
		} catch (Exception e) {
			throw new Exception(e);
		}

		return false;
	}

	/**
	 * Función que cancela una sesión en particular del sistema
	 * 
	 * @param id El identificador de la sesión
	 * @throws Exception Lanzará una excepción en caso de que no exista el
	 *                   espectáculo o la sesión
	 * @author Ricardo Espantaleón
	 */
	public void cancelarSesionEspectaculo(int id) throws Exception {
		dao.borrarSesionEspectaculo(id);
	}

	/**
	 * Función que devuelve un espectáculo en particular
	 * 
	 * @param titulo Título del espectáculo a buscar
	 * @return Retorna la instancia el espectáculo
	 * @throws Exception Lanza una excepción en caso de que no exista el espectáculo
	 * 
	 * @author Ricardo Espantaleón
	 */
	public Espectaculo getEspectaculo(String titulo) throws Exception {
		if (dao.getEspectaculo(titulo) == null)
			throw new Exception("El espectáculo no existe en el sistema\n\n");

		return dao.getEspectaculo(titulo);
	}

	/**
	 * Función que actualiza los datos de un espectáculo
	 *
	 * @param titulo      Título del espectáculo a buscar del cual queramos
	 *                    actualizar sus datos
	 * @param categoria   Categoría nueva a asignar
	 * @param descripcion Descripción nueva a asignar
	 * @throws Exception Lanza una excepción en caso de que no exista el espectáculo
	 * @author Ricardo Espantaleón
	 */
	public void actualizarDatosEspectaculo(String titulo, String categoria, String descripcion) throws Exception {
		if (existeEspectaculo(titulo) == false)
			throw new Exception("No existe ese espectáculo en el sistema\n");

		if (categoria != "")
			dao.actualizarCategoriaEspectaculo(titulo, categoria);

		if (descripcion != "")
			dao.actualizarDescripcionEspectaculo(titulo, descripcion);
	}

	/**
	 * Función que me devuelve los espectáculos disponibles de una categoría
	 * 
	 * @param categoria Categoría de espectáculos a buscar
	 * @return Retorna un ArrayList de espectáculos de una categoría en concreto
	 * @see ArrayList
	 * @throws Exception Lanza una excepción en caso de que no exista el espectáculo
	 * @author Ricardo Espantaleón
	 */
	public ArrayList<Espectaculo> espectaculosDisponibles(String categoria) throws Exception {
		ArrayList<Espectaculo> espectaculo;
		try {
			espectaculo = dao.getEspectaculosCategoria(categoria);
		} catch (Exception e) {
			throw new Exception(e);
		}
		return espectaculo;
	}

	/**
	 * Función que me devuelve todos los espectáculos disponibles
	 * 
	 * @return Retorna un ArrayList de todos los espectáculos disponibles
	 * @see ArrayList
	 * @throws Exception Lanza una excepción en caso de que no exista el espectáculo
	 * @author Ricardo Espantaleón
	 */
	public ArrayList<Espectaculo> espectaculosDisponibles() throws Exception {
		ArrayList<Espectaculo> espectaculosDisponibles = new ArrayList<Espectaculo>();
		try {
			ArrayList<Espectaculo> espectaculos = dao.getEspectaculos();
			for (int i = 0; i < espectaculos.size(); ++i) {
				if (espectaculos.get(i).entradasDisponibles())
					espectaculosDisponibles.add(espectaculos.get(i));
			}
		} catch (Exception e) {
			throw new Exception(e);
		}
		return espectaculosDisponibles;
	}

	/**
	 * Función que me retorna todas las críticas de un espectáculo
	 * 
	 * @param titulo Título del espectáculo a encontrar
	 * @return Retorna un arrayList de todas las críticas asociados al espectáculo
	 * @throws Exception Lanza una excepción en caso de que el espectáculo no exista
	 * 
	 * @author Ricardo Espantaleón
	 */
	public ArrayList<Critica> consultarCriticas(String titulo) throws Exception {
		ArrayList<Critica> criticasEspectaculo = new ArrayList<Critica>();
		ArrayList<Critica> criticas = dao.getCriticas();

		if (!existeEspectaculo(titulo))
			throw new Exception("El espectáculo introducido no existe en el sistema\n\n");

		for (int i = 0; i < criticas.size(); ++i) {
			if (criticas.get(i).getTitulo().equalsIgnoreCase(titulo))
				criticasEspectaculo.add(criticas.get(i));
		}

		return criticasEspectaculo;
	}

	// Revisar la clase Critica, la cual recibe un espectaculo como variable, no es
	// eficiente, mejor asignar la variable titulo a espectaculo.titulo

	/**
	 * La función añade una crítica nueva al vector
	 * 
	 * @param critica La crítica ya inicializada
	 * @exception Exception lanza si la crítica ya existe o si el espectáculo
	 *                      asociado todavía no se ha celebrado
	 * @author Nicolás López
	 * 
	 */
	public void crearCritica(Critica critica) throws Exception {
		if (criticaCreada(critica))
			throw new Exception("Crítica ya existente en el sistema");

		Espectaculo e = dao.getEspectaculo(critica.getTitulo());

		if (e == null)
			throw new Exception("No existe ningún espectáculo con ese título en el sistema\n\n");

		for (int i = 0; i < dao.getEspectaculo(critica.getTitulo()).getSesiones().size(); i++) {

			if (!fechaMayorActual(dao.getEspectaculo(critica.getTitulo()).getSesiones().get(i).getFecha())) {
				// Por tanto no se ha celebrado
				dao.insertarCritica(critica);
				return;
			}
		}

		throw new Exception("La crítica debe crearse una vez haya ocurrido el espectáculo\n\n");
	}

	/**
	 * La función devuelve el número de entradas de un espectáculo
	 * 
	 * @param titulo El título del espectáculo
	 * @param fecha  La fecha y hora del espectáculo
	 * @return El número de entradas de dicho espectáculo
	 * @throws Exception Lanza una excepción si no encuentra el espectáculo
	 * @author Nicolás López
	 */
	public int getNumeroEntradas(String titulo, Date fecha) throws Exception {
		if (dao.getEspectaculo(titulo) == null) {
			throw new Exception("No existe espectáculo");
		}
		Espectaculo espectaculo = dao.getEspectaculo(titulo);
		for (int i = 0; i < espectaculo.getSesiones().size(); i++) {
			if (espectaculo.getSesiones().get(i).getFecha().equals(fecha))
				return espectaculo.getSesiones().get(i).getVendidas();
		}
		return -1;
	}

	/**
	 * La función devuelve el número de localidades de un espectáculo
	 * 
	 * @param titulo El título del espectáculo
	 * @param fecha  La fecha y hora del espectáculo
	 * @return El número de localidades de dicho espectáculo
	 * @throws Exception Lanza una excepción si no encuentra el espectáculo
	 * @author Nicolás López
	 */
	public int getNumeroLocalidades(String titulo, Date fecha) throws Exception {
		if (existeEspectaculo(titulo) == false)
			throw new Exception("No existe tal espectáculo");
		Espectaculo e = dao.getEspectaculo(titulo);
		int index = -1;
		for (int i = 0; i < e.getSesiones().size(); i++) {
			if (e.getSesiones().get(i).getFecha().equals(fecha))
				index = i;
		}
		if (index == -1)
			throw new Exception("No existe sesión con esa fecha");
		return dao.getEspectaculo(titulo).getSesiones().get(index).getLocalidades();
	}

	/**
	 * Función que me devuelve un próximo espectáculo
	 * 
	 * @param titulo El titulo del espectáculo
	 * @return Un array con los proximos espectáculos que se realizarán
	 * @throws Exception Lanza excepción genérica si no existe
	 * @author Nicolás López
	 */
	public ArrayList<Sesion> proximoEspectaculo(String titulo) throws Exception {
		if (existeEspectaculo(titulo) == false) {
			throw new Exception("No existe el espectáculo");
		}
		Espectaculo e = dao.getEspectaculo(titulo);
		ArrayList<Sesion> sesionesFecha = new ArrayList<Sesion>();
		for (int i = 0; i < e.getSesiones().size(); i++) {
			if (fechaMayorActual(e.getSesiones().get(i).getFecha()) == true) {
				sesionesFecha.add(e.getSesiones().get(i));
			}
		}
		return sesionesFecha;
	}

	/**
	 * Función que filtra espectáculos por su categoría
	 * 
	 * @param categoria La categoría del espectáculo
	 * @return Un array de los espectáculos de la categoría indicada
	 * @author Nicolás López
	 * @throws Exception Lanza una excepción genérica si no existen espectáculos de
	 *                   la categoría
	 */
	public ArrayList<Espectaculo> getEspectaculosCategoria(String categoria) throws Exception {
		ArrayList<Espectaculo> espectaculosCategoria = dao.getEspectaculosCategoria(categoria);
		if (dao.getEspectaculosCategoria(categoria) == null)
			throw new Exception("No existen espectáculos de esta categoría");
		return espectaculosCategoria;
	}

	/**
	 * Función que compara 2 fechas, la actual y la del espectáculo
	 * 
	 * @param fechaEspectaculo Fecha de la sesion que se quiere comprobar
	 * @return Verdadero si la fecha del espectáculo es mayor a la actual y falso si
	 *         no lo es
	 * @author Enrique Estevez Mayoral
	 */
	static public boolean fechaMayorActual(Date fechaEspectaculo) {

		Date fechaActual = new Date();

		if (fechaEspectaculo.compareTo(fechaActual) > 0)
			return true;

		return false;
	}

	/**
	 * Función que compara 2 fechas, la actual y la del espectáculo
	 * 
	 * @param fechaEspectaculo Fecha de la sesion que se quiere comprobar
	 * @return Verdadero si la fecha del espectáculo es menor a la actual y falso si
	 *         no lo es
	 * @author Enrique Estevez Mayoral
	 */
	static public boolean fechaMenorActual(Date fechaEspectaculo) {

		Date fechaActual = new Date();

		if (fechaEspectaculo.compareTo(fechaActual) < 0)
			return true;

		return false;
	}

	/**
	 * Función que indica si un usuario es administrador o no
	 * 
	 * @param nick El nick del usuario
	 * @return Devuelve true si el usuario es administrador y false si no lo es
	 * @author Nicolás López
	 */
	public boolean esUsuarioAdministrador(String nick) {
		try {
			Espectador admin = dao.getUsuario(nick);
			if (admin != null && admin.esAdmin() == 1)
				return true;

		} catch (Exception e) {
			System.err.println(e);

		}

		return false;
	}

	/**
	 * Función que devuelve las sesiones que van despues de una fecha en concreto
	 * 
	 * @param fecha La fecha de la sesión
	 * @return Devuelve un array con todas las sesiones posteriores a la fecha
	 *         pasada por argumentos
	 * @throws Exception Lanza una excepción genérica si no hay sesiones posteriores
	 * @author Ricardo Espantaleón
	 */
	public ArrayList<Sesion> getSesionesPosteriores(Date fecha) throws Exception {
		if (dao.getSesionesPosteriorFecha(fecha) == null)
			throw new Exception("No hay sesiones posteriores");

		return dao.getSesionesPosteriorFecha(fecha);
	}

	/**
	 * Función que comprueba que dado un nick introducido la contraseña asociada al
	 * usuario es correcta
	 * 
	 * @param nick     Nickname del usuario a comprobar
	 * @param password Contraseña del registro a comprobar
	 * @return Verdadero en caso de que la contraseña introducida corresponde a la
	 *         del usuario
	 * @throws Exception Lanza excepciones genéricas en caso de error en la función
	 *                   del DAO
	 * 
	 * @author Ricardo Espantaleón
	 */
	public boolean compruebaUsuarioYPassword(String nick, String password) throws Exception {
		Espectador usuario = null;

		try {
			usuario = dao.getUsuario(nick, password);
		} catch (Exception e) {
			throw new Exception(e);

		}

		return usuario != null;
	}

	/**
	 * Función que inserta valores en el log de la base de datos
	 * 
	 * @param nick           Nickname del usuario a insertar en la base de datos
	 * @param ultimaConexion Fecha de la última conexión al servidor web
	 * @throws Exception Lanza excepción en caso de error en la base de datos
	 * 
	 * @author Ricardo Espantaleón Pérez
	 */
	public void actualizarLog(String nick, Date ultimaConexion) throws Exception {
		if (!existeNick(nick))
			throw new Exception("El usuario no existe en el sistema");

		try {
			dao.insertaRegistroLog(nick, ultimaConexion);

		} catch (Exception e) {
			throw new Exception(e);
		}

	}

	/**
	 * Función que retorna los registros del Log de la base de datos
	 * 
	 * @return Retorna un vector con las distintas tuplas del Log hasta el momento
	 * @throws Exception Lanza excepción en caso de error en la base de datos
	 */
	public ArrayList<Log> getRegistrosLog() throws Exception {
		ArrayList<Log> registros = new ArrayList<Log>();

		try {
			registros = dao.getRegistrosLog();

		} catch (Exception e) {
			throw new Exception(e);
		}

		return registros;
	}

	/**
	 * Función que agrega una sesión de un espectáculo concreto
	 * 
	 * @param sesion Sesion a insertar del espectáculo
	 * @throws Exception Lanzará excepción en caso de error en los datos de entrada
	 *                   o en caso de error en la base de datos
	 */
	public void agregarSesion(Sesion sesion) throws Exception {
		if (fechaMenorActual(sesion.getFecha())) {
			throw new Exception("La fecha no es válida");
		}
		if (sesion.getLocalidades() < 1) {
			throw new Exception("Las localidades no son válidas");
		}

		try {
			dao.insertarSesionEspectaculo(sesion);
		} catch (Exception e) {
			throw new Exception(e);
		}
	}

	/**
	 * Función que modifica una sesión existente en el sistema
	 * 
	 * @param sesion Sesión nueva modificada a insertar en la base de datos
	 * @throws Exception Lanzará excepción en caso de error en los datos de entrada
	 *                   o en caso de error en la base de datos
	 */
	public void actualizarSesion(Sesion sesion) throws Exception {
		if (fechaMenorActual(sesion.getFecha())) {
			throw new Exception("La fecha no es válida");
		}
		if (sesion.getLocalidades() < 1) {
			throw new Exception("Las localidades no son válidas");
		}

		try {
			dao.modificarSesion(sesion);
		} catch (Exception e) {
			throw new Exception(e);
		}

	}

	/**
	 * Función que retorna la cadena del tipo de Espectáculo a consultar
	 * 
	 * @param titulo Título del espectáculo a consultar
	 * @return Retorna la cadena asociada al tipo de espectáculo
	 * 
	 * @throws Exception Lanzará excepción en caso de error en la base de datos, o
	 *                   violación de aserto
	 */
	public String getTipoEspectaculo(String titulo) throws Exception {
		if (!existeEspectaculo(titulo))
			throw new Exception("El espectáculo no existe");

		tipo_espectaculo tipo = null;

		try {
			tipo = dao.getTipoEspectaculo(titulo);
		} catch (Exception e) {
			throw new Exception(e);
		}

		if (tipo == tipo_espectaculo.PUNTUAL)
			return "Puntual";

		else if (tipo == tipo_espectaculo.MULTIPLE)
			return "Múltiple";

		return "Temporada";
	}

	/**
	 * Función que cancela un espectáculo del sistema
	 * 
	 * @param titulo Titulo del espectáculo a cancelar
	 * @throws Exception Lanzará excepción en caso de error en la base de datos, o
	 *                   violación de aserto
	 */
	public void cancelarEspectaculo(String titulo) throws Exception {
		if (!existeEspectaculo(titulo))
			throw new Exception("El espectáculo no existe");

		try {
			// Primero cancelo todas las sesiones asociadas al espectáculo
			dao.cancelarTodasSesionesEspectaculo(titulo);

			// Eliminamos el espectáculo en cuestión
			dao.borrarEspectaculo(titulo);

		} catch (Exception e) {
			throw new Exception(e);
		}
	}

	public Sesion getSesion(int id) throws Exception {
		Sesion sesion = null;
		sesion = dao.getSesion(id);
		if (sesion == null) {
			throw new Exception("La sesión no existe");
		}
		return sesion;
	}

	/**
	 * Función que retorna los espectáculos que puede valorar un usuario; ya que
	 * este solamente podrá valorar aquellos espectáculos los cuales no haya
	 * valorado previamente
	 * 
	 * @param nick Nickname del usuario del cual consultar los espectáculos
	 *             valorables
	 * @return Retorna una lista de espectáculos posibles a valorar
	 * @throws Exception Lanzará excepción en caso de error en la base de datos
	 * 
	 * @author Ricardo Espantaleón
	 */
	public ArrayList<Espectaculo> getEspectaculosAValorarPorUsuario(String nick) throws Exception {
		ArrayList<Espectaculo> espectaculos = null;

		try {

			espectaculos = dao.consultarEspectaculosAValorar(nick);

		} catch (Exception e) {
			throw new Exception(e);
		}

		return espectaculos;
	}

	public ArrayList<Critica> getCriticasTitulo(String titulo) throws Exception {
		ArrayList<Critica> critica = dao.consultarCriticasEspectaculo(titulo);
		if (critica == null) {
			throw new Exception("No hay críticas de ese Espectáculo");
		}
		return critica;
	}

	/**
	 * Funcion que devuelve un int con las aparaciones de una valoracion
	 * 
	 * @param nick      Nickname del usuario del cual consultar los espectáculos
	 *                  valorables
	 * @param nickAutor nick de la persona que realiza la crítica
	 * @param titulo    titulo del espectáculo en cuestión
	 * @return Retorna las apariciones de una valoracion
	 * @throws Exception Lanzará excepción en caso de error en la base de datos
	 * 
	 * @author Ricardo Espantaleón
	 */
	public int estaValorado(String nick, String nickAutor, String titulo) throws Exception {
		int cont = 0;

		try {
			cont = dao.existeValoracionCritica(nickAutor, titulo, nick);
		} catch (Exception e) {
			throw new Exception(e);
		}

		return cont;
	}

	/**
	 * Función que obtiene la valoración asociada un espectáculo dada sus críticas
	 * 
	 * @param titulo Título del espectáculo del cual consultar la nota media
	 * @return Retorna la valoración del espectáculo consultado
	 * @throws Exception Lanzará excepción en caso de que no exista el espectáculo o
	 *                   en caso de error en la base de datos
	 */
	public float getValoracionEspectaculo(String titulo) throws Exception {
		if (!existeEspectaculo(titulo))
			throw new Exception("El espectáculo no existe en el sistema");

		float valoracion = 0;

		try {
			valoracion = dao.getPuntuacionEspectaculo(titulo);

		} catch (Exception e) {
			throw new Exception(e);

		}

		return valoracion;
	}

	/**
	 * Función que retorna la valoración media de una crítica de un espectáculo
	 * concreto
	 * 
	 * @param titulo    Título del espectáculo al cual va dirigo la crítica
	 * @param nickAutor Nick del autor de la crítica
	 * @return Retorna la valoración media a la crítica
	 * @throws Exception Lanzará excepción en caso de error en la base de datos o en
	 *                   caso de que no exista ni el espectáculo o el nick autor de
	 *                   la crítica
	 * 
	 * @author Ricardo Espantaleón Pérez
	 */
	public float getValoracionCritica(String titulo, String nickAutor) throws Exception {
		if (!existeEspectaculo(titulo))
			throw new Exception("El espectáculo no existe en el sistema");

		if (!existeNick(nickAutor))
			throw new Exception("El usuario no existe en el sistema");

		float valoracion = 0;

		try {
			valoracion = dao.getPuntuacionCritica(titulo, nickAutor);

		} catch (Exception e) {
			throw new Exception(e);

		}

		return valoracion;

	}
}