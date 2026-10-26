package entidades;

import java.util.Objects;
import java.util.Set;

/**
 * Representa a un historial clínico de un paciente dentro del sistema.
 *
 * @author Diego Rodríguez Fernández
 */
public class Historial {
	
	private Long numHistoria;
	private String descripcion;
	
	Set<Alergia> alergias;	
	private Paciente paciente;
	
	public Historial() {
		
	}
	
	public Historial(Long numHistoria, String descripcion) {
		super();
		this.numHistoria = numHistoria;
		this.descripcion = descripcion;
	}

	public Long getNumHistoria() {
		return numHistoria;
	}

	public void setNumHistoria(Long numHistoria) {
		this.numHistoria = numHistoria;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public Paciente getPaciente() {
		return paciente;
	}

	public void setPaciente(Paciente paciente) {
		this.paciente = paciente;
	}

	public Set<Alergia> getAlergias() {
		return alergias;
	}

	public void setAlergias(Set<Alergia> alergias) {
		this.alergias = alergias;
	}

	@Override
	public String toString() {
		return "Historial [numHistoria=" + numHistoria + ", descripcion=" + descripcion + ", alergias=" + alergias
				+ "]";
	}
	
	
}
