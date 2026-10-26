package entidades;

/**
 * Representa a un empleado dentro del sistema.
 *
 * @author Diego Rodríguez Fernández
 */
public class Empleado {
	protected Long idEmpleado;
	protected String nombre;
	protected String apellidos;
	protected String telefono;
	protected String email;
	protected String dnifNie;
	protected String direccion;
	
	public Empleado() {
	}

	public Empleado(Long idEmpleado, String nombre, String apellidos, String telefono, String email, String dnifNie,
			String direccion) {
		super();
		this.idEmpleado = idEmpleado;
		this.nombre = nombre;
		this.apellidos = apellidos;
		this.telefono = telefono;
		this.email = email;
		this.dnifNie = dnifNie;
		this.direccion = direccion;
	}

	public Long getIdEmpleado() {
		return idEmpleado;
	}

	public void setIdEmpleado(Long idEmpleado) {
		this.idEmpleado = idEmpleado;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellidos() {
		return apellidos;
	}

	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
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

	public String getDnifNie() {
		return dnifNie;
	}

	public void setDnifNie(String dnifNie) {
		this.dnifNie = dnifNie;
	}

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	@Override
	public String toString() {
		return "Empleado [idEmpleado=" + idEmpleado + ", nombre=" + nombre + ", apellidos=" + apellidos + ", telefono="
				+ telefono + ", email=" + email + ", dnifNie=" + dnifNie + ", direccion=" + direccion + "]";
	}

	public void mostrar() {
		System.out.println("ID empleado: " + idEmpleado);
		System.out.println("Nombre: " + nombre);
		System.out.println("Apellidos: " + apellidos);
		System.out.println("Teléfono: " + telefono);
		System.out.println("Email: " + email);
		System.out.println("DNI/NIE: " + dnifNie);
		System.out.println("Dirección: " + direccion);
	}
}
