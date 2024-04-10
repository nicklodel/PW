package business.log;

import java.util.Date;

/**
 * Clase que gestiona el registro del Log del servidor Web
 * 
 * @author Ricardo Espantaleón
 *
 */
public class Log {

	// Variables
	private String nick;
	private Date ultimaConexion;
	private Date fechaCreacion;
	private int esAdmin;

	// Métodos

	/**
	 * Constructor parámetrizado de la Clase Log
	 * 
	 * @param nick           Nickname del usuario a agregar
	 * @param ultimaConexion Última conexión del usuario a insertar
	 * @param fechaCreación  Fecha de creación del usuario
	 * @param esAdmin        Boolean que indica si el usuario es administrador
	 * 
	 * @author Ricardo Espantaleón
	 */
	public Log(String nick, Date ultimaConexion, Date fechaCreacion, int esAdmin) {
		this.nick = nick;
		this.ultimaConexion = ultimaConexion;
		this.fechaCreacion = fechaCreacion;
		this.esAdmin = esAdmin;
	}

	/**
	 * Constructor parámetrizado de la Clase Log
	 * 
	 * @param nick           Nickname del usuario a agregar
	 * @param ultimaConexion Última conexión del usuario a insertar
	 * 
	 * @author Ricardo Espantaleón
	 */
	public Log(String nick, Date ultimaConexion) {
		this.nick = nick;
		this.ultimaConexion = ultimaConexion;
	}

	/**
	 * Función que retorna el nick
	 * 
	 * @return Retorna el nick del usuario en el registro del log
	 * @author Ricardo Espantaleón
	 */
	public String getNick() {
		return this.nick;
	}

	/**
	 * Función que retorna la última conexión
	 * 
	 * @return Retorna la última conexión del usuario en el log
	 * @author Ricardo Espantaleón
	 */
	public Date getUltimaConexion() {
		return this.ultimaConexion;
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

	/**
	 * Función que retorna si el usuario es administrador
	 * 
	 * @return Retorna un entero indicando si el usuario es administrador o no
	 * @author Ricardo Espantaleón Pérez
	 */
	public int getEsAdmin() {
		return this.esAdmin;
	}

	/**
	 * Función que asigna el nick del usuario del log
	 * 
	 * @param nick el nick del usuario
	 * @author Ricardo Espantaleón
	 */
	public void setNick(String nick) {
		this.nick = nick;
	}

	/**
	 * Función que asigna la última conexión del usuario dado
	 * 
	 * @param ultimaConexion la fecha de última conexión
	 * @author Ricardo Espantaleón
	 */
	public void setUltimaConexion(Date ultimaConexion) {
		this.ultimaConexion = ultimaConexion;
	}

	/**
	 * Función que asigna la fecha de creación del usuario
	 * 
	 * @param fechaCreacion Fecha de la creación del usuario
	 * @author Ricardo Espantaleón
	 */
	public void setFechaCreacion(Date fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}

	/**
	 * Función que asigna si el usuario es administrador
	 * 
	 * @param esAdmin Entero booleano que indica si el usuario es administrador
	 * @author Ricardo Espantaleón Pérez
	 */
	public void setEsAdmin(int esAdmin) {
		this.esAdmin = esAdmin;
	}

}
