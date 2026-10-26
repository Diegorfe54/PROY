package entidades;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * Representa a una cita médica en la clínica dentro del sistema.
 *
 * @author Diego Rodríguez Fernández
 */
public class Cita {
	
	protected Long idCita;
	protected LocalDateTime fechaHora;
	protected String anotaciones;
	
	protected Tratamiento tratamiento;
	
	public Cita() {
		
	}

	public Cita(Long idCita, LocalDateTime fechaHora, String anotaciones) {
		super();
		this.idCita = idCita;
		this.fechaHora = fechaHora;
		this.anotaciones = anotaciones;
	}

	public Long getIdCita() {
		return idCita;
	}

	public void setIdCita(Long idCita) {
		this.idCita = idCita;
	}

	public LocalDateTime getFechaHora() {
		return fechaHora;
	}

	public void setFechaHora(LocalDateTime fechaHora) {
		this.fechaHora = fechaHora;
	}
	
	public String getAnotaciones() {
		return anotaciones;
	}

	public void setAnotaciones(String anotaciones) {
		this.anotaciones = anotaciones;
	}

	public Tratamiento getTratamiento() {
		return tratamiento;
	}

	public void setTratamiento(Tratamiento tratamiento) {
		this.tratamiento = tratamiento;
	}

	@Override
	public String toString() {
		return "Cita [idCita=" + idCita + ", fechaHora=" + fechaHora + ", anotaciones=" + anotaciones + "]";
	}
	
}
