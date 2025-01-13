package buildingblocks.utils.bean;

import java.util.*;
import java.util.stream.Collectors;
import org.springframework.boot.autoconfigure.AutoConfigurationPackages;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.ResolvableType;
import org.springframework.util.ClassUtils;

public final class SpringBeanUtils {
    /**
     * Resolves bean names by handling mixed scoped and non-scoped scenarios.
     * For the same bean type, prefers non-scoped over scoped.
     * For different bean types, includes both scoped and non-scoped.
     *
     * @param beanNames Array of bean names from getBeanNamesForType
     * @return Array of resolved bean names
     */
    public static String[] resolveBeanNames(String[] beanNames) {
        if (beanNames.length == 0) {
            return new String[0];
        }

        // Group beans by their base name (removing 'scopedTarget.' prefix)
        Map<String, List<String>> groupedBeans = Arrays.stream(beanNames)
                .collect(Collectors.groupingBy(
                        name -> name.startsWith("scopedTarget.") ? name.substring("scopedTarget.".length()) : name));

        // Process each group to select the appropriate bean name
        List<String> resolvedList = groupedBeans.values().stream()
                .map(beans -> {
                    if (beans.size() == 1) {
                        return beans.get(0);
                    }

                    // Multiple beans for the same base name - prefer scoped
                    return beans.stream()
                            .filter(name -> name.startsWith("scopedTarget."))
                            .findFirst()
                            .orElse(beans.get(0));
                })
                .collect(Collectors.toList());

        // Convert List<String> to String[] before returning
        return resolvedList.toArray(new String[0]);
    }

    /**
     * Gets all matching beans, handling mixed scoped scenarios correctly.
     *
     * @param context ApplicationContext to get beans from
     * @param resolvableType Type to resolve
     * @return Array of resolved bean names
     */
    public static String[] resolveBeans(ApplicationContext context, ResolvableType resolvableType) {
        String[] beanNames = context.getBeanNamesForType(resolvableType);
        return resolveBeanNames(beanNames);
    }

    public static String gtePackageName(Class<?> clazz) {
        // https://dev.java/learn/reflection/
        return ClassUtils.getPackageName(clazz);
    }

    /**
     * Gets a single bean, throwing an exception if multiple or none are found.
     *
     * @param context ApplicationContext to get the bean from
     * @param resolvableType Type to resolve
     * @param beanType Class type for error messages
     * @return Single resolved bean name
     * @throws IllegalStateException if no unique bean can be resolved
     */
    public static String resolveSingleBean(
            ApplicationContext context, ResolvableType resolvableType, Class<?> beanType) {
        String[] beanNames = resolveBeans(context, resolvableType);

        if (beanNames.length == 0) {
            throw new IllegalStateException(String.format("No bean registered for type: '%s'", beanType.getName()));
        }

        if (beanNames.length > 1) {
            throw new IllegalStateException(String.format(
                    "Multiple beans registered for type: '%s'. Found: %s",
                    beanType.getName(), String.join(", ", beanNames)));
        }

        return beanNames[0];
    }

    /**
     * Retrieves the list of packages registered with Spring Boot's AutoConfigurationPackages.
     * the retried packages can be pass to `PersistenceManagedTypesScanner.scan` to create `PersistenceManagedTypes`.
     * @param applicationContext The applicationContext instance.
     * @return List of package names registered for `auto-configuration`.
     * @throws IllegalStateException if the packages are not available.
     */
    public static List<String> getAutoConfigurationPackages(ApplicationContext applicationContext) {
        try {
            return AutoConfigurationPackages.get(applicationContext);
        } catch (IllegalStateException e) {
            throw new IllegalStateException(
                    "No auto-configuration packages registered. Ensure Spring Boot's AutoConfigurationPackages is set up properly.",
                    e);
        }
    }

    /**
     * Retrieves the list of packages scanned by Spring's @ComponentScan annotation.
     *
     * @param applicationContext The ApplicationContext instance.
     * @return List of base packages scanned as part of the ComponentScan.
     */
    public static List<String> getComponentScanPackages(ApplicationContext applicationContext) {
        if (!(applicationContext instanceof AnnotationConfigApplicationContext)) {
            throw new IllegalArgumentException(
                    "ApplicationContext must be an instance of AnnotationConfigApplicationContext.");
        }

        AnnotationConfigApplicationContext configContext = (AnnotationConfigApplicationContext) applicationContext;
        Set<String> scannedPackages = new LinkedHashSet<>();

        // Get all registered configuration classes
        for (String beanName : configContext.getBeanDefinitionNames()) {
            // Retrieve the bean class if it's a configuration class
            Class<?> beanClass = getBeanClass(configContext, beanName);
            if (beanClass != null && beanClass.isAnnotationPresent(ComponentScan.class)) {
                ComponentScan componentScan = beanClass.getAnnotation(ComponentScan.class);

                // Add base packages from @ComponentScan
                scannedPackages.addAll(Arrays.asList(componentScan.basePackages()));

                // Add packages from basePackageClasses if specified
                for (Class<?> baseClass : componentScan.basePackageClasses()) {
                    scannedPackages.add(baseClass.getPackage().getName());
                }
            }
        }

        return new ArrayList<>(scannedPackages);
    }

    /**
     * Helper method to resolve the Class<?> for a bean definition.
     *
     * @param context ApplicationContext
     * @param beanName Bean name to resolve
     * @return The Class<?> of the bean, or null if not resolvable
     */
    private static Class<?> getBeanClass(ApplicationContext context, String beanName) {
        try {
            return context.getType(beanName);
        } catch (Exception e) {
            // Log or handle exception if required
            return null;
        }
    }
}
