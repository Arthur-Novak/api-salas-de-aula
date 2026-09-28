# API de Cadastro de Salas — UFSM

API REST para cadastrar e administrar salas de aula. Projeto desenvolvido como atividade de Programação Orientada a Objetos para Web 2.

## Tecnologias

- Java 17 ou superior e Spring Boot 4.1.1
- Maven
- Spring Web para os endpoints REST
- Spring Data JPA e Hibernate para acessar o banco
- PostgreSQL para armazenar as salas
- Flyway para criar a tabela e inserir os dados iniciais
- Bean Validation para validar os dados recebidos
- Lombok para reduzir código repetitivo na entidade
- springdoc OpenAPI e Swagger UI para documentar e testar a API

## Dados de uma sala

| Campo | Regra |
| --- | --- |
| `id` | Identificador gerado pelo banco |
| `nome` | Obrigatório; de 3 a 50 caracteres |
| `codigo` | Obrigatório; único; até 10 caracteres (coluna `codigo_sala`) |
| `capacidadeAlunos` | Obrigatório; entre 10 e 200 |
| `quantidadeComputadores` | Opcional; quando informado, maior ou igual a 0 |
| `anoConstrucao` | Obrigatório; entre 1960 e 2026 |
| `area` | Obrigatório; maior que 0; representado por `BigDecimal` em m² |
| `situacao` | `DISPONIVEL`, `EM_REFORMA` ou `INTERDITADA` |

## Como executar na sua máquina

### 1. Pré-requisitos

Instale Java 17 ou superior e PostgreSQL. O projeto usa Maven; se o repositório incluir o Maven Wrapper (`mvnw` e `mvnw.cmd`), você pode usá-lo sem instalar Maven separadamente.

### 2. Obtenha o projeto

Clone o repositório pelo Git ou baixe o ZIP pelo GitHub. Depois abra a pasta do projeto no IntelliJ IDEA como projeto Maven e aguarde o download das dependências.

### 3. Crie um banco vazio

No pgAdmin, crie um banco de dados chamado `salas_ufsm` (ou use outro nome e ajuste a URL abaixo). **Não crie a tabela manualmente:** o Flyway fará isso quando a aplicação iniciar.

### 4. Configure a conexão

Confira `src/main/resources/application.properties`. Um exemplo de configuração é:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/salas_ufsm
spring.datasource.username=${DB_USER:postgres}
spring.datasource.password=${DB_PASSWORD}
spring.jpa.hibernate.ddl-auto=validate
```

Configure `DB_PASSWORD` com a senha do seu PostgreSQL nas variáveis de ambiente da execução no IntelliJ (**Run → Edit Configurations → Environment variables**). Se seu usuário for diferente de `postgres`, configure também `DB_USER`. Ajuste a porta `5432` se sua instalação usar outra. Não publique senhas reais no GitHub.

### 5. Inicie a aplicação

Execute a classe principal do Spring Boot pelo IntelliJ. Alternativamente, na pasta do projeto, use o Maven Wrapper:

```bash
# Windows
mvnw.cmd spring-boot:run

# Linux/macOS
./mvnw spring-boot:run
```

Na primeira inicialização, o Flyway executa as migrations da pasta `src/main/resources/db/migration`:

- **V1:** cria a tabela `salas`, incluindo a restrição `UNIQUE` de `codigo_sala`.
- **V2:** insere três salas de exemplo, uma para cada situação.

As migrations aplicadas são registradas no banco e não são executadas novamente nos próximos inícios. Se você alterar o banco depois, crie uma nova migration (`V3__...sql`) em vez de editar uma versão que já foi executada.

### 6. Acesse e teste a API

Com a aplicação em execução, abra [Swagger UI](http://localhost:8080/swagger-ui.html). Se você configurou outra porta para o servidor, substitua `8080` na URL.

| Método | Rota | Ação | Sucesso |
| --- | --- | --- | --- |
| `POST` | `/salas` | Cadastrar sala | `201 Created` |
| `GET` | `/salas` | Listar todas as salas | `200 OK` |
| `GET` | `/salas/{id}` | Buscar sala por ID | `200 OK` |
| `PUT` | `/salas/{id}` | Atualizar sala por ID | `200 OK` |
| `DELETE` | `/salas/{id}` | Excluir sala por ID | `204 No Content` |

Para testar o cadastro, abra `POST /salas` no Swagger, clique em **Try it out**, envie o JSON e clique em **Execute**:

```json
{
  "nome": "Sala de Estudos",
  "codigo": "SALA404",
  "capacidadeAlunos": 25,
  "quantidadeComputadores": 0,
  "anoConstrucao": 2015,
  "area": 42.50,
  "situacao": "EM_REFORMA"
}
```

Para atualizar, informe o `id` em `PUT /salas/{id}` e envie todos os campos da sala no JSON. Você pode conferir os IDs e as três salas iniciais em `GET /salas`.

## Regra de exclusão

Salas com situação `DISPONIVEL` não podem ser excluídas. Para as demais situações, `DELETE /salas/{id}` remove a sala e retorna `204 No Content`.
