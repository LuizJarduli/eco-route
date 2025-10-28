-- V1: Cria as tabelas de autenticação, agora com campos de auditoria

-- Tabela de Usuários
CREATE TABLE users (
    id VARCHAR(36) PRIMARY KEY,
    username VARCHAR(100) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    name VARCHAR(255) NOT NULL,
    profile_pic VARCHAR(255) NULL,

    -- Campos de auditoria
    created_at DATETIME NOT NULL,
    updated_at DATETIME NOT NULL,
    deleted_at DATETIME NULL
);

-- Tabela de Papéis (Roles) - Você pode decidir se ela precisa de auditoria também.
-- Se sim, a entidade Role também deve herdar de BaseEntity.
CREATE TABLE roles (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL UNIQUE,

    -- Campos de auditoria
    created_at DATETIME NOT NULL,
    updated_at DATETIME NOT NULL,
    deleted_at DATETIME NULL
);

-- Tabela de Junção (geralmente não precisa de campos de auditoria)
CREATE TABLE user_roles (
    user_id VARCHAR(36) NOT NULL,
    role_id BIGINT NOT NULL,
    PRIMARY KEY (user_id, role_id),
    FOREIGN KEY (user_id) REFERENCES users(id),
    FOREIGN KEY (role_id) REFERENCES roles(id)
);

-- Inserir papéis (roles) padrão
INSERT INTO roles (name, created_at, updated_at) VALUES ('ADMIN', NOW(), NOW());
INSERT INTO roles (name, created_at, updated_at) VALUES ('USER', NOW(), NOW());

-- Inserir user admin
INSERT INTO users (id, username, password, name, profile_pic, created_at, updated_at, deleted_at)
VALUES (
    'c67c33ce-43b5-47de-b8f0-dbb8ed73d5eb',
    'admin@gmail.com',
    '$2a$10$8svvNFN8rNzk3wEWrz22z.MFw/7h98GcWrq1Ai0tu54C7zkGBiQMq', -- 3. SENHA HASHED (ex: 'admin123')
    'Administrator',
    NULL,
    NOW(),
    NOW(),
    NULL
);

INSERT INTO user_roles (user_id, role_id)
VALUES (
    'c67c33ce-43b5-47de-b8f0-dbb8ed73d5eb',
    1
);