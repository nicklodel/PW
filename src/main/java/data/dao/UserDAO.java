package data.dao;

import java.io.FileInputStream;

import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.Properties;

import com.mysql.jdbc.ResultSet;
import data.common.BDConnection;
import business.critica.Critica;
import business.encryptor.Encryptor;
import business.espectaculo.Espectaculo;
import business.espectaculo.EspectaculoPaseMultiple;
import business.espectaculo.EspectaculoPuntual;
import business.espectaculo.EspectaculoTemporada;
import business.espectador.Espectador;
import business.gestorDeEspectaculos.GestorDeEspectaculos.tipo_espectaculo;
import business.log.Log;
import business.sesion.Sesion;

/**
 * La clase DAO que se conecta a la base de datos MySQL por medio de JDBC
 * 
 * @author Ricardo Espantaleón
 * @author Enrique Estévez
 * @author Nicolás López
 */

public class UserDAO {

	static final Properties ficheroPropiedades = new Properties();

	private BDConnection dbConnection = null;

	/**
	 * Función que importa el fichero de propiedades, donde están las consultas
	 * 
	 * @author Ricardo Espantaleón
	 * @throws IOException Excepción producida cuando ocurre un error en las
	 *                     operaciones de entrada y salida
	 */
	public UserDAO() throws IOException {
		InputStream entrada = null;

		try {
			// LA RUTA NO FUNCIONA EN LA PAGINA WEB
			entrada = new FileInputStream(
					"/home/ricardo/Universidad/Tercero/PW/P2/Ejercicio2/src/main/webapp/files/queries/queries.properties");

			ficheroPropiedades.load(entrada);

			// BDConnection dbConnection = new BDConnection();

		} catch (IOException error) {
			throw new IOException(error);

		}
	}

	/**
	 * Constructor que inicializa el conector a la base de datos
	 * 
	 * @param url      La url de la base de datos
	 * @param user     El usuario de la base de datos a la base de datos que
	 *                 accederemos
	 * @param password La password de la base de datos a la que accederemos
	 * @throws IOException Lanza una excepción en caso de no poder importar el
	 *                     fichero de propiedades
	 */
	public UserDAO(String url, String user, String password) throws IOException {
		InputStream entrada = null;

		try {
			// LA RUTA NO FUNCIONA EN LA PAGINA WEB
			entrada = new FileInputStream(
					"/home/ricardo/Universidad/Tercero/PW/P2/Ejercicio2/src/main/webapp/files/queries/queries.properties");

			ficheroPropiedades.load(entrada);

			// BDConnection dbConnection = new BDConnection();

		} catch (IOException error) {
			throw new IOException(error);

		}

		dbConnection = new BDConnection(url, user, password);

	}

	/**
	 * Constructor que inicializa el conector a la base de datos
	 * 
	 * @param url      La url de la base de datos
	 * @param user     El usuario de la base de datos a la base de datos que
	 *                 accederemos
	 * @param password La password de la base de datos a la que accederemos
	 * @param fichero  InputStream del fichero de consultas SQL a inicializar en el
	 *                 DAO
	 * @throws IOException Lanza una excepción en caso de no poder importar el
	 *                     fichero de propiedades
	 */
	public UserDAO(String url, String user, String password, InputStream fichero) throws IOException {
		InputStream entrada = fichero;

		try {
			// LA RUTA NO FUNCIONA EN LA PAGINA WEB
			ficheroPropiedades.load(entrada);

			// BDConnection dbConnection = new BDConnection();

		} catch (IOException error) {
			throw new IOException(error);

		}

		dbConnection = new BDConnection(url, user, password);

	}

	/**
	 * Función que se conecta a la base de datos y devuelve todos los usuarios que
	 * hay en la base de datos
	 * 
	 * @return Un array de tipo espectador que contiene todos los espectadores que
	 *         hay en la base de datos, devuelve null en caso de que no haya nada en
	 *         la base de datos
	 * @author Ricardo Espantaleón
	 * @author Enrique Estévez
	 * @throws SQLException Lanza excepciones de tipo SQL en caso de cualquier fallo
	 *                      relacionado con la base de datos
	 * @see SQLException
	 */
	public ArrayList<Espectador> getUsuarios() throws Exception {
		ArrayList<Espectador> usuarios = new ArrayList<Espectador>();

		try {

			Connection connection = dbConnection.getConnection();

			String query = ficheroPropiedades.getProperty("consultasTodosUsuarios");

			Statement stmt = connection.createStatement();
			ResultSet rs = (ResultSet) stmt.executeQuery(query);

			while (rs.next()) {

				usuarios.add(new Espectador(rs.getString("nick"), rs.getString("nombre_y_apellidos"),
						rs.getString("email")));
			}

			if (stmt != null) {
				stmt.close();
			}

			// Si stmt == null entonces no existe esa consulta

			dbConnection.closeConnection();

		} catch (SQLException e) {
			throw new SQLException(e);
		}

		if (usuarios.size() == 0)
			return null;

		return usuarios;
	}

	/**
	 * Función que se conecta a la base de datos y devuelve todas las críticas del
	 * autor pasado por argumento en el caso de que exista en la base de datos
	 * 
	 * @param autor Autor de las críticas
	 * @return Todas las críticas del autor que existen en la base de datos,
	 *         devuelve null en caso de que no esté en la base de datos
	 * @author Enrique Estévez
	 * @throws SQLException Lanza excepciones de tipo SQL en caso de cualquier fallo
	 *                      relacionado con la base de datos
	 * @see SQLException
	 */
	public ArrayList<Critica> getCriticasAutor(String autor) throws Exception {
		ArrayList<Critica> tituloADevolver = new ArrayList<Critica>();

		try {

			Connection connection = dbConnection.getConnection();
			// Important: This query is hard-coded here for illustrative purposes only

			// "select nick from Usuario where nick = "rick" "
			String query = String.format("%s \"%s\"", ficheroPropiedades.getProperty("consultaCriticasAutor"), autor);

			// Important: We can replace this direct invocation to CRUD operations in
			// DBConnection
			Statement stmt = connection.createStatement();
			ResultSet rs = (ResultSet) stmt.executeQuery(query);

			while (rs.next()) {

				tituloADevolver.add(new Critica(rs.getString("titulo_espectaculo"), rs.getFloat("puntuacion"),
						rs.getString("comentario"), rs.getString("nick_autor")));
			}

			if (stmt != null) {
				stmt.close();
			}

			dbConnection.closeConnection();

		} catch (SQLException e) {
			throw new SQLException(e);
		}

		return tituloADevolver;
	}

