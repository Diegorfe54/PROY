package main;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectOutputStream;

import entidades.Alergia;
import entidades.Empleado;
import entidades.empleados.CategoriaEnfermero;
import entidades.empleados.Secretario;
import entidades.empleados.Enfermero;
import entidades.empleados.Cirujano;

public class GestorEmpleados {
	
	public static void menuModificar() {
		System.out.println("1.-Modificar nombre de usuario.");
		System.out.println("2.-Modificar contraseña.");
		System.out.println("3.-Modificar nombre de empleado (Nombre y apellidos).");
		System.out.println("4.-Modificar tipo de empleado.");
		System.out.println("5.-Modificar todo.");
		System.out.println("6.- CANCELAR");
	}
	
	public static boolean modificarTipoEmpleado(String[] datos) {
		System.out.println("Introduce el nuevo tipo de empleado: ");
		menuTipoEmpleado();
		int option = -1;
		do {
			option = Utilidades.leerInt(Main.SC);
			switch(option) {
				case 1:
					datos[3] = "secretariado";
					option = 0;
					break;
				case 2:
					datos[3] = "enfermeria";
					option = 0;
					break;
				case 3:
					datos[3] = "cirujano";
					option = 0;
					break;
				default:
					System.err.println("ERROR: Introduce un valor numérico válido.");
					break;
			}
		} while(option!=0);
		return true;
	}
	
	public static boolean elegirModificar(String[] datos) {
		System.out.println("Introduza lo que quiere modificar: ");
		menuModificar();
		boolean elegido = false;
		while(!elegido) {
			switch(Utilidades.leerInt(Main.SC)) {
				case 1:
					System.out.println("Introduce el nuevo nombre de usuario: ");
					datos[0] = Utilidades.leerLinea(Main.SC);
					elegido = true;
					break;
				case 2:
					System.out.println("Introduce la nueva contraseña: ");
					datos[1] = Utilidades.leerLinea(Main.SC);
					elegido = true;
					break;
				case 3:
					System.out.println("Introduce el nuevo nombre completo del empleado: ");
					datos[2] = Utilidades.leerLinea(Main.SC);
					elegido = true;
					break;
				case 4:
					elegido = modificarTipoEmpleado(datos);
					break;
				case 5:
					System.out.println("Introduce el nuevo nombre de usuario: ");
					datos[0] = Utilidades.leerLinea(Main.SC);
					System.out.println("Introduce la nueva contraseña: ");
					datos[1] = Utilidades.leerLinea(Main.SC);
					System.out.println("Introduce el nuevo nombre completo del empleado: ");
					datos[2] = Utilidades.leerLinea(Main.SC);
					modificarTipoEmpleado(datos);
					elegido = true;
				case 0:
	    			System.out.println("Operación cancelada. No se ha modificado nada.");
	    			return false;
			}
		}
		escribirEmpleadoModificar(datos[0],datos[1],datos[2],datos[3]);
		return true;
	}
	
	public static void menuTipoEmpleado() {
		System.out.println("1.- Secretariado");
		System.out.println("2.- Enfermeria");
		System.out.println("3.- Cirujano");
	}
	
	public static void menuCategoriaEnfemeria() {
		System.out.println("1.- Categoria A");
		System.out.println("2.- Categoria B");
		System.out.println("3.- Categoria C");
	}
	
	public static boolean escribirEmpleadoModificar(String nombreUsuario, String contrasenia, String nombreCompleto, String tipoEmpleado) {
	    if(!tipoEmpleado.equals("secretariado")||!tipoEmpleado.equals("enfermeria")
	    		||!tipoEmpleado.equals("cirujano")) {
	        System.err.println("ERROR: tipo de empleado desconocido.");
	        return false;
	    }
	    try (BufferedWriter bw = new BufferedWriter(
	            new FileWriter("ficheros/credenciales.txt"))) {
	        bw.write(nombreUsuario + "|" 
	                + contrasenia + "|" 
	                + nombreCompleto + "|"
	                + tipoEmpleado);
	        bw.newLine();
	        System.out.println("Empleado modificado correctamente.");
	        return true;
	    } catch (IOException e) {
	        System.err.println("ERROR: no se ha podido guardar el empleado.");
	        return false;
	    }
	}
	
	public static boolean escribirEmpleado(Empleado empleado, String nombreUsuario, String contrasenia) {
	    String tipoEmpleado;
	    if (empleado instanceof Secretario) {
	        tipoEmpleado = "secretariado";
	    } else if (empleado instanceof Enfermero) {
	        tipoEmpleado = "enfermeria";
	    } else if (empleado instanceof Cirujano) {
	        tipoEmpleado = "cirujano";
	    } else {
	        System.err.println("ERROR: tipo de empleado desconocido.");
	        return false;
	    }
	    try (BufferedWriter bw = new BufferedWriter(
	            new FileWriter("ficheros/credenciales.txt"))) {
	        bw.write(nombreUsuario + "|" 
	                + contrasenia + "|" 
	                + empleado.getNombre() + " "
	                + empleado.getApellidos()
	                + tipoEmpleado);
	        bw.newLine();
	        System.out.println("Empleado registrado correctamente.");
	        return true;
	    } catch (IOException e) {
	        System.err.println("ERROR: no se ha podido guardar el empleado.");
	        return false;
	    }
	}
	
