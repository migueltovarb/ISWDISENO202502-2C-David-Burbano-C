package com.company.book;

public class TestBook {
    public static void main(String[] args) {
        // Crear un autor
        Author author = new Author("Gabriel García Márquez", "gabo@gmail.com", 'm');

        // Crear libros usando ambos constructores
        Book book1 = new Book("Cien años de soledad", author, 59.99);
        Book book2 = new Book("El amor en los tiempos del cólera", author, 39.99, 5);

        // Mostrar los libros
        System.out.println(book1);
        System.out.println(book2);

        // Probar métodos
        System.out.println("Nombre del libro: " + book1.getName());
        System.out.println("Autor del libro: " + book1.getAuthor().getName());
        System.out.println("Precio: " + book1.getPrice());
        System.out.println("Cantidad: " + book1.getQty());

        // Cambiar precio y cantidad
        book1.setPrice(65.99);
        book1.setQty(10);
        System.out.println("Después de actualizar: " + book1);
    }
}
