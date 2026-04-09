package org.aburavov.otus.java.professional.hw01;

import com.google.common.base.Joiner;
import java.util.List;

public class HelloOtus {
    public static void main(String[] args) {
        List<String> items = List.of("Hello", "OTUS", "Java", "Professional");
        String result = Joiner.on(", ").join(items);
        System.out.println(result);
    }
}
