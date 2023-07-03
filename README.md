# PosTech Challenge  Aquitetura e Desenvolvimento Java.
**InteligenciaEletricaAPI**

![Java](https://img.shields.io/badge/java-%23ED8B00.svg?style=for-the-badge&logo=openjdk&logoColor=white)
![Spring](https://img.shields.io/badge/spring-%236DB33F.svg?style=for-the-badge&logo=spring&logoColor=white)
![Badge em Desenvolvimento](http://img.shields.io/static/v1?label=STATUS&message=EM%20DESENVOLVIMENTO&color=GREEN&style=for-the-badge)


*******

# Índice

* [Comandos cUrl para Testes Manuais](#Comandos-cUrl-para-Testes-Manuais)
* [Ressumo de EndPoints](#Ressumo-de-EndPoints)
* [JSONs Exemplo](#JSONs-Exemplo)

*******



## Funcionalidades

:heavy_check_mark: Cadastrar

:heavy_check_mark: Consultar Todos

:heavy_check_mark: Consultar Por Id

:heavy_check_mark: Consultar Por Nome

:heavy_check_mark: Deletar Por Id

:heavy_check_mark: Alterar Por Id

:heavy_check_mark: Pesquisar Por Id

:heavy_check_mark: Pesquisar Por Nome ou Rua;

*******

<br>


## :page_with_curl: Comandos cUrl para Testes Manuais

<br>

## Comandos para: Endereços

### Cadastro Endereço
```shell
curl -i -X POST http://localhost:8080/endereco  -H "Content-Type:application/json" -d '{"rua": "Rua Cadastro novo Endereco", "numero": "130", "bairro": "Bela Vista I", "cidade": "São José", "estado": "Santa Catarina"}'
```

### Altera Endereço
```shell
curl -i -X PUT http://localhost:8080/endereco/1  -H "Content-Type:application/json" -d '{"rua": "Nova Rua", "numero": "200", "bairro": "Novo Bairro", "cidade": "Nova Cidade", "estado": "Novo Estado"}'
```

### Pega Todos os Endereços Cadastrados
```shell
curl -i -X GET http://localhost:8080/endereco
```

### Pega Endereços Por Id
```shell
curl -i -X GET http://localhost:8080/endereco/1
```

### Pega Endereco Por Rua
```shell
curl -i -X GET http://localhost:8080/endereco/rua/Nova%20Rua
```

### Deleta Endereco Por Id
```shell
curl -i -X DELETE http://localhost:8080/endereco/1
```

<br>

*******

<br>

## Comandos para: Equipamentos

### Cadastra Equipamento
```shell
curl -i -X POST http://localhost:8080/equipamento  -H "Content-Type:application/json" -d '{"nome": "Novo Eletro 1","modelo": "Antigo 1","potencia": "50W"}'
```

### Altera Equipamento
```shell
curl -i -X PUT http://localhost:8080/equipamento/1  -H "Content-Type:application/json" -d '{"nome": "Eletro 1 Altrado","modelo": "Novo","potencia": "230W"}'
```

### Pega Todos os Equipamentos Cadastrados
```shell
curl -i -X GET http://localhost:8080/equipamento
```

### Pega Equipamentos Por Id
```shell
curl -i -X GET http://localhost:8080/equipamento/1
```

### Pega Equipamentos Por Nome
```shell
curl -i -X GET http://localhost:8080/equipamento/nome/Eletro%201%20Altrado
```

### Deleta Equipamento Por Id
```shell
curl -i -X DELETE http://localhost:8080/equipamento/1
```

<br>

*******

<br>

## Comandos para: Pessoas

### Cadastra Pessoa
```shell
curl -i -X POST http://localhost:8080/pessoa  -H "Content-Type:application/json" -d '{"nome": "Nova Pessoa","dataNascimento": "01/01/2000","sexo": "masculino", "parentesco": "Filho"}'
```

### Altera Pessoa
```shell
curl -i -X PUT http://localhost:8080/pessoa/1  -H "Content-Type:application/json" -d '{"nome": "Nome Alterado","dataNascimento": "02/02/2002","sexo": "feminino", "parentesco": "Filha"}'
```

### Pega Todas as Pessoas Cadastradas
```shell
curl -i -X GET http://localhost:8080/pessoa
```

### Pega Pessoas Por Id
```shell
curl -i -X GET http://localhost:8080/pessoa/1
```

### Pega Pessoa Por Nome
```shell
curl -i -X GET http://localhost:8080/pessoa/nome/Nome%20Alterado
```

### Deleta Pessoa Por Id
```shell
curl -i -X DELETE http://localhost:8080/pessoa/1
```

<br>

*******

<br>

## Ressumo de EndPoints

## Comandos para: Endereços

### Cadastro Endereço
```shell
POST http://localhost:8080/endereco
```

### Altera Endereço
```shell
PUT http://localhost:8080/endereco/1
```

### Pega Todos os Endereços Cadastrados
```shell
GET http://localhost:8080/endereco
```

### Pega Endereços Por Id
```shell
GET http://localhost:8080/endereco/1
```

### Pega Endereco Por Rua
```shell
GET http://localhost:8080/endereco/rua/Nova%20Rua
```

### Deleta Endereco Por Id
```shell
DELETE http://localhost:8080/endereco/1
```

<br>

*******

<br>

## Comandos para: Equipamentos

### Cadastra Equipamento
```shell
POST http://localhost:8080/equipamento
```

### Altera Equipamento
```shell
PUT http://localhost:8080/equipamento/1
```

### Pega Todos os Equipamentos Cadastrados
```shell
GET http://localhost:8080/equipamento
```

### Pega Equipamentos Por Id
```shell
GET http://localhost:8080/equipamento/1
```

### Pega Equipamentos Por Nome
```shell
GET http://localhost:8080/equipamento/nome/Eletro%201%20Altrado
```

### Deleta Equipamento Por Id
```shell
DELETE http://localhost:8080/equipamento/1
```

<br>

*******

<br>

## Comandos para: Pessoas

### Cadastra Pessoa
```shell
POST http://localhost:8080/pessoa
```

### Altera Pessoa
```shell
PUT http://localhost:8080/pessoa/1
```

### Pega Todas as Pessoas Cadastradas
```shell
GET http://localhost:8080/pessoa
```

### Pega Pessoas Por Id
```shell
GET http://localhost:8080/pessoa/1
```

### Pega Pessoa Por Nome
```shell
GET http://localhost:8080/pessoa/nome/Nome%20Alterado
```

### Deleta Pessoa Por Id
```shell
DELETE http://localhost:8080/pessoa/1
```

<br>

*******

<br>




## JSONs Exemplo

### Json Endereco
    {
        "rua": "Rua Sao Jose, 130 3",
        "numero": "200",
        "bairro": "Boa Vista",
        "cidade": "Jalapeno",
        "estado": "Santa Catarina"
    }

### Json Equipamento
    {
        "nome": "Ferro Passar 3",
        "modelo": "Antigo",
        "potencia": "50W"
    }

### Json Pessoa
    {
        "nome": "Nome do segundo",
        "dataNascimento": "01/01/2000",
        "sexo": "masculino",
        "parentesco": "Filho"
    }

