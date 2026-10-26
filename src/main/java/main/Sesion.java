package main;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/**
 * Representa una sesión dentro del programa.
 * 
 * <p>
 * Permite registrar una sesión que se mantendrá activa mientras el programa
 * este siendo ejecutado.
 * </p>
 * 
 * @author Diego Rodríguez Fernández
 */
public class Sesion {

	private static final String NOMBRE_ADMINISTRADOR = "Administrador";

	private static Sesion sesion;

	private String nombreUsuario;
	private String nombre;
	private Perfiles perfil;

	public Sesion() {

	}

	public Sesion(String nombreUsuario, String nombre, Perfiles perfil) {
		super();
		this.nombreUsuario = nombreUsuario;
		this.nombre = nombre;
		this.perfil = perfil;
	}

	public String getNombreUsuario() {
		return nombreUsuario;
	}

	public void setNombreUsuario(String nombreUsuario) {
		this.nombreUsuario = nombreUsuario;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Perfiles getPerfil() {
		return perfil;
	}

	public void setPerfil(Perfiles perfil) {
		this.perfil = perfil;
	}

	@Override
	public String toString() {
		return "Sesion [nombreUsuario=" + nombreUsuario + ", nombre=" + nombre + ", perfil=" + perfil + "]";
	}

	/**
	 * Autentica a un usuario, ya sea el administrador o un empleado.
	 * 
	 * @return la sesión iniciada, o {@code null} si no se ha podido iniciar.
	 */
	public static Sesion login() {
		if (sesion != null) {
			System.err.println("ERROR: Ya hay una sesión iniciada. Cierre sesión primero.");
			return sesion;
		}
		System.out.println("Introduzca su nombre de usuario:");
		String usuario = Utilidades.leerLinea(Main.SC);
		System.out.println("Introduzca su contrasenia: ");
		String contrasenia = Utilidades.leerLinea(Main.SC);
		if (usuario == null || contrasenia == null) {
			return null;
		}
		usuario = usuario.replaceAll("\\s+", "");
		contrasenia = contrasenia.replaceAll("\\s+", "");

		if (usuario.isEmpty() || contrasenia.isEmpty()) {
			System.err.println("ERROR: El usuario y contraseña no pueden estar vacíos. Introduzcalo de nuevo.");
			return null;
		}
		Sesion candidata;
		String adminNombre = Configuracion.getAdminNombre();
		if (adminNombre != null && usuario.equals(adminNombre)) {
			candidata = autenticarAdmin(usuario, contrasenia);
		} else {
			candidata = autenticarEmpleado(usuario, contrasenia);
		}
		sesion = candidata;
		return sesion;
	}

	/**
	 * Cierra la sesión actual.
	 * 
	 * @return siempre {@code null}, no hay sesión activa.
	 */
	public static Sesion logout() {
		sesion = null;
		return sesion;
	}
	
	/**
	 * Autentica las credenciales del administrador comparando con los valores
	 * obtenidos de {@link Configuracion}.
	 *
	 * @param usuario     nombre de usuario introducido.
	 * @param contrasenia contraseña introducida.
	 * @return la sesión iniciada, o {@code null} si la contraseña es incorrecta.
	 *
	 * @see #login()
	 */
	private static Sesion autenticarAdmin(String usuario, String contrasenia) {
		if (contrasenia.equals(Configuracion.getAdminPassword())) {
			return new Sesion(usuario, NOMBRE_ADMINISTRADOR, Perfiles.ADMINISTRADOR);
		}
		System.err.println("ERROR: La contraseña es incorrecta, inténtelo de nuevo.");
		return null;
	}
	
	/**
	 * Autentica las credenciales del empleado introducidas por el usuario.
	 * 
	 * @param usuario String registra el nombre de usuario introducido por el usuario.
	 * @param contrasenia String registra la contraseña introducida por el usuario.
	 * @return la sesion iniciada, o {@code null} si no se ha podido iniciar.
	 * 
	 * @see #login()
	 */
	private static Sesion autenticarEmpleado(String usuario, String contrasenia) {
		boolean usuarioExiste = false;
		boolean contraseniaExiste = false;
 
		try (BufferedReader br = new BufferedReader(new FileReader(new File(Configuracion.getRutaCredenciales())))) {
			String linea;
			while ((linea = br.readLine()) != null) {
				String[] palabras = linea.split("\\|");
				if (palabras.length < 4) {
					continue; // línea mal formada
				}
				boolean usuarioCoincide = usuario.equals(palabras[0]);
				boolean contraseniaCoincide = contrasenia.equals(palabras[1]);
 
				if (usuarioCoincide && contraseniaCoincide) {
					Perfiles perfil = resolverPerfilEmpleado(palabras[3]);
					if (perfil == null) {
						System.err.println("ERROR: El usuario tiene un perfil no válido (\"" + palabras[3]
								+ "\") en credenciales.txt. Contacte con el administrador.");
						return null;
					}
					return new Sesion(usuario, palabras[2], perfil);
				}
				usuarioExiste = usuarioCoincide;
				contraseniaExiste = contraseniaCoincide;
			}
			mostrarErrorCredenciales(usuarioExiste, contraseniaExiste);
		} catch (FileNotFoundException e) {
			System.err.println("ERROR: No se ha encontrado el archivo credenciales.txt");
		} catch (IOException e) {
			System.err.println("ERROR: No se ha podido leer el archivo credenciales.txt.");
		}
		return null;
	}
 
	/**
	 * Muestra los errores que puedan darse al validar credenciales.
	 * 
	 * @param usuarioExiste boolean indica si el usuario introducido existe.
	 * @param contraseniaExiste boolean indica si la contraseña introducida es correcta.
	 *
	 * @see #autenticarEmpleado()
	 */
	private static void mostrarErrorCredenciales(boolean usuarioExiste, boolean contraseniaExiste) {
		if (usuarioExiste) {
			System.err.println("ERROR: La contraseña es incorrecta, inténtelo de nuevo.");
		} else if (contraseniaExiste) {
			System.err.println("ERROR: El nombre de usuario es incorrecto, inténtelo de nuevo.");
		} else {
			System.err.println("ERROR: El nombre de usuario y la contraseña no son válidos.");
		}
	}
	
	/**
	 * Resuelve y valida el perfil de un empleado leído de credenciales.txt asignándole
	 * su constante correspondiente del enum {@link Perfiles}.
	 *
	 * @param textoPerfil cadena leída de credenciales.txt
	 * @return el enum {@link Perfiles} correspondiente, o {@code null} si no es válido.
	 */
	private static Perfiles resolverPerfilEmpleado(String textoPerfil) {
		if (textoPerfil == null) {
			return null;
		}
		String normalizado = textoPerfil.trim().toLowerCase();
		if (normalizado.equals(Perfiles.SECRETARIA.getNombre()) || normalizado.equals("secretariado")) {
			return Perfiles.SECRETARIA;
		}
		if (normalizado.equals(Perfiles.ENFERMERIA.getNombre())) {
			return Perfiles.ENFERMERIA;
		}
		if (normalizado.equals(Perfiles.CIRUJANO.getNombre())) {
			return Perfiles.CIRUJANO;
		}
		return null;
	}
}
