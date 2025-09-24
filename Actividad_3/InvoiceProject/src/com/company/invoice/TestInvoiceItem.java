package com.company.invoice;

public class TestInvoiceItem {
    public static void main(String[] args) {
        InvoiceItem item1 = new InvoiceItem("A101", "Laptop", 2, 850.50);

        System.out.println(item1);  // toString()
        System.out.println("ID: " + item1.getID());
        System.out.println("Description: " + item1.getDesc());
        System.out.println("Quantity: " + item1.getQty());
        System.out.println("Unit Price: " + item1.getUnitPrice());
        System.out.println("Total: " + item1.getTotal());

        
        item1.setQty(3);
        item1.setUnitPrice(900.00);

        System.out.println("\nAfter update:");
        System.out.println(item1);
        System.out.println("New Total: " + item1.getTotal());
    }
}
