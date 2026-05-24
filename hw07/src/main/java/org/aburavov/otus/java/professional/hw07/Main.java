package org.aburavov.otus.java.professional.hw07;

import org.aburavov.otus.java.professional.hw07.atm.Atm;
import org.aburavov.otus.java.professional.hw07.atm.AtmFactory;
import org.aburavov.otus.java.professional.hw07.atm.Department;
import org.aburavov.otus.java.professional.hw07.atm.Nominal;

public class Main {
    public static void main(String[] args) {
        System.out.println("HW07");

        Department department = new Department();
        Atm atm1 = AtmFactory.createStandard();
        Atm atm2 = AtmFactory.createEmpty();
        department.addAtm(atm1);
        department.addAtm(atm2);

        System.out.println("Initial balance of all department: " + department.getBalance());

        atm1.withdraw(Nominal.TEN, 3);
        atm2.deposit(Nominal.HUNDRED, 5);
        System.out.println("After operations: " + department.getBalance());

        department.restoreAll();
        System.out.println("After restoreAll: " + department.getBalance());
    }
}
