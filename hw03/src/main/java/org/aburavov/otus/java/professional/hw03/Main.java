package org.aburavov.otus.java.professional.hw03;

import org.aburavov.otus.java.professional.hw03.tester.RunStats;
import org.aburavov.otus.java.professional.hw03.tester.TestRunner;

public class Main {
    public static void main(String[] args) {
        System.out.println("HW03");

        RunStats stats = TestRunner.runTests(TestCalculator.class.getName());
        System.out.println(stats);
    }
}
