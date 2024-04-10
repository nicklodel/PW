package data.common;

import java.io.FileInputStream;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

//mport org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
//import org.jasypt.properties.EncryptableProperties;

/**
 * Clase que realiza la conexión con la base de datos y el cierre de la conexión
 * de la misma
 * 
 * @author Ricardo Espantaleón
 * @author Enrique Estévez
 */
public class BDConnection {
	protected Connection connection = null;

	protected String url;

	protected String user;

	protected String password;

	protected Properties config = null;

	private String seed = "DfNpWTADCuIqu05WSd8mpJUeqz0yroPB";

	/**
	 * Constructor parametrizado qu inicializa las variables de la clase
	 * 
	 * @param url      URL de conexión a la base de datos
	 * @param user     Usuario de conexión a la base de datos
	 * @param password Contraseña de conexión a la base de datos
	 */
	public BDConnection(String url, String user, String password) {
		this.user = user;
		this.password = password;
		this.url = url;

	}

	/**
	 * Función que realiza la conexión con la base de datos
	 * 
	 * @return La conexión con la base de datos o una excepción en el caso de uqe no
	 *         se produzca
	 * @throws SQLException Lanza excepciones de tipo SQL en caso de cualquier fallo
	 *                      relacionado con la base de datos
	 * @see SQLException
	 * @throws ClassNotFoundException El ClassLoader intenta leer la descripción de
	 *                                una clase, y la definición de dicha clase no
	 *                                es encontrada
	 * @author Ricardo Espantaleón
	 * @author Enrique Estévez
	 */
	public Connection getConnection() throws SQLException, ClassNotFoundException {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			this.connection = (Connection) DriverManager.getConnection(url, user, password);

		} catch (SQLException e) {
			throw new SQLException("Connection to MySQL has failed!");

		} catch (ClassNotFoundException e) {
			throw new ClassNotFoundException("JDBC Driver not found.");

		}

		return this.connection;
	}

	/**
	 * Función que cierra la conexión con la base de datos
	 * 
	 * @throws SQLException Lanza excepciones de tipo SQL en caso de cualquier fallo
	 *                      relacionado con la base de datos
	 * @see SQLException
	 * @author Ricardo Espantaleón
	 * @author Enrique Estévez
	 */
	public void closeConnection() throws SQLException {
		try {
			if (this.connection != null && !this.connection.isClosed()) {
				this.connection.close();

			}
		} catch (SQLException e) {
			throw new SQLException("Error while trying to close the connection.");

		}
	}

}