	public static Empleado elegirTipoEmpleado(Long id, String nombre, String apellidos, String telefono, String email, String dni, String direccion ) {
		System.out.println("Elija el tipo de empleado: ");
		String tipoEmpleado = "";
		int option = -1;
		boolean elegido = false;
		Empleado empl = null;
		while (!elegido) {
			menuTipoEmpleado();
			switch (Utilidades.leerInt(Main.SC)) {
			case 1:
				tipoEmpleado = "secretariado";
				System.out.println("Introduzca años de experiencia: ");
				int anios = Utilidades.leerInt(Main.SC);
				empl = new Secretario(anios);
				empl.setIdEmpleado(id);
				empl.setApellidos(apellidos);
				empl.setTelefono(telefono);
				empl.setEmail(email);
				empl.setDnifNie(dni);
				empl.setDireccion(direccion);
				System.out.println("Has elegido secretaria.");
				elegido = true;
				break;
			case 2:
				tipoEmpleado = "enfermeria";
				System.out.println("Elige el tipo de enfermero: ");
				int option1 = -1;
				CategoriaEnfermero catEnf = null;
				do {
					menuCategoriaEnfemeria();
					option1 = Utilidades.leerInt(Main.SC);
					switch (option1) {
						case 1:
							catEnf = CategoriaEnfermero.A;
							option = 0;
							break;
						case 2:
							catEnf = CategoriaEnfermero.B;
							option = 0;
							break;
						case 3:
							catEnf = CategoriaEnfermero.C;
							option = 0;
							break;
						default:
							System.err.println("ERROR: Introduce un valor numérico válido.");
							break;
					}
				} while (option1!=0);
				System.out.println("Has elegido enfermeria");
				empl = new Enfermero(catEnf);
				empl.setIdEmpleado(id);
				empl.setApellidos(apellidos);
				empl.setTelefono(telefono);
				empl.setEmail(email);
				empl.setDnifNie(dni);
				empl.setDireccion(direccion);
				elegido = true;
				break;
			case 3:
				tipoEmpleado = "cirujano";
				empl = new Cirujano();
				empl.setIdEmpleado(id);
				empl.setApellidos(apellidos);
				empl.setTelefono(telefono);
				empl.setEmail(email);
				empl.setDnifNie(dni);
				empl.setDireccion(direccion);
				System.out.println("Has elegido cirujano");
				elegido = true;
				break;
			default:
				System.err.println("ERROR: Introduce un valor numérico válido.");
				elegido = false;
				break;
			}
		}
		return empl;
	}
	
	public static boolean anadirEmpleado() {
		System.out.println("Introduzca el id del empleado: ");
		Long id = Utilidades.leerLong(Main.SC);
		System.out.println("Introduza el nombre del empleado: ");
		String nombre = Utilidades.leerLinea(Main.SC);
		System.out.println("Introduza los apellidos del empleado: ");
		String apellidos = Utilidades.leerLinea(Main.SC);
		System.out.println("Introduza el telefono del empleado: ");
		String telefono = Utilidades.leerLinea(Main.SC);
		System.out.println("Introduza el email del empleado: ");
		String email = Utilidades.leerLinea(Main.SC);
		System.out.println("Introduza el DNI/NIF/NIE del empleado: ");
		String dni = Utilidades.leerLinea(Main.SC);
		System.out.println("Introduza la dirección del empleado: ");
		String direccion = Utilidades.leerLinea(Main.SC);
		System.out.println("Introduzca nombre de usuario del empleado: ");
		String nombreUsuario = Utilidades.leerLinea(Main.SC);
		System.out.println("Introduzca la contraseña del empleado: ");
		String password = Utilidades.leerLinea(Main.SC);
		Empleado empl = elegirTipoEmpleado(id,nombre,apellidos,telefono,email,dni,direccion);
		escribirEmpleado(empl,nombreUsuario,password);
		return true;
	}
	
	public static boolean modificarEmpleado() {
		System.out.println("Introduzca el nombre de usuario del empleado a modificar: ");
		String nombreUsuario = Utilidades.leerLinea(Main.SC);
	    try (BufferedReader br = new BufferedReader(
	            new FileReader("ficheros/credenciales.txt"))) {
	    	String[] datos = new String[4];
	    	String line = br.readLine();
	    	while(line!=null) {
	    		datos = br.readLine().split("|");
	    		String nmUs = datos[0];
	    		if (nmUs.equals(nombreUsuario)) {
	    			elegirModificar(datos);
	    			System.out.println("Empleado registrado correctamente.");
	    			return true;
	    		}
	        
	        return true;
	    } 
	    }catch (IOException e) {
	        System.err.println("ERROR: no se ha podido guardar el empleado.");
	        return false;
	    }
		return true;
	}
	
	public static boolean eliminarEmpleado() {
		return true;
	}
	
	public static boolean listarEmpleados() {
		return true;
	}
}
