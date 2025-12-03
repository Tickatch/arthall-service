CREATE SCHEMA IF NOT EXISTS arthall_service;

-- p_arthall
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

-- ENUM 타입 (스키마 명시)
CREATE TYPE arthall_service.stage_status AS ENUM ('ACTIVE', 'INACTIVE');

-- p_stage
DROP TABLE IF EXISTS arthall_service.p_stage CASCADE;

CREATE TABLE arthall_service.p_stage
(
    id         BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,

    arthall_id BIGINT                       NOT NULL
        REFERENCES arthall_service.p_arthall (id),

    name       VARCHAR(50)                  NOT NULL,
    status     arthall_service.stage_status NOT NULL,

    created_at TIMESTAMP                    NOT NULL DEFAULT NOW(),
    created_by VARCHAR(100)                 NOT NULL,

    updated_at TIMESTAMP                    NOT NULL DEFAULT NOW(),
    updated_by VARCHAR(100)                 NOT NULL,

    deleted_at TIMESTAMP                    NULL,
    deleted_by VARCHAR(100)                 NULL
);
