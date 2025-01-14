package buildingblocks.mediator.abstractions.requests;

@FunctionalInterface
public interface RequestHandlerDelegate<TResponse> {
    TResponse handle();
}
