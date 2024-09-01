CREATE TABLE IF NOT EXISTS apps (
    id BIGINT AUTO_INCREMENT,
    name VARCHAR(255),
    monthlyCost DECIMAL(4,2),
    PRIMARY KEY(id)
);

CREATE TABLE IF NOT EXISTS clients (
    id BIGINT AUTO_INCREMENT,
    name VARCHAR(255),
    email VARCHAR(255),
    PRIMARY KEY(id)
);

CREATE TABLE IF NOT EXISTS signatures (
    id BIGINT AUTO_INCREMENT,
    appId BIGINT,
    clientId BIGINT,
    startDate DATE,
    endDate DATE,
    PRIMARY KEY(id),
    FOREIGN KEY(appId) REFERENCES apps(id),
    FOREIGN KEY(clientId) REFERENCES clients(id)
);

CREATE TABLE IF NOT EXISTS payments (
    id BIGINT AUTO_INCREMENT,
    signatureId BIGINT,
    payedValue DECIMAL(4,2),
    paymentDate DATE,
    promotion VARCHAR(100),
    PRIMARY KEY(id),
    FOREIGN KEY(signatureId) REFERENCES signatures(id)
);

CREATE TABLE IF NOT EXISTS users (
    id BIGINT AUTO_INCREMENT,
    username VARCHAR(255),
    password VARCHAR(255),
    PRIMARY KEY(id)
);