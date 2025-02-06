package buildingblocks.utils.reflection;

import org.reflections.Reflections;
import org.reflections.scanners.SubTypesScanner;
import org.reflections.util.ConfigurationBuilder;
import org.springframework.context.ApplicationContext;

import java.lang.reflect.Constructor;
import java.util.Set;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

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

  public static Class<?> findClassFromName(String className) {
    try {
      return Class.forName(className);
    } catch (ClassNotFoundException e) {
      throw new RuntimeException(e);
    }
  }

  public static <T> Class<T> getGenericTypeParameter(Class<?> clazz, Class<?> interfaceType) {
    try {
      // Get the generic interfaces of the class
      Type[] genericInterfaces = clazz.getGenericInterfaces();

      // Iterate through the generic interfaces to find the one with the generic type parameter
      for (Type type : genericInterfaces) {
        if (type instanceof ParameterizedType) {
          ParameterizedType parameterizedType = (ParameterizedType) type;

          // Check if the raw type matches the provided interface type
          if (parameterizedType.getRawType().equals(interfaceType)) {
            Type[] typeArguments = parameterizedType.getActualTypeArguments();

            // Return the first type argument (T)
            if (typeArguments.length > 0 && typeArguments[0] instanceof Class) {
              return (Class<T>) typeArguments[0];
            }
          }
        }
      }

      throw new IllegalArgumentException("Could not infer generic type parameter for " + clazz.getName());
    } catch (Exception ex) {
      throw new IllegalArgumentException("Failed to infer generic type parameter", ex);
    }
  }
}