	/**
	 * Función que se conecta a la base de datos y devuelve la crítica de un autor
	 * pasado por argumento en el caso de que exista en la base de datos
	 * 
	 * @param titulo Título de la crítica
	 * @param autor  Autor de la crítica
	 * @return El autor de la crítica en la base de datos, devuelve null en caso de
	 *         que no esté en la base de datos
	 * @author Enrique Estévez
	 * @throws SQLException Lanza excepciones de tipo SQL en caso de cualquier fallo
	 *                      relacionado con la base de datos
	 * @see SQLException
	 */
	public Critica getCriticaAutor(String titulo, String autor) throws Exception {
		Critica criticaADevolver = null;
		try {

			Connection connection = dbConnection.getConnection();
			// Important: This query is hard-coded here for illustrative purposes only

			// "select nick from Usuario where nick = "rick" "
			String query = String.format("%s", ficheroPropiedades.getProperty("consultaCriticaAutor"));
			PreparedStatement ps = connection.prepareStatement(query);
			ps.setString(1, titulo);
			ps.setString(2, autor);

			// Important: We can replace this direct invocation to CRUD operations in
			// DBConnection

			ResultSet rs = (ResultSet) ps.executeQuery();

			while (rs.next()) {

				criticaADevolver = new Critica(rs.getString("titulo_espectaculo"), rs.getFloat("puntuacion"),
						rs.getString("comentario"), rs.getString("nick_autor"));
			}

			dbConnection.closeConnection();

		} catch (SQLException e) {
			throw new SQLException(e);
		}

		return criticaADevolver;
	}

	/**
	 * Función que se conecta a la base de datos y devuelve todos las críticas que
	 * hay en la base de datos
	 * 
	 * @return Un array de tipo Critica que contiene todos las criticas que hay en
	 *         la base de datos o devuelve null en caso de que no haya nada en la
	 *         base de datos
	 * @author Enrique Estévez
	 * @throws SQLException Lanza excepciones de tipo SQL en caso de cualquier fallo
	 *                      relacionado con la base de datos
	 * @see SQLException
	 */
	public ArrayList<Critica> getCriticas() throws Exception {
		ArrayList<Critica> criticas = new ArrayList<Critica>();
		try {

			Connection connection = dbConnection.getConnection();
			// Important: This query is hard-coded here for illustrative purposes only
			String query = ficheroPropiedades.getProperty("consultarTodasCriticas");

			// Important: We can replace this direct invocation to CRUD operations in
			// DBConnection
			Statement stmt = connection.createStatement();
			ResultSet rs = (ResultSet) stmt.executeQuery(query);

			while (rs.next()) {

				criticas.add(new Critica(rs.getString("titulo_espectaculo"), rs.getFloat("puntuacion"),
						rs.getString("comentario"), rs.getString("nick_autor")));
			}

			if (stmt != null) {
				stmt.close();
			}

			// Si stmt == null entonces no existe esa consulta

			dbConnection.closeConnection();

		} catch (SQLException e) {
			throw new SQLException(e);
		}

		if (criticas.size() == 0)
			return null;

		return criticas;
	}

	/**
	 * Función que inserta una tupla en la tabla Usuarios de la base de datos
	 * 
	 * @param usuario  El usuario que queremos inserta en la tabla Usuarios
	 * @param password Contraseña del usuario a insertar
	 * @author Ricardo Espantaleón
	 * @author Enrique Estévez
	 * @throws SQLException Lanza excepciones de tipo SQL en caso de cualquier fallo
	 *                      relacionado con la base de datos
	 * @see SQLException
	 */
	public void insertarUsuario(Espectador usuario, String password) throws Exception {
		try {

			Connection connection = dbConnection.getConnection();

			// Inserta la fecha actual del sistema, dado que es el momento en el cual se
			// creó el usuario
			Timestamp ts = new Timestamp(System.currentTimeMillis());

			PreparedStatement ps = connection.prepareStatement(ficheroPropiedades.getProperty("insertarUsuario"));
			ps.setString(1, usuario.getNick());
			ps.setString(2, usuario.getEmail());
			ps.setString(3, usuario.getNombreApellidos());
			ps.setInt(4, usuario.esAdmin());

			// Encripto la password
			String encryptedPassword = Encryptor.encryptPassword(password);

			ps.setString(5, encryptedPassword);
			ps.setTimestamp(6, ts);

			ps.executeUpdate();

			dbConnection.closeConnection();

		} catch (SQLException e) {
			throw new SQLException(e);
		}

	}

	/**
	 * Función que actualiza el email del usuario cuyo nickname sea el pasado por
	 * argumentos
	 * 
	 * @param nick  El nickname del usuario al que se le actualizara el email
	 * @param email El nuevo email que tendrá el ususario
	 * @author Enrique Estévez
	 * @throws SQLException Lanza excepciones de tipo SQL en caso de cualquier fallo
	 *                      relacionado con la base de datos
	 * @see SQLException
	 */
	public void actualizarEmail(String nick, String email) throws Exception {
		try {

			Connection connection = dbConnection.getConnection();
			PreparedStatement ps = connection.prepareStatement(ficheroPropiedades.getProperty("actualizarEmail"));

			ps.setString(1, email);
			ps.setString(2, nick);

			ps.executeUpdate();

			dbConnection.closeConnection();

		} catch (SQLException e) {
			throw new SQLException(e);
		}

	}

	/**
	 * Función que actualiza el nombre y apellidos del usuario cuyo nickname sea el
	 * pasado por argumentos
	 * 
	 * @param nick            El nickname del usuario al que se le actualizara el
	 *                        email
	 * @param nombreApellidos El nuevo nombre y apellidos que tendrá el ususario
	 * @author Enrique Estévez
	 * @throws SQLException Lanza excepciones de tipo SQL en caso de cualquier fallo
	 *                      relacionado con la base de datos
	 * @see SQLException
	 */
	public void actualizarNombreApellidos(String nick, String nombreApellidos) throws Exception {
		try {

			Connection connection = dbConnection.getConnection();
			PreparedStatement ps = connection
					.prepareStatement(ficheroPropiedades.getProperty("actualizarNombreApellidos"));
			ps.setString(1, nombreApellidos);
			ps.setString(2, nick);
			ps.executeUpdate();

			dbConnection.closeConnection();

		} catch (SQLException e) {
			throw new SQLException(e);

		}

	}

	/**
	 * Función que inserta una tupla en la tabla Critica de la base de datos
	 * 
	 * @param critica La crítica que queremos inserta en la tabla Critica
	 * @author Enrique Estévez
	 * @throws SQLException Lanza excepciones de tipo SQL en caso de cualquier fallo
	 *                      relacionado con la base de datos
	 * @see SQLException
	 */
	public void insertarCritica(Critica critica) throws Exception {
		try {

			Connection connection = dbConnection.getConnection();

			PreparedStatement ps = connection.prepareStatement(ficheroPropiedades.getProperty("insertarCritica"));
			ps.setString(1, critica.getTitulo());
			ps.setFloat(2, critica.getPuntuacion());
			ps.setString(3, critica.getComentario());
			ps.setString(4, critica.getNickAutor());

			ps.executeUpdate();

			dbConnection.closeConnection();

		} catch (SQLException e) {
			throw new SQLException(e);
		}

	}

