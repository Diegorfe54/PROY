package main;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import entidades.Empleado;
import entidades.empleados.CategoriaEnfermero;
import entidades.empleados.Cirujano;
import entidades.empleados.Enfermero;
import entidades.empleados.Especialidades;
import entidades.empleados.Secretario;

/**
 * Gestiona los empleados almacenados en el fichero de credenciales
 * {@code credenciales.txt}. Cada línea del fichero tiene el formato:
 * {@code nombreUsuario|contraseña|nombreCompleto|perfil}.
 *
 * @author Diego Rodríguez Fernández
 */
public class GestorEmpleados {

	private static final String SEPARADOR = "|";
	private static final String SEPARADOR_REGEX = "\\|";

	/**
	 * Devuelve el fichero de credenciales cuya ruta se lee de
	 * {@link Configuracion#getRutaCredenciales()}.
	 */
	private static File getCredencialesFile() {
		return new File(Configuracion.getRutaCredenciales());
	}

	/**
	 * Muestra el menú de opciones para modificar un empleado.
	 */
	private static void menuModificar() {
		System.out.println("1.- Modificar nombre de usuario.");
		System.out.println("2.- Modificar contraseña.");
		System.out.println("3.- Modificar nombre completo del empleado.");
		System.out.println("4.- Modificar tipo de empleado.");
		System.out.println("5.- Modificar todo.");
		System.out.println("0.- CANCELAR");
	}

	/**
	 * Muestra el menú de perfiles de empleado disponibles.
	 */
	private static void menuTipoEmpleado() {
		System.out.println(Perfiles.SECRETARIA.getId() + ".- " + Perfiles.SECRETARIA.name().substring(0, 1) + Perfiles.SECRETARIA.name().substring(1).toLowerCase());
		System.out.println(Perfiles.ENFERMERIA.getId() + ".- Enfermería");
		System.out.println(Perfiles.CIRUJANO.getId() + ".- Cirujano");
	}

	/**
	 * Muestra el menú de categorías de enfermero disponibles.
	 */
	private static void menuCategoriaEnfermeria() {
		System.out.println("1.- Categoría A");
		System.out.println("2.- Categoría B");
		System.out.println("3.- Categoría C");
	}

	/**
	 * Lee todas las líneas válidas del fichero de credenciales y las devuelve como
	 * lista de arrays {@code String[4]}: [usuario, contraseña, nombre, tipo].
	 *
	 * @return lista con los datos de todos los empleados, o vacía si el fichero no
	 *         existe o está vacío.
	 */
	private static List<String[]> leerTodosEmpleados() {
		List<String[]> empleados = new ArrayList<>();
		if (!getCredencialesFile().isFile()) {
			return empleados;
		}
		try (BufferedReader br = new BufferedReader(new FileReader(getCredencialesFile()))) {
			String linea;
			while ((linea = br.readLine()) != null) {
				linea = linea.trim();
				if (linea.isEmpty()) {
					continue;
				}
				String[] datos = linea.split(SEPARADOR_REGEX, -1);
				if (datos.length == 4) {
					empleados.add(datos);
				}
			}
		} catch (IOException e) {
			System.err.println("ERROR: no se ha podido leer el fichero de credenciales.");
		}
		return empleados;
	}

	/**
	 * Busca en el fichero de credenciales al empleado cuyo nombre de usuario
	 * coincide con {@code nombreUsuario}.
	 *
	 * @param nombreUsuario nombre de usuario a buscar.
	 * @return array {@code String[4]} con los datos del empleado, o {@code null} si
	 *         no existe.
	 */
	private static String[] buscarEmpleado(String nombreUsuario) {
		for (String[] datos : leerTodosEmpleados()) {
			if (datos[0].equals(nombreUsuario)) {
				return datos;
			}
		}
		return null;
	}

	/**
	 * Sobreescribe el fichero de credenciales con la lista de empleados
	 * proporcionada.
	 *
	 * @param empleados lista de arrays {@code String[4]} a escribir.
	 * @return {@code true} si la escritura ha sido correcta.
	 */
	private static boolean sobreescribirFichero(List<String[]> empleados) {
		try (BufferedWriter bw = new BufferedWriter(new FileWriter(getCredencialesFile(), false))) {
			for (String[] datos : empleados) {
				bw.write(datos[0] + SEPARADOR + datos[1] + SEPARADOR + datos[2] + SEPARADOR + datos[3]);
				bw.newLine();
			}
			return true;
		} catch (IOException e) {
			System.err.println("ERROR: no se ha podido guardar el fichero de credenciales.");
			return false;
		}
	}

