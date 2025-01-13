package buildingblocks.mediator.abstractions.commands;

import buildingblocks.mediator.abstractions.requests.IRequest;

public interface ICommand<TResponse> extends IRequest<TResponse>, IBaseCommand {}
