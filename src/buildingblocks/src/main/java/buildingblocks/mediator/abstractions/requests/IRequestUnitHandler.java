package buildingblocks.mediator.abstractions.requests;

public interface IRequestUnitHandler<TRequest extends IRequest<Unit>> extends IRequestHandler<TRequest, Unit> {}
