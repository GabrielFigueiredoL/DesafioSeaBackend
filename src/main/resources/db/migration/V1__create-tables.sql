CREATE TABLE enderecos(
    id SERIAL PRIMARY KEY NOT NULL UNIQUE,
    cep TEXT NOT NULL,
    logradouro TEXT NOT NULL,
    bairro TEXT NOT NULL,
    cidade TEXT NOT NULL,
    uf TEXT NOT NULL,
    complemento TEXT
);

CREATE TABLE clientes(
    id SERIAL PRIMARY KEY NOT NULL UNIQUE,
    nome TEXT NOT NULL,
    cpf TEXT NOT NULL,
    endereco_id SERIAL NOT NULL UNIQUE,
    FOREIGN KEY (endereco_id) REFERENCES enderecos(id)
);

CREATE TABLE telefones(
    id SERIAL PRIMARY KEY NOT NULL UNIQUE,
    cliente_id SERIAL NOT NULL,
    tipo TEXT NOT NULL,
    numero TEXT NOT NULL,
    FOREIGN KEY (cliente_id) REFERENCES clientes(id)
);

CREATE TABLE emails(
    id SERIAL PRIMARY KEY NOT NULL UNIQUE,
    cliente_id SERIAL NOT NULL,
    email TEXT NOT NULL,
    FOREIGN KEY (cliente_id) REFERENCES clientes(id)
);

CREATE TABLE usuarios(
    id SERIAL PRIMARY KEY,
    login TEXT NOT NULL UNIQUE,
    senha TEXT NOT NULL,
    role TEXT NOT NULL
);