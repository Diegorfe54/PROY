package main;

/**
 * Representa los diferentes perfiles de acceso disponibles en el sistema.
 * 
 * @author Diego Rodríguez Fernández
 */
public enum Perfiles {
	/** Perfil de secretaria. */
	SECRETARIA(1, "secretaria"),
	/** Perfil de enfermería. */
	ENFERMERIA(2, "enfermeria"),
	/** Perfil de cirujano. */
	CIRUJANO(3, "cirujano"),
	/** Perfil de administrador. */
	ADMINISTRADOR(4, "administrador");

	private final int id;
	private final String nombre;

	Perfiles(int id, String nombre) {
		this.id = id;
		this.nombre = nombre;
	}

	/**
	 * Obtiene el identificador del perfil.
	 *
	 * @return identificador numérico del perfil
	 */
	public int getId() {
		return id;
	}

	/**
	 * Obtiene el nombre del perfil.
	 *
	 * @return nombre descriptivo del perfil
	 */
	public String getNombre() {
		return nombre;
	}

	/**
	 * Devuelve el perfil correspondiente según el id numérico seleccionado en el menú.
	 *
	 * @param id identificador de la opción del menú
	 * @return Perfiles correspondiente o null si no es válido
	 */
	public static Perfiles desdeId(int id) {
		for (Perfiles p : values()) {
			if (p.id == id) {
				return p;
			}
		}
		return null;
	}
}
