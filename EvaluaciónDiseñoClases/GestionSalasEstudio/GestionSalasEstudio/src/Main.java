import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Scanner;

/**
 * Clase principal para el sistema de gestión de salas de estudio
 */
public class Main {
    private static Universidad universidad = new Universidad();
    private static Scanner scanner = new Scanner(System.in);
    private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    
    public static void main(String[] args) {
        System.out.println("=================================================");
        System.out.println("   SISTEMA DE GESTIÓN DE SALAS DE ESTUDIO");
        System.out.println("=================================================");
        
        boolean continuar = true;
        
        while (continuar) {
            mostrarMenu();
            int opcion = leerOpcion();
            
            try {
                switch (opcion) {
                    case 1:
                        registrarEstudiante();
                        break;
                    case 2:
                        registrarSala();
                        break;
                    case 3:
                        realizarReserva();
                        break;
                    case 4:
                        consultarHistorial();
                        break;
                    case 5:
                        mostrarSalasDisponibles();
                        break;
                    case 6:
                        mostrarTodosLosEstudiantes();
                        break;
                    case 7:
                        mostrarTodasLasSalas();
                        break;
                    case 8:
                        mostrarTodasLasReservas();
                        break;
                    case 9:
                        cargarDatosDePrueba();
                        break;
                    case 0:
                        continuar = false;
                        System.out.println("¡Gracias por usar el sistema!");
                        break;
                    default:
                        System.out.println("ERROR: Opción no válida. Intente de nuevo.");
                }
            } catch (Exception e) {
                System.out.println("ERROR: " + e.getMessage());
            }
            
            if (continuar) {
                System.out.println("\nPresiona Enter para continuar...");
                scanner.nextLine();
            }
        }
        
        scanner.close();
    }
    
    private static void mostrarMenu() {
        System.out.println("\n=== MENÚ PRINCIPAL ===");
        System.out.println("1. Registrar Estudiante");
        System.out.println("2. Registrar Sala de Estudio");
        System.out.println("3. Realizar Reserva");
        System.out.println("4. Consultar Historial de Estudiante");
        System.out.println("5. Mostrar Salas Disponibles");
        System.out.println("6. Mostrar Todos los Estudiantes");
        System.out.println("7. Mostrar Todas las Salas");
        System.out.println("8. Mostrar Todas las Reservas");
        System.out.println("9. Cargar Datos de Prueba");
        System.out.println("0. Salir");
        System.out.print("\nSeleccione una opción: ");
    }
    
    private static int leerOpcion() {
        try {
            int opcion = scanner.nextInt();
            scanner.nextLine(); // Consumir el salto de línea
            return opcion;
        } catch (Exception e) {
            scanner.nextLine(); // Limpiar el buffer
            return -1;
        }
    }
    
    private static void registrarEstudiante() {
        System.out.println("\n=== REGISTRAR ESTUDIANTE ===");
        
        System.out.print("Nombre completo: ");
        String nombre = scanner.nextLine();
        
        System.out.print("Código institucional: ");
        String codigo = scanner.nextLine();
        
        System.out.print("Programa académico: ");
        String programa = scanner.nextLine();
        
        Estudiante estudiante = new Estudiante();
        estudiante.setNombre(nombre);
        estudiante.setCodigoInstitucional(codigo);
        estudiante.setProgramaAcademico(programa);
        
        universidad.registrarEstudiante(estudiante);
        System.out.println("ÉXITO: Estudiante registrado exitosamente!");
    }
    
    private static void registrarSala() {
        System.out.println("\n=== REGISTRAR SALA DE ESTUDIO ===");
        
        System.out.print("Número de sala: ");
        String numeroSala = scanner.nextLine();
        
        System.out.print("Capacidad máxima: ");
        int capacidad = scanner.nextInt();
        scanner.nextLine();
        
        SalaEstudio sala = new SalaEstudio();
        sala.setNumeroSala(numeroSala);
        sala.setCapacidadMax(capacidad);
        sala.setDisponibilidad(true);
        
        universidad.registrarSala(sala);
        System.out.println("ÉXITO: Sala registrada exitosamente!");
    }
    
