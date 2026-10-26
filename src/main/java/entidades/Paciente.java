package entidades;

import java.time.LocalDate;
import java.util.Objects;
import java.util.Set;

/**
 * Representa a un paciente dentro del sistema.
 *
 * @author Diego Rodríguez Fernández
 */

public class Paciente {
	
	private Long idPaciente;
	private String nombre;
	private LocalDate fechaNacimiento;
	private String telefono;
	private String email;
	private String direccion;
	private String nifNie;
	
	private Set<Tratamiento> tratamientos;
	private Historial historial;
	
	public Paciente() {
		
	}
	
	public Paciente(Long idPaciente, String nombre, LocalDate fechaNacimiento, String telefono, String email,
			String direccion, String nifNie) {
		super();
		this.idPaciente = idPaciente;
		this.nombre = nombre;
		this.fechaNacimiento = fechaNacimiento;
		this.telefono = telefono;
		this.email = email;
		this.direccion = direccion;
		this.nifNie = nifNie;
	}

	public Long getIdPaciente() {
		return idPaciente;
	}

	public void setIdPaciente(Long idPaciente) {
		this.idPaciente = idPaciente;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public LocalDate getFechaNacimiento() {
		return fechaNacimiento;
	}

	public void setFechaNacimiento(LocalDate fechaNacimiento) {
		this.fechaNacimiento = fechaNacimiento;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public String getNifNie() {
		return nifNie;
	}

	public void setNifNie(String nifNie) {
		this.nifNie = nifNie;
	}
	
	public Historial getHistorial() {
		return historial;
	}

	public void setHistorial(Historial historial) {
		this.historial = historial;
	}

	public Set<Tratamiento> getTratamientos() {
		return tratamientos;
	}

	public void setTratamientos(Set<Tratamiento> tratamientos) {
		this.tratamientos = tratamientos;
	}

	@Override
	public String toString() {
		return "Paciente [idPaciente=" + idPaciente + ", nombre=" + nombre + ", fechaNacimiento=" + fechaNacimiento
				+ ", telefono=" + telefono + ", email=" + email + ", direccion=" + direccion + ", nifNie=" + nifNie
				+ ", tratamientos=" + tratamientos + "]";
	}

	
}
