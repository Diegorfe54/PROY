package main;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Pattern;

import entidades.Alergia;
import entidades.Medicamento;

/**
 * Gestiona las alergias y su medicamento asociado almacenados en ficheros
 * binarios: un fichero {@code alergia_<id>.dat} por cada alergia.
 * 
 * @author Diego Rodríguez Fernández
 */
public class GestorAlergias {
	private static final File CARPETA = new File("src/main/resources/alergias");
	private static final Pattern PATRON_FICHERO = Pattern.compile("alergia_(\\d+)\\.dat");
	private static File[] ficheros = CARPETA.listFiles((dir, nombre) -> nombre.endsWith(".dat"));

	/**
	 * Identifica un fichero de alergia mediante su id.
	 * 
	 * @param id Long del fichero que se busca.
	 * @return fichero cuyo nombre contiene el id.
	 */
	private static File ficheroDe(long id) {
		return new File(CARPETA, "alergia_" + id + ".dat");
	}

	/**
	 * Lista los ficheros .dat de la carpeta.
	 * 
	 * @return lista de ficheros .dat en la carpeta
	 */
	private static File[] listarFicheros() {
		File[] ficheros = CARPETA.listFiles((dir, nombre) -> PATRON_FICHERO.matcher(nombre).matches());
		return (ficheros == null) ? new File[0] : ficheros;
	}

