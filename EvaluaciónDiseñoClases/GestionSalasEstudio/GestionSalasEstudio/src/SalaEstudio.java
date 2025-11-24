/**
 * Clase que representa una sala de estudio
 */
public class SalaEstudio {
    private String numeroSala;
    private int capacidadMax;
    private boolean disponibilidad;
    
    /**
     * Constructor de la clase SalaEstudio
     */
    public SalaEstudio() {
        this.numeroSala = "";
        this.capacidadMax = 0;
        this.disponibilidad = true;
    }
    
    /**
     * Obtiene el número de la sala
     * @return String número de la sala
     */
    public String getNumeroSala() {
        return numeroSala;
    }
    
    /**
     * Establece el número de la sala
     * @param numeroSala String con el número de la sala
     */
    public void setNumeroSala(String numeroSala) {
        this.numeroSala = numeroSala;
    }
    
    /**
     * Obtiene la capacidad máxima de la sala
     * @return int capacidad máxima
     */
    public int getCapacidadMax() {
        return capacidadMax;
    }
    
    /**
     * Establece la capacidad máxima de la sala
     * @param capacidadMax int con la capacidad máxima
     */
    public void setCapacidadMax(int capacidadMax) {
        this.capacidadMax = capacidadMax;
    }
    
    /**
     * Obtiene la disponibilidad de la sala
     * @return boolean disponibilidad de la sala
     */
    public boolean getDisponibilidad() {
        return disponibilidad;
    }
    
    /**
     * Establece la disponibilidad de la sala
     * @param disponibilidad boolean con la disponibilidad
     */
    public void setDisponibilidad(boolean disponibilidad) {
        this.disponibilidad = disponibilidad;
    }
}