	/**
	 * Función que borra la crítica cuyo título y nick del autor coincida con los
	 * pasados por argumento
	 * 
	 * @param titulo El título de la crítica a borrar
	 * @param nick   El nick del autor de la crítica
	 * @author Enrique Estévez
	 * @throws SQLException Lanza excepciones de tipo SQL en caso de cualquier fallo
	 *                      relacionado con la base de datos
	 * @see SQLException
	 */
	public void borrarCritica(String titulo, String nick) throws Exception {
		try {

			Connection connection = dbConnection.getConnection();
			PreparedStatement ps = connection
					.prepareStatement(ficheroPropiedades.getProperty("borrarCriticaDeUsuario"));

			ps.setString(1, titulo);
			ps.setString(2, nick);

			ps.executeUpdate();

			// A continuación borramos todas las valoraciones, que se habían hecho a la
			// crítica

			ps = connection.prepareStatement(ficheroPropiedades.getProperty("borrarValoracionesCritica"));

			ps.setString(1, titulo);
			ps.setString(2, nick);

			dbConnection.closeConnection();

		} catch (SQLException e) {
			throw new SQLException(e);
		}

	}

	/**
	 * Función que se conecta a la base de datos y devuelve todos los espectáculos
	 * que hay en la base de datos
	 * 
	 * @return Un array con todos los espectáculos que haya en la base de datos, o
	 *         null si no está en la base de datos
	 * @author Enrique Estévez
	 * @throws SQLException Lanza excepciones de tipo SQL en caso de cualquier fallo
	 *                      relacionado con la base de datos
	 * @see SQLException
	 */
	public ArrayList<Espectaculo> getEspectaculos() throws Exception {
		ArrayList<Espectaculo> usuarios = new ArrayList<Espectaculo>();

		try {

			Connection connection = dbConnection.getConnection();

			String query = ficheroPropiedades.getProperty("consultasTodosEspectaculos");

			Statement stmt = connection.createStatement();
			ResultSet rs = (ResultSet) stmt.executeQuery(query);

			while (rs.next()) {
				if (rs.getString("tipo_espectaculo").equals("PUNTUAL"))
					usuarios.add(new EspectaculoPuntual(rs.getString("titulo"), rs.getString("categoria"),
							rs.getString("descripcion"), getSesionesEspectaculo(rs.getString("titulo"))));

				else if (rs.getString("tipo_espectaculo").equals("MULTIPLE"))
					usuarios.add(new EspectaculoPaseMultiple(rs.getString("titulo"), rs.getString("categoria"),
							rs.getString("descripcion"), getSesionesEspectaculo(rs.getString("titulo"))));

				else
					usuarios.add(new EspectaculoTemporada(rs.getString("titulo"), rs.getString("categoria"),
							rs.getString("descripcion"), getSesionesEspectaculo(rs.getString("titulo"))));
			}

			if (stmt != null) {
				stmt.close();
			}

			// Si stmt == null entonces no existe esa consulta

			dbConnection.closeConnection();

		} catch (SQLException e) {
			throw new SQLException(e);
		}

		if (usuarios.size() == 0)
			return null;

		return usuarios;
	}

	/**
	 * Función que devuelve las características del espectáculo cuya categoría
	 * coincida con la pasada por argumento en el caso de que este en la base de
	 * datos
	 * 
	 * @param categoria La categoría del los espectáculos
	 * @return Un array de los espectáculos de la categoria que se le pasa por
	 *         argumento, o null si no está en la base de datos
	 * @author Enrique Estévez
	 * @throws SQLException Lanza excepciones de tipo SQL en caso de cualquier fallo
	 *                      relacionado con la base de datos
	 * @see SQLException
	 */
	public ArrayList<Espectaculo> getEspectaculosCategoria(String categoria) throws Exception {
		ArrayList<Espectaculo> espectaculoADevolver = new ArrayList<Espectaculo>();

		try {

			Connection connection = dbConnection.getConnection();

			PreparedStatement ps = connection
					.prepareStatement(ficheroPropiedades.getProperty("consultaCategoriaEspecifica"));
			ps.setString(1, categoria);

			ResultSet rs = (ResultSet) ps.executeQuery();

			while (rs.next()) {
				// Existen espectaculos con dicha categorÃ­a

				if (rs.getString("tipo_espectaculo") == "PUNTUAL")
					espectaculoADevolver.add(new EspectaculoPuntual(rs.getString("titulo"), rs.getString("categoria"),
							rs.getString("descripcion"), null));

				else if (rs.getString("tipo_espectaculo") == "MULTIPLE")
					espectaculoADevolver.add(new EspectaculoPaseMultiple(rs.getString("titulo"),
							rs.getString("categoria"), rs.getString("descripcion"), null));

				else
					espectaculoADevolver.add(new EspectaculoTemporada(rs.getString("titulo"), rs.getString("categoria"),
							rs.getString("descripcion"), null));
			}

			dbConnection.closeConnection();

		} catch (SQLException e) {
			throw new SQLException(e);
		}

		return espectaculoADevolver;
	}

	/**
	 * Función que se conecta a la base de datos y devuelve el Espectador cuyo nick
	 * sea el pasado por argumento en el caso de que exista en la base de datos
	 * 
	 * @param nick El nick del espectador que buscamos
	 * @return El nick del usuario en la base de datos o null si no está en la base
	 *         de datos
	 * @author Ricardo Espantaleón
	 * @author Enrique Estévez
	 * @throws SQLException Lanza excepciones de tipo SQL en caso de cualquier fallo
	 *                      relacionado con la base de datos
	 * @see SQLException
	 */
	public Espectador getUsuario(String nick) throws Exception {
		Espectador usuario = null;

		try {

			Connection connection = dbConnection.getConnection();
			// Important: This query is hard-coded here for illustrative purposes only

			// "select nick from Usuario where nick = "rick" "
			String query = String.format("%s \"%s\"", ficheroPropiedades.getProperty("consultarUsuarioEspecifico"),
					nick);

			// Important: We can replace this direct invocation to CRUD operations in
			// DBConnection
			Statement stmt = connection.createStatement();
			ResultSet rs = (ResultSet) stmt.executeQuery(query);

			while (rs.next()) {

				usuario = new Espectador(rs.getString("nick"), rs.getString("nombre_y_apellidos"),
						rs.getString("email"), rs.getInt("es_admin"),
						new Date(rs.getTimestamp("fecha_creacion").getTime()));
			}

			if (stmt != null) {
				stmt.close();
			}

			dbConnection.closeConnection();

		} catch (SQLException e) {
			throw new SQLException(e);
		}

		return usuario;
	}

	/**
	 * Función que devuelve una Critica del autor cuyo nick coincida con el pasado
	 * por argumento
	 * 
	 * @param nick Nick del autor de la crítica
	 * @return Devuelve una clase Critica cuyo nick coincida con el pasado por
	 *         argumentos, devuelve null si el nick no está en la base de datos
	 * @author Enrique Estévez
	 * @throws SQLException Lanza excepciones de tipo SQL en caso de cualquier fallo
	 *                      relacionado con la base de datos
	 * @see SQLException
	 */
	public ArrayList<Critica> getCriticas(String nick) throws Exception {
		ArrayList<Critica> criticas = new ArrayList<Critica>();

		try {

			Connection connection = dbConnection.getConnection();
			// Important: This query is hard-coded here for illustrative purposes only
			String query = String.format("%s \"%s\"", ficheroPropiedades.getProperty("consultaCriticasAutor"), nick);

			// Important: We can replace this direct invocation to CRUD operations in
			// DBConnection
			Statement stmt = connection.createStatement();
			ResultSet rs = (ResultSet) stmt.executeQuery(query);

			while (rs.next()) {

				criticas.add(new Critica(rs.getString("titulo_espectaculo"), rs.getFloat("puntuacion"),
						rs.getString("comentario"), rs.getString("nick_autor")));
			}

			if (stmt != null) {
				stmt.close();
			}

			// Si stmt == null entonces no existe esa consulta

			dbConnection.closeConnection();

		} catch (SQLException e) {
			throw new SQLException(e);
		}

		if (criticas.size() == 0)
			return null;

		return criticas;
	}

