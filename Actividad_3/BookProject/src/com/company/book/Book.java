package com.company.book;

public class Book {
    private String name;
    private Author author;  // Composición: un libro TIENE un autor
    private double price;
    private int qty = 0;

    // Constructor con qty por defecto en 0
    public Book(String name, Author author, double price) {
        this.name = name;
        this.author = author;
        this.price = price;
    }

    // Constructor con todos los atributos
    public Book(String name, Author author, double price, int qty) {
        this.name = name;
        this.author = author;
        this.price = price;
        this.qty = qty;
    }

    // Getters
    public String getName() {
        return name;
    }

    public Author getAuthor() {
        return author;
    }

    public double getPrice() {
        return price;
    }

    public int getQty() {
        return qty;
    }

    // Setters
    public void setPrice(double price) {
        this.price = price;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    // toString reutilizando Author.toString()
    @Override
    public String toString() {
        return "Book[name=" + name + ", " + author.toString() + ", price=" + price + ", qty=" + qty + "]";
    }
}