    private static void realizarReserva() {
        System.out.println("\n=== REALIZAR RESERVA ===");
        
        // Mostrar estudiantes disponibles
        List<Estudiante> estudiantes = universidad.getEstudiantes();
        if (estudiantes.isEmpty()) {
            System.out.println("ERROR: No hay estudiantes registrados. Registre un estudiante primero.");
            return;
        }
        
        System.out.println("\nEstudiantes disponibles:");
        for (int i = 0; i < estudiantes.size(); i++) {
            Estudiante e = estudiantes.get(i);
            System.out.println((i + 1) + ". " + e.getNombre() + " (" + e.getCodigoInstitucional() + ")");
        }
        
        System.out.print("Seleccione estudiante (número): ");
        int indiceEstudiante = scanner.nextInt() - 1;
        scanner.nextLine();
        
        if (indiceEstudiante < 0 || indiceEstudiante >= estudiantes.size()) {
            System.out.println("ERROR: Estudiante no válido.");
            return;
        }
        
        // Mostrar salas disponibles
        List<SalaEstudio> salas = universidad.getSalas();
        if (salas.isEmpty()) {
            System.out.println("ERROR: No hay salas registradas. Registre una sala primero.");
            return;
        }
        
        System.out.println("\nSalas disponibles:");
        for (int i = 0; i < salas.size(); i++) {
            SalaEstudio s = salas.get(i);
            System.out.println((i + 1) + ". Sala " + s.getNumeroSala() + " (Capacidad: " + s.getCapacidadMax() + ")");
        }
        
        System.out.print("Seleccione sala (número): ");
        int indiceSala = scanner.nextInt() - 1;
        scanner.nextLine();
        
        if (indiceSala < 0 || indiceSala >= salas.size()) {
            System.out.println("ERROR: Sala no válida.");
            return;
        }
        
        // Solicitar fecha y hora
        System.out.print("Fecha y hora (formato: yyyy-MM-dd HH:mm, ejemplo: 2025-10-15 14:30): ");
        String fechaHoraStr = scanner.nextLine();
        
        try {
            LocalDateTime fechaHora = LocalDateTime.parse(fechaHoraStr, formatter);
            
            universidad.reservarSala(estudiantes.get(indiceEstudiante), salas.get(indiceSala), fechaHora);
            System.out.println("ÉXITO: Reserva realizada exitosamente!");
            
        } catch (DateTimeParseException e) {
            System.out.println("ERROR: Formato de fecha inválido. Use: yyyy-MM-dd HH:mm");
        }
    }
    
    private static void consultarHistorial() {
        System.out.println("\n=== CONSULTAR HISTORIAL ===");
        
        List<Estudiante> estudiantes = universidad.getEstudiantes();
        if (estudiantes.isEmpty()) {
            System.out.println("ERROR: No hay estudiantes registrados.");
            return;
        }
        
        System.out.println("\nEstudiantes disponibles:");
        for (int i = 0; i < estudiantes.size(); i++) {
            Estudiante e = estudiantes.get(i);
            System.out.println((i + 1) + ". " + e.getNombre() + " (" + e.getCodigoInstitucional() + ")");
        }
        
        System.out.print("Seleccione estudiante (número): ");
        int indice = scanner.nextInt() - 1;
        scanner.nextLine();
        
        if (indice < 0 || indice >= estudiantes.size()) {
            System.out.println("ERROR: Estudiante no válido.");
            return;
        }
        
        Estudiante estudiante = estudiantes.get(indice);
        List<Reserva> historial = universidad.consultarHistorial(estudiante);
        
        System.out.println("\nHistorial de " + estudiante.getNombre() + ":");
        if (historial.isEmpty()) {
            System.out.println("   No tiene reservas.");
        } else {
            for (Reserva r : historial) {
                System.out.println("   • Sala: " + r.getSalaReservada().getNumeroSala() + 
                                 " | Fecha: " + r.getFechaHora().format(formatter));
            }
        }
    }
    
    private static void mostrarSalasDisponibles() {
        System.out.println("\n=== SALAS DISPONIBLES ===");
        List<SalaEstudio> disponibles = universidad.mostrarSalasDisponibles();
        
        if (disponibles.isEmpty()) {
            System.out.println("ERROR: No hay salas disponibles en este momento.");
        } else {
            for (SalaEstudio sala : disponibles) {
                System.out.println("Sala: " + sala.getNumeroSala() + 
                                 " | Capacidad: " + sala.getCapacidadMax());
            }
        }
    }
    