	/**
	 * Función que devuelve las críticas asociadas a un espectáculo
	 * 
	 * @param titulo El título del espectáculo
	 * @return Un array con todas las críticas de un espectáculo, o null si no está
	 *         en la base de datos
	 * @author Enrique Estévez
	 * @throws SQLException Lanza excepciones de tipo SQL en caso de cualquier fallo
	 *                      relacionado con la base de datos
	 * @see SQLException
	 * 
	 * @deprecated
	 */
	public ArrayList<Critica> getCriticasEspectaculo(String titulo) throws Exception {
		ArrayList<Critica> criticas = new ArrayList<Critica>();

		try {

			Connection connection = dbConnection.getConnection();

			String query = String.format("%s \"%s\"", ficheroPropiedades.getProperty("consultaCriticasEspectaculo"),
					titulo);

			Statement stmt = connection.createStatement();
			ResultSet rs = (ResultSet) stmt.executeQuery(query);

			while (rs.next()) {

				criticas.add(new Critica(rs.getString("titulo_espectaculo"), rs.getFloat("puntuacion"),
						rs.getString("comentario"), rs.getString("nick_autor")));
			}

			if (stmt != null) {
				stmt.close();
			}

			// Si stmt == null entonces no existe esa consulta

			dbConnection.closeConnection();

		} catch (SQLException e) {
			throw new SQLException(e);
		}

		if (criticas.size() == 0)
			return null;

		return criticas;
	}

	/**
	 * Función que devuelve un Espectaculo si el título pasado por argumento
	 * coincide con alguno de los espectáculos de la base de datos
	 * 
	 * @param titulo El título del espectáculo
	 * @return Devuelve un Espectaculo con el título pasado por argumento o null si
	 *         esté no está en la base de datos
	 * @author Enrique Estévez
	 * @throws SQLException Lanza excepciones de tipo SQL en caso de cualquier fallo
	 *                      relacionado con la base de datos
	 * @see SQLException
	 */
	public Espectaculo getEspectaculo(String titulo) throws Exception {
		Espectaculo espectaculo = null;

		try {

			Connection connection = dbConnection.getConnection();
			// Important: This query is hard-coded here for illustrative purposes only

			// "select nick from Usuario where nick = "rick" "
			String query = String.format("%s \"%s\"", ficheroPropiedades.getProperty("consultaEspectaculoEspecifico"),
					titulo);

			// Important: We can replace this direct invocation to CRUD operations in
			// DBConnection
			Statement stmt = connection.createStatement();
			ResultSet rs = (ResultSet) stmt.executeQuery(query);

			while (rs.next()) {
				if (rs.getString("tipo_espectaculo").equals("PUNTUAL"))
					espectaculo = new EspectaculoPuntual(rs.getString("titulo"), rs.getString("categoria"),
							rs.getString("descripcion"), getSesionesEspectaculo(titulo));

				else if (rs.getString("tipo_espectaculo").equals("MULTIPLE"))
					espectaculo = new EspectaculoPaseMultiple(rs.getString("titulo"), rs.getString("categoria"),
							rs.getString("descripcion"), getSesionesEspectaculo(titulo));

				else
					espectaculo = new EspectaculoTemporada(rs.getString("titulo"), rs.getString("categoria"),
							rs.getString("descripcion"), getSesionesEspectaculo(titulo));
			}

			if (stmt != null) {
				stmt.close();
			}

			dbConnection.closeConnection();

		} catch (SQLException e) {
			throw new SQLException(e);
		}

		return espectaculo;
	}

	/**
	 * Función que inserta una tupla en la tabla Espectaculo de la base de datos
	 * 
	 * @param espectaculo     El espectáculo que queremos insertar en la tabla
	 *                        Espectaculo
	 * @param tipoEspectaculo El tipo de espectáculo del cual será dicho espectáculo
	 *                        a insertar
	 * @author Ricardo Espantaleón
	 * @author Enrique Estévez
	 * @throws SQLException Lanza excepciones de tipo SQL en caso de cualquier fallo
	 *                      relacionado con la base de datos
	 * @see SQLException
	 */
	public void insertarEspectaculo(Espectaculo espectaculo, String tipoEspectaculo) throws Exception {

		try {

			Connection connection = dbConnection.getConnection();
			PreparedStatement ps = connection.prepareStatement(ficheroPropiedades.getProperty("insertarEspectaculo"));

			ps.setString(1, espectaculo.getTitulo());
			ps.setString(2, espectaculo.getCategorias());
			ps.setString(3, espectaculo.getDescripcion());
			ps.setString(4, tipoEspectaculo);

			ps.executeUpdate();

			// Inserto las sesiones

			ArrayList<Sesion> sesiones = espectaculo.getSesiones();
			ps = connection.prepareStatement(ficheroPropiedades.getProperty("insertarSesion"));

			for (int i = 0; i < sesiones.size(); ++i) {
				Timestamp ts = new Timestamp(sesiones.get(i).getFecha().getTime());

				ps.setTimestamp(1, ts);
				ps.setInt(2, sesiones.get(i).getLocalidades());
				ps.setInt(3, sesiones.get(i).getVendidas());
				ps.setString(4, espectaculo.getTitulo());

				ps.executeUpdate();

			}

			dbConnection.closeConnection();

		} catch (SQLException e) {
			throw new SQLException(e);

		}

	}

	/**
	 * Función que comprueba si existe un nick en la base de datos
	 * 
	 * @param nick Nick a comprobar que está registrado en la base de datos
	 * @return Retorna el nick si ha sido encontrado o null si esté no existe en la
	 *         base de datos
	 * @throws SQLException Lanza excepciones de tipo SQL en caso de cualquier fallo
	 *                      relacionado con la base de datos
	 * @see SQLException
	 * @author Ricardo Espantaleón
	 */
	public String getNick(String nick) throws Exception {

		String nickConsulta = null;

		try {

			Connection connection = dbConnection.getConnection();
			PreparedStatement ps = connection
					.prepareStatement(ficheroPropiedades.getProperty("consultaNickEspecifico"));
			ps.setString(1, nick);

			ResultSet rs = (ResultSet) ps.executeQuery();

			while (rs.next())
				nickConsulta = rs.getString("nick");

			dbConnection.closeConnection();

		} catch (SQLException e) {
			throw new SQLException(e);
		}

		return nickConsulta;
	}

