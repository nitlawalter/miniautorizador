# Projeto Miniautorizador
Autorizador de transações de cartões de benefícios.

# Execução do Projeto

- Na raiz do projeto executar o comando: "docker compose up" para subir o container do banco mysql

- Script para criação do banco:

  create database miniautorizador;
  
  use miniautorizador;

  CREATE TABLE `miniautorizador`.`cartao` (
    `id` INT NOT NULL AUTO_INCREMENT,
    `numero_cartao` VARCHAR(45) NOT NULL,
    `senha` VARCHAR(45) NOT NULL,
    `saldo` DECIMAL(10,3) NOT NULL DEFAULT 500.00,
    PRIMARY KEY (`id`))
  COMMENT = '					';

  ALTER TABLE `miniautorizador`.`cartao`
  ADD COLUMN `versao` INT NULL AFTER `saldo`; 
  
  # Testes via SWAGGER
  
  - localhost:8080/swagger-ui/index.html
