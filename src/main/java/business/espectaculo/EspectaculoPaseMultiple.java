package business.espectaculo;

import java.util.ArrayList;

import business.sesion.Sesion;

/**
 * La clase Critica es un TAD que se encarga de almacenar y gestionar todo los
 * atributos relacionados con la sesión de un espectáculo de pase mÃºltiple
 * 
 * @author Enrique Estévez Mayoral
 * @see Espectaculo
 * @version 1.0
 */
public class EspectaculoPaseMultiple extends Espectaculo {

	/**
	 * Constructor parametrizado de la clase EspectaculoPaseMultiple
	 * 
	 * @param titulo      Cadena que contiene el título del espectáculo
	 * @param categorias  Cadena que contiene las categorías del espectáculo
	 * @param descripcion Cadena que contiene la descripción del espectáculo
	 * @param sesiones    Clase de tipo Sesion que contiene la fecha y localidades
	 *                    de la sesión de un espectáculo
	 * @author Enrique Estévez Mayoral
	 */
	public EspectaculoPaseMultiple(String titulo, String categorias, String descripcion, ArrayList<Sesion> sesiones) {
		super(titulo, categorias, descripcion, sesiones);
		this.titulo = titulo;
		this.categorias = categorias;
		this.descripcion = descripcion;
		this.sesiones = sesiones;
	}

}
