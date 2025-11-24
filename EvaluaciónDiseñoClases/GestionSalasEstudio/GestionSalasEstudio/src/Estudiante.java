/**
 * Clase que representa un estudiante en el sistema de gestión de salas de estudio
 */
public class Estudiante {
    private String nombre;
    private String codigoInstitucional;
    private String programaAcademico;
    
    /**
     * Constructor de la clase Estudiante
     */
    public Estudiante() {
        this.nombre = "";
        this.codigoInstitucional = "";
        this.programaAcademico = "";
    }
    
    /**
     * Obtiene el nombre del estudiante
     * @return String nombre del estudiante
     */
    public String getNombre() {
        return nombre;
    }
    
    /**
     * Establece el nombre del estudiante
     * @param nombre String con el nombre del estudiante
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    
    /**
     * Obtiene el código institucional del estudiante
     * @return String código institucional
     */
    public String getCodigoInstitucional() {
        return codigoInstitucional;
    }
    
    /**
     * Establece el código institucional del estudiante
     * @param codigoInstitucional String con el código institucional
     */
    public void setCodigoInstitucional(String codigoInstitucional) {
        this.codigoInstitucional = codigoInstitucional;
    }
    
    /**
     * Obtiene el programa académico del estudiante
     * @return String programa académico
     */
    public String getProgramaAcademico() {
        return programaAcademico;
    }
    
    /**
     * Establece el programa académico del estudiante
     * @param programaAcademico String con el programa académico
     */
    public void setProgramaAcademico(String programaAcademico) {
        this.programaAcademico = programaAcademico;
    }
}