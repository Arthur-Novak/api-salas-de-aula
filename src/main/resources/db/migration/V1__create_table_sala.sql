CREATE TABLE salas (
                       id BIGSERIAL PRIMARY KEY,
                       nome VARCHAR(50) NOT NULL,
                       codigo_sala VARCHAR(10) NOT NULL,
                       capacidade_alunos INTEGER NOT NULL,
                       quantidade_computadores INTEGER,
                       ano_construcao INTEGER NOT NULL,
                       area NUMERIC(10, 2) NOT NULL,
                       situacao VARCHAR(20) NOT NULL,

                       CONSTRAINT uk_salas_codigo_sala UNIQUE (codigo_sala)
);