package main;
 
import java.util.NoSuchElementException;
import java.util.Scanner;
 
/**
 * Métodos de entrada por consola reutilizables en todo el programa. Todos
 * reciben el {@code Scanner} como parámetro para no abrir varios sobre
 * {@code System.in}.
 * 
 * @author Diego Rodríguez Fernández
 */
public final class Utilidades {
 
	private Utilidades() {
	}
 
	/**
	 * Lee una línea completa de texto del {@code Scanner} indicado y le elimina los
	 * espacios al principio y al final. Si no hay ninguna línea disponible (por
	 * ejemplo, se ha cerrado la entrada estándar) o el {@code Scanner} está
	 * cerrado, muestra un error y devuelve {@code null}.
	 *
	 * @param sc {@code Scanner} del que se lee la línea.
	 * @return Línea introducida sin espacios en los extremos (puede ser una cadena
	 *         vacía si solo se pulsa Enter) o {@code null} si no se ha podido leer.
	 */
	public static String leerLinea(Scanner sc) {
		try {
			return sc.nextLine().trim();
		} catch (NoSuchElementException | IllegalStateException e) {
			System.err.println("ERROR: No se ha podido leer la entrada.");
			return null;
		}
	}
 
	/**
	 * Lee un número entero. Si lo introducido no es un entero válido, muestra un
	 * error y devuelve {@code -1}. Pensado para opciones de menú, donde {@code -1}
	 * actúa como "opción inválida".
	 *
	 * @param sc {@code Scanner} del que se lee el número.
	 * @return Entero introducido o {@code -1} si no es válido.
	 */
	public static int leerInt(Scanner sc) {
		String linea = leerLinea(sc);
		if (linea == null) {
			return -1;
		}
		Integer num = parsearInt(linea);
		if (num == null) {
			System.err.println("ERROR: Introduce un valor numérico.");
			return -1;
		}
		return num;
	}
 
	/**
	 * Lee un número entero largo. Si lo introducido no es válido, muestra un error
	 * y devuelve {@code null}.
	 *
	 * @param sc {@code Scanner} del que se lee el número.
	 * @return Número introducido o {@code null} si no es válido.
	 */
	public static Long leerLong(Scanner sc) {
		String linea = leerLinea(sc);
		if (linea == null) {
			return null;
		}
		try {
			return Long.parseLong(linea);
		} catch (NumberFormatException e) {
			System.err.println("ERROR: Introduce un valor numérico.");
			return null;
		}
	}
 
	/**
	 * Pide confirmación con Enter = sí. Equivale a {@code confirmar(sc, true)}.
	 *
	 * @param sc {@code Scanner} del que se lee la respuesta.
	 * @return {@code true} si confirma.
	 */
	public static boolean confirmar(Scanner sc) {
		return confirmar(sc, true);
	}
 
	/**
	 * Lee una respuesta de tipo sí/no. Acepta s, si, sí, y, yes (afirmativas) y n,
	 * no (negativas), sin distinguir mayúsculas. Si la respuesta es Enter, devuelve
	 * el valor por defecto. Con cualquier otra entrada, la repite hasta que sea
	 * válida. Si no se puede leer la entrada, devuelve {@code false}.
	 *
	 * @param sc         {@code Scanner} del que se lee la respuesta.
	 * @param porDefecto valor devuelto si el usuario solo pulsa Enter. Para
	 *                   operaciones destructivas conviene {@code false}.
	 * @return {@code true} si confirma, {@code false} en caso contrario.
	 */
	public static boolean confirmar(Scanner sc, boolean porDefecto) {
		Boolean resultado = null;
		while (resultado == null) {
			String respuesta = leerLinea(sc);
			if (respuesta == null) {
				resultado = false;
			} else if (respuesta.isEmpty()) {
				resultado = porDefecto;
			} else {
				char letra = Character.toLowerCase(respuesta.charAt(0));
				if (letra == 's' || letra == 'y') {
					resultado = true;
				} else if (letra == 'n') {
					resultado = false;
				} else {
					System.err.println("ERROR: Responda con S o N.");
				}
			}
		}
		return resultado;
	}
 
	/**
	 * Pide un texto no vacío, repitiendo la petición hasta que sea válido.
	 *
	 * @param mensaje mensaje mostrado al usuario.
	 * @param sc      {@code Scanner} del que se lee el texto.
	 * @return Texto introducido (nunca vacío).
	 * @throws IllegalStateException si se cierra la entrada estándar.
	 */
	public static String pedirTexto(String mensaje, Scanner sc) {
		return pedirTexto(mensaje, sc, Integer.MAX_VALUE);
	}
 
	/**
	 * Pide un texto no vacío de longitud máxima {@code maxLongitud}, repitiendo la
	 * petición hasta que sea válido.
	 *
	 * @param mensaje     mensaje mostrado al usuario.
	 * @param sc          {@code Scanner} del que se lee el texto.
	 * @param maxLongitud número máximo de caracteres permitidos.
	 * @return Texto introducido (no vacío y dentro del límite).
	 * @throws IllegalStateException si se cierra la entrada estándar.
	 */
	public static String pedirTexto(String mensaje, Scanner sc, int maxLongitud) {
		String texto = null;
		boolean valido = false;
		do {
			System.out.println(mensaje);
			texto = leerLinea(sc);
			if (texto == null) {
				throw new IllegalStateException("Entrada estándar cerrada.");
			}
			if (texto.isEmpty()) {
				System.err.println("ERROR: el campo no puede estar vacío.");
			} else if (texto.length() > maxLongitud) {
				System.err.println("ERROR: máximo " + maxLongitud + " caracteres.");
			} else {
				valido = true;
			}
		} while (!valido);
		return texto;
	}
 
	/**
	 * Pide un entero dentro del rango [min, max], repitiendo la petición hasta que
	 * sea válido.
	 *
	 * @param mensaje mensaje mostrado al usuario.
	 * @param sc      {@code Scanner} del que se lee el número.
	 * @param min     valor mínimo permitido (incluido).
	 * @param max     valor máximo permitido (incluido).
	 * @return Entero introducido dentro del rango.
	 * @throws IllegalStateException si se cierra la entrada estándar.
	 */
	public static int pedirInt(String mensaje, Scanner sc, int min, int max) {
		Integer num = null;
		do {
			System.out.println(mensaje);
			String linea = leerLinea(sc);
			if (linea == null) {
				throw new IllegalStateException("Entrada estándar cerrada.");
			}
			num = parsearInt(linea);
			if (num == null) {
				System.err.println("ERROR: introduzca un número entero.");
			} else if (num < min || num > max) {
				System.err.println("ERROR: el valor debe estar entre " + min + " y " + max + ".");
				num = null;
			}
		} while (num == null);
		return num;
	}
 
	/**
	 * Pide un entero mayor o igual que 1.
	 *
	 * @param mensaje mensaje mostrado al usuario.
	 * @param sc      {@code Scanner} del que se lee el número.
	 * @return Entero positivo introducido.
	 * @throws IllegalStateException si se cierra la entrada estándar.
	 */
	public static int pedirIntPositivo(String mensaje, Scanner sc) {
		return pedirInt(mensaje, sc, 1, Integer.MAX_VALUE);
	}
 
	/** Convierte a entero, o devuelve {@code null} si no es válido. */
	private static Integer parsearInt(String texto) {
		try {
			return Integer.parseInt(texto);
		} catch (NumberFormatException e) {
			return null;
		}
	}
}