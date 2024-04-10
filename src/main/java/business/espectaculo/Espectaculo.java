package business.espectaculo;

import java.util.ArrayList;
import java.util.Date;

import business.sesion.Sesion;

/**
 * La clase Critica es un TAD que se encarga de almacenar y gestionar todo los
 * atributos relacionados con un espectáculo
 * 
 * @author Enrique Estévez Mayoral
 * @author Ricardo Espantaleón
 * @see Sesion
 * @version 1.0
 */
public class Espectaculo {

	String titulo;
	String categorias;
	String descripcion;
	ArrayList<Sesion> sesiones;

	/**
	 * Constructor parametrizado de la clase Espectaculo
	 * 
	 * @param titulo      Cadena que contiene el título del espectáculo
	 * @param categoria   Cadena que contiene las categorías del espectáculo
	 * @param descripcion Cadena que contiene la descripción del espectáculo
	 * @param sesiones    Clase de tipo Sesion que contiene la fecha y localidades
	 *                    de la sesión de un espectáculo
	 * @author Enrique Estévez Mayoral
	 */
	Espectaculo(String titulo, String categoria, String descripcion, ArrayList<Sesion> sesiones) {
		this.titulo = titulo;
		this.categorias = categoria;
		this.descripcion = descripcion;
		this.sesiones = sesiones;
	}

	/**
	 * Función que devuelve el título del espectáculo
	 * 
	 * @return El título del espectáculo
	 * @author Enrique Estévez Mayoral
	 */
	public String getTitulo() {
		return titulo;
	}

	/**
	 * Función que devuelve la categoria del espectáculo
	 * 
	 * @return Las categorías del espectáculo
	 * @author Enrique Estévez Mayoral
	 */
	public String getCategorias() {
		return categorias;
	}

	/**
	 * Función que devuelve la descripción del espectáculo
	 * 
	 * @return La descripción del espectáculo
	 * @author Enrique Estévez Mayoral
	 */
	public String getDescripcion() {
		return descripcion;
	}

	/**
	 * Función que devuelve las sesiones del espectáculo
	 * 
	 * @return Las sesiones del espectáculo
	 * @author Enrique Estévez Mayoral
	 */
	public ArrayList<Sesion> getSesiones() {
		return sesiones;
	}

	/**
	 * Cambia el valor de la variable título por el valor pasado por argumento
	 * 
	 * @param titulo Título a cambiar a la instancia
	 * @author Enrique Estévez Mayoral
	 * 
	 */
	void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	/**
	 * Cambia el valor de la variable categorías por el valor pasado por argumento
	 * 
	 * @param categorias Categoría a cambiar a la instancia
	 * @author Enrique Estévez Mayoral
	 */
	void setCategorias(String categorias) {
		this.categorias = categorias;
	}

	/**
	 * Cambia el valor de la variable descripción por el valor pasado por argumento
	 * 
	 * @param titulo Título a cambiar a la instancia
	 * @author Enrique Estévez Mayoral
	 */
	void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	/**
	 * Cambia el valor de la variable sesiones por el valor pasado por argumento
	 * 
	 * @author Enrique Estévez Mayoral
	 */
	void setSesiones(ArrayList<Sesion> sesiones) {
		this.sesiones = sesiones;
	}

	/**
	 * Función que elimina todas las sesiones asociadas al espectáculo
	 * 
	 * @author Ricardo Espantaleón
	 */
	public void eliminaSesiones() {
		this.sesiones.clear();
	}

	/**
	 * Función que elimina una sesión de un espectáculo
	 * 
	 * 
	 * @param fecha Fecha y hora de la sesión a eliminar
	 * 
	 * @author Ricardo Espantaleón
	 */
	void eliminaSesion(Date fecha) {
		for (int i = 0; i < sesiones.size(); ++i) {
			if (sesiones.get(i).getFecha().compareTo(fecha) == 0) {
				sesiones.remove(i);
				break;
			}
		}
	}

	/**
	 * Función que afirma que quedan entradas disponibles.
	 * 
	 * @return true
	 */
	public boolean entradasDisponibles() {

		return true;
	}

}