    private static void mostrarTodosLosEstudiantes() {
        System.out.println("\n=== TODOS LOS ESTUDIANTES ===");
        List<Estudiante> estudiantes = universidad.getEstudiantes();
        
        if (estudiantes.isEmpty()) {
            System.out.println("ERROR: No hay estudiantes registrados.");
        } else {
            for (Estudiante e : estudiantes) {
                System.out.println(e.getNombre() + 
                                 " | Código: " + e.getCodigoInstitucional() + 
                                 " | Programa: " + e.getProgramaAcademico());
            }
        }
    }
    
    private static void mostrarTodasLasSalas() {
        System.out.println("\n=== TODAS LAS SALAS ===");
        List<SalaEstudio> salas = universidad.getSalas();
        
        if (salas.isEmpty()) {
            System.out.println("ERROR: No hay salas registradas.");
        } else {
            for (SalaEstudio s : salas) {
                System.out.println("Sala: " + s.getNumeroSala() + 
                                 " | Capacidad: " + s.getCapacidadMax() + 
                                 " | Disponible: " + (s.getDisponibilidad() ? "Sí" : "No"));
            }
        }
    }
    
    private static void mostrarTodasLasReservas() {
        System.out.println("\n=== TODAS LAS RESERVAS ===");
        List<Reserva> reservas = universidad.getReservas();
        
        if (reservas.isEmpty()) {
            System.out.println("ERROR: No hay reservas registradas.");
        } else {
            for (Reserva r : reservas) {
                System.out.println(r.getEstudiante().getNombre() + 
                                 " | Sala: " + r.getSalaReservada().getNumeroSala() + 
                                 " | Fecha: " + r.getFechaHora().format(formatter));
            }
        }
    }
    
    private static void cargarDatosDePrueba() {
        System.out.println("\n=== CARGANDO DATOS DE PRUEBA ===");
        
        try {
            // Estudiantes de prueba
            Estudiante e1 = new Estudiante();
            e1.setNombre("Ana María Torres");
            e1.setCodigoInstitucional("EST2025001");
            e1.setProgramaAcademico("Ingeniería de Software");
            universidad.registrarEstudiante(e1);
            
            Estudiante e2 = new Estudiante();
            e2.setNombre("Carlos Rodríguez");
            e2.setCodigoInstitucional("EST2025002");
            e2.setProgramaAcademico("Administración de Empresas");
            universidad.registrarEstudiante(e2);
            
            Estudiante e3 = new Estudiante();
            e3.setNombre("Laura Gómez");
            e3.setCodigoInstitucional("EST2025003");
            e3.setProgramaAcademico("Psicología");
            universidad.registrarEstudiante(e3);
            
            // Salas de prueba
            SalaEstudio s1 = new SalaEstudio();
            s1.setNumeroSala("A101");
            s1.setCapacidadMax(25);
            s1.setDisponibilidad(true);
            universidad.registrarSala(s1);
            
            SalaEstudio s2 = new SalaEstudio();
            s2.setNumeroSala("B205");
            s2.setCapacidadMax(15);
            s2.setDisponibilidad(true);
            universidad.registrarSala(s2);
            
            SalaEstudio s3 = new SalaEstudio();
            s3.setNumeroSala("C303");
            s3.setCapacidadMax(30);
            s3.setDisponibilidad(true);
            universidad.registrarSala(s3);
            
            // Reservas de prueba
            universidad.reservarSala(e1, s1, LocalDateTime.of(2025, 10, 20, 9, 0));
            universidad.reservarSala(e2, s2, LocalDateTime.of(2025, 10, 21, 14, 30));
            universidad.reservarSala(e3, s3, LocalDateTime.of(2025, 10, 22, 16, 0));
            
            System.out.println("ÉXITO: Datos de prueba cargados exitosamente!");
            System.out.println("   • 3 estudiantes registrados");
            System.out.println("   • 3 salas registradas");
            System.out.println("   • 3 reservas creadas");
            
        } catch (Exception e) {
            System.out.println("ERROR: Error al cargar datos de prueba: " + e.getMessage());
        }
    }
}