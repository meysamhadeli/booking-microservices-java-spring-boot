package buildingblocks.outboxprocessor;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.Statement;

public class PersistMessageTableInitializer {
    private final DataSource dataSource;

    public PersistMessageTableInitializer(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public void initialize() {
        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement()) {

            String createTableSQL = """
                CREATE TABLE IF NOT EXISTS persist_messages (
                    id UUID PRIMARY KEY,
                    data_type VARCHAR(255) NOT NULL,
                    data TEXT NOT NULL,
                    created TIMESTAMP NOT NULL,
                    retry_count INT NOT NULL,
                    message_status VARCHAR(50) NOT NULL,
                    delivery_type VARCHAR(50) NOT NULL,
                    version BIGINT,
                    CONSTRAINT chk_message_status CHECK (message_status IN ('InProgress', 'Processed')),
                    CONSTRAINT chk_delivery_type CHECK (delivery_type IN ('Inbox', 'Outbox', 'Internal'))
                );
            """;
            statement.execute(createTableSQL);

        } catch (Exception ex) {
            throw new RuntimeException("Failed to initialize persist_messages table", ex);
        }
    }
}
