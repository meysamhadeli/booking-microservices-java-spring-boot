CREATE TABLE flights
(
  id                   UUID    NOT NULL,
  created_at           TIMESTAMP WITHOUT TIME ZONE,
  created_by           BIGINT,
  last_modified        TIMESTAMP WITHOUT TIME ZONE,
  last_modified_by     BIGINT,
  version              BIGINT,
  is_deleted           BOOLEAN NOT NULL,
  flight_number        VARCHAR(255),
  aircraft_id          UUID,
  departure_airport_id UUID,
  departure_date       TIMESTAMP WITHOUT TIME ZONE,
  arrive_date          TIMESTAMP WITHOUT TIME ZONE,
  arrive_airport_id    UUID,
  duration_minutes     DECIMAL,
  flight_date          TIMESTAMP WITHOUT TIME ZONE,
  status               VARCHAR(255),
  price                DECIMAL,
  CONSTRAINT pk_flights PRIMARY KEY (id)
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
