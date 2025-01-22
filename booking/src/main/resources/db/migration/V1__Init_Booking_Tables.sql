CREATE TABLE bookings
(
    id                   UUID    NOT NULL,
    created_at           TIMESTAMP WITHOUT TIME ZONE,
    created_by           BIGINT,
    last_modified        TIMESTAMP WITHOUT TIME ZONE,
    last_modified_by     BIGINT,
    version              BIGINT,
    is_deleted           BOOLEAN NOT NULL,
    name                 VARCHAR(255),
    flight_number        VARCHAR(255),
    aircraft_id          UUID,
    departure_airport_id UUID,
    arrive_airport_id    UUID,
    flight_date          TIMESTAMP WITHOUT TIME ZONE,
    price                DECIMAL,
    description          VARCHAR(255),
    seat_number          VARCHAR(255),
    CONSTRAINT pk_bookings PRIMARY KEY (id)
);

CREATE TABLE persist_messages
(
    id             UUID         NOT NULL,
    data_type      VARCHAR(255) NOT NULL,
    data           TEXT         NOT NULL,
    created        TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    retry_count    INTEGER      NOT NULL,
    message_status VARCHAR(255) NOT NULL,
    delivery_type  VARCHAR(255) NOT NULL,
    version        BIGINT,
    CONSTRAINT pk_persist_messages PRIMARY KEY (id)
);