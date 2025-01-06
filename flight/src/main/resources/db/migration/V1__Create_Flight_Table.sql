CREATE TABLE flights
(
  id                   UUID NOT NULL,
  created_at           TIMESTAMP WITHOUT TIME ZONE,
  created_by           BIGINT,
  last_modified        TIMESTAMP WITHOUT TIME ZONE,
  last_modified_by     BIGINT,
  is_deleted           BOOLEAN,
  version              BIGINT,
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
