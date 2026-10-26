package main;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

/**
 * Punto único de acceso a la configuración del sistema. Lee el fichero
 * {@code application.properties} con {@link FileInputStream} al arrancar y
 * expone cada propiedad mediante métodos estáticos.
 *
 * <p>
 * Así ninguna otra clase necesita conocer ni la ruta del fichero de
 * propiedades ni gestionar su lectura.
 * </p>
 *
 * @author Diego Rodríguez Fernández
 */
public final class Configuracion {

	/** Ruta al fichero de propiedades. Es la única constante hardcodeada. */
	private static final String RUTA_PROPERTIES = "src/main/resources/application.properties";

	// Claves del fichero .properties
	private static final String CLAVE_ADMIN_NOMBRE    = "admin-name";
	private static final String CLAVE_ADMIN_PASSWORD   = "admin-password";
	private static final String CLAVE_RUTA_CREDENCIALES = "ruta-credenciales";
	private static final String CLAVE_RUTA_ALERGIAS    = "ruta-alergias";

	private static final Properties PROPS = cargarPropiedades();

	/** No se instancia: clase de utilidades estática. */
	private Configuracion() {
	}

	/**
	 * Lee el fichero {@value #RUTA_PROPERTIES} y devuelve el objeto
	 * {@link Properties} con todas las claves cargadas.
	 *
	 * @return propiedades cargadas, o un objeto vacío si el fichero no se puede
	 *         leer (el programa seguirá arrancando pero fallará al pedir cualquier
	 *         propiedad).
	 */
	private static Properties cargarPropiedades() {
		Properties props = new Properties();
		try (FileInputStream fis = new FileInputStream(RUTA_PROPERTIES)) {
			props.load(fis);
		} catch (FileNotFoundException e) {
			System.err.println("ERROR: No se ha encontrado el fichero de configuración: " + RUTA_PROPERTIES);
		} catch (IOException e) {
			System.err.println("ERROR: No se ha podido leer el fichero de configuración: " + RUTA_PROPERTIES);
		}
		return props;
	}

	/**
	 * Devuelve el nombre de usuario del administrador.
	 *
	 * @return valor de la clave {@value #CLAVE_ADMIN_NOMBRE}.
	 */
	public static String getAdminNombre() {
		return PROPS.getProperty(CLAVE_ADMIN_NOMBRE);
	}

	/**
	 * Devuelve la contraseña del administrador.
	 *
	 * @return valor de la clave {@value #CLAVE_ADMIN_PASSWORD}.
	 */
	public static String getAdminPassword() {
		return PROPS.getProperty(CLAVE_ADMIN_PASSWORD);
	}

	/**
	 * Devuelve la ruta al fichero de credenciales de empleados.
	 *
	 * @return valor de la clave {@value #CLAVE_RUTA_CREDENCIALES}.
	 */
	public static String getRutaCredenciales() {
		return PROPS.getProperty(CLAVE_RUTA_CREDENCIALES);
	}

	/**
	 * Devuelve la ruta a la carpeta de ficheros de alergias.
	 *
	 * @return valor de la clave {@value #CLAVE_RUTA_ALERGIAS}.
	 */
	public static String getRutaAlergias() {
		return PROPS.getProperty(CLAVE_RUTA_ALERGIAS);
	}
}
