
# Sea Tecnologia - Desafio Backend opção 1

Este projeto é uma API REST desenvolvida para solucionar os desafios propostos.<br>
É possivel acessar o frontend da aplicação [aqui](https://github.com/GabrielFigueiredoL/DesafioSeaFrontend).

## Stack utilizada
- Java 8
- Spring boot
- Spring Security
- Validation
- Lombok
- Java-JWT
- Flyway
- Postgres


## Funcionalidades

- Operações CRUD com cliente
- Autorização e autenticação
- Gerenciamento de erros
- Validação de requisições

## Rodando localmente

Certifique-se de ter instalado o docker e possuir a versão 8 do java

Clone o projeto

```bash
  git clone https://github.com/GabrielFigueiredoL/DesafioSeaBackend
```

Entre no diretório do projeto

```bash
  cd DesafioSeaBackend
```

Inicie o postgres

```bash
  docker-compose up -d
```

Instale as dependências

```bash
   ./mvnw clean package -DskipTests
```

Inicie o servidor
```bash
   java -jar target/seaApi-0.0.1-SNAPSHOT.jar 
```



## Documentação da API

### Acessar aplicação

```http
  POST /login
```
Parâmentros da requisição
| **Campo** | **Tipo** | **Obrigatório** | **Formato e Validação**                                                                           | **Descrição**                           |
| :-------- | :------- | :-------------- | :------------------------------------------------------------------------------------------------ | :-------------------------------------- |
| `login`    | `String` | Sim             | login cadastrado | Login utilizado para acessar a aplicação  
| `password`    | `String` | Sim             | senha cadastrada| Senha correspondente ao login

JSON de exemplo
```json
{
  "login": "admin",
  "senha": "123qwe123"
}
```
Como resposta, o endpoint responde com:

- `200 OK` -> Corpo da requisição: role do usuário
  - O usuário foi logado com sucesso
  - A autorização é enviada em forma de cookies HttpOnly
- `403 Forbidden` -> Sem corpo
  - Login ou senha errados

#### Adicionar clientes

```http
  POST /clients
```
Parâmetros da requisição
| **Parâmetro** | **Tipo**            | **Obrigatório** | **Formato e Validação**                                                                                                                                       | **Descrição**                                             |
| :------------ | :------------------ | :-------------- | :------------------------------------------------------------------------------------------------------------------------------------------------------------ | :-------------------------------------------------------- |
| `name`        | `String`            | Sim             | - **Tamanho**: 3 a 100 caracteres<br>- **Formato**: Letras, números e espaços<br>- **Exemplo**: "Fulano de Tal"                                                  | Nome completo do cliente.                                 |
| `cpf`         | `String`            | Sim             | - **Tamanho**: 11 caracteres numéricos (sem formatação)<br>- **Exemplo**: "12345678909"                                                                       | CPF do cliente.                                           |
| `address`     | `Address` | Sim             | - **Formato**: Objeto com os seguintes campos:<br>  - `cep`<br>  - `street`<br>  - `district`<br>  - `city`<br>  - `stateCode`<br>  - `complement` (opcional) | Endereço completo do cliente.                             |
| `emails`      | `List<Email>`    | Sim             | - **Tamanho**: 1 ou mais e-mails válidos<br>- **Formato**: \[email válido] | Lista de e-mails do cliente. Cada e-mail deve ser válido. |
| `phones`      | `List<Phone>`    | Sim             | - **Tamanho**: 1 ou mais telefones válidos<br>- **Formato**: Cada telefone deve conter `type` e `number` válidos                                              | Lista de telefones do cliente, com tipo e número.         |
 |

 Parâmentros de Address
 | **Campo**    | **Tipo** | **Obrigatório** | **Formato e Validação**                                                      | **Descrição**                                        |
| :----------- | :------- | :-------------- | :--------------------------------------------------------------------------- | :--------------------------------------------------- |
| `cep`        | `String` | Sim             | - **Formato**: 8 dígitos numéricos (sem traços)<br>- **Exemplo**: "12345678" | Código de Endereçamento Postal (CEP) do cliente.     |
| `street`     | `String` | Sim             | - **Tamanho**: 1 a 200 caracteres<br>- **Exemplo**: "Rua A"      | Nome do logradouro (rua, avenida, etc.).             |
| `district`   | `String` | Sim             | - **Tamanho**: 1 a 100 caracteres<br>- **Exemplo**: "Bairro B"                 | Nome do bairro ou distrito.                          |
| `city`       | `String` | Sim             | - **Tamanho**: 1 a 100 caracteres<br>- **Exemplo**: "Cidade C"              | Nome da cidade.                                      |
| `stateCode`  | `String` | Sim             | - **Formato**: 2 caracteres maiúsculos (UF)<br>- **Exemplo**: "CC"           | Unidade Federativa (Estado).                         |
| `complement` | `String` | Não             | - **Tamanho**: 0 a 100 caracteres (opcional)<br>- **Exemplo**: "Casa 1"    | Complemento do endereço, como número do apartamento. |

Parâmentros de Email
| **Campo** | **Tipo** | **Obrigatório** | **Formato e Validação**                                                                             | **Descrição**      |
| :-------- | :------- | :-------------- | :-------------------------------------------------------------------------------------------------- | :----------------- |
| `email`   | `String` | Sim             | - **Formato**: Email válido<br>- **Exemplo**: "[fulanodetal@email.com](mailto:fulanodetal@email.com)" | E-mail do cliente. |


Parâmentros de Phone
| **Campo** | **Tipo** | **Obrigatório** | **Formato e Validação**                                                                           | **Descrição**                           |
| :-------- | :------- | :-------------- | :------------------------------------------------------------------------------------------------ | :-------------------------------------- |
| `type`    | `String` | Sim             | - **Tipos válidos**: "celular", "residencial", "comercial"                                   | Tipo de telefone (Celular, Residencial, Comercial.). |
| `number`  | `String` | Sim             | - **Formato**: Número de telefone válido (com código de área)<br>- **Exemplo**: "61991234567" | Número do telefone.                     |

JSON de exemplo
```json
{
  "name": "Fulano de Tal",
  "cpf": "12345678909",
  "address": {
    "cep": "12345678",
    "street": "Logradouro A",
    "district": "Bairro B",
    "city": "Cidade C",
    "stateCode": "CC",
    "complement": "Casa 1"
  },
  "emails": [
    {
      "email": "fulanodetal@email.com"
    }
  ],
  "phones": [
    {
      "type": "celular",
      "number": "61991234567"
    }
  ]
}
```
Como resposta, o endpoint responde com:

- `201 Created` -> Corpo da requisição: Cliente com id
  - O cliente foi criado, validado e registrado
- `400 Bad Request` -> Corpo da requisição: Descrição(s) do erro(s)
  - A API não compreendeu a requisição do cliente (JSON com argumentos faltando e/ou tipo inválido e/ou escritos de maneira errada).

#### Exibir todos os clientes

```http
  GET /clients
```
Como resposta, o endpoint responde com:

- `200 OK` -> Corpo da requisição: Lista de clientes

#### Exibir cliente por id

```http
  GET /clients/:id
```
Como resposta, o endpoint responde com:

- `200 OK` -> Corpo da requisição: Cliente
- `400 Bad Request` -> Corpo da requisição: Sem corpo

```http
  DELETE /clients/:id
```
Como resposta, o endpoint responde com:

- `200 OK` -> Corpo da requisição: Sem corpo
- `400 Bad Request` -> Corpo da requisição: Sem corpo

```http
  PUT /clients/:id
```
Parâmetros da requisição
| **Parâmetro** | **Tipo**            | **Obrigatório** | **Formato e Validação**                                                                                                                                       | **Descrição**                                             |
| :------------ | :------------------ | :-------------- | :------------------------------------------------------------------------------------------------------------------------------------------------------------ | :-------------------------------------------------------- |
| `name`        | `String`            | Sim             | - **Tamanho**: 3 a 100 caracteres<br>- **Formato**: Letras, números e espaços<br>- **Exemplo**: "Fulano de Tal"                                                  | Nome completo do cliente.                                 |
| `cpf`         | `String`            | Sim             | - **Tamanho**: 11 caracteres numéricos (sem formatação)<br>- **Exemplo**: "12345678909"                                                                       | CPF do cliente.                                           |
| `address`     | `Address` | Sim             | - **Formato**: Objeto com os seguintes campos:<br>  - `cep`<br>  - `street`<br>  - `district`<br>  - `city`<br>  - `stateCode`<br>  - `complement` (opcional) | Endereço completo do cliente.                             |
| `emails`      | `List<Email>`    | Sim             | - **Tamanho**: 1 ou mais e-mails válidos<br>- **Formato**: \[email válido] | Lista de e-mails do cliente. Cada e-mail deve ser válido. |
| `phones`      | `List<Phone>`    | Sim             | - **Tamanho**: 1 ou mais telefones válidos<br>- **Formato**: Cada telefone deve conter `type` e `number` válidos                                              | Lista de telefones do cliente, com tipo e número.         |
 |

 Parâmentros de Address
 | **Campo**    | **Tipo** | **Obrigatório** | **Formato e Validação**                                                      | **Descrição**                                        |
| :----------- | :------- | :-------------- | :--------------------------------------------------------------------------- | :--------------------------------------------------- |
| `cep`        | `String` | Sim             | - **Formato**: 8 dígitos numéricos (sem traços)<br>- **Exemplo**: "12345678" | Código de Endereçamento Postal (CEP) do cliente.     |
| `street`     | `String` | Sim             | - **Tamanho**: 1 a 200 caracteres<br>- **Exemplo**: "Rua A"      | Nome do logradouro (rua, avenida, etc.).             |
| `district`   | `String` | Sim             | - **Tamanho**: 1 a 100 caracteres<br>- **Exemplo**: "Bairro B"                 | Nome do bairro ou distrito.                          |
| `city`       | `String` | Sim             | - **Tamanho**: 1 a 100 caracteres<br>- **Exemplo**: "Cidade C"              | Nome da cidade.                                      |
| `stateCode`  | `String` | Sim             | - **Formato**: 2 caracteres maiúsculos (UF)<br>- **Exemplo**: "CC"           | Unidade Federativa (Estado).                         |
| `complement` | `String` | Não             | - **Tamanho**: 0 a 100 caracteres (opcional)<br>- **Exemplo**: "Casa 1"    | Complemento do endereço, como número do apartamento. |

Parâmentros de Email
| **Campo** | **Tipo** | **Obrigatório** | **Formato e Validação**                                                                             | **Descrição**      |
| :-------- | :------- | :-------------- | :-------------------------------------------------------------------------------------------------- | :----------------- |
| `email`   | `String` | Sim             | - **Formato**: Email válido<br>- **Exemplo**: "[fulanodetal@email.com](mailto:fulanodetal@email.com)" | E-mail do cliente. |
| `id`  | `String` | Sim             | - **Formato**: string<br>- **Exemplo**: "2" | id do campo   

Parâmentros de Phone
| **Campo** | **Tipo** | **Obrigatório** | **Formato e Validação**                                                                           | **Descrição**                           |
| :-------- | :------- | :-------------- | :------------------------------------------------------------------------------------------------ | :-------------------------------------- |
| `type`    | `String` | Sim             | - **Tipos válidos**: "celular", "residencial", "comercial"                                   | Tipo de telefone (Celular, Residencial, Comercial.). |
| `number`  | `String` | Sim             | - **Formato**: Número de telefone válido (com código de área)<br>- **Exemplo**: "61991234567" | Número do telefone.                     |
| `id`  | `String` | Sim             | - **Formato**: string<br>- **Exemplo**: "2" | id do campo                     |

JSON de exemplo
```json
{
  "name": "Fulano de Tal",
  "cpf": "12345678909",
  "address": {
    "cep": "12345678",
    "street": "Logradouro A",
    "district": "Bairro B",
    "city": "Cidade C",
    "stateCode": "CC",
    "complement": "Casa 1"
  },
  "emails": [
    {
      "id": "2"
      "email": "fulanodetal@email.com"
    }
  ],
  "phones": [
    {
      "id": "2"
      "type": "celular",
      "number": "61991234567"
    }
  ]
}

```
