package buildingblocks.utils.reflection;

import org.reflections.Reflections;
import org.reflections.scanners.SubTypesScanner;
import org.reflections.util.ConfigurationBuilder;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public final class ReflectionUtils {
  public static <T> T getInstanceOfSubclass(Class<T> abstractClass) {
    try {
      // Initialize Reflections with the provided package name
      Reflections reflections = new Reflections(new ConfigurationBuilder()
        .forPackages("")
        .setScanners(new SubTypesScanner(false))
      );

      // Get all subclasses of the abstract class
      Set<Class<? extends T>> subTypes = reflections.getSubTypesOf(abstractClass);

      if (!subTypes.isEmpty()) {
        // Get the first subclass (you can enhance this to return any specific subclass if needed)
        Class<? extends T> firstSubclass = subTypes.iterator().next();

        // Create an instance of the subclass using reflection
        Constructor<? extends T> constructor = firstSubclass.getDeclaredConstructor();
        return constructor.newInstance();
      }
      return null; // or throw an exception if no subclass is found
    } catch (Exception ex) {
      throw new RuntimeException("Error occurred while creating an instance of subclass", ex);
    }
  }

  public static <T> List<T> getAllInstanceOfSubclasses(Class<T> abstractClass) {
    List<T> instances = new ArrayList<>();
    try {
      // Initialize Reflections with the provided package name
      Reflections reflections = new Reflections(new ConfigurationBuilder()
        .forPackages("")
        .setScanners(new SubTypesScanner(false))
      );

      // Get all subclasses of the abstract class
      Set<Class<? extends T>> subTypes = reflections.getSubTypesOf(abstractClass);

      if (!subTypes.isEmpty()) {
        // Get the first subclass (you can enhance this to return any specific subclass if needed)

        for (Class<? extends T> subType : subTypes) {
          try {
            // Create a new instance using the no-argument constructor
            T instance = subType.getDeclaredConstructor().newInstance();
            instances.add(instance);
          } catch (InstantiationException | IllegalAccessException |
                   NoSuchMethodException | InvocationTargetException ex) {
            throw new RuntimeException("Error occurred while creating an instance of subclasses", ex);
          }
        }
      }
      return instances;
    } catch (Exception ex) {
      throw new RuntimeException("Error occurred while creating an instance of subclass", ex);
    }
  }

  public static Class<?> findClassFromName(String className) {
    try {
      return Class.forName(className);
    } catch (ClassNotFoundException e) {
      throw new RuntimeException(e);
    }
  }
}