	/**
	 * Función que comprueba si existe un email en la base de datos
	 * 
	 * @param email Email a comprobar que está registrado en la base de datos
	 * @return Retorna el email si ha sido encontrado o null si esté no existe en la
	 *         base de datos
	 * @throws SQLException Lanza excepciones de tipo SQL en caso de cualquier fallo
	 *                      relacionado con la base de datos
	 * @see SQLException
	 * @author Ricardo Espantaleón
	 */
	public String getEmail(String email) throws Exception {

		String nickConsulta = null;

		try {

			Connection connection = dbConnection.getConnection();
			PreparedStatement ps = connection
					.prepareStatement(ficheroPropiedades.getProperty("consultaEmailEspecifico"));
			ps.setString(1, email);

			ResultSet rs = (ResultSet) ps.executeQuery();

			while (rs.next())
				nickConsulta = rs.getString("email");

			dbConnection.closeConnection();

		} catch (SQLException e) {
			throw new SQLException(e);
		}

		return nickConsulta;
	}

	/**
	 * Función que inserta una valoración a una crítica por parte de un usuario
	 * 
	 * @param nickValoracion           Nick del usuario que está valorando la
	 *                                 crítica
	 * @param nickAutorCritica         Nick del usuario que ha creado la crítica
	 * @param tituloEspectaculoCritica Título del espectáculo de la crítica
	 * @param valoracion               Flotante con la nota a la crítica
	 * @throws SQLException Lanza excepciones de tipo SQL en caso de cualquier fallo
	 *                      relacionado con la base de datos
	 * @see SQLException
	 * @author Ricardo Espantaleón
	 */
	public void valorarCritica(String nickValoracion, String nickAutorCritica, String tituloEspectaculoCritica,
			float valoracion) throws Exception {

		try {

			Connection connection = dbConnection.getConnection();
			PreparedStatement ps = connection
					.prepareStatement(ficheroPropiedades.getProperty("insertaValoracionCritica"));

			ps.setString(1, tituloEspectaculoCritica);
			ps.setString(2, nickValoracion);
			ps.setString(3, nickAutorCritica);
			ps.setFloat(4, valoracion);

			ps.executeUpdate();

			dbConnection.closeConnection();

		} catch (SQLException e) {
			throw new SQLException(e);
		}
	}

	/**
	 * Función que devuelve la media de las valoraciones a una crítica
	 * 
	 * @param tituloEspectaculoCritica Título del espectáculo de la crítica del cual
	 *                                 queremos obtener las valoraciones
	 * @return Retorna 0 en caso de que no haya ninguna valoración del espectáculo o
	 *         retorna la media de las críticas del espectáculo
	 * @throws SQLException Lanza excepciones de tipo SQL en caso de cualquier fallo
	 *                      relacionado con la base de datos
	 * @see SQLException
	 * @author Ricardo Espantaleón
	 */
	public float getValoracionCritica(String tituloEspectaculoCritica) throws Exception {
		float valoracion = (float) 0.0;

		try {

			Connection connection = dbConnection.getConnection();
			PreparedStatement ps = connection
					.prepareStatement(ficheroPropiedades.getProperty("consultaValoracionCritica"));
			ps.setString(1, tituloEspectaculoCritica);

			ResultSet rs = (ResultSet) ps.executeQuery();

			while (rs.next())
				valoracion = rs.getFloat("media");

			dbConnection.closeConnection();
		} catch (SQLException e) {
			throw new SQLException(e);
		}

		return valoracion;
	}

	/**
	 * Función que me devuelve las sesiones de un espectáculo en concreto
	 * 
	 * @param tituloEspectaculo El título del espectáculo
	 * @return Un array con todas las sesiones del espectáculo que se pasa como
	 *         argumento o null si no se encuentra nada en la base de datos
	 * @throws SQLException Lanza excepciones de tipo SQL en caso de cualquier fallo
	 *                      relacionado con la base de datos
	 * @see SQLException
	 * @author Ricardo Espantaleón
	 */
	public ArrayList<Sesion> getSesionesEspectaculo(String tituloEspectaculo) throws Exception {
		ArrayList<Sesion> sesionesEspectaculo = new ArrayList<Sesion>();

		try {

			Connection connection = dbConnection.getConnection();

			PreparedStatement ps = connection
					.prepareStatement(ficheroPropiedades.getProperty("consultarSesionesEspectaculo"));
			ps.setString(1, tituloEspectaculo);

			ResultSet rs = (ResultSet) ps.executeQuery();

			while (rs.next()) {
				sesionesEspectaculo.add(new Sesion(rs.getInt("localidades"),
						new Date(rs.getTimestamp("fecha").getTime()), rs.getInt("entradas_vendidas"), rs.getInt("id")));
			}

			// Si stmt == null entonces no existe esa consulta

			dbConnection.closeConnection();

		} catch (SQLException e) {
			throw new SQLException(e);
		}

		return sesionesEspectaculo;
	}

	/**
	 * Función que elimina todas las sesiones de un espectáculo
	 * 
	 * @param tituloEspectaculo El título del espectáculo al que se le eliminarán
	 *                          todas las sesiones
	 * @throws SQLException Lanza excepciones de tipo SQL en caso de cualquier fallo
	 *                      relacionado con la base de datos
	 * @see SQLException
	 * @author Ricardo Espantaleón
	 */
	public void cancelarTodasSesionesEspectaculo(String tituloEspectaculo) throws Exception {

		try {

			Connection connection = dbConnection.getConnection();

			PreparedStatement ps = connection
					.prepareStatement(ficheroPropiedades.getProperty("borraTodasSesionesEspectaculo"));
			ps.setString(1, tituloEspectaculo);

			ps.executeUpdate();

			dbConnection.closeConnection();

		} catch (SQLException e) {
			throw new SQLException(e);
		}
	}

	/**
	 * Función que actualiza la categoría de un espectáculo
	 * 
	 * @param tituloEspectaculo El título del espectáculo al que se le actualizará
	 *                          la categoría
	 * @param nuevaCategoria    La nueva categoría del espectáculo
	 * @throws SQLException Lanza excepciones de tipo SQL en caso de cualquier fallo
	 *                      relacionado con la base de datos
	 * @author Ricardo Espantaleón
	 * @see SQLException
	 */
	public void actualizarCategoriaEspectaculo(String tituloEspectaculo, String nuevaCategoria) throws Exception {

		try {

			Connection connection = dbConnection.getConnection();

			PreparedStatement ps = connection
					.prepareStatement(ficheroPropiedades.getProperty("actualizarCategoriaEspectaculo"));
			ps.setString(1, nuevaCategoria);
			ps.setString(2, tituloEspectaculo);

			ps.executeUpdate();

			dbConnection.closeConnection();

		} catch (SQLException e) {
			throw new SQLException(e);
		}

	}

