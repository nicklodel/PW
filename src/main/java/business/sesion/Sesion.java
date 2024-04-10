package business.sesion;

import java.util.Date;

import business.espectaculo.Espectaculo;

/**
 * La clase Critica es un TAD que se encarga de almacenar y gestionar todo los
 * atributos relacionados con la sesión de un espectáculo
 * 
 * @author Enrique Estévez Mayoral
 * @author Nicolás López
 * @see Espectaculo
 * @version 1.0
 */
public class Sesion {

	private int localidades;
	private Date fecha;
	private int entradasVendidas;
	private int id;
	private String espectaculo;

	/**
	 * Constructor vacío de la clase Sesion
	 * 
	 * @author Enrique Estévez Mayoral
	 */
	public Sesion() {
	}

	/**
	 * Constructor parametrizado de la clase Sesion
	 * 
	 * @param localidades      Numero entero que contiene las localidades que tiene
	 *                         una sesión de un espectáculo
	 * @param fecha            Fecha y hora de una sesión de un espectáculo
	 * @param entradasVendidas Entradas vendidas del espectáculo
	 * @param id               El identificador de la sesión
	 * @author Enrique Estévez Mayoral
	 */
	public Sesion(int localidades, Date fecha, int entradasVendidas, int id) {
		this.localidades = localidades;
		this.fecha = fecha;
		this.entradasVendidas = entradasVendidas;
		this.id = id;
		this.espectaculo = "";
	}

	/**
	 * Constructor parametrizado de la clase Sesion
	 * 
	 * @param localidades      Numero entero que contiene las localidades que tiene
	 *                         una sesión de un espectáculo
	 * @param fecha            Fecha y hora de una sesión de un espectáculo
	 * @param entradasVendidas Entradas vendidas del espectáculo
	 * @param id               El identificador de la sesión
	 * @param espectaculo      El tipo de espectáculo de la sesión
	 * @author Enrique Estévez Mayoral
	 */
	public Sesion(int localidades, Date fecha, int entradasVendidas, int id, String espectaculo) {
		this.localidades = localidades;
		this.fecha = fecha;
		this.entradasVendidas = entradasVendidas;
		this.id = id;
		this.espectaculo = espectaculo;
	}

	/**
	 * Constructor parametrizado de la clase Sesion (sin id)
	 * 
	 * @param localidades      Numero entero que contiene las localidades que tiene
	 *                         una sesión de un espectáculo
	 * @param fecha            Fecha y hora de una sesión de un espectáculo
	 * @param entradasVendidas Entradas vendidas del espectáculo
	 * @author Enrique Estévez Mayoral
	 */
	public Sesion(int localidades, Date fecha, int entradasVendidas) {
		this.localidades = localidades;
		this.fecha = fecha;
		this.entradasVendidas = entradasVendidas;
		this.espectaculo = "";
	}

	/**
	 * Función que devuelve las localidades de la clase Sesion
	 * 
	 * @return Devuelve el número de localidades
	 * @author Enrique Estévez Mayoral
	 */
	public int getLocalidades() {
		return localidades;
	}

	/**
	 * Función que devuelve la fecha y hora de la clase Sesion
	 * 
	 * @return Devuelve la fecha y hora
	 * @author Enrique Estévez Mayoral
	 */
	public Date getFecha() {
		return fecha;
	}

	/**
	 * Función que cambia el valor de la variable localidades por el valor pasado
	 * como argumento
	 * 
	 * @param localidades Número de localidades de la sesión
	 * @author Enrique Estévez Mayoral
	 */
	public void setLocalidades(int localidades) {
		this.localidades = localidades;
	}

	/**
	 * Función que cambia el valor de la variable fecha por el valor pasado como
	 * argumento
	 * 
	 * @param fecha Fecha y hora de la sesión
	 * @author Enrique Estevez Mayoral
	 */
	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	/**
	 * Función que asigna un nuevo ID a la sesión
	 * 
	 * @param id ID nuevo a asignar a la sesión
	 * 
	 * @author Ricardo Espantaleón Pérez
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * Función que suma una entrada vendida
	 *
	 * @author Nicolás López
	 */
	public void entradaVendida() {
		this.entradasVendidas++;
	}

	/**
	 * Función que devuelve las localidades vendidas de la clase Sesion
	 * 
	 * @return Devuelve el número de localidades vendidas
	 * @author Nicolás López
	 */
	public int getVendidas() {
		return this.entradasVendidas;
	}

	/**
	 * Función que retorna el id asociado a una sesión en la base de datos
	 * 
	 * @return El id de la sesión
	 * @author Nicolás López
	 */
	public int getId() {
		return this.id;
	}

	/**
	 * Función que retorna el tipo de espectáculo asociado a una sesión en la base
	 * de datos
	 * 
	 * @return El tipo de espectáculo de la sesión
	 * @author Nicolás López
	 */
	public String getEspectaculo() {
		return espectaculo;
	}

	/**
	 * Función que cambia el valor de la variable fecha por el valor pasado como
	 * argumento a la función
	 * 
	 * @param espectaculo El tipo de espectáculo de la sesión
	 * @author Nicolás López
	 */
	public void setEspectaculo(String espectaculo) {
		this.espectaculo = espectaculo;
	}
}
