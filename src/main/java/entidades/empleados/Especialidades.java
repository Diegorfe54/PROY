package entidades.empleados;

/**
 * Representa las diferentes especialidades médicas disponibles en el sistema.
 *
 * @author Diego Rodríguez Fernández
 */
public enum Especialidades {
	/** Especialidad de odontología. */
	ODONTOLOGIA(1, "Odontología"),
	/** Especialidad de fisioterapia. */
	FISIOTERAPIA(2, "Fisioterapia"),
	/** Especialidad de ginecología. */
	GINECOLOGIA(3, "Ginecología"),
	/** Especialidad de cirugía plástica. */
	CIRUGIA_PLASTICA(4, "Cirugía plástica"),
	/** Especialidad de digestivo. */
	DIGESTIVO(5, "Digestivo"),
	/** Especialidad de cirugía pediátrica. */
	CIRUGIA_PEDIATRICA(6, "Cirugía pediátrica"),
	/** Especialidad de radiología. */
	RADIOLOGIA(7, "Radiología"),
	/** Especialidad de geriatría. */
	GERIATRIA(8, "Geriatría");
	
	private final int id;
	private final String nombre;
	
	Especialidades(int id, String nombre) {
		this.id = id;
		this.nombre = nombre;
	}
	
    /**
     * Obtiene el identificador de la especialidad.
     *
     * @return identificador numérico de la especialidad
     */
	public int getId() {
		return id;
	}
	
    /**
     * Obtiene el nombre de la especialidad.
     *
     * @return nombre descriptivo de la especialidad
     */
	public String getNombre( ) {
		return nombre;
	}
}
