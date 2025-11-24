import java.time.LocalDateTime;

/**
 * Clase que representa una reserva de sala de estudio
 */
public class Reserva {
    private Estudiante estudiante;
    private SalaEstudio salaReservada;
    private LocalDateTime fechaHora;
    
    /**
     * Constructor de la clase Reserva
     */
    public Reserva() {
        this.estudiante = null;
        this.salaReservada = null;
        this.fechaHora = null;
    }
    
    /**
     * Obtiene el estudiante de la reserva
     * @return Estudiante que realizó la reserva
     */
    public Estudiante getEstudiante() {
        return estudiante;
    }
    
    /**
     * Establece el estudiante de la reserva
     * @param estudiante Estudiante que realiza la reserva
     */
    public void setEstudiante(Estudiante estudiante) {
        this.estudiante = estudiante;
    }
    
    /**
     * Obtiene la sala reservada
     * @return SalaEstudio reservada
     */
    public SalaEstudio getSalaReservada() {
        return salaReservada;
    }
    
    /**
     * Establece la sala reservada
     * @param salaReservada SalaEstudio a reservar
     */
    public void setSalaReservada(SalaEstudio salaReservada) {
        this.salaReservada = salaReservada;
    }
    
    /**
     * Obtiene la fecha y hora de la reserva
     * @return LocalDateTime con fecha y hora
     */
    public LocalDateTime getFechaHora() {
        return fechaHora;
    }
    
    /**
     * Establece la fecha y hora de la reserva
     * @param fechaHora LocalDateTime con fecha y hora
     */
    public void setFechaHora(LocalDateTime fechaHora) {
        this.fechaHora = fechaHora;
    }
}