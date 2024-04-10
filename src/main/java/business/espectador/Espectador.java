/**
 * 
 */
package business.espectador;

import java.util.Date;
import business.encryptor.Encryptor;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * La clase Espectador con atributos nombre y apellidos, nick , y email
 * 
 * @author Enrique Estévez Mayoral
 * @author Ricardo Espantaleón
 *
 */
public class Espectador {

	// Variables
	private String nombreApellidos;
	private String nick;
	private String email;
	private Pattern VALID_EMAIL_ADDRESS_REGEX = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$",
			Pattern.CASE_INSENSITIVE);
	private Date fechaCreacion;
	private int esAdmin = 0;

	/**
	 * Constructor vacío para la clase Espectador
	 * 
	 * @author Ricardo Espantaleón Pérez
	 */
	public Espectador() {
		this.nombreApellidos = "";
		this.nick = "";
		this.email = "";
	}

	/**
	 * Constructor de la clase Espectador que se le pasa por argumento el nick, el
	 * nombre y los apellidos, y el email
	 * 
	 * @param nick            El nick del espectador
	 * @param nombreApellidos El nombre y apellidos del espectador
	 * @param email           El email del espectador
	 * @param esAdmin         Booleano que indica si el usuario es admin o no
	 * @param fechaCreacion   La fecha en la que se creó el usuario
	 * @throws Exception Lanza una excepción en el caso de que el formato del email
	 *                   no sea correcto
	 * @author Enrique Estévez Mayoral
	 * @author Ricardo Espantaleón
	 */
	public Espectador(String nick, String nombreApellidos, String email, int esAdmin, Date fechaCreacion)
			throws Exception {
		if (!validarEmail(email))
			throw new Exception("El formato del email es incorrecto");
		this.nick = nick;
		this.nombreApellidos = nombreApellidos;
		this.email = email;
		this.esAdmin = esAdmin;
		this.fechaCreacion = fechaCreacion;
	}

	/**
	 * Constructor de la clase Espectador que se le pasa por argumento el nick, el
	 * nombre y los apellidos, y el email
	 * 
	 * @param nick            El nick del espectador
	 * @param nombreApellidos El nombre y apellidos del espectador
	 * @param email           El email del espectador
	 * @param esAdmin         Booleano que indica si el usuario es admin o no
	 * @throws Exception Lanza una excepción en el caso de que el formato del email
	 *                   no sea correcto
	 * @author Enrique Estévez Mayoral
	 * @author Ricardo Espantaleón
	 */
	public Espectador(String nick, String nombreApellidos, String email, int esAdmin) throws Exception {
		if (!validarEmail(email))
			throw new Exception("El formato del email es incorrecto");
		this.nick = nick;
		this.nombreApellidos = nombreApellidos;
		this.email = email;
		this.esAdmin = esAdmin;

	}

	/**
	 * Constructor de la clase Espectador que se le pasa por argumento el nick, el
	 * nombre y los apellidos, y el email
	 * 
	 * @param nick            El nick del espectador
	 * @param nombreApellidos El nombre y apellidos del espectador
	 * @param email           El email del espectador
	 * @throws Exception Lanza una excepción en el caso de que el formato del email
	 *                   no sea correcto
	 * @author Enrique Estévez Mayoral
	 * @author Ricardo Espantaleón
	 */
	public Espectador(String nick, String nombreApellidos, String email) throws Exception {
		if (!validarEmail(email))
			throw new Exception("El formato del email es incorrecto");
		this.nick = nick;
		this.nombreApellidos = nombreApellidos;
		this.email = email;

	}

	/**
	 * Constructor de la clase Espectador
	 * 
	 * @param e El espectador
	 * @throws Exception Lanza una excepción en el caso de que el formato del email
	 *                   no sea correcto
	 * @author Enrique Estévez Mayoral
	 */
	public Espectador(Espectador e) throws Exception {
		if (!validarEmail(e.getEmail()))
			throw new Exception("El formato del email es incorrecto");

		this.nick = e.getNick();
		this.nombreApellidos = e.getNombreApellidos();
		this.email = e.getEmail();
	}

	/**
	 * Devuelve el nombre y apellidos del Espectador
	 * 
	 * @return nombreApellidos El nombre y apellidos del espectador
	 * @author Enrique Estévez Mayoral
	 * @author Ricardo Espantaleón
	 */
	public String getNombreApellidos() {
		return this.nombreApellidos;
	}

	/**
	 * Devuelve el nick del Espectador
	 * 
	 * @return nick El nick del espectador
	 * @author Enrique Estévez Mayoral
	 * @author Ricardo Espantaleón
	 */
	public String getNick() {
		return this.nick;
	}

	/**
	 * Devuelve el email del Espectador
	 * 
	 * @return email El email del espectador
	 * @author Enrique Estévez Mayoral
	 * @author Ricardo Espantaleón
	 */
	public String getEmail() {
		return this.email;
	}

	/**
	 * Cambia el valor de la variable nombreApellidos por el valor pasado por
	 * argumento
	 * 
	 * @param nombreApellidos El nombre y apellidos del espectador
	 * @author Enrique Estévez Mayoral
	 * @author Ricardo Espantaleón
	 */
	public void setNombreApellidos(String nombreApellidos) {
		this.nombreApellidos = nombreApellidos;
	}

	/**
	 * Cambia el valor de la variable nick por el valor pasado por argumento
	 * 
	 * @param nick El nick del espectador
	 * @author Enrique Estévez Mayoral
	 */
	public void setNick(String nick) {
		this.nick = nick;
	}

	/**
	 * Cambia el valor de la variable email por el valor pasado por argumento
	 * 
	 * @param email El email del espectador
	 * @throws Exception Lanza una excepción en el caso de que el formato del email
	 *                   no sea correcto
	 * @author Enrique Estévez Mayoral
	 * @author Ricardo Espantaleón
	 */
	public void setEmail(String email) throws Exception {
		if (!validarEmail(email))
			throw new Exception("El formato del email es incorrecto");

		this.email = email;
	}

	/**
	 * Función para comprobar si el email introducido cumple la REGEX
	 * 
	 * @param email El email del espectador
	 * @return Verdadero si cumple el formato y falso en caso contrario
	 * 
	 * @author Ricardo Espantaleón
	 */
	private boolean validarEmail(String email) {
		Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(email);

		return matcher.find();
	}

	/**
	 * Funcion que indica si un usuario tiene derechos de administrador o no
	 * 
	 * @return Devuelve true si el usuario es administrador y false si no lo es
	 * @author Enrique Estévez Mayoral
	 */
	public int esAdmin() {
		return this.esAdmin;
	}

	/**
	 * Cambia el valor de la variable fechaCreacion por el valor pasado por
	 * argumento
	 * 
	 * @param fechaCreacion La fecha de creación del usuario
	 * @author Ricardo Espantaleón
	 */
	public void setFechaCreacion(Date fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}

	/**
	 * Función que retorna la fecha de creación del usuario
	 * 
	 * @return Retorna la fecha de creación del usuario
	 * @author Ricardo Espantaleón
	 */
	public Date getFechaCreacion() {
		return this.fechaCreacion;
	}

}