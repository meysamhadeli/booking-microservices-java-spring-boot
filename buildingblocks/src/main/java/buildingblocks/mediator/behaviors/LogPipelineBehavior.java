package buildingblocks.mediator.behaviors;

import buildingblocks.mediator.abstractions.requests.IPipelineBehavior;
import buildingblocks.mediator.abstractions.requests.IRequest;
import buildingblocks.mediator.abstractions.requests.RequestHandlerDelegate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LogPipelineBehavior<TRequest extends IRequest<TResponse>, TResponse>
        implements IPipelineBehavior<TRequest, TResponse> {

    private static final Logger logger = LoggerFactory.getLogger(LogPipelineBehavior.class);

    @Override
    public TResponse handle(TRequest request, RequestHandlerDelegate<TResponse> next) {
        long startTime = System.currentTimeMillis();

        logger.info("Handling request of type: {}", request.getClass().getSimpleName());
        logger.debug("Request details: {}", request);

        TResponse response;

        try {
            // Delegate to the next handler in the pipeline
            response = next.handle();
        } catch (Exception ex) {
            logger.error(
                    "Error occurred while handling request of type {}",
                    request.getClass().getSimpleName(),
                    ex);
            throw ex;
        } finally {
            long endTime = System.currentTimeMillis();
            long executionTime = endTime - startTime;

            logger.info(
                    "Request of type {} handled in {} ms", request.getClass().getSimpleName(), executionTime);
        }

        logger.debug("Response details: {}", response);
        return response;
    }
}
