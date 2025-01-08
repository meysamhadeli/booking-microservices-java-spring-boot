package buildingblocks.r2dbc;

import io.r2dbc.spi.ConnectionFactories;
import io.r2dbc.spi.ConnectionFactory;
import io.r2dbc.spi.ConnectionFactoryOptions;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.ReactiveAuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.r2dbc.config.AbstractR2dbcConfiguration;
import org.springframework.r2dbc.connection.R2dbcTransactionManager;
import org.springframework.transaction.ReactiveTransactionManager;
import org.springframework.transaction.reactive.TransactionalOperator;

@Configuration
@EnableJpaAuditing
@Import(R2dbcOptions.class)
public class R2dbcConfiguration extends AbstractR2dbcConfiguration {

    private final R2dbcOptions r2dbcOptions;

    public R2dbcConfiguration(R2dbcOptions r2dbcOptions) {
        this.r2dbcOptions = r2dbcOptions;
    }

    @Bean
    @Override
    @ConditionalOnMissingBean
    public ConnectionFactory connectionFactory() {
        return ConnectionFactories.get(ConnectionFactoryOptions.builder()
                .option(ConnectionFactoryOptions.DRIVER, r2dbcOptions.getDriver())
                .option(ConnectionFactoryOptions.HOST, r2dbcOptions.getHost())
                .option(ConnectionFactoryOptions.PORT, r2dbcOptions.getPort())
                .option(ConnectionFactoryOptions.DATABASE, r2dbcOptions.getDatabase())
                .option(ConnectionFactoryOptions.USER, r2dbcOptions.getUsername())
                .option(ConnectionFactoryOptions.PASSWORD, r2dbcOptions.getPassword())
                .build());
    }

    @Bean
    public ReactiveTransactionManager transactionManager(ConnectionFactory connectionFactory) {
        return new R2dbcTransactionManager(connectionFactory);
    }

    @Bean
    public TransactionalOperator transactionalOperator(ReactiveTransactionManager transactionManager) {
        return TransactionalOperator.create(transactionManager);
    }

    @Bean
    public ReactiveAuditorAware<Long> reactiveAuditorAware() {
        return new ReactiveAuditorAwareImpl();
    }
}
