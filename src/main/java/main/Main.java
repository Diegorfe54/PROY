package main;

import java.io.IOException;
import java.util.Scanner;

/**
 * Clase principal del programa.
 * 
 * @author Diego Rodríguez Fernández
 */
public class Main {
	public static final Scanner SC = new Scanner(System.in);
	private static Sesion sesion;

	/**
	 * Muestra el menú de invitado.
	 */
	public static void invitado() {
		System.out.println("=== MENU DE INVITADO ===");
		System.out.println("1.- Log In");
		System.out.println("0.- SALIR");
	}

	/**
	 * Muestra el menú del secretariado.
	 */
	public static void secretariado() {
		System.out.println("=== MENU DE SECRETARIADO ===");
		System.out.println("1.- Log Out");
		System.out.println("2.- Eliminar Empleado");
		System.out.println("3.- Añadir Empleado");
		System.out.println("4.- Modificar Empleado");
		System.out.println("0.- SALIR");
	}

	/**
	 * Muestra el menú de enfermería.
	 */
	public static void enfermeria() {
		System.out.println("=== MENU DE ENFERMERIA ===");
		System.out.println("1.- Log Out");
		System.out.println("2.- Añadir Alergia");
		System.out.println("3.- Editar Alergia");
		System.out.println("4.- Ver Alergia");
		System.out.println("5.- Listar Alergias Registradas");
		System.out.println("0.- SALIR");
	}

	/**
	 * Muestra el menú de administrador.
	 */
	public static void administrador() {
		System.out.println("=== MENU DE ADMINISTRADOR ===");
		System.out.println("1.- Log Out");
		System.out.println("2.- Eliminar Empleado");
		System.out.println("3.- Añadir Empleado");
		System.out.println("4.- Modificar Empleado");
		System.out.println("5.- Añadir Alergia");
		System.out.println("6.- Editar Alergia");
		System.out.println("7.- Ver Alergia");
		System.out.println("8.- Listar Alergias Registradas");
		System.out.println("0.- SALIR");
	}

	/**
	 * Decide qué menú mostrar en función del perfil que haya iniciado sesión.
	 * 
	 * @param perfil perfil del usuario autenticado.
	 */
	public static void mostrar(Perfiles perfil) {
		if (perfil == null) {
			invitado();
			return;
		}
		switch (perfil) {
		case SECRETARIA:
			secretariado();
			break;
		case ENFERMERIA:
			enfermeria();
			break;
		case ADMINISTRADOR:
			administrador();
			break;
		case CIRUJANO:
		default:
			invitado();
			break;
		}
	}

	/**
	 * Maneja qué acciones realizar para cada perfil.
	 * 
	 * @param perfil perfil del usuario autenticado.
	 * @param opcion int introducido por el usuario.
	 * @return {@code false} si la opción no existe para ese perfil.
	 */
	private static boolean ejecutarAccion(Perfiles perfil, int opcion) {
		if (perfil == null) {
			return false;
		}
		switch (perfil) {
		case SECRETARIA:
			return accionesSecretariado(opcion);
		case ENFERMERIA:
			return accionesEnfermeria(opcion);
		case ADMINISTRADOR:
			return accionesAdministrador(opcion);
		case CIRUJANO:
		default:
			return false;
		}
	}

	/**
	 * Maneja las acciones de las que dispone un empleado del secretariado.
	 * 
	 * @param opcion int introducido por el usuario.
	 * @return boolean validar si salir o no.
	 */
	private static boolean accionesSecretariado(int opcion) {
		switch (opcion) {
		case 2:
			GestorEmpleados.eliminarEmpleado();
			return true;
		case 3:
			GestorEmpleados.anadirEmpleado();
			return true;
		case 4:
			GestorEmpleados.modificarEmpleado();
			return true;
		default:
			return false;
		}
	}

	/**
	 * Maneja las acciones de las que dispone un empleado de enfermeria.
	 * 
	 * @param opcion int introducido por el usuario.
	 * @return boolean validar si salir o no.
	 */
	private static boolean accionesEnfermeria(int opcion) {
		switch (opcion) {
		case 2:
			GestorAlergias.anadirAlergia();
			return true;
		case 3:
			GestorAlergias.editarAlergia();
			return true;
		case 4:
			GestorAlergias.verAlergia();
			return true;
		case 5:
			System.out.println("== LISTADO DE ALERGIAS REGISTRADAS ==");
			System.out.println("");
			GestorAlergias.verAlergiasDisponibles();
			System.out.println("");
			return true;
		default:
			return false;
		}
	}

	/**
	 * Maneja las acciones de las que dispone un administrador.
	 * 
	 * @param opcion int introducido por el usuario.
	 * @return boolean validar si salir o no.
	 */
	private static boolean accionesAdministrador(int opcion) {
		if (opcion >= 5 && opcion <= 8) {
			return accionesEnfermeria(opcion - 3);
		}
		return accionesSecretariado(opcion);
	}

	/**
	 * Un ciclo del menú de invitado.
	 * 
	 * @return {@code true} si el usuario quiere salir del programa.
	 * @throws IOException si no se puede leer el archivo de credenciales.
	 */
	private static boolean turnoInvitado() {
		invitado();
		switch (Utilidades.leerInt(SC)) {
		case 1:
			iniciarSesion();
			return false;
		case 0:
			System.out.println("Has salido correctamente del menú de inicio de sesión.");
			return true;
		default:
			System.err.println("ERROR: Has introducido incorrectamente los datos. Seleccione una opción del menú.");
			return false;
		}
	}

	/**
	 * Se encarga de llamar al método {@link Sesion#login()} para iniciar sesión.
	 * 
	 * @throws IOException no se ha podido leer el fichero de validación.
	 */
	private static void iniciarSesion() {
		sesion = Sesion.login();
		if (sesion != null) {
			System.out.println(
					"Bienvenido/a " + sesion.getPerfil().getNombre() + " " + sesion.getNombre() + ", tenga un buen día.");
		}
	}

	/**
	 * Un ciclo del menú del usuario autenticado.
	 * 
	 * @return {@code true} si el usuario quiere salir del programa.
	 */
	private static boolean turnoAutenticado() {
		Perfiles perfil = sesion.getPerfil();
		mostrar(perfil);
		int opcion = Utilidades.leerInt(SC);
		if (opcion == 0) {
			System.out.println("Has salido correctamente del menú de trabajo.");
			return true;
		}
		if (opcion == 1) {
			cerrarSesion();
		} else if (!ejecutarAccion(perfil, opcion)) {
			System.err.println("ERROR: Has introducido incorrectamente los datos. Seleccione una opción del menú.");
		}
		return false;
	}

	/**
	 * Se encarga de llamar al método {@link Sesion#logout()} para cerrar la sesión.
	 */
	private static void cerrarSesion() {
		sesion = Sesion.logout();
		if (sesion == null) {
			System.out.println("Se ha cerrado sesión correctamente");
		} else {
			System.err.println("ERROR: No se ha podido cerrar sesión, inténtelo de nuevo.");
		}
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println("INI");

		try {
			boolean salir = false;
			while (!salir) {
				salir = (sesion == null) ? turnoInvitado() : turnoAutenticado();
			}
		} catch (IllegalStateException e) {
			System.err.println("ERROR: " + e.getMessage());
		}

		System.out.println("FIN");
	}

}
