package entidades.citas;

import java.util.Objects;

import entidades.Cita;

/**
 * Representa a una revisión médica en el sistema.
 *
 * @author Diego Rodríguez Fernández
 */
public class Revision extends Cita {
	
	private Long idRevision;
	
	public Revision() {
		
	}
	 
	public Revision(Long idRevision) {
		super();
		this.idRevision = idRevision;
	}

	public Long getIdRevision() {
		return idRevision;
	}
	
	public void setIdRevision(Long idRevision) {
		this.idRevision = idRevision;
	}
	
	@Override
	public String toString() {
		return "Revision [idRevision=" + idRevision + ", idCita=" + idCita + ", fechaHora=" + fechaHora
				+ ", anotaciones=" + anotaciones + "]";
	}
	
}
