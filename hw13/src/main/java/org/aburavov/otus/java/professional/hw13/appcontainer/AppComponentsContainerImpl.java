package org.aburavov.otus.java.professional.hw13.appcontainer;

import java.lang.reflect.Method;
import java.util.*;

import org.aburavov.otus.java.professional.hw13.appcontainer.api.AppComponent;
import org.aburavov.otus.java.professional.hw13.appcontainer.api.AppComponentsContainer;
import org.aburavov.otus.java.professional.hw13.appcontainer.api.AppComponentsContainerConfig;

public class AppComponentsContainerImpl implements AppComponentsContainer {

    private final List<Object> appComponents = new ArrayList<>();
    private final Map<String, Object> appComponentsByName = new HashMap<>();

    public AppComponentsContainerImpl(Class<?> initialConfigClass) {
        processConfig(initialConfigClass);
    }

    private void processConfig(Class<?> configClass) {
        checkConfigClass(configClass);
        try {
            Object configInstance = configClass.getDeclaredConstructor().newInstance();

            List<Method> componentMethods = Arrays.stream(configClass.getDeclaredMethods())
                    .filter(method -> method.isAnnotationPresent(AppComponent.class))
                    .sorted(Comparator.comparingInt(
                            method -> method.getAnnotation(AppComponent.class).order()))
                    .toList();

            for (Method method : componentMethods) {
                String name = method.getAnnotation(AppComponent.class).name();
                if (appComponentsByName.containsKey(name)) {
                    throw new AppComponentException("Duplicate: " + name);
                }
                Object[] args = Arrays.stream(method.getParameterTypes())
                        .map(this::getAppComponent)
                        .toArray();
                Object component = method.invoke(configInstance, args);
                appComponents.add(component);
                appComponentsByName.put(name, component);
            }
        } catch (ReflectiveOperationException e) {
            throw new AppComponentException("Failed to process config " + configClass.getName(), e);
        }
    }

    private void checkConfigClass(Class<?> configClass) {
        if (!configClass.isAnnotationPresent(AppComponentsContainerConfig.class)) {
            throw new IllegalArgumentException(String.format("Given class is not config %s", configClass.getName()));
        }
    }

    @Override
    public <C> C getAppComponent(Class<C> componentClass) {

        List<Object> found =
                appComponents.stream().filter(componentClass::isInstance).toList();
        if (found.size() != 1) {
            throw new AppComponentException(String.format(
                    "Expected 1 component of type %s, found %d", componentClass.getName(), found.size()));
        }
        return componentClass.cast(found.getFirst());
    }

    @Override
    @SuppressWarnings("unchecked")
    public <C> C getAppComponent(String componentName) {
        Object component = appComponentsByName.get(componentName);
        if (component == null) {
            throw new AppComponentException("Component not found: " + componentName);
        }
        return (C) component;
    }
}
