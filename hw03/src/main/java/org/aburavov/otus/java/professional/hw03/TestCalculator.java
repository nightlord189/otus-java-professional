package org.aburavov.otus.java.professional.hw03;

import org.aburavov.otus.java.professional.hw03.tester.annotations.After;
import org.aburavov.otus.java.professional.hw03.tester.annotations.Before;
import org.aburavov.otus.java.professional.hw03.tester.annotations.Test;

public class TestCalculator {
    private Calculator calculator;

    @Before
    public void Prepare () {
        System.out.println("TestCalculator: Prepare");
        calculator = new Calculator();
    }

    @Test
    public void TestSum () {
        System.out.println("TestCalculator: TestSum");
        int result = calculator.sum(1, 2);
        if (result != 3) {
            throw new RuntimeException("Sum test failed");
        }
    }

    @Test
    public void TestDivide () {
        System.out.println("TestCalculator: TestDivide");
        float result = calculator.divide(10, 0);
        if (result != 5) {
            throw new RuntimeException("Divide test failed");
        }
    }

    @After
    public void Finish () {
        System.out.println("TestCalculator: Finish");
    }
}
