package entidades.citas;

import java.util.Objects;

import entidades.Cita;

/**
 * Representa a una intervención médica en el sistema.
 *
 * @author Diego Rodríguez Fernández
 */
public class Intervencion extends Cita {
	private Long idIntervencion;
	private int duracionMinutos;
	
	public Intervencion() {
		
	}

	public Intervencion(Long idIntervencion, int duracionMinutos) {
		super();
		this.idIntervencion = idIntervencion;
		this.duracionMinutos = duracionMinutos;
	}

	public Long getIdIntervencion() {
		return idIntervencion;
	}
	
	public void setIdIntervencion(Long idIntervencion) {
		this.idIntervencion = idIntervencion;
	}
	
	public int getDuracionMinutos() {
		return duracionMinutos;
	}

	public void setDuracionMinutos(int duracionMinutos) {
		this.duracionMinutos = duracionMinutos;
	}

	@Override
	public String toString() {
		return "Intervencion [idIntervencion=" + idIntervencion + ", duracionMinutos=" + duracionMinutos + ", idCita="
				+ idCita + ", fechaHora=" + fechaHora + ", anotaciones=" + anotaciones + "]";
	}
	
	
}
