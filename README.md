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


## Subindo Ambiente Desenvolvimento

Na pasta AmbienteDev subis docker-compose

Esse comando só é necessário uma vez, ou depois de alguma alteração no docker-compose
```shell
# docker compose build
```

Sobe um docker com postgres e outro com PgAdmin
```shell
# docker compose up
```

Para e desaloca os containers
```shell
# docker compose down
```


<br>

*******

## Ressumo de EndPoints
<br>

### Comandos para: Endereços

#### &emsp;&emsp; Cadastro Endereço
```shell
POST http://localhost:8080/endereco
{
    "rua": "Rua Sao Jose, 130",
    "numero": "200",
    "bairro": "Boa Vista",
    "cidade": "Jalapeno",
    "estado": "Santa Catarina"
}
```
#### &emsp;&emsp; Pega Todos os Endereço
```shell
GET http://localhost:8080/endereco
```
#### &emsp;&emsp; Pega Endereço Por Id
```shell
GET http://localhost:8080/endereco/{id}
```
#### &emsp;&emsp; Pega Endereço Por Rua
```shell
GET http://localhost:8080/endereco/rua/{rua}
```
#### &emsp;&emsp; Altera Endereço Por Id
```shell
PUT http://localhost:8080/endereco/{id}
{
    "rua": "Rua dos acorianos",
    "numero": "130",
    "bairro": "Bela Vista I",
    "cidade": "São José",
    "estado": "Santa Catarina"
}
```
#### &emsp;&emsp; Deleta Endereço Por Id
```shell
DELETE http://localhost:8080/endereco/{id}
```

<br>

*******

<br>

### Comandos para: Equipamentos

#### &emsp;&emsp; Cadastra Equipamento
```shell
POST http://localhost:8080/equipamento/{id}
```

#### &emsp;&emsp; Pega Todos os Equipamentos
```shell
GET http://localhost:8080/equipamento
```

#### &emsp;&emsp; Pega Equipamentos Por Id
```shell
GET http://localhost:8080/equipamento/{id}
```

#### &emsp;&emsp; Pega Equipamentos Por Nome
```shell
GET http://localhost:8080/equipamento/nome/{nome}
```

#### &emsp;&emsp; Altera Equipamento Por Id
```shell
PUT http://localhost:8080/equipamento/1
{
    "nome": "Ferro passar 1234",
    "modelo": "Andtigo",
    "potencia": "50w"
}
```

#### &emsp;&emsp; Deleta Equipamento Por Id
```shell
DELETE http://localhost:8080/equipamento/{id}
```

<br>

*******

<br>

### Comandos para: Pessoas

#### &emsp;&emsp; Cadastra Pessoa
```shell
POST http://localhost:8080/pessoa/{id_familia}
```

#### &emsp;&emsp; Altera Pessoa
```shell
PUT http://localhost:8080/pessoa/1
```

#### &emsp;&emsp; Pega Todas as Pessoas Cadastradas
```shell
GET http://localhost:8080/pessoa
```

#### &emsp;&emsp; Pega Pessoas Por Id
```shell
GET http://localhost:8080/pessoa/{id}
```

#### &emsp;&emsp; Pega Pessoa Por Nome
```shell
GET http://localhost:8080/pessoa/nome/{nome}
```

#### &emsp;&emsp; Altera Pessoa Por Id
```shell
PUT http://localhost:8080/pessoa/nome/{id}
{
    "nome": "Diego Vargas Alterado",
    "data_nascimento": "01/01/2000",
    "sexo": "masculino", 
    "codigo_cliente": "Filho",
    "relacionamento": "Filho"
}
```

#### &emsp;&emsp; Deleta Pessoa Por Id
```shell
DELETE http://localhost:8080/pessoa/{id}
```

#### &emsp;&emsp; Associa Pessoas a Endereços
```shell
POST http://localhost:8080/pessoa/{id_pessoa}/cadastraEnderecos
[2, 4, 6]
```

#### &emsp;&emsp; Lista Pessoas Por Id da Familia 
```shell
GET http://localhost:8080/pessoa/listafamilia/{id}
```

#### &emsp;&emsp; Lista Pessoas Por Nome da Familia
```shell
GET http://localhost:8080/pessoa/listafamilia/{nome_familia}
```

<br>

*******

<br>

### Comandos para: Equipamentos

#### &emsp;&emsp; Cadastra Familia
```shell
POST http://localhost:8080/familia/{nome}
```

#### &emsp;&emsp; Altera Familia Por Id
```shell
PUT http://localhost:8080/familia/{id}/{nome}
```

#### &emsp;&emsp; Pega Familia Por Nome
```shell
GET http://localhost:8080/familia/nome/{nome}
```

#### &emsp;&emsp; Pega Familia Por Id
```shell
GET http://localhost:8080/familia/{id}
```

#### &emsp;&emsp; Deleta Familia Por Id
```shell
DELETE http://localhost:8080/familia/{id}
```

#### &emsp;&emsp; Pega Todas as Familias
```shell
GET http://localhost:8080/familia
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
        "data_nascimento": "01/01/2000",
        "sexo": "masculino",
        "codigo_cliente": "12345",
        "relacionamento": "Filho"
    }

