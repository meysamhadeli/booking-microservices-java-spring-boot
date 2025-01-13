package buildingblocks.mediator;

import buildingblocks.mediator.abstractions.IMediator;
import buildingblocks.mediator.abstractions.requests.IRequest;
import buildingblocks.mediator.behaviors.LogPipelineBehavior;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;

// https://docs.spring.io/spring-boot/reference/features/developing-auto-configuration.html#features.developing-auto-configuration.condition-annotations

@EnableConfigurationProperties(MediatorProperties.class)
@ConditionalOnMissingBean({IMediator.class})
@ConditionalOnClass({IMediator.class})
@ConditionalOnProperty(prefix = "mediator", name = "enabled", havingValue = "true", matchIfMissing = true)
@Configuration
// register a bean only if a bean of the specified type is not already defined in the application context.
public class MediatorConfiguration {

    MediatorConfiguration() {}

    @Bean
    @ConditionalOnMissingBean
    @Scope(value = "request", proxyMode = ScopedProxyMode.TARGET_CLASS)
    public IMediator mediator(ApplicationContext applicationContext) {
        return new Mediator(applicationContext);
    }

    @Bean
    @Scope(value = "request", proxyMode = ScopedProxyMode.TARGET_CLASS)
    // @ConditionalOnMissingBean
    @ConditionalOnProperty(
            prefix = "mediator",
            name = "enabled-log-pipeline",
            havingValue = "true",
            matchIfMissing = true)
    public <TRequest extends IRequest<TResponse>, TResponse>
    LogPipelineBehavior<TRequest, TResponse> logPipelineBehavior() {
        return new LogPipelineBehavior<>();
    }
}
