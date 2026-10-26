package entidades.empleados;

import entidades.Empleado;

/**
 * Representa a un secretario dentro del sistema.
 *
 * @author Diego Rodríguez Fernández
 */
public class Secretario extends Empleado {
	private int aniosExperiencia;

	public Secretario() {
	}

	public Secretario(int aniosExperiencia) {
		super();
		this.aniosExperiencia = aniosExperiencia;
	}

	public int getAniosExperiencia() {
		return aniosExperiencia;
	}

	public void setAniosExperiencia(int aniosExperiencia) {
		this.aniosExperiencia = aniosExperiencia;
	}

	@Override
	public String toString() {
		return "Secretario [aniosExperiencia=" + aniosExperiencia + ", idEmpleado=" + idEmpleado + ", nombre=" + nombre
				+ ", apellidos=" + apellidos + ", telefono=" + telefono + ", email=" + email + ", dnifNie=" + dnifNie
				+ ", direccion=" + direccion + ", getAniosExperiencia()=" + getAniosExperiencia() + ", getIdEmpleado()="
				+ getIdEmpleado() + ", getNombre()=" + getNombre() + ", getApellidos()=" + getApellidos()
				+ ", getTelefono()=" + getTelefono() + ", getEmail()=" + getEmail() + ", getDnifNie()=" + getDnifNie()
				+ ", getDireccion()=" + getDireccion() + ", toString()=" + super.toString() + ", getClass()="
				+ getClass() + ", hashCode()=" + hashCode() + "]";
	}

	@Override
	public void mostrar() {
		super.mostrar();
		System.out.println("Años de experiencia: " + aniosExperiencia);
	}
}
