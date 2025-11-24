import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Clase principal que gestiona el sistema de salas de estudio
 */
public class Universidad {
    private List<Estudiante> estudiantes;
    private List<SalaEstudio> salas;
    private List<Reserva> reservas;
    
    /**
     * Constructor de la clase Universidad
     */
    public Universidad() {
        this.estudiantes = new ArrayList<>();
        this.salas = new ArrayList<>();
        this.reservas = new ArrayList<>();
    }
    
    /**
     * Obtiene la lista de estudiantes
     * @return List<Estudiante> lista de estudiantes
     */
    public List<Estudiante> getEstudiantes() {
        return estudiantes;
    }
    
    /**
     * Establece la lista de estudiantes
     * @param estudiantes List<Estudiante> lista de estudiantes
     */
    public void setEstudiantes(List<Estudiante> estudiantes) {
        this.estudiantes = estudiantes;
    }
    
    /**
     * Obtiene la lista de salas
     * @return List<SalaEstudio> lista de salas
     */
    public List<SalaEstudio> getSalas() {
        return salas;
    }
    
    /**
     * Establece la lista de salas
     * @param salas List<SalaEstudio> lista de salas
     */
    public void setSalas(List<SalaEstudio> salas) {
        this.salas = salas;
    }
    
    /**
     * Obtiene la lista de reservas
     * @return List<Reserva> lista de reservas
     */
    public List<Reserva> getReservas() {
        return reservas;
    }
    
    /**
     * Establece la lista de reservas
     * @param reservas List<Reserva> lista de reservas
     */
    public void setReservas(List<Reserva> reservas) {
        this.reservas = reservas;
    }
    
    /**
     * Registra un nuevo estudiante en el sistema
     * @param estudiante Estudiante a registrar
     * @throws IllegalArgumentException si los datos están vacíos o el código ya existe
     */
    public void registrarEstudiante(Estudiante estudiante) {
        // Validación: No permitir campos vacíos
        if (estudiante.getNombre() == null || estudiante.getNombre().trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre del estudiante no puede estar vacío");
        }
        if (estudiante.getCodigoInstitucional() == null || estudiante.getCodigoInstitucional().trim().isEmpty()) {
            throw new IllegalArgumentException("El código institucional no puede estar vacío");
        }
        if (estudiante.getProgramaAcademico() == null || estudiante.getProgramaAcademico().trim().isEmpty()) {
            throw new IllegalArgumentException("El programa académico no puede estar vacío");
        }
        
        // Validación: No permitir duplicar código institucional
        for (Estudiante e : estudiantes) {
            if (e.getCodigoInstitucional().equals(estudiante.getCodigoInstitucional())) {
                throw new IllegalArgumentException("Ya existe un estudiante con este código institucional");
            }
        }
        
        estudiantes.add(estudiante);
    }
    
    /**
     * Registra una nueva sala de estudio en el sistema
     * @param salaEstudio SalaEstudio a registrar
     * @throws IllegalArgumentException si los datos están vacíos o el número de sala ya existe
     */
    public void registrarSala(SalaEstudio salaEstudio) {
        // Validación: No permitir campos vacíos
        if (salaEstudio.getNumeroSala() == null || salaEstudio.getNumeroSala().trim().isEmpty()) {
            throw new IllegalArgumentException("El número de sala no puede estar vacío");
        }
        if (salaEstudio.getCapacidadMax() <= 0) {
            throw new IllegalArgumentException("La capacidad máxima debe ser mayor a 0");
        }
        
        // Validación: No permitir duplicar número de sala
        for (SalaEstudio s : salas) {
            if (s.getNumeroSala().equals(salaEstudio.getNumeroSala())) {
                throw new IllegalArgumentException("Ya existe una sala con este número");
            }
        }
        
        salas.add(salaEstudio);
    }
    
    /**
     * Reserva una sala para un estudiante en una fecha y hora específica
     * @param estudiante Estudiante que realiza la reserva
     * @param salaEstudio Sala a reservar
     * @param fechaHora Fecha y hora de la reserva
     * @throws IllegalArgumentException si no se cumple alguna validación
     */
    public void reservarSala(Estudiante estudiante, SalaEstudio salaEstudio, LocalDateTime fechaHora) {
        // Validación: No permitir campos vacíos/nulos
        if (estudiante == null) {
            throw new IllegalArgumentException("El estudiante no puede ser nulo");
        }
        if (salaEstudio == null) {
            throw new IllegalArgumentException("La sala no puede ser nula");
        }
        if (fechaHora == null) {
            throw new IllegalArgumentException("La fecha y hora no pueden ser nulas");
        }
        
        // Validación: No permitir reservar una sala ya ocupada en la misma fecha y hora
        for (Reserva r : reservas) {
            if (r.getSalaReservada().getNumeroSala().equals(salaEstudio.getNumeroSala()) &&
                r.getFechaHora().equals(fechaHora)) {
                throw new IllegalArgumentException("La sala ya está ocupada en esa fecha y hora");
            }
        }
        
        // Validación: No permitir duplicar una reserva de la misma sala, mismo estudiante, misma fecha y hora
        for (Reserva r : reservas) {
            if (r.getEstudiante().getCodigoInstitucional().equals(estudiante.getCodigoInstitucional()) &&
                r.getSalaReservada().getNumeroSala().equals(salaEstudio.getNumeroSala()) &&
                r.getFechaHora().equals(fechaHora)) {
                throw new IllegalArgumentException("Ya existe una reserva idéntica");
            }
        }
        
        Reserva nuevaReserva = new Reserva();
        nuevaReserva.setEstudiante(estudiante);
        nuevaReserva.setSalaReservada(salaEstudio);
        nuevaReserva.setFechaHora(fechaHora);
        
        reservas.add(nuevaReserva);
    }
    
    /**
     * Consulta el historial de reservas de un estudiante
     * @param estudiante Estudiante del cual consultar el historial
     * @return List<Reserva> lista de reservas del estudiante
     */
    public List<Reserva> consultarHistorial(Estudiante estudiante) {
        List<Reserva> historial = new ArrayList<>();
        for (Reserva r : reservas) {
            if (r.getEstudiante().getCodigoInstitucional().equals(estudiante.getCodigoInstitucional())) {
                historial.add(r);
            }
        }
        return historial;
    }
    
    /**
     * Muestra todas las salas disponibles (no ocupadas en el momento actual)
     * @return List<SalaEstudio> lista de salas disponibles
     */
    public List<SalaEstudio> mostrarSalasDisponibles() {
        List<SalaEstudio> salasDisponibles = new ArrayList<>();
        LocalDateTime ahora = LocalDateTime.now();
        
        for (SalaEstudio sala : salas) {
            boolean ocupada = false;
            for (Reserva reserva : reservas) {
                if (reserva.getSalaReservada().getNumeroSala().equals(sala.getNumeroSala()) &&
                    reserva.getFechaHora().equals(ahora)) {
                    ocupada = true;
                    break;
                }
            }
            if (!ocupada) {
                salasDisponibles.add(sala);
            }
        }
        return salasDisponibles;
    }
}