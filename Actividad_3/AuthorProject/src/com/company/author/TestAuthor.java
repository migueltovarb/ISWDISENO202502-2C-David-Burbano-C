package com.company.author;

public class TestAuthor {
    public static void main(String[] args) {
        // Crear autores ficticios
        Author a1 = new Author("Gabriel García Márquez", "gabo@gmail.com", 'm');
        Author a2 = new Author("Isabel Allende", "isabel@outlook.com", 'f');

        // Mostrar datos de autores
        System.out.println(a1);
        System.out.println(a2);

        // Probar getters
        System.out.println("Nombre: " + a1.getName());
        System.out.println("Email: " + a1.getEmail());
        System.out.println("Género: " + a1.getGender());

        // Cambiar el email
        a1.setEmail("nuevo_email@gmail.com");
        System.out.println("Después de actualizar el email: " + a1);
    }
}
