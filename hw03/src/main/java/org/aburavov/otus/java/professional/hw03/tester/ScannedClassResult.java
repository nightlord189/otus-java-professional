package org.aburavov.otus.java.professional.hw03.tester;

import java.lang.reflect.Method;
import java.util.List;

class ScannedClassResult {
    Class<?> clazz;
    List<Method> beforeMethods;
    List<Method> testMethods;
    List<Method> afterMethods;

    public ScannedClassResult (Class<?> clazz, List<Method> beforeMethods, List<Method> testMethods, List<Method> afterMethods) {
        this.clazz = clazz;
        this.beforeMethods = beforeMethods;
        this.testMethods = testMethods;
        this.afterMethods = afterMethods;
    }
}