	/**
	 * Añade una nueva línea al final del fichero de credenciales con los datos del
	 * empleado proporcionado.
	 *
	 * @param nombreUsuario nombre de usuario del empleado.
	 * @param contrasenia   contraseña del empleado.
	 * @param empleado      objeto {@link Empleado} con el nombre, apellidos y tipo.
	 * @return {@code true} si la escritura ha sido correcta.
	 */
	private static boolean escribirEmpleado(String nombreUsuario, String contrasenia, Empleado empleado) {
		Perfiles perfil = obtenerPerfilEmpleado(empleado);
		if (perfil == null) {
			return false;
		}
		String nombreCompleto = empleado.getNombre() + " " + empleado.getApellidos();
		try (BufferedWriter bw = new BufferedWriter(new FileWriter(getCredencialesFile(), true))) {
			bw.write(nombreUsuario + SEPARADOR + contrasenia + SEPARADOR + nombreCompleto + SEPARADOR + perfil.getNombre());
			bw.newLine();
			return true;
		} catch (IOException e) {
			System.err.println("ERROR: no se ha podido guardar el empleado.");
			return false;
		}
	}

	/**
	 * Determina el perfil de empleado a partir de la clase del objeto.
	 *
	 * @param empleado objeto {@link Empleado}.
	 * @return {@link Perfiles} correspondiente, o {@code null} si el tipo es desconocido.
	 */
	private static Perfiles obtenerPerfilEmpleado(Empleado empleado) {
		if (empleado instanceof Secretario) {
			return Perfiles.SECRETARIA;
		} else if (empleado instanceof Enfermero) {
			return Perfiles.ENFERMERIA;
		} else if (empleado instanceof Cirujano) {
			return Perfiles.CIRUJANO;
		}
		System.err.println("ERROR: tipo de empleado desconocido.");
		return null;
	}

	/**
	 * Muestra los datos de un empleado por pantalla.
	 *
	 * @param datos array {@code String[4]}: [usuario, contraseña, nombre, tipo].
	 */
	private static void mostrarEmpleado(String[] datos) {
		System.out.println("  Usuario   : " + datos[0]);
		System.out.println("  Nombre    : " + datos[2]);
		System.out.println("  Perfil    : " + datos[3]);
	}

	/**
	 * Pide los datos del nuevo empleado, muestra un resumen y confirma el alta.
	 *
	 * @return {@code true} si el empleado se ha registrado correctamente.
	 */
	public static boolean anadirEmpleado() {
		Long id = pedirId();
		if (id == null) {
			return false;
		}
		String nombre      = Utilidades.pedirTexto("Introduzca el nombre del empleado: ", Main.SC);
		String apellidos   = Utilidades.pedirTexto("Introduzca los apellidos del empleado: ", Main.SC);
		String telefono    = Utilidades.pedirTexto("Introduzca el teléfono del empleado: ", Main.SC);
		String email       = Utilidades.pedirTexto("Introduzca el email del empleado: ", Main.SC);
		String dni         = Utilidades.pedirTexto("Introduzca el DNI/NIE del empleado: ", Main.SC);
		String direccion   = Utilidades.pedirTexto("Introduzca la dirección del empleado: ", Main.SC);
		String nombreUsuario = pedirNombreUsuarioNuevo();
		if (nombreUsuario == null) {
			return false;
		}
		String contrasenia = Utilidades.pedirTexto("Introduzca la contraseña del empleado: ", Main.SC);

		Empleado empl = elegirTipoEmpleado(id, nombre, apellidos, telefono, email, dni, direccion);
		if (empl == null) {
			return false;
		}

		Perfiles perfil = obtenerPerfilEmpleado(empl);
		System.out.println("=== DATOS DEL NUEVO EMPLEADO ===");
		System.out.println();
		System.out.println("Usuario: " + nombreUsuario);
		empl.mostrar();
		System.out.println();
		System.out.println("¿Confirmar el alta del empleado? [Y/n]");
		if (!Utilidades.confirmar(Main.SC)) {
			System.out.println("Operación cancelada.");
			return false;
		}

		if (escribirEmpleado(nombreUsuario, contrasenia, empl)) {
			System.out.println("Empleado registrado correctamente.");
			return true;
		}
		return false;
	}

