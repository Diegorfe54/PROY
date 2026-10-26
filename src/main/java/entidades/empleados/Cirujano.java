package entidades.empleados;

import java.util.Set;

import entidades.Empleado;

/**
 * Representa a un cirujano dentro del sistema.
 *
 * @author Diego Rodríguez Fernández
 */
public class Cirujano extends Empleado {
	Set<Especialidades> especialidades;

	public Cirujano() {
	}
	
	public Cirujano(Set<Especialidades> especialidades) {
		super();
		this.especialidades = especialidades;
	}

	public Set<Especialidades> getEspecialidades() {
		return especialidades;
	}

	public void setEspecialidades(Set<Especialidades> especialidades) {
		this.especialidades = especialidades;
	}

	@Override
	public String toString() {
		return "Cirujano [especialidades=" + especialidades + ", idEmpleado=" + idEmpleado + ", nombre=" + nombre
				+ ", apellidos=" + apellidos + ", telefono=" + telefono + ", email=" + email + ", dnifNie=" + dnifNie
				+ ", direccion=" + direccion + ", getEspecialidades()=" + getEspecialidades() + ", getIdEmpleado()="
				+ getIdEmpleado() + ", getNombre()=" + getNombre() + ", getApellidos()=" + getApellidos()
				+ ", getTelefono()=" + getTelefono() + ", getEmail()=" + getEmail() + ", getDnifNie()=" + getDnifNie()
				+ ", getDireccion()=" + getDireccion() + ", toString()=" + super.toString() + ", getClass()="
				+ getClass() + ", hashCode()=" + hashCode() + "]";
	}

	@Override
	public void mostrar() {
		super.mostrar();
		System.out.print("Especialidades: ");
		if (especialidades == null || especialidades.isEmpty()) {
			System.out.println("Ninguna");
		} else {
			StringBuilder sb = new StringBuilder();
			for (Especialidades esp : especialidades) {
				if (sb.length() > 0) {
					sb.append(", ");
				}
				sb.append(esp.getNombre());
			}
			System.out.println(sb.toString());
		}
	}
}