	/**
	 * Función que actualiza la descripción de un espectáculo
	 * 
	 * @param tituloEspectaculo El titulo del espectáculo al que se le actualizará
	 *                          la descripción
	 * @param nuevaDescripcion  La nueva descripción del espectáculo
	 * @throws SQLException Lanza excepciones de tipo SQL en caso de cualquier fallo
	 *                      relacionado con la base de datos
	 * @author Ricardo Espantaleón
	 * @see SQLException
	 */
	public void actualizarDescripcionEspectaculo(String tituloEspectaculo, String nuevaDescripcion) throws Exception {

		try {

			Connection connection = dbConnection.getConnection();

			PreparedStatement ps = connection
					.prepareStatement(ficheroPropiedades.getProperty("actualizarDescripcionEspectaculo"));
			ps.setString(1, nuevaDescripcion);
			ps.setString(2, tituloEspectaculo);

			ps.executeUpdate();

			dbConnection.closeConnection();

		} catch (SQLException e) {
			throw new SQLException(e);
		}

	}

	/**
	 * Función que incrementa el número de entradas vendidas de una sesión de un
	 * espectáculo
	 * 
	 * @param id Identificador asociado al espectáculo
	 * @throws SQLException Lanza excepciones de tipo SQL en caso de cualquier fallo
	 *                      relacionado con la base de datos
	 * @author Ricardo Espantaleón
	 * @see SQLException
	 */
	public void actualizarVentaEntrada(int id) throws Exception {

		try {

			Connection connection = dbConnection.getConnection();

			PreparedStatement ps = connection
					.prepareStatement(ficheroPropiedades.getProperty("actualizarVentaEntrada"));

			ps.setInt(1, id);

			ps.executeUpdate();

			dbConnection.closeConnection();

		} catch (SQLException e) {
			throw new SQLException(e);
		}
	}

	/**
	 * Función que elimina una sesión de un espectacáculo
	 * 
	 * @param id El id de la sesión de un espectáculo
	 * @author Ricardo Espantaleón
	 * @throws Exception Lanza excepciones de tipo SQL en caso de cualquier fallo
	 *                   relacionado con la base de datos
	 * @see SQLException
	 */
	public void borrarSesionEspectaculo(int id) throws Exception {

		try {

			Connection connection = dbConnection.getConnection();

			PreparedStatement ps = connection
					.prepareStatement(ficheroPropiedades.getProperty("borrarSesionEspectaculo"));
			ps.setInt(1, id);

			ps.executeUpdate();

			dbConnection.closeConnection();

		} catch (SQLException e) {
			throw new SQLException(e);
		}

	}

	/**
	 * Función que devuelve el número de entradas vendidas de una sesión
	 * 
	 * @param id El id de la sesión de un espectáculo
	 * @return Devuelve el número de entradas vendidas de una sesión o null si no se
	 *         encuentra el id en la base de datos
	 * @author Ricardo Espantaleón
	 * @throws Exception Lanza excepciones de tipo SQL en caso de cualquier fallo
	 *                   relacionado con la base de datos
	 * @see SQLException
	 */
	public int getEntradas(int id) throws Exception {

		int entradasVendidas = 0;

		try {

			Connection connection = dbConnection.getConnection();

			PreparedStatement ps = connection.prepareStatement(ficheroPropiedades.getProperty("consultarEntradas"));
			ps.setInt(1, id);

			ResultSet rs = (ResultSet) ps.executeQuery();

			while (rs.next()) {
				entradasVendidas = rs.getInt("entradas_vendidas");
			}

			dbConnection.closeConnection();

		} catch (SQLException e) {
			throw new SQLException(e);
		}

		return entradasVendidas;
	}

	/**
	 * Función que devuelve el número de localidades de una sesión
	 * 
	 * @param id El id de la sesión de un espectáculo
	 * @return Devuelve el número de localidades de una sesión o null si no se
	 *         encuentra el id en la base de datos
	 * @author Ricardo Espantaleón
	 * @throws Exception Lanza excepciones de tipo SQL en caso de cualquier fallo
	 *                   relacionado con la base de datos
	 * @see SQLException
	 */
	public int getLocalidades(int id) throws Exception {

		int localidades = 0;

		try {

			Connection connection = dbConnection.getConnection();

			PreparedStatement ps = connection.prepareStatement(ficheroPropiedades.getProperty("consultarLocalidades"));
			ps.setInt(1, id);

			ResultSet rs = (ResultSet) ps.executeQuery();

			while (rs.next()) {
				localidades = rs.getInt("localidades");
			}

			dbConnection.closeConnection();

		} catch (SQLException e) {
			throw new SQLException(e);
		}

		return localidades;
	}

	/**
	 * Función que devuelve las sesiones que van despues de una fecha determinada
	 * 
	 * @param fecha Una fecha en concreto
	 * @return Devuelve un array con las sesiones que son posteriores a la fecha
	 *         pasada por argumento o null si no se encuentra el id en la base de
	 *         datos
	 * @author Ricardo Espantaleón
	 * @throws Exception Lanza excepciones de tipo SQL en caso de cualquier fallo
	 *                   relacionado con la base de datos
	 * @see SQLException
	 */
	public ArrayList<Sesion> getSesionesPosteriorFecha(java.util.Date fecha) throws Exception {

		ArrayList<Sesion> sesiones = new ArrayList<Sesion>();

		try {

			Connection connection = dbConnection.getConnection();

			PreparedStatement ps = connection
					.prepareStatement(ficheroPropiedades.getProperty("consultarSesionesAPartirDeFecha"));
			Timestamp ts = new Timestamp(fecha.getTime());
			ps.setTimestamp(1, ts);

			ResultSet rs = (ResultSet) ps.executeQuery();

			while (rs.next()) {
				sesiones.add(new Sesion(rs.getInt("localidades"), new Date(rs.getTimestamp("fecha").getTime()),
						rs.getInt("entradas_vendidas"), rs.getInt("id"), rs.getString("espectaculo")));
			}

			dbConnection.closeConnection();

		} catch (SQLException e) {
			throw new SQLException(e);
		}

		if (sesiones.size() == 0)
			return null;

		return sesiones;
	}

	/**
	 * Devuelve un usuario dado su nick y su contraseña
	 * 
	 * @param nick     Nickname del usuario a consultar
	 * @param password Contraseña del usuario a consultar
	 * @return Retorna el usuario encontrado en la consulta
	 * @throws Exception Lanza excepciones en caso de error en la base de datos
	 * 
	 * @author Ricardo Espantaleón Pérez
	 */
	public Espectador getUsuario(String nick, String password) throws Exception {

		Espectador espectador = null;

		try {

			Connection connection = dbConnection.getConnection();

			PreparedStatement ps = connection
					.prepareStatement(ficheroPropiedades.getProperty("consultarUsuarioYPassword"));

			ps.setString(1, nick);

			// Encripto la password
			String encryptedPassword = Encryptor.encryptPassword(password);

			ps.setString(2, encryptedPassword);

			ResultSet rs = (ResultSet) ps.executeQuery();

			while (rs.next()) {
				espectador = new Espectador(rs.getString("nick"), rs.getString("nombre_y_apellidos"),
						rs.getString("email"), rs.getInt("es_admin"),
						new Date(rs.getTimestamp("fecha_creacion").getTime()));
			}

			dbConnection.closeConnection();

		} catch (SQLException e) {
			throw new SQLException(e);
		}

		return espectador;
	}

