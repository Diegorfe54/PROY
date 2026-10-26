package entidades;

import java.io.Serializable;
import java.util.Objects;

/**
 * Representa a una alegia a un medicamento dentro del sistema.
 *
 * @author Diego Rodríguez Fernández
 */
public class Alergia  implements Serializable {
	
	private Long idAlergia;
	private String nombre;
	
	private Medicamento medicamento;
	
	public Alergia() {

	}
	
	public Alergia(Long idAlergia, String nombre) {
		super();
		this.idAlergia = idAlergia;
		this.nombre = nombre;
	}

	public Long getIdAlergia() {
		return idAlergia;
	}

	public void setIdAlergia(Long idAlergia) {
		this.idAlergia = idAlergia;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Medicamento getMedicamento() {
		return medicamento;
	}

	public void setMedicamento(Medicamento medicamento) {
		this.medicamento = medicamento;
	}

	@Override
	public String toString() {
		return "Alergia [idAlergia=" + idAlergia + ", nombre=" + nombre + "]";
	}
	
	public void mostrar() {
		System.out.println("ID: "+idAlergia);
		System.out.println("Nombre: "+nombre);
		System.out.println("ID medicamento: "+medicamento.getIdMedicamento());
		System.out.println("Nombre medicamento: "+medicamento.getNombre());
		System.out.println("Principio activo: "+medicamento.getPrincipioActivo());
		System.out.println("Dosis máxima diaria (mg): "+medicamento.getDosisMaxDiariaMg());
	}
}
