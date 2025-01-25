package buildingblocks.utils.reflection;

import org.reflections.Reflections;
import org.reflections.scanners.SubTypesScanner;
import org.reflections.util.ConfigurationBuilder;
import org.springframework.context.ApplicationContext;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public final class ReflectionUtils {
  public static <T> T getInstanceOfSubclass(Class<T> abstractClass, ApplicationContext applicationContext) {
    try {
      Reflections reflections = new Reflections(new ConfigurationBuilder()
        .forPackages("")
        .setScanners(new SubTypesScanner(false))
      );

      // Get all subclasses of the abstract class
      Set<Class<? extends T>> subTypes = reflections.getSubTypesOf(abstractClass);

      if (!subTypes.isEmpty()) {
        for (Class<? extends T> subType : subTypes) {
          // Check if the subclass is managed by Spring
          if (applicationContext.containsBean(subType.getSimpleName())) {
            // Return the Spring-managed bean
            return applicationContext.getBean(subType);
          } else {
            // Fall back to creating a new instance manually
            Constructor<? extends T> constructor = subType.getDeclaredConstructor();
            return constructor.newInstance();
          }
        }
      }
      return null; // or throw an exception if no subclass is found
    } catch (Exception ex) {
      throw new RuntimeException("Error occurred while creating an instance of subclass", ex);
    }
  }

  public static <T> List<T> getAllInstanceOfSubclasses(Class<T> abstractClass, ApplicationContext applicationContext) {
    List<T> instances = new ArrayList<>();
    try {
      Reflections reflections = new Reflections(new ConfigurationBuilder()
        .forPackages("")
        .setScanners(new SubTypesScanner(false))
      );

      Set<Class<? extends T>> subTypes = reflections.getSubTypesOf(abstractClass);

      for (Class<? extends T> subType : subTypes) {
        try {
          // Check if the class is managed by Spring
          if (applicationContext.containsBean(subType.getSimpleName())) {
            T instance = applicationContext.getBean(subType);
            instances.add(instance);
          } else {
            // Fall back to no-argument constructor
            T instance = subType.getDeclaredConstructor().newInstance();
            instances.add(instance);
          }
        } catch (Exception ex) {
          throw new RuntimeException("Error occurred while creating an instance of subclasses", ex);
        }
      }
    } catch (Exception ex) {
      throw new RuntimeException("Error occurred while creating an instance of subclass", ex);
    }
    return instances;
  }

  public static Class<?> findClassFromName(String className) {
    try {
      return Class.forName(className);
    } catch (ClassNotFoundException e) {
      throw new RuntimeException(e);
    }
  }
}
