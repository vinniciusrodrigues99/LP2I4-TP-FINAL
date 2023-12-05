CREATE TABLE alunos_academia(
ID_ALUNO INT NOT NULL,
NOME_ALUNO VARCHAR(50),
IDADE INT,
PESO DECIMAL(5, 2),
ALTURA DECIMAL(5, 2),
OBJETIVO VARCHAR(255)
);
INSERT INTO CADASTRO_ALUNOS2 VALUES
(1, "VINNICIUS O. RODRIGUES", 24, 73.50, 1.84, "GANHO DE MASSA");
INSERT INTO CADASTRO_ALUNOS VALUES
(1, "maria", 24, 73.50, 1.84, "GANHO DE MASSA");
CREATE TABLE alunos_academia2(
id_aluno INT NOT NULL,
nome_aluno VARCHAR(50),
idade INT,
peso DECIMAL(5, 2),
altura DECIMAL(5, 2),
objetivo VARCHAR(255)
);

INSERT INTO alunos_academia2 values 
(1, "maria", 24, 73.50, 1.84, "GANHO DE MASSA");

SELECT * FROM alunos_academia2;

ALTER TABLE alunos_academia2
ADD PRIMARY KEY (id_aluno);

RENAME TABLE alunos_academia2 TO alunos_academia_java;
ALTER TABLE alunos_academia_java MODIFY id_aluno INT NOT NULL DEFAULT 0;
SELECT * FROM alunos_academia_java;

CREATE DATABASE Academia;
USE ACADEMIA;
CREATE TABLE alunos_academia(
id_aluno INT NOT NULL,
nome_aluno VARCHAR(50),
idade INT,
peso DECIMAL(5, 2),
altura DECIMAL(5, 2),
objetivo VARCHAR(255)
);
INSERT INTO ALUNOS_ACADEMIA SELECT * FROM funcionarios.alunos_academia_java;
ALTER TABLE alunos_academia MODIFY id_aluno INT NOT NULL DEFAULT 0;
SELECT * FROM alunos_academia;
