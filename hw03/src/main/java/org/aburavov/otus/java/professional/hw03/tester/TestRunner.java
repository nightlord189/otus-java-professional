package org.aburavov.otus.java.professional.hw03.tester;

import org.aburavov.otus.java.professional.hw03.tester.annotations.*;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TestRunner {
    public static RunStats runTests (String className) {
        System.out.println("Running tests for class: "+className);

        ScannedClassResult scanResult = scanClass(className);

        if (scanResult == null) {
            throw new RuntimeException("Class not found");
        }

        long success = 0;
        for (Method testMethod: scanResult.testMethods) {
            if (runSingleTest(scanResult, testMethod)) {
                success++;
            }
        }

        return new RunStats(
                className,
                scanResult.testMethods.size(),
                success,
                scanResult.testMethods.size() - success
        );
    }

    private static boolean runSingleTest (ScannedClassResult scanResult, Method testMethod) {
        Object instance = initInstance(scanResult.clazz);
        try {
            List<RunMethodResult> beforeResults = runMethods(scanResult.beforeMethods, instance);
            if (beforeResults.stream().anyMatch(r -> !r.isSuccess())) return false;

            RunMethodResult testResult = runMethod(testMethod, instance);
            return testResult.isSuccess();
        } finally {
            runMethods(scanResult.afterMethods, instance);
        }
    }

    private static List<RunMethodResult> runMethods (List<Method> methods, Object instance) {
        List<RunMethodResult> results = new ArrayList<>(methods.size());

        for (Method m: methods) {
            RunMethodResult result = runMethod(m, instance);
            results.add(result);
        }

        return results;
    }

    private static RunMethodResult runMethod (Method m, Object instance) {
        RunMethodResult result = new RunMethodResult(
               m.getName()
       );

        try {
            m.invoke(instance);
            result.setSuccess(true);
        } catch (InvocationTargetException | IllegalAccessException e) {
            System.out.println("Run method failed: "+m.getName()+": "+e);
            result.setException(e);
        }

        return result;
    }

    private static Object initInstance (Class<?> clazz) {
        try {
            return clazz.getDeclaredConstructor().newInstance();
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }

    private static ScannedClassResult scanClass (String className) {
        Class<?> clazz = null;

        try {
            clazz= Class.forName(className);
        } catch (ClassNotFoundException e) {
            return null;
        }

        Method[] methods = clazz.getMethods();

        List<Method> beforeMethods = Arrays.stream(methods).filter(m -> m.getAnnotation(Before.class) != null).toList();
        List<Method> testMethods = Arrays.stream(methods).filter(m -> m.getAnnotation(Test.class) != null).toList();
        List<Method> afterMethods = Arrays.stream(methods).filter(m -> m.getAnnotation(After.class) != null).toList();

        return new ScannedClassResult(clazz, beforeMethods, testMethods, afterMethods);
    }
}
