package entidades;

import java.time.LocalDate;
import java.util.Objects;
import java.util.Set;

public class Tratamiento {
	
	private Long idTratamiento;
	private String nombre;
	private LocalDate fechaInicio = LocalDate.now();
	private boolean consentimiento = false;
	
	private Long idPaciente; // CARGA LIGERA 
	private Paciente paciente; // CARGA PESADA
	private Set<Cita> citas;
	
	public Tratamiento() {
		
	}
	
	public Tratamiento(Long idTratamiento, String nombre, LocalDate fechaInicio, boolean consentimiento) {
		super();
		this.idTratamiento = idTratamiento;
		this.nombre = nombre;
		this.fechaInicio = fechaInicio;
		this.consentimiento = consentimiento;
	}

	public Long getIdTratamiento() {
		return idTratamiento;
	}

	public void setIdTratamiento(Long idTratamiento) {
		this.idTratamiento = idTratamiento;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public LocalDate getFechaInicio() {
		return fechaInicio;
	}

	public void setFechaInicio(LocalDate fechaInicio) {
		this.fechaInicio = fechaInicio;
	}

	public boolean isConsentimiento() {
		return consentimiento;
	}

	public void setConsentimiento(boolean consentimiento) {
		this.consentimiento = consentimiento;
	}

	public Paciente getPaciente() {
		return paciente;
	}

	public void setPaciente(Paciente paciente) {
		this.paciente = paciente;
	}

	public Set<Cita> getCitas() {
		return citas;
	}

	public void setCitas(Set<Cita> citas) {
		this.citas = citas;
	}

	@Override
	public String toString() {
		return "Tratamiento [idTratamiento=" + idTratamiento + ", nombre=" + nombre + ", fechaInicio=" + fechaInicio
				+ ", consentimiento=" + consentimiento + ", idPaciente=" + idPaciente + ", paciente=" + paciente + "]";
	}
	
}
