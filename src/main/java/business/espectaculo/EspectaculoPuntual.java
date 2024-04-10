package business.espectaculo;

import java.util.ArrayList;

import business.sesion.Sesion;

/**
 * La clase Critica es un TAD que se encarga de almacenar y gestionar todo los
 * atributos relacionados con la sesión de un espectáculo puntual
 * 
 * @author Enrique Estévez Mayoral
 * @see Espectaculo
 * @version 1.0
 */
public class EspectaculoPuntual extends Espectaculo {

	/**
	 * Constructor parametrizado de la clase EspectaculoPuntual
	 * 
	 * @param titulo      Cadena que contiene el título del espectáculo
	 * @param categorias  Cadena que contiene las categorías del espectáculo
	 * @param descripcion Cadena que contiene la descripción del espectáculo
	 * @param sesion      Clase de tipo Sesion que contiene la fecha y localidades
	 *                    de la sesión de un espectáculo
	 * @author Enrique Estévez Mayoral
	 */
	public EspectaculoPuntual(String titulo, String categorias, String descripcion, ArrayList<Sesion> sesion) {
		super(titulo, categorias, descripcion, sesion);
		this.titulo = titulo;
		this.categorias = categorias;
		this.descripcion = descripcion;
		this.sesiones = sesion;
	}

}