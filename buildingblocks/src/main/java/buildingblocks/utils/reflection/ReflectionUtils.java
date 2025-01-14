package buildingblocks.utils.reflection;

import org.reflections.Reflections;
import org.reflections.scanners.SubTypesScanner;
import org.reflections.util.ConfigurationBuilder;

import java.lang.reflect.Constructor;
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
}
