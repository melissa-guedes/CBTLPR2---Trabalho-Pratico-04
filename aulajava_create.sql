IF DB_ID('aulajava') IS NOT NULL
BEGIN
    ALTER DATABASE aulajava SET SINGLE_USER WITH ROLLBACK IMMEDIATE;
    DROP DATABASE aulajava;
END
GO

CREATE DATABASE aulajava;
GO

USE aulajava;
GO

CREATE TABLE tbcargos (
    cd_cargo SMALLINT NOT NULL PRIMARY KEY,
    ds_cargo CHAR(20) NULL
);

CREATE TABLE tbfuncs (
    cod_func DECIMAL(9,0) NOT NULL PRIMARY KEY,
    nome_func CHAR(30) NULL,
    sal_func MONEY NULL,
    cod_cargo SMALLINT NULL,

    CONSTRAINT FK_tbfuncs_tbcargos 
        FOREIGN KEY (cod_cargo) REFERENCES tbcargos(cd_cargo)
);

INSERT INTO tbcargos (cd_cargo, ds_cargo) VALUES
(1, 'Administrativo'),
(2, 'Financeiro'),
(3, 'TI'),
(4, 'Comercial'),
(5, 'Diretoria');

INSERT INTO tbfuncs (cod_func, nome_func, sal_func, cod_cargo) VALUES
(1, 'Marcelo                     ', 2000.00, 1),
(2, 'Maria                       ', 3500.50, 3),
(3, 'Joao                        ', 1800.00, 2),
(4, 'Ana                         ', 4200.00, 5);

USE aulajava;
GO


-- 2. Seleciona e une os dados de funcionários e cargos
SELECT
    f.cod_func AS Codigo,
    f.nome_func AS Nome_Funcionario, -- REMOVIDO TRIM()
    f.sal_func AS Salario,
    c.ds_cargo AS Cargo             -- REMOVIDO TRIM()
FROM
    tbfuncs f
INNER JOIN
    tbcargos c ON f.cod_cargo = c.cd_cargo;
GO