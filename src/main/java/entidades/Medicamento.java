package entidades;

import java.io.Serializable;
import java.util.Objects;

/**
 * Representa a un medicamento dentro del sistema.
 *
 * @author Diego Rodríguez Fernández
 */
public class Medicamento  implements Serializable {
	private Long idMedicamento;
	private String nombre;
	private String principioActivo;
	private int dosisMaxDiariaMg;
	
	private Alergia alergia;
	
	public Medicamento() {
		
	}

	public Medicamento(Long idMedicamento, String nombre, String principioActivo, int dosisMaxDiariaMg) {
		super();
		this.idMedicamento = idMedicamento;
		this.nombre = nombre;
		this.principioActivo = principioActivo;
		this.dosisMaxDiariaMg = dosisMaxDiariaMg;
	}

	public Long getIdMedicamento() {
		return idMedicamento;
	}

	public void setIdMedicamento(Long idMedicamento) {
		this.idMedicamento = idMedicamento;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getPrincipioActivo() {
		return principioActivo;
	}

	public void setPrincipioActivo(String principioActivo) {
		this.principioActivo = principioActivo;
	}

	public double getDosisMaxDiariaMg() {
		return dosisMaxDiariaMg;
	}

	public void setDosisMaxDiariaMg(int dosisMaxDiariaMg) {
		this.dosisMaxDiariaMg = dosisMaxDiariaMg;
	}

	public Alergia getAlergia() {
		return alergia;
	}

	public void setAlergia(Alergia alergia) {
		this.alergia = alergia;
	}

	@Override
	public String toString() {
		return "Medicamento [idMedicamento=" + idMedicamento + ", nombre=" + nombre + ", principioActivo="
				+ principioActivo + ", dosisMaxDiariaMg=" + dosisMaxDiariaMg + "]";
	}
	
	
}