	/**
	 * Función que inserta un nuevo registro de última conexión en la tabla Log
	 * 
	 * En caso de que el usuario nunca haya sido insertado en la tabla se
	 * actualizará por primera vez
	 * 
	 * En caso de que ya haya sido insertado previamente se procederá a actualizar
	 * directamente su última conexión
	 * 
	 * @param nick           Nickname del usuario a insertar en el Log
	 * @param ultimaConexion Última conexión del usuario el cual ha sido logeado con
	 *                       éxito
	 * @throws Exception Lanza excepción en caso de error en la base de datos
	 * 
	 * @author Ricardo Espantaleón
	 */
	public void insertaRegistroLog(String nick, Date ultimaConexion) throws Exception {

		String nickLog = null;

		try {

			Connection connection = dbConnection.getConnection();

			PreparedStatement ps = connection.prepareStatement(ficheroPropiedades.getProperty("consultarUsuarioLog"));
			ps.setString(1, nick);

			ResultSet rs = (ResultSet) ps.executeQuery();

			while (rs.next())
				nickLog = rs.getString("nick");

			Timestamp ts = new Timestamp(ultimaConexion.getTime());

			// El usuario ya fue insertado en el log previamente
			if (nickLog != null) {
				PreparedStatement psUpdate = connection
						.prepareStatement(ficheroPropiedades.getProperty("actualizarRegistroLog"));

				psUpdate.setTimestamp(1, ts);
				psUpdate.setString(2, nick);
				psUpdate.executeUpdate();

				// El usuario nunca fue insertado en el log, lo inserto ahora
			} else {
				PreparedStatement psInsert = connection
						.prepareStatement(ficheroPropiedades.getProperty("insertarRegistroLog"));

				psInsert.setString(1, nick);
				psInsert.setTimestamp(2, ts);
				psInsert.executeUpdate();
			}

			dbConnection.closeConnection();

		} catch (SQLException e) {
			throw new SQLException(e);
		}
	}

	/**
	 * Función que retorna los registros del log de la base de datos
	 * 
	 * @return Retorna un vector del tipo DTO Log con las distintas tuplas
	 *         registradas en el Log hasta el momento
	 * @throws SQLException Lanzará la excepción en caso de error en la base de
	 *                      datos
	 */
	public ArrayList<Log> getRegistrosLog() throws Exception {
		ArrayList<Log> registros = new ArrayList<Log>();

		try {

			Connection connection = dbConnection.getConnection();

			PreparedStatement ps = connection.prepareStatement(ficheroPropiedades.getProperty("consultarRegistrosLog"));
			ResultSet rs = (ResultSet) ps.executeQuery();

			while (rs.next())
				registros.add(new Log(rs.getString("nick"), new Date(rs.getTimestamp("ultima_conexion").getTime()),
						new Date(rs.getTimestamp("fecha_creacion").getTime()), rs.getInt("es_admin")));

			dbConnection.closeConnection();

		} catch (SQLException e) {
			throw new SQLException(e);
		}

		return registros;
	}

	/**
	 * Función que inserta una sesión asociada a un espectáculo
	 * 
	 * @param Sesion sesion a insertar en la base de datos
	 * @throws SQLException Lanzará la excepción en caso de error en la base de
	 *                      datos
	 * 
	 * @author Ricardo Espantaleón Pérez
	 */
	public void insertarSesionEspectaculo(Sesion sesion) throws Exception {

		try {
			Connection connection = dbConnection.getConnection();

			PreparedStatement ps = connection.prepareStatement(ficheroPropiedades.getProperty("insertarSesion"));
			Timestamp ts = new Timestamp(sesion.getFecha().getTime());

			ps.setTimestamp(1, ts);
			ps.setInt(2, sesion.getLocalidades());
			ps.setInt(3, sesion.getVendidas());
			ps.setString(4, sesion.getEspectaculo());

			ps.executeUpdate();

		} catch (SQLException e) {
			throw new SQLException(e);
		}
	}

	/**
	 * Función que actualiza los datos de una sesión
	 * 
	 * @param Sesion sesión nueva a actualizar en la base de datos
	 * 
	 * @throws SQLException Lanzará la excepción en caso de error en la base de
	 *                      datos
	 * 
	 * @author Ricardo Espantaleón Pérez
	 */
	public void modificarSesion(Sesion sesion) throws Exception {

		try {
			Connection connection = dbConnection.getConnection();

			PreparedStatement ps = connection.prepareStatement(ficheroPropiedades.getProperty("actualizarSesion"));
			Timestamp ts = new Timestamp(sesion.getFecha().getTime());

			ps.setInt(1, sesion.getLocalidades());
			ps.setTimestamp(2, ts);
			ps.setInt(3, sesion.getId());

			ps.executeUpdate();

		} catch (SQLException e) {
			throw new SQLException(e);
		}
	}

	/**
	 * Función que retorna el tipo de espectáculo dado el título del mismo
	 * 
	 * @param titulo Título del espectáculo a consultar
	 * @return Retorna el tipo de Espectáculo en cuestión
	 * @throws Exception Lanzará excepción en caso de error en la base de datos
	 * 
	 * @author Ricardo Espantaleón
	 */
	public tipo_espectaculo getTipoEspectaculo(String titulo) throws Exception {
		tipo_espectaculo tipo = null;

		try {
			Connection connection = dbConnection.getConnection();

			PreparedStatement ps = connection
					.prepareStatement(ficheroPropiedades.getProperty("consultarTipoEspectaculo"));

			ps.setString(1, titulo);

			ResultSet rs = (ResultSet) ps.executeQuery();

			while (rs.next()) {
				if (rs.getString("tipo_espectaculo").equals("PUNTUAL"))
					tipo = tipo_espectaculo.PUNTUAL;

				else if (rs.getString("tipo_espectaculo").equals("MULTIPLE"))
					tipo = tipo_espectaculo.MULTIPLE;

				else
					tipo = tipo_espectaculo.TEMPORADA;
			}

		} catch (SQLException e) {
			throw new SQLException(e);
		}

		return tipo;
	}

	/**
	 * Función que elimina un espectáculo de la BBDD
	 * 
	 * @param titulo Título del espectáculo a eliminar
	 * @throws Exception Lanzará excepción en caso de error en la base de datos
	 * 
	 * @author Ricardo Espantaleón
	 */
	public void borrarEspectaculo(String titulo) throws Exception {
		try {
			Connection connection = dbConnection.getConnection();

			PreparedStatement ps = connection.prepareStatement(ficheroPropiedades.getProperty("borrarEspectaculo"));

			ps.setString(1, titulo);

			ps.executeUpdate();

		} catch (SQLException e) {
			throw new SQLException(e);
		}
	}

	/**
	 * Función que consulta una sesión concreta dado su ID
	 * 
	 * @param id ID de la sesión a consultar
	 * @return Retorna el DTO de la sesión en concreto
	 * @throws Exception Lanza excepción en caso de error en la base de datos
	 * 
	 * @author Ricardo Espantaleón
	 */
	public Sesion getSesion(int id) throws Exception {
		Sesion sesion = null;

		try {
			Connection connection = dbConnection.getConnection();

			PreparedStatement ps = connection.prepareStatement(ficheroPropiedades.getProperty("consultarSesion"));

			ps.setInt(1, id);

			ResultSet rs = (ResultSet) ps.executeQuery();

			while (rs.next())
				sesion = new Sesion(rs.getInt("localidades"), new Date(rs.getTimestamp("fecha").getTime()),
						rs.getInt("entradas_vendidas"), rs.getInt("id"), rs.getString("espectaculo"));

		} catch (SQLException e) {
			throw new SQLException(e);
		}

		return sesion;
	}

	/**
	 * Función que devuelve los espectáculos que puede valorar un usuario en
	 * concreto
	 * 
	 * @param nick Nickname del usuario a comprobar que espectáculos puede valorar
	 * @return Retorna aquellos espectáculos disponibles a valorar
	 * @throws Exception Lanzará excepción en caso de error en la base de datos
	 */
	public ArrayList<Espectaculo> consultarEspectaculosAValorar(String nick) throws Exception {
		ArrayList<Espectaculo> espectaculos = new ArrayList<Espectaculo>();

		try {
			Connection connection = dbConnection.getConnection();

			PreparedStatement ps = connection
					.prepareStatement(ficheroPropiedades.getProperty("consultarEspectaculosDisponiblePorUsuario"));

			ps.setString(1, nick);

			ResultSet rs = (ResultSet) ps.executeQuery();

			while (rs.next())
				espectaculos.add(new EspectaculoPuntual(rs.getString("titulo"), rs.getString("categoria"),
						rs.getString("descripcion"), null));

		} catch (SQLException e) {
			throw new SQLException(e);
		}

		return espectaculos;
	}

	/**
	 * Función que devuelve todas las críticas dado un espectáctulo
	 * 
	 * @param tituloEspectaculo Título del espectáculo de las críticas a obtener
	 * @return Retorna las críticas asociadas al espectáculo
	 * @throws Exception Lanzará excepción en caso de error en la base de datos
	 * 
	 * @author Ricardo Espantaleón
	 */
	public ArrayList<Critica> consultarCriticasEspectaculo(String tituloEspectaculo) throws Exception {
		ArrayList<Critica> criticas = new ArrayList<Critica>();

		try {
			Connection connection = dbConnection.getConnection();

			PreparedStatement ps = connection
					.prepareStatement(ficheroPropiedades.getProperty("consultarCriticasEspectaculo"));

			ps.setString(1, tituloEspectaculo);

			ResultSet rs = (ResultSet) ps.executeQuery();

			while (rs.next()) {

				criticas.add(new Critica(rs.getString("titulo_espectaculo"), rs.getFloat("puntuacion"),
						rs.getString("comentario"), rs.getString("nick_autor")));
			}

		} catch (SQLException e) {

			throw new SQLException(e);
		}

		return criticas;
	}

	/**
	 * Función que comprueba si un usuario ya ha valorado previamente una crítica de
	 * otro usuario
	 * 
	 * @param nickAutorCritica  Nick del autor de la crítica a valorar
	 * @param tituloEspectaculo Título del espectáctulo al cual va dirigido la
	 *                          crítica
	 * @param nickValorante     Nick de la persona que va a valorar la crítica en
	 *                          cuestión
	 * @return Retorna el número de filas donde existe la coincidencia
	 * @throws Exception Lanzará excepción en caso de error en la base de datos
	 * 
	 * @author Ricardo Espantaleón Pérez
	 */
	public int existeValoracionCritica(String nickAutorCritica, String tituloEspectaculo, String nickValorante)
			throws Exception {
		int count = 0;

		try {
			Connection connection = dbConnection.getConnection();

			PreparedStatement ps = connection
					.prepareStatement(ficheroPropiedades.getProperty("existeValoracionCritica"));

			ps.setString(1, tituloEspectaculo);
			ps.setString(2, nickValorante);
			ps.setString(3, nickAutorCritica);

			ResultSet rs = (ResultSet) ps.executeQuery();

			while (rs.next())
				count = rs.getInt("Count");

		} catch (SQLException e) {

			throw new SQLException(e);
		}

		return count;
	}

	/**
	 * Función que obtiene la valoración que tiene un espectáculo dada sus críticas
	 * 
	 * @param titulo Título del espectáculo del cual obtener la valoración
	 * @return Retorna la valoración asociada al espectáculo
	 * @throws Exception Lanzará excepción en caso de error en la basse de datos
	 * 
	 * @author Ricardo Espantaleón Pérez
	 */
	public float getPuntuacionEspectaculo(String titulo) throws Exception {
		float valoracion = 0;

		try {
			Connection connection = dbConnection.getConnection();

			PreparedStatement ps = connection
					.prepareStatement(ficheroPropiedades.getProperty("consultarValoracionEspectaculo"));

			ps.setString(1, titulo);
			ResultSet rs = (ResultSet) ps.executeQuery();

			while (rs.next())
				valoracion = rs.getFloat("Puntuacion");

		} catch (SQLException e) {

			throw new SQLException(e);
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
	 * @throws Exception Lanzará excepción en caso de error en la base de datos
	 * 
	 * @author Ricardo Espantaleón Pérez
	 */
	public float getPuntuacionCritica(String titulo, String nickAutor) throws Exception {
		float valoracion = 0;

		try {
			Connection connection = dbConnection.getConnection();

			PreparedStatement ps = connection
					.prepareStatement(ficheroPropiedades.getProperty("consultarValoracionCritica"));

			ps.setString(1, titulo);
			ps.setString(2, nickAutor);
			ResultSet rs = (ResultSet) ps.executeQuery();

			while (rs.next())
				valoracion = rs.getFloat("Valoracion");

		} catch (SQLException e) {

			throw new SQLException(e);
		}

		return valoracion;
	}

	/**
	 * Función que elimina a un usuario de la base de datos y sus correspondientes
	 * críticas y valoraciones
	 * 
	 * @param nick Nickname del usuario a eliminar en el sistema
	 * @throws Exception Lanzará excepción en caso de error en la base de datos
	 * 
	 * @author Ricardo Espantaleón Pérez
	 */
	public void borrarUsuario(String nick) throws Exception {

		try {

			Connection connection = dbConnection.getConnection();

			// Borramos el usuario

			PreparedStatement ps = connection.prepareStatement(ficheroPropiedades.getProperty("borrarUsuario"));

			ps.setString(1, nick);
			ps.executeUpdate();

			// Borramos las críticas del usuario

			ps = connection.prepareStatement(ficheroPropiedades.getProperty("borrarCriticasUsuario"));

			ps.setString(1, nick);
			ps.executeUpdate();

			// Borramos las valoraciones a las críticas del usuario

			ps = connection.prepareStatement(ficheroPropiedades.getProperty("borrarValoracionesUsuario"));

			ps.setString(1, nick);
			ps.executeUpdate();

		} catch (SQLException e) {

			throw new SQLException(e);
		}

	}
}