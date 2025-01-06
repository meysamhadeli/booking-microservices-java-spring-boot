package buildingblocks.core.exception;

public class AggregateNotFoundException extends RuntimeException {
    public AggregateNotFoundException(String typeName, String id) {
        super(typeName + " with id '" + id + "' was not found");
    }

    public static <T> AggregateNotFoundException forType(Class<T> type, String id) {
        return new AggregateNotFoundException(type.getSimpleName(), id);
    }
}