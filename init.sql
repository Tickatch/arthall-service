CREATE SCHEMA IF NOT EXISTS arthall_service;
DROP TABLE IF EXISTS arthall_service.p_arthall CASCADE;
CREATE TABLE arthall_service.p_arthall
(
    id         BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    name       VARCHAR(50)  NOT NULL,
    address    VARCHAR(255) NOT NULL,

    status     VARCHAR(20)  NOT NULL
        CHECK (status IN ('ACTIVE', 'INACTIVE')),

    created_at TIMESTAMP    NOT NULL DEFAULT NOW(),
    created_by VARCHAR(100) NOT NULL,

    updated_at TIMESTAMP    NOT NULL DEFAULT NOW(),
    updated_by VARCHAR(100) NOT NULL,

    deleted_at TIMESTAMP,
    deleted_by VARCHAR(100)
);