	/**
	 * Lee un fichero
	 * 
	 * @param f File es el fichero que se quiere leer.
	 * @return contenido Alergia.
	 */
	private static Alergia leerFichero(File f) {
		try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(f))) {
			return (Alergia) ois.readObject();
		} catch (IOException | ClassNotFoundException e) {
			System.err.println("ERROR: no se ha podido leer " + f.getName());
			return null;
		}
	}

	/**
	 * Lee todas las alergias.
	 * 
	 * @return lista de todas las alergias.
	 */
	public static List<Alergia> leerTodasAlergias() {
		List<Alergia> alergias = new ArrayList<>();
		for (File f : listarFicheros()) {
			Alergia a = leerFichero(f);
			if (a != null) {
				alergias.add(a);
			}
		}
		alergias.sort(Comparator.comparing(Alergia::getIdAlergia));
		return alergias;
	}

	/**
	 * Muestra por pantalla todas las alergias en un repositorio.
	 */
	public static void verAlergiasDisponibles() {
		List<Alergia> lista = leerTodasAlergias();
		if (lista.isEmpty()) {
			System.out.println("No hay alergias registradas.");
			return;
		}
		for (Alergia a : lista) {
			System.out.println(a.getIdAlergia() + " - " + a.getNombre());
		}
	}

	/**
	 * Registra una alergia en un fichero.
	 * 
	 * @param alergia Alergia a registrar
	 * @return boolean
	 */
	public static boolean escribirAlergia(Alergia alergia) {
		File f = ficheroDe(alergia.getIdAlergia());
		try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(f))) {
			oos.writeObject(alergia);
			System.out.println("Alergia registrada con id " + alergia.getIdAlergia() + ".");
			return true;
		} catch (IOException e) {
			System.err.println("ERROR: no se ha podido guardar la alergia.");
			return false;
		}
	}

	/**
	 * Guarda una alergia en un nuevo fichero.
	 * 
	 * @return boolean escribirAlergia
	 * @see #escribirAlergia(Alergia)
	 */
	public static boolean anadirAlergia() {
		System.out.println("Introduzca id de la alergia/medicamento: ");
		Long id = Utilidades.leerLong(Main.SC);
		if (ficheroDe(id).exists()) {
			System.err.println("ERROR: ya existe un fichero para el id " + id + ".");
			return false;
		}
		System.out.println("Introduzca nombre de la alergia: ");
		String nombreAlergia = Utilidades.leerLinea(Main.SC);
		System.out.println("Introduzca nombre del medicamento: ");
		String nombreMed = Utilidades.leerLinea(Main.SC);
		System.out.println("Introduzca el principio activo del medicamento: ");
		String principio = Utilidades.leerLinea(Main.SC);
		System.out.println("Introduzca la dosis máxima diaria del medicamento en miligramos: ");
		int dosis = Utilidades.leerInt(Main.SC);

		Alergia alergia = new Alergia(id, nombreAlergia);
		Medicamento med = new Medicamento(id, nombreMed, principio, dosis);
		alergia.setMedicamento(med);
		med.setAlergia(alergia);

		System.out.println("Se va a registrar:");
		System.out.println("");
		alergia.mostrar();
		System.out.println("");
		System.out.println("¿Confirmar el alta de la alergia? [Y/n]");
		String respuesta = Utilidades.leerLinea(Main.SC);
		if (respuesta.isEmpty() || respuesta.equalsIgnoreCase("n")) {
			System.out.println("Operación cancelada.");
			return false;
		}
		return escribirAlergia(alergia);
	}

	/**
	 * Edita una alergía de un fichero.
	 * 
	 * @return boolean que valida si se ha completado el proceso.
	 */
	public static boolean editarAlergia() {
		System.out.println("¿Desea ver todas las alergias registradas? [Y/n]");
		if (Utilidades.confirmar(Main.SC)) verAlergiasDisponibles();
		System.out.println("Introduzca id de la alergia a modificar: ");
		Long id = Utilidades.leerLong(Main.SC);
		if (id == null || id <= 0) {
			System.err.println("ERROR: El id debe ser un número positivo.");
			return false;
		}
		File f = ficheroDe(id);
		if (!f.isFile()) {
			System.err.println("ERROR; No existe ninguna alergia con id " + id + ".");
			return false;
		}
    	System.out.println("=== DATOS ACTUALES ===");
		Alergia alergia = leerFichero(f);
		if (alergia == null || alergia.getMedicamento() == null) {
			System.err.println("ERROR: la alergia no se puede leer o está incompleta.");
			return false;
		}
		System.out.println("");
		alergia.mostrar();
		System.out.println("");
		Medicamento med = alergia.getMedicamento();
    	boolean modificado = false;
    	while(!modificado) {
        	System.out.println("Que desea editar: ");
        	System.out.println("1.- Nombre de la alergia.");
        	System.out.println("2.- Nombre del medicamento.");
        	System.out.println("3.- Principio activo del medicamento.");
        	System.out.println("4.- Dosis máxima diaria del medicamento.");
        	System.out.println("5.- Todos los datos.");
        	System.out.println("0.- CANCELAR");
    		switch(Utilidades.leerInt(Main.SC)) {
    		case 1:
    			alergia.setNombre(Utilidades.pedirTexto("Introduzca el nuevo nombre de la alergia: ",Main.SC));
    			modificado = true;
    			break;
    		case 2:
    			med.setNombre(Utilidades.pedirTexto("Introduzca el nuevo nombre del medicamento: ",Main.SC));
    			modificado = true;
    			break;
    		case 3:
    			med.setPrincipioActivo(Utilidades.pedirTexto("Introduzca el nuevo principio activo del medicamento: ",Main.SC));
    			modificado = true;
    			break;
    		case 4:
    			med.setDosisMaxDiariaMg(Utilidades.pedirIntPositivo("Introduzca la nueva dosis máxima diaria (mg): ",Main.SC));
    			modificado = true;
    			break;
    		case 5:
    			alergia.setNombre(Utilidades.pedirTexto("Introduzca el nuevo nombre de la alergia: ",Main.SC));
    			med.setNombre(Utilidades.pedirTexto("Introduzca el nuevo nombre del medicamento: ",Main.SC));
    			med.setPrincipioActivo(Utilidades.pedirTexto("Introduzca el nuevo principio activo del medicamento: ",Main.SC));
    			med.setDosisMaxDiariaMg(Utilidades.pedirIntPositivo("Introduzca la nueva dosis máxima diaria (mg): ",Main.SC));
    			modificado = true;
    			break;
    		case 0:
    			System.out.println("Operación cancelada. No se ha modificado nada.");
    			return false;
    		default:
    			System.out.println("Introduzca un valor listado en el menú.");
    			break;
    		}
    	}
    	if (!modificado) {
    		System.out.println("No se ha realizado ningún cambio.");
    		return false;
    	}
    	System.out.println("=== ALERGIA MODIFICADA ===");
    	System.out.println("");
    	alergia.mostrar();
    	System.out.println("");
    	System.out.println("¿Confirmas los cambios? [Y/n]");
    	if (!Utilidades.confirmar(Main.SC)) {
    		System.out.println("Operación cancelada. No se ha modificado nada.");
    		return false;
    	}
    	if (escribirAlergia(alergia)) {
    		System.out.println("Alergia actualizada.");
    		return true;
    	}
    	return false;
    }

	/**
	 * Pide al usuario un id, lee el fichero de la alergia con ese id y la muestra
	 * por pantalla junto con los datos de su medicamento.
	 *
	 * @return {@code true} si la alergia se ha encontrado, leído y mostrado;
	 *         {@code false} en cualquier otro caso (id inválido, no existe o
	 *         fichero ilegible).
	 * @see #leerFichero(File)
	 */
	public static boolean verAlergia() {
		System.out.println("¿Desea ver todas las alergias registradas? [Y/n]");
		if (Utilidades.confirmar(Main.SC)) {
			verAlergiasDisponibles();
		}
		System.out.println("Introduzca id de la alergia a buscar: ");
		Long id = Utilidades.leerLong(Main.SC);
		if (id == null || id <= 0) {
			System.err.println("ERROR: El id debe ser un número positivo.");
			return false;
		}
		File f = ficheroDe(id);
		if (!f.isFile()) {
			System.err.println("ERROR: No existe ninguna alergia con id " + id + ".");
			return false;
		}
		Alergia alergia = leerFichero(f);
		if (alergia == null) {
			return false;
		}
		System.out.println("=== DATOS DE LA ALERGIA ===");
		System.out.println("");
		alergia.mostrar();
		System.out.println("");
		return true;
	}
}