	/**
	 * Pide un id de empleado válido (número positivo).
	 *
	 * @return el id introducido, o {@code null} si no es válido.
	 */
	private static Long pedirId() {
		System.out.println("Introduzca el id del empleado: ");
		Long id = Utilidades.leerLong(Main.SC);
		if (id == null || id <= 0) {
			System.err.println("ERROR: el id debe ser un número positivo.");
			return null;
		}
		return id;
	}

	/**
	 * Pide un nombre de usuario que no exista ya en el fichero de credenciales.
	 *
	 * @return el nombre de usuario introducido, o {@code null} si la entrada es
	 *         inválida.
	 */
	private static String pedirNombreUsuarioNuevo() {
		String nombreUsuario = Utilidades.pedirTexto("Introduzca el nombre de usuario del empleado: ", Main.SC);
		if (buscarEmpleado(nombreUsuario) != null) {
			System.err.println("ERROR: ya existe un empleado con el nombre de usuario \"" + nombreUsuario + "\".");
			return null;
		}
		return nombreUsuario;
	}

	/**
	 * Pide el nombre de usuario del empleado a modificar, carga sus datos, permite
	 * elegir qué campo cambiar y guarda los cambios en el fichero.
	 *
	 * @return {@code true} si el empleado se ha modificado correctamente.
	 */
	public static boolean modificarEmpleado() {
		System.out.println("¿Desea ver todos los empleados registrados? [Y/n]");
		if (Utilidades.confirmar(Main.SC)) {
			listarEmpleados();
		}
		System.out.println("Introduzca el nombre de usuario del empleado a modificar: ");
		String nombreUsuario = Utilidades.leerLinea(Main.SC);
		List<String[]> todos = leerTodosEmpleados();
		String[] objetivo = encontrarEnLista(todos, nombreUsuario);
		if (objetivo == null) {
			System.err.println("ERROR: no existe ningún empleado con el nombre de usuario \"" + nombreUsuario + "\".");
			return false;
		}

		System.out.println("=== DATOS ACTUALES ===");
		System.out.println();
		mostrarEmpleado(objetivo);
		System.out.println();

		if (!elegirModificar(objetivo)) {
			return false;
		}

		System.out.println("=== EMPLEADO MODIFICADO ===");
		System.out.println();
		mostrarEmpleado(objetivo);
		System.out.println();
		System.out.println("¿Confirmar los cambios? [Y/n]");
		if (!Utilidades.confirmar(Main.SC)) {
			System.out.println("Operación cancelada. No se ha modificado nada.");
			return false;
		}

		if (sobreescribirFichero(todos)) {
			System.out.println("Empleado modificado correctamente.");
			return true;
		}
		return false;
	}

	/**
	 * Localiza en la lista el array cuyo primer elemento (nombre de usuario)
	 * coincide con {@code nombreUsuario}.
	 *
	 * @param lista         lista de arrays con los datos de todos los empleados.
	 * @param nombreUsuario nombre de usuario a buscar.
	 * @return la referencia al array encontrado (que forma parte de la lista), o
	 *         {@code null} si no existe.
	 */
	private static String[] encontrarEnLista(List<String[]> lista, String nombreUsuario) {
		for (String[] datos : lista) {
			if (datos[0].equals(nombreUsuario)) {
				return datos;
			}
		}
		return null;
	}

	/**
	 * Muestra el submenú de modificación y aplica el cambio elegido sobre el array
	 * de datos del empleado (modificación en memoria).
	 *
	 * @param datos array {@code String[4]} del empleado a modificar.
	 * @return {@code true} si se ha elegido algún campo y se ha modificado;
	 *         {@code false} si el usuario cancela.
	 */
	private static boolean elegirModificar(String[] datos) {
		System.out.println("Introduzca lo que quiere modificar: ");
		boolean modificado = false;
		while (!modificado) {
			menuModificar();
			switch (Utilidades.leerInt(Main.SC)) {
			case 1:
				datos[0] = Utilidades.pedirTexto("Introduce el nuevo nombre de usuario: ", Main.SC);
				modificado = true;
				break;
			case 2:
				datos[1] = Utilidades.pedirTexto("Introduce la nueva contraseña: ", Main.SC);
				modificado = true;
				break;
			case 3:
				datos[2] = Utilidades.pedirTexto("Introduce el nuevo nombre completo del empleado: ", Main.SC);
				modificado = true;
				break;
			case 4:
				modificado = elegirTipoEmpleadoTexto(datos);
				break;
			case 5:
				datos[0] = Utilidades.pedirTexto("Introduce el nuevo nombre de usuario: ", Main.SC);
				datos[1] = Utilidades.pedirTexto("Introduce la nueva contraseña: ", Main.SC);
				datos[2] = Utilidades.pedirTexto("Introduce el nuevo nombre completo del empleado: ", Main.SC);
				elegirTipoEmpleadoTexto(datos);
				modificado = true;
				break;
			case 0:
				System.out.println("Operación cancelada. No se ha modificado nada.");
				return false;
			default:
				System.err.println("ERROR: Introduzca un valor listado en el menú.");
				break;
			}
		}
		return true;
	}

	/**
	 * Pide el nuevo perfil de empleado y lo actualiza en el array de datos.
	 *
	 * @param datos array {@code String[4]} del empleado a modificar.
	 * @return {@code true} cuando se elige un perfil válido.
	 */
	private static boolean elegirTipoEmpleadoTexto(String[] datos) {
		System.out.println("Introduce el nuevo tipo de empleado: ");
		boolean elegido = false;
		while (!elegido) {
			menuTipoEmpleado();
			int opc = Utilidades.leerInt(Main.SC);
			switch (opc) {
			case 1:
				datos[3] = Perfiles.SECRETARIA.getNombre();
				elegido = true;
				break;
			case 2:
				datos[3] = Perfiles.ENFERMERIA.getNombre();
				elegido = true;
				break;
			case 3:
				datos[3] = Perfiles.CIRUJANO.getNombre();
				elegido = true;
				break;
			default:
				System.err.println("ERROR: Introduce un valor numérico válido.");
				break;
			}
		}
		return true;
	}

	/**
	 * Pide el nombre de usuario del empleado a eliminar, muestra sus datos,
	 * solicita confirmación y elimina la entrada del fichero.
	 *
	 * @return {@code true} si el empleado se ha eliminado correctamente.
	 */
	public static boolean eliminarEmpleado() {
		System.out.println("¿Desea ver todos los empleados registrados? [Y/n]");
		if (Utilidades.confirmar(Main.SC)) {
			listarEmpleados();
		}
		System.out.println("Introduzca el nombre de usuario del empleado a eliminar: ");
		String nombreUsuario = Utilidades.leerLinea(Main.SC);
		List<String[]> todos = leerTodosEmpleados();
		String[] objetivo = encontrarEnLista(todos, nombreUsuario);
		if (objetivo == null) {
			System.err.println("ERROR: no existe ningún empleado con el nombre de usuario \"" + nombreUsuario + "\".");
			return false;
		}

		System.out.println("=== EMPLEADO A ELIMINAR ===");
		System.out.println();
		mostrarEmpleado(objetivo);
		System.out.println();
		System.out.println("¿Confirmar la eliminación del empleado? Esta acción no se puede deshacer. [s/N]");
		if (!Utilidades.confirmar(Main.SC, false)) {
			System.out.println("Operación cancelada. No se ha eliminado nada.");
			return false;
		}

		todos.remove(objetivo);
		if (sobreescribirFichero(todos)) {
			System.out.println("Empleado eliminado correctamente.");
			return true;
		}
		return false;
	}

	/**
	 * Lee todos los empleados del fichero y los muestra por pantalla.
	 *
	 * @return {@code true} si hay al menos un empleado; {@code false} si el fichero
	 *         está vacío o no existe.
	 */
	public static boolean listarEmpleados() {
		List<String[]> todos = leerTodosEmpleados();
		if (todos.isEmpty()) {
			System.out.println("No hay empleados registrados.");
			return false;
		}
		System.out.println("=== LISTADO DE EMPLEADOS ===");
		System.out.println();
		for (String[] datos : todos) {
			mostrarEmpleado(datos);
			System.out.println();
		}
		return true;
	}

