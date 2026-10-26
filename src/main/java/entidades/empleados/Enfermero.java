package entidades.empleados;

import entidades.Empleado;

public class Enfermero extends Empleado {
	private CategoriaEnfermero categoria;
	
	public Enfermero() {
	}

	public Enfermero(CategoriaEnfermero categoria) {
		super();
		this.categoria = categoria;
	}

	public CategoriaEnfermero getCategoria() {
		return categoria;
	}

	public void setCategoria(CategoriaEnfermero categoria) {
		this.categoria = categoria;
	}

	@Override
	public String toString() {
		return "Enfermero [categoria=" + categoria + ", idEmpleado=" + idEmpleado + ", nombre=" + nombre
				+ ", apellidos=" + apellidos + ", telefono=" + telefono + ", email=" + email + ", dnifNie=" + dnifNie
				+ ", direccion=" + direccion + "]";
	}
}
