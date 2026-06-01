package org.aburavov.otus.java.professional.hw05;

public class Main {
    public static void main(String[] args) {
        System.out.println("HW05");

        TestLoggingInterface t = LogProxyFactory.create(new TestLogging());

        t.calculation(6);
        t.calculation(6, 7);
        t.calculation(6, 7, "eight");

        t.noLogging(42);
    }
}
