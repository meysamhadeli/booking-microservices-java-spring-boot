package buildingblocks.mediator.abstractions;

import buildingblocks.mediator.abstractions.commands.ICommand;
import buildingblocks.mediator.abstractions.queries.IQuery;
import buildingblocks.mediator.abstractions.requests.IRequest;

public interface ISender {

    <TResponse> TResponse send(IRequest<TResponse> request);

    <TResponse> TResponse send(ICommand<TResponse> command);

    <TResponse> TResponse send(IQuery<TResponse> query);
}
