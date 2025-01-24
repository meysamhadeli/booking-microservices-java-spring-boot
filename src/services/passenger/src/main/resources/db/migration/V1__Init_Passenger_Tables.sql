CREATE TABLE passengers
(
    id               UUID    NOT NULL,
    created_at       TIMESTAMP WITHOUT TIME ZONE,
    created_by       BIGINT,
    last_modified    TIMESTAMP WITHOUT TIME ZONE,
    last_modified_by BIGINT,
    version          BIGINT,
    is_deleted       BOOLEAN NOT NULL,
    passenger_type   VARCHAR(255),
    name             VARCHAR(255),
    passport_number  VARCHAR(255),
    age              INTEGER NOT NULL,
    CONSTRAINT pk_passengers PRIMARY KEY (id)
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