package entidades.empleados;

public class Enfermero {
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
		return "Enfermero [categoria=" + categoria + ", getCategoria()=" + getCategoria() + ", getClass()=" + getClass()
				+ ", hashCode()=" + hashCode() + ", toString()=" + super.toString() + "]";
	}
	
	
}
