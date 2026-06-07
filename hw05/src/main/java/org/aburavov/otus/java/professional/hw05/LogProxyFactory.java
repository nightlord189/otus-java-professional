package org.aburavov.otus.java.professional.hw05;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Arrays;
import java.util.stream.Collectors;

public class LogProxyFactory {
    private LogProxyFactory() {
    }

    @SuppressWarnings("unchecked")
    public static <T> T create(T target) {
        Class<?> targetClass = target.getClass();
        InvocationHandler handler = new LogInvocationHandler(target);
        return (T) Proxy.newProxyInstance(
                targetClass.getClassLoader(),
                targetClass.getInterfaces(),
                handler);
    }

    private static class LogInvocationHandler implements InvocationHandler {
        private final Object target;

        LogInvocationHandler(Object target) {
            this.target = target;
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            if (needLog(method)) {
                log(method.getName(), args);
            }
            return method.invoke(target, args);
        }

        private void log(String method, Object... args) {
            String params = Arrays.stream(args)
                    .map(String::valueOf)
                    .collect(Collectors.joining(", "));
            System.out.println("executed method: " + method + ", param: " + params);
        }

        private boolean needLog(Method method) {
            try {
                Method impl = target.getClass()
                        .getMethod(method.getName(), method.getParameterTypes());
                return impl.isAnnotationPresent(Log.class);
            } catch (NoSuchMethodException e) {
                return false;
            }
        }
    }
}
