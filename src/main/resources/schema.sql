CREATE TABLE IF NOT EXISTS owners (
    id BIGINT AUTO_INCREMENT,
    cell_phone_number VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL,
    created_at DATETIME(6) NOT NULL,
    updated_at DATETIME(6) NOT NULL,
    PRIMARY KEY (id)
    );

CREATE TABLE IF NOT EXISTS products (
    id BIGINT AUTO_INCREMENT,
    owner_id BIGINT,
    product_category VARCHAR(255) NOT NULL,
    price INT NOT NULL,
    cost INT NOT NULL,
    name VARCHAR(255) NOT NULL,
    name_init VARCHAR(255) NOT NULL,
    description TEXT NOT NULL,
    barcode VARCHAR(255) NOT NULL,
    expiration_date DATETIME(6) NOT NULL,
    product_size VARCHAR(255) NOT NULL,
    created_at DATETIME(6) NOT NULL,
    updated_at DATETIME(6) NOT NULL,
    FOREIGN KEY (owner_id) REFERENCES owners(id),
    PRIMARY KEY (id)
    );

CREATE TABLE IF NOT EXISTS auth_tokens (
    id BIGINT AUTO_INCREMENT,
    owner_id BIGINT UNIQUE,
    token VARCHAR(255),
    created_at DATETIME(6) NOT NULL,
    updated_at DATETIME(6) NOT NULL,
    FOREIGN KEY (owner_id) REFERENCES owners(id),
    PRIMARY KEY (id)
    );
