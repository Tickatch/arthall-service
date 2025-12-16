CREATE SCHEMA IF NOT EXISTS arthall_service;

DROP TABLE IF EXISTS arthall_service.p_stage CASCADE;
DROP TABLE IF EXISTS arthall_service.p_arthall CASCADE;

CREATE TABLE arthall_service.p_arthall
(
    id         BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    name       VARCHAR(50)  NOT NULL,
    address    VARCHAR(255) NOT NULL,

    status     VARCHAR(20)  NOT NULL,

    created_at TIMESTAMP    NOT NULL DEFAULT NOW(),
    created_by VARCHAR(100) NOT NULL,

    updated_at TIMESTAMP    NOT NULL DEFAULT NOW(),
    updated_by VARCHAR(100) NOT NULL,

    deleted_at TIMESTAMP,
    deleted_by VARCHAR(100)
);

CREATE TABLE arthall_service.p_stage
(
    id         BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,

    arthall_id BIGINT       NOT NULL
        REFERENCES arthall_service.p_arthall (id),

    name       VARCHAR(50)  NOT NULL,
    status     VARCHAR(20)  NOT NULL,

    created_at TIMESTAMP    NOT NULL DEFAULT NOW(),
    created_by VARCHAR(100) NOT NULL,

    updated_at TIMESTAMP    NOT NULL DEFAULT NOW(),
    updated_by VARCHAR(100) NOT NULL,

    deleted_at TIMESTAMP,
    deleted_by VARCHAR(100)
);

CREATE EXTENSION IF NOT EXISTS vector;

DROP TABLE IF EXISTS arthall_service.p_stageseat CASCADE;

CREATE TABLE arthall_service.p_stageseat
(
    id         BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,

    stage_id   BIGINT       NOT NULL,
    seatNumber VARCHAR(10)  NOT NULL,

    status     VARCHAR(20)  NOT NULL
        CHECK (status IN ('ACTIVE', 'INACTIVE')),

    row        INT          NOT NULL,
    col        INT          NOT NULL,

    vector     VECTOR(2)    NOT NULL,

    created_at TIMESTAMP    NOT NULL DEFAULT NOW(),
    created_by VARCHAR(100) NOT NULL,

    updated_at TIMESTAMP    NOT NULL DEFAULT NOW(),
    updated_by VARCHAR(100) NOT NULL,

    deleted_at TIMESTAMP,
    deleted_by VARCHAR(100),

    CONSTRAINT fk_stage
        FOREIGN KEY (stage_id)
            REFERENCES arthall_service.p_stage (id)
);