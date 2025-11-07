-- V2: Creates the core tables for the ticketing system (Tickets, Waste Types, Photos)

-- Lookup Table for Waste Types
-- This is the table your backoffice will manage
CREATE TABLE waste_types (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL UNIQUE,
    description TEXT NULL,

     -- Audit fields
    created_at DATETIME(6) NOT NULL,
    updated_at DATETIME(6) NOT NULL,
    deleted_at DATETIME(6) NULL
);

-- Main Table for Tickets (Incidents)
CREATE TABLE tickets (
    id VARCHAR(36) PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    description TEXT NULL,

    -- Priority Level (Stores the Enum name: LOW, MEDIUM, HIGH)
    priority VARCHAR(30) NOT NULL,

    -- Coordinates (Optional)
    -- DECIMAL(10, 8) for latitude (e.g., -23.45678901)
    -- DECIMAL(11, 8) for longitude (e.g., -046.12345678)
    latitude DECIMAL(10, 8) NULL,
    longitude DECIMAL(11, 8) NULL,

    -- Manual Address (Optional, fallback)
    street_address VARCHAR(255) NULL,
    neighborhood VARCHAR(100) NULL,
    city VARCHAR(100) NULL,
    state VARCHAR(2) NULL,
    postal_code VARCHAR(9) NULL,

    -- Foreign Keys
    user_id VARCHAR(36) NOT NULL,
    waste_type_id BIGINT NOT NULL,

    -- Audit fields (from BaseEntity)
    created_at DATETIME(6) NOT NULL,
    updated_at DATETIME(6) NOT NULL,
    deleted_at DATETIME(6) NULL,

    FOREIGN KEY (user_id) REFERENCES users(id),
    FOREIGN KEY (waste_type_id) REFERENCES waste_types(id)
);

-- Table to store the URLs of photos for a ticket
CREATE TABLE ticket_photos (
    id VARCHAR(36) PRIMARY KEY,
    ticket_id VARCHAR(36) NOT NULL,
    image_url VARCHAR(1024) NOT NULL, -- URL from S3, Firebase Storage, etc.

    -- Audit fields
    created_at DATETIME(6) NOT NULL,
    updated_at DATETIME(6) NOT NULL,
    deleted_at DATETIME(6) NULL,

    FOREIGN KEY (ticket_id) REFERENCES tickets(id)
);

-- Insert some default waste types for the backoffice to manage
INSERT INTO waste_types (name, description, created_at, updated_at, deleted_at) VALUES ('DOMESTIC', 'Common household waste.', NOW(6), NOW(6), NULL);
INSERT INTO waste_types (name, description, created_at, updated_at, deleted_at) VALUES ('RECYCLABLE', 'Plastic, paper, metal, and glass.', NOW(6), NOW(6), NULL);
INSERT INTO waste_types (name, description, created_at, updated_at, deleted_at) VALUES ('CONSTRUCTION_DEBRIS', 'Debris from civil construction sites.', NOW(6), NOW(6), NULL);
INSERT INTO waste_types (name, description, created_at, updated_at, deleted_at) VALUES ('GREEN_WASTE', 'Branches and leaves from gardens and parks.', NOW(6), NOW(6), NULL);
INSERT INTO waste_types (name, description, created_at, updated_at, deleted_at) VALUES ('ELECTRONIC_WASTE', 'Electronic equipment and batteries.', NOW(6), NOW(6), NULL);