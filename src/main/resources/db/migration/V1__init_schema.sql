CREATE SCHEMA IF NOT EXISTS purchase;

CREATE TABLE purchase.customer
(
    customer_code           VARCHAR PRIMARY KEY,
    customer_name           VARCHAR NOT NULL,
    customer_inn            VARCHAR NOT NULL,
    customer_kpp            VARCHAR NOT NULL,
    customer_legal_address  VARCHAR,
    customer_postal_address VARCHAR,
    customer_email          VARCHAR,
    customer_code_main      VARCHAR REFERENCES purchase.customer (customer_code),
    is_organization         BOOLEAN DEFAULT FALSE,
    is_person               BOOLEAN DEFAULT FALSE
);

CREATE TABLE purchase.lot
(
    id             SERIAL PRIMARY KEY,
    lot_name       VARCHAR                                                   NOT NULL,
    customer_code  VARCHAR                                                   NOT NULL REFERENCES purchase.customer (customer_code),
    price          NUMERIC(15, 2)                                            NOT NULL,
    currency_code  VARCHAR(3) CHECK (currency_code IN ('RUB', 'USD', 'EUR')) NOT NULL,
    nds_rate       VARCHAR CHECK (nds_rate IN ('Без НДС', '18%', '20%'))     NOT NULL,
    place_delivery VARCHAR,
    date_delivery  TIMESTAMP
);