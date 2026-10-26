package main;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;
import java.util.Scanner;

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

	// Rutas y archivos de autenticación
	private static final String RUTA_RECURSOS = "src/main/resources";
	private static final File CREDENCIALES_FILE = new File(RUTA_RECURSOS, "credenciales.txt");
	private static final File PROPERTIES_FILE = new File(RUTA_RECURSOS, "application.properties");

	// Datos administrador
	private static final String ADMIN_USUARIO = "admin-name";
	private static final String ADMIN_PASSWORD = "admin-password";
	private static final String NOMBRE_ADMINISTRADOR = "Administrador";
	
	public static final String TIPO_SECRETARIADO = "secretariado";
	public static final String TIPO_ENFERMERIA = "enfermeria";
	public static final String TIPO_ADMIN = "administrador";

	private static Sesion sesion;

	private String nombreUsuario;
	private String nombre;
	private String tipoEmpleado;

	public Sesion() {

	}

	public Sesion(String nombreUsuario, String nombre, String tipoEmpleado) {
		super();
		this.nombreUsuario = nombreUsuario;
		this.nombre = nombre;
		this.tipoEmpleado = tipoEmpleado;
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

	public String getTipoEmpleado() {
		return tipoEmpleado;
	}

	public void setTipoEmpleado(String tipoEmpleado) {
		this.tipoEmpleado = tipoEmpleado;
	}

	@Override
	public String toString() {
		return "Sesion [nombreUsuario=" + nombreUsuario + ", nombre=" + nombre + ", tipoEmpleado=" + tipoEmpleado
				+ ", getNombreUsuario()=" + getNombreUsuario() + ", getNombre()=" + getNombre() + ", getTipoEmpleado()="
				+ getTipoEmpleado() + "]";
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
		Properties adminProperties = cargarPropiedadesAdmin();
		if (adminProperties != null && usuario.equals(adminProperties.getProperty(ADMIN_USUARIO))) {
			candidata = autenticarAdmin(usuario, contrasenia, adminProperties);
		} else {
			candidata = autenticarEmpleado(usuario, contrasenia);
			if (candidata != null && !normalizarPerfilEmpleado(candidata)) {
				return null;
			}
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
	 * Carga application.properties.
	 * 
	 * @return las propiedades del administrador, o {@code null} si no se pudo leer el fichero.
	 */
	private static Properties cargarPropiedadesAdmin() {
		Properties props = new Properties();
		try (FileReader fr = new FileReader(PROPERTIES_FILE)) {
			props.load(fr);;
			return props;
		} catch (FileNotFoundException e) {
			System.err.println("ERROR: No se ha encontrado el archivo application.properties");
		} catch (IOException e) {
			System.err.println("ERROR: No se ha podido leer el archivo application.properties.");
		}
		return null;
	}
	
	/**
	 * Auntentica las credenciales del administrador.
	 * 
	 * @param usuario String registra el nombre de usuario introducido por el usuario.
	 * @param contrasenia String registra la contraseña introducida por el usuario.
	 * @param adminProperties objeto {@link Properties} que contiene las propiedades de configuración utilizadas para validar las credenciales del administrador.
	 * @return la sesion iniciada, o {@code null} si no se ha podido iniciar.
	 * 
	 * @see #login()
	 */
	private static Sesion autenticarAdmin(String usuario, String contrasenia, Properties adminProperties) {
		if (contrasenia.equals(adminProperties.getProperty(ADMIN_PASSWORD))) {
			return new Sesion(usuario, NOMBRE_ADMINISTRADOR, TIPO_ADMIN);
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
 
		try (BufferedReader br = new BufferedReader(new FileReader(CREDENCIALES_FILE))) {
			String linea;
			while ((linea = br.readLine()) != null) {
				String[] palabras = linea.split("\\|");
				if (palabras.length < 4) {
					continue; // línea mal formada
				}
				boolean usuarioCoincide = usuario.equals(palabras[0]);
				boolean contraseniaCoincide = contrasenia.equals(palabras[1]);
 
				if (usuarioCoincide && contraseniaCoincide) {
					return new Sesion(usuario, palabras[2], palabras[3]);
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
	 * Normaliza (sin espacios ni mayúsculas) y valida el perfil de un empleado leído
	 * de credenciales.txt. Solo se admiten secretariado y enfermería: el perfil
	 * admin existe únicamente en application.properties.
	 *
	 * @param candidata sesión recién autenticada, cuyo perfil se normaliza.
	 * @return {@code true} si el perfil es válido.
	 */
	private static boolean normalizarPerfilEmpleado(Sesion candidata) {
		String perfil = (candidata.getTipoEmpleado() == null) ? "" : candidata.getTipoEmpleado().trim().toLowerCase();

		if (perfil.equals(TIPO_SECRETARIADO) || perfil.equals(TIPO_ENFERMERIA)) {
			candidata.setTipoEmpleado(perfil);
			return true;
		}
		System.err.println("ERROR: El usuario tiene un perfil no válido (\"" + candidata.getTipoEmpleado()
				+ "\") en credenciales.txt. Contacte con el administrador.");
		return false;
	}
}
