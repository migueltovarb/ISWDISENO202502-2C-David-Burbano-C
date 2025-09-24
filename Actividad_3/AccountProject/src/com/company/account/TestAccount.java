package com.company.account;

public class TestAccount {
    public static void main(String[] args) {
        // Crear cuentas
        Account acc1 = new Account("A001", "Carlos", 5000);
        Account acc2 = new Account("A002", "Maria", 3000);

        // Mostrar cuentas iniciales
        System.out.println(acc1);
        System.out.println(acc2);

        // Probar credit
        acc1.credit(2000);
        System.out.println("Después de depositar 2000 en acc1: " + acc1);

        // Probar debit
        acc1.debit(1000);
        System.out.println("Después de retirar 1000 de acc1: " + acc1);

        // Probar transferencia
        acc1.transferTo(acc2, 2500);
        System.out.println("Después de transferir 2500 de acc1 a acc2:");
        System.out.println(acc1);
        System.out.println(acc2);

        // Intentar débito mayor al balance
        acc1.debit(10000);
    }
}