	/**
	 * Muestra el menú de tipo de empleado y crea el objeto {@link Empleado}
	 * correspondiente con todos los datos proporcionados.
	 *
	 * @param id        id del empleado.
	 * @param nombre    nombre del empleado.
	 * @param apellidos apellidos del empleado.
	 * @param telefono  teléfono del empleado.
	 * @param email     email del empleado.
	 * @param dni       DNI/NIE del empleado.
	 * @param direccion dirección del empleado.
	 * @return el objeto {@link Empleado} creado, o {@code null} si no se elige
	 *         ningún tipo válido.
	 */
	private static Empleado elegirTipoEmpleado(Long id, String nombre, String apellidos,
			String telefono, String email, String dni, String direccion) {
		System.out.println("Elija el tipo de empleado: ");
		boolean elegido = false;
		Empleado empl = null;
		while (!elegido) {
			menuTipoEmpleado();
			int opc = Utilidades.leerInt(Main.SC);
			switch (opc) {
			case 1:
				System.out.println("Introduzca años de experiencia: ");
				int anios = Utilidades.leerInt(Main.SC);
				empl = new Secretario(anios);
				System.out.println("Has elegido secretariado.");
				elegido = true;
				break;
			case 2:
				CategoriaEnfermero catEnf = elegirCategoriaEnfermero();
				empl = new Enfermero(catEnf);
				System.out.println("Has elegido enfermería.");
				elegido = true;
				break;
			case 3:
				Set<Especialidades> especialidades = pedirEspecialidadesCirujano();
				empl = new Cirujano(especialidades);
				System.out.println("Has elegido cirujano.");
				elegido = true;
				break;
			default:
				System.err.println("ERROR: Introduce un valor numérico válido.");
				break;
			}
		}
		if (empl != null) {
			empl.setIdEmpleado(id);
			empl.setNombre(nombre);
			empl.setApellidos(apellidos);
			empl.setTelefono(telefono);
			empl.setEmail(email);
			empl.setDnifNie(dni);
			empl.setDireccion(direccion);
		}
		return empl;
	}

	/**
	 * Muestra el menú de todas las especialidades disponibles y la opción de terminar.
	 */
	private static void menuEspecialidades() {
		System.out.println("=== ESPECIALIDADES DISPONIBLES ===");
		for (Especialidades esp : Especialidades.values()) {
			System.out.println(esp.getId() + ".- " + esp.getNombre());
		}
		System.out.println("0.- Terminar selección");
	}

	/**
	 * Pide al usuario que seleccione las especialidades del cirujano una a una
	 * hasta que decida terminar introduciendo 0.
	 *
	 * @return conjunto de {@link Especialidades} seleccionadas.
	 */
	private static Set<Especialidades> pedirEspecialidadesCirujano() {
		Set<Especialidades> especialidades = new HashSet<>();
		System.out.println("Seleccione las especialidades del cirujano:");
		boolean terminado = false;
		while (!terminado) {
			menuEspecialidades();
			System.out.println("Introduzca el número de la especialidad (0 para finalizar): ");
			int opc = Utilidades.leerInt(Main.SC);
			if (opc == 0) {
				terminado = true;
			} else {
				Especialidades seleccionada = resolverEspecialidadPorId(opc);
				if (seleccionada != null) {
					if (especialidades.add(seleccionada)) {
						System.out.println("Añadida: " + seleccionada.getNombre());
					} else {
						System.out.println("La especialidad ya estaba añadida.");
					}
				} else {
					System.err.println("ERROR: Seleccione una opción válida.");
				}
			}
		}
		return especialidades;
	}

	/**
	 * Resuelve la especialidad a partir de su identificador numérico.
	 *
	 * @param id identificador numérico de la especialidad.
	 * @return {@link Especialidades} correspondiente o {@code null} si no existe.
	 */
	private static Especialidades resolverEspecialidadPorId(int id) {
		for (Especialidades esp : Especialidades.values()) {
			if (esp.getId() == id) {
				return esp;
			}
		}
		return null;
	}

	/**
	 * Muestra el submenú de categoría de enfermero y devuelve la elegida.
	 *
	 * @return {@link CategoriaEnfermero} seleccionada.
	 */
	private static CategoriaEnfermero elegirCategoriaEnfermero() {
		System.out.println("Elige la categoría del enfermero: ");
		CategoriaEnfermero catEnf = null;
		while (catEnf == null) {
			menuCategoriaEnfermeria();
			switch (Utilidades.leerInt(Main.SC)) {
			case 1:
				catEnf = CategoriaEnfermero.A;
				break;
			case 2:
				catEnf = CategoriaEnfermero.B;
				break;
			case 3:
				catEnf = CategoriaEnfermero.C;
				break;
			default:
				System.err.println("ERROR: Introduce un valor numérico válido.");
				break;
			}
		}
		return catEnf;
	}
}
