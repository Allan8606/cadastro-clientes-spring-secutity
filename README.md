# 🔐 API de Cadastro de Clientes (Spring Security)

Este projeto é uma API RESTful desenvolvida em **Java** com **Spring Boot**, focada no aprendizado e implementação de Autenticação e Autorização robustas.

O sistema utiliza **Tokens JWT (JSON Web Token)** para autenticação *stateless* e controla o acesso aos recursos baseado no perfil do usuário (**ADMIN** ou **BASIC**).

## 🚀 Tecnologias Utilizadas

* **Java 17+**
* **Spring Boot 3**
* **Spring Security 6** (Proteção da API)
* **Auth0 Java-JWT** (Geração e Validação de Tokens)
* **Spring Data JPA** (Persistência de dados)
* **PostgreSQL** (Banco de dados)
* **Bean Validation** (Validação de DTOs)
* **Maven** (Gerenciador de dependências)

---

## ⚙️ Funcionalidades de Segurança

O projeto implementa um fluxo de segurança completo:

1.  **Criptografia de Senha:** As senhas são salvas no banco usando Hash **BCrypt**. Nenhuma senha é salva em texto puro.
2.  **Autenticação Stateless:** Login via `/login` retorna um **Token JWT**.
3.  **Filtro de Segurança (Catraca):** Um `SecurityFilter` intercepta todas as requisições para validar o token antes de deixar a requisição chegar ao Controller.
4.  **Controle de Acesso (RBAC):**
    * **ADMIN:** Pode cadastrar e excluir clientes.
    * **BASIC:** Pode apenas listar e visualizar dados.
    * **PÚBLICO:** Login e Cadastro de novos funcionários.
5.  **Tratamento de Erros Global:** Respostas de erro (400, 403, 404) formatadas em JSON amigável e limpo, sem expor *Stack Trace*.

---

## 📂 Estrutura do Projeto

A organização segue as boas práticas de separação de responsabilidades:
```text
src/main/java/com/allan_dev/cadastroclientes
├── config/             # Configurações gerais (Beans, Swagger, etc)
├── controller/         # Pontos de entrada da API (Endpoints/Rotas)
├── dto/                # Objetos de Transferência de Dados (Request/Response)
├── entity/             # Entidades do Banco de Dados (Mapeamento JPA)
├── infra/              # Infraestrutura e utilitários transversais
│   ├── security/       # Segurança (Filtros, JWT, TokenService, Configs)
│   └── exception/      # Tratamento Global de Erros (Exception Handler)
├── repository/         # Interfaces de acesso ao banco (Spring Data JPA)
└── service/            # Camada de Negócio (Regras e Lógica)
```
## 3. Rodando a Aplicação

No terminal, na raiz do projeto:

```bash
mvn spring-boot:run
```

# 🚀 Endpoints da API

Aqui estão as principais rotas para teste no **Postman**.

---

### 🔓 Públicos (Não precisa de Token)

| Método | Rota        | Descrição                                     |
|--------|-------------|-----------------------------------------------|
| POST   | `/login`    | Recebe email/senha e devolve o Token JWT.     |
| POST   | `/cadastrar`| Cria um novo usuário/funcionário no sistema.  |

---

### 🔒 Protegidos (Requer Token Bearer)

| Método | Rota             | Permissão          | Descrição              |
|--------|------------------|--------------------|------------------------|
| GET    | `/cliente`      | `ADMIN` ou `BASIC` | Lista os clientes.     |
| GET    | `/cliente/{id}` | `ADMIN` ou `BASIC` | Detalha um cliente.    |
| POST   | `/cliente`      | Somente `ADMIN`    | Cadastra um cliente.   |
| DELETE | `/cliente/{id}` | Somente `ADMIN`    | Exclui um cliente.     |

---

## 🧪 Como Testar (Fluxo Sugerido)

### 1️⃣ Cadastrar Funcionário

Faça um:

```http
POST /cadastrar
```

Crie um usuário com perfil **ADMIN**.

---

### 2️⃣ Login

```http
POST /login
```

Copie o **token** retornado.

---

### 3️⃣ Acessar Rotas Protegidas

```http
GET /cliente
```

No Postman:

- Vá em **Authorization**
- Selecione `Bearer Token`
- Cole o token no campo.

---

### 4️⃣ Testar Erros

Busque um ID inexistente para validar o erro **404**:

```http
GET /cliente/9999
```

---

## 👨‍💻 Autor

Desenvolvido por **Allan Isaac** durante estudos de aprofundamento em  
**Java Backend** e **Spring Security**.




