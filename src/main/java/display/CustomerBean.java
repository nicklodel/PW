package display;

import java.io.Serializable;

import business.espectador.Espectador;
import java.util.Date;

/**
 * @author ricardo
 *
 */
public class CustomerBean implements java.io.Serializable {
	// variables
	private static final long serialVersionUID = 1L;
	
	// Nickname correspondiente al usuario 
	private String nick;
	
	// Entero que nos indica si el usuario es administrador o no
	private int esAdmin;
	
	// Boolean que nos indica si el usuario ha sido validado o no
	private boolean validado;
	
	// Fecha del inicio de conexión del CustomerBean
	private Date ultimaConexion;

	/**
	 * Constructor de la clase Customer Bean
	 *
	 * @author Nicolás López
	 */
	public CustomerBean() {
		this.nick = "";
		this.esAdmin = 0;
		this.validado = false;
	}

	/**
	 * Función que cambia el valor de la variable nick por el valor pasado como
	 * argumento a la función
	 *
	 * @param nick El nickname del CustomerBean
	 * @author Ricardo Espantaleón
	 */
	public void setNick(String nick) {
		this.nick = nick;
	}

	/**
	 * Función que devuelve el parametro nick del CustomerBean
	 * 
	 * @return El parametro nick del CustomerBean
	 * @author Enrique Estévez Mayoral
	 */
	public String getNick() {
		return this.nick;
	}

	/**
	 * Función que devuelve el esAdmin validado del CustomerBean
	 * 
	 * @return El parametro esAdmin del CustomerBean
	 * @author Enrique Estévez Mayoral
	 */
	public int getEsAdmin() {
		return this.esAdmin;
	}

	/**
	 * Función que cambia el valor de la variable esAdmin por el valor pasado como
	 * argumento a la función
	 *
	 * @param admin El boolean que indica si el usuario es admin o no
	 * @author Nicolas
	 */
	public void setEsAdmin(int admin) {
		this.esAdmin = admin;
	}

	/**
	 * Función que devuelve el parametro validado del CustomerBean
	 * 
	 * @return El parametro validado del CustomerBean
	 * @author Enrique Estévez Mayoral
	 */
	public boolean getValidado() {
		return this.validado;
	}

	/**
	 * Función que cambia el valor de la variable validado por el valor pasado como
	 * argumento a la función
	 *
	 * @param valido Boolean que indica si la password es valida o no
	 * @author Enrique Estévez Mayoral
	 */
	public void setValidado(boolean valido) {
		this.validado = valido;
	}

	/**
	 * Función que devuelve la última conexión de un cliente
	 *
	 * @return fecha de ultima conexion
	 */
	public Date getUltimaConexion() {
		return this.ultimaConexion;
	}

	/**
	 * Función que actualiza la fecha de última conexión
	 *
	 * @param conexion la fecha de conexion reciente
	 *
	 */
	public void setUltimaConexion(Date conexion) {
		this.ultimaConexion = conexion;
	}

}
