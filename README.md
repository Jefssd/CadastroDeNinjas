# CadastroDeNinjas

API REST desenvolvida em **Java + Spring Boot** para o cadastro e gerenciamento de **Ninjas** e **Missões**, incluindo o vínculo entre eles (cada ninja pode estar associado a uma missão).

## Sumário

- [Visão geral](#visão-geral)
- [Tecnologias e dependências](#tecnologias-e-dependências)
- [Modelo de arquitetura](#modelo-de-arquitetura)
- [Modelo de dados](#modelo-de-dados)
- [Configuração e execução](#configuração-e-execução)
- [Endpoints da API](#endpoints-da-api)
- [Tratamento de erros](#tratamento-de-erros)
- [Estrutura de pastas](#estrutura-de-pastas)

## Visão geral

O projeto expõe duas entidades principais:

- **Ninja**: possui nome, e-mail e idade, podendo estar vinculado a uma missão.
- **Missão**: possui nome e nível de dificuldade, podendo ter vários ninjas associados a ela.

Cada entidade possui seu próprio CRUD completo (criar, listar, buscar por ID, atualizar e excluir), exposto via endpoints REST que retornam e recebem JSON.

## Tecnologias e dependências

| Dependência | Finalidade |
|---|---|
| **Java 21** | Linguagem e versão do JDK do projeto. |
| **Spring Boot 4.1.0** (`spring-boot-starter-parent`) | Framework base que gerencia as versões e o autoconfigure da aplicação. |
| **spring-boot-starter-webmvc** | Cria a camada web (MVC) e expõe os endpoints REST via `@RestController`. |
| **spring-boot-starter-data-jpa** | Abstração de persistência baseada em JPA/Hibernate, usada pelos repositórios (`JpaRepository`). |
| **spring-boot-starter-validation** | Habilita as anotações de validação de Bean Validation (`@NotBlank`, `@Email`, `@Min`, `@Valid`) nos modelos e nos controllers. |
| **Lombok** | Gera automaticamente getters, setters, construtores e `toString`/`equals`/`hashCode` (`@Data`, `@NoArgsConstructor`, `@AllArgsConstructor`), reduzindo código repetitivo nas entidades. |
| **H2 Database** (escopo `runtime`) | Banco de dados em memória, disponível para testes/execução local sem depender de um banco externo. |
| **Oracle JDBC Driver (`ojdbc11`)** | Driver usado para conectar a aplicação a um banco de dados **Oracle**, que é o banco configurado por padrão em `application.properties`. |
| **spring-boot-starter-webmvc-test** (escopo `test`) | Suporte a testes de integração/unitários da camada web. |
| **spring-boot-maven-plugin** | Plugin do Maven que empacota a aplicação como um executável (`.jar`) e permite rodá-la via `mvn spring-boot:run`. |

O gerenciamento de dependências e o build são feitos via **Maven** (`pom.xml`).

## Modelo de arquitetura

O projeto segue uma **arquitetura em camadas (layered architecture)**, padrão comum em aplicações Spring Boot, separando responsabilidades por pacote:

```
Controller  →  Service  →  Repository  →  Banco de Dados
   (HTTP)      (regras)      (JPA)
```

- **Controller** (`NinjaController`, `MissoesController`)
  Camada de entrada da API. Recebe as requisições HTTP, valida o corpo da requisição (`@Valid`) e delega o processamento para a camada de serviço, devolvendo a resposta em JSON.

- **Service** (`NinjaService`, `MissoesService`)
  Camada de regras de negócio. Orquestra as operações de CRUD, aplica validações adicionais (como checar existência antes de atualizar/excluir) e lança exceções de negócio quando necessário.

- **Repository** (`NinjaRepository`, `MissoesRepository`)
  Camada de acesso a dados (DAO), implementada como interfaces que estendem `JpaRepository`. O Spring Data JPA gera as implementações automaticamente, fornecendo métodos de CRUD prontos sem necessidade de SQL manual.

- **Model / Entity** (`NinjaModel`, `MissoesModel`)
  Representam as tabelas do banco de dados via anotações JPA (`@Entity`, `@Table`, `@Id`, etc.), além de conterem as regras de validação de campo (Bean Validation).

- **Exceptions** (`exceptions/*`)
  Camada transversal de tratamento de erros. Contém exceções de negócio customizadas e um `@RestControllerAdvice` (`GlobalExceptionHandler`) que intercepta essas exceções (e outras genéricas) e converte para respostas HTTP padronizadas.

Essa separação segue o princípio de **injeção de dependência via construtor**, usado em todos os `Controller`s e `Service`s (sem uso de `@Autowired` em campo), o que facilita testes e mantém o baixo acoplamento entre camadas.

### Diagrama de pacotes

```
dev.jef.CadastroDeNinjas
├── CadastroDeNinjasApplication.java   → classe principal (@SpringBootApplication)
├── ninjas/
│   ├── NinjaController.java           → endpoints REST /ninjas
│   ├── NinjaService.java              → regras de negócio dos ninjas
│   ├── NinjaRepository.java           → acesso a dados (JpaRepository)
│   └── NinjaModel.java                → entidade JPA "tb_cadastro"
├── missoes/
│   ├── MissoesController.java         → endpoints REST /missoes
│   ├── MissoesService.java            → regras de negócio das missões
│   ├── MissoesRepository.java         → acesso a dados (JpaRepository)
│   └── MissoesModel.java              → entidade JPA "tab_missoes"
exceptions/
├── ErroResposta.java                  → DTO de resposta de erro
├── GlobalExceptionHandler.java        → tratamento global de exceções (@RestControllerAdvice)
├── NinjaNaoEncontradoException.java   → exceção 404 de ninja
└── MissaoNaoEncontradaException.java  → exceção 404 de missão
```

> **Observação:** o pacote `exceptions` está fora de `dev.jef.CadastroDeNinjas` (na raiz de `src/main/java`). Como o `@SpringBootApplication` faz *component scan* a partir do pacote da classe principal, para que o `GlobalExceptionHandler` seja detectado como bean é necessário que ele esteja no mesmo pacote-raiz da aplicação ou que um scan adicional seja configurado.

## Modelo de dados

### Ninja (`tb_cadastro`)

| Campo | Tipo | Regras |
|---|---|---|
| `id` | `Long` | Chave primária, gerada automaticamente (`IDENTITY`). |
| `nome` | `String` | Obrigatório (`@NotBlank`). |
| `email` | `String` | Obrigatório e deve ser um e-mail válido (`@NotBlank`, `@Email`). |
| `idade` | `int` | Deve ser maior que zero (`@Min(1)`). |
| `missoes` | `MissoesModel` | Relação `@ManyToOne` — um ninja pode estar associado a apenas uma missão por vez (FK `missoes_id`). |

### Missão (`tab_missoes`)

| Campo | Tipo | Regras |
|---|---|---|
| `id` | `Long` | Chave primária, gerada automaticamente (`IDENTITY`). |
| `nome` | `String` | Obrigatório (`@NotBlank`). |
| `dificuldade` | `String` | Obrigatória (`@NotBlank`). |
| `ninja` | `List<NinjaModel>` | Relação `@OneToMany` (mapeada pelo campo `missoes` em `NinjaModel`) — uma missão pode ter vários ninjas. Anotado com `@JsonIgnore` para evitar loop infinito de serialização JSON. |

**Relacionamento:** `Ninja` (N) —— (1) `Missão`, ou seja, muitos ninjas podem estar associados à mesma missão, mas cada ninja participa de apenas uma missão por vez.

## Configuração e execução

### Pré-requisitos

- Java 21 (JDK)
- Maven (ou usar o Maven Wrapper incluso: `mvnw` / `mvnw.cmd`)
- Um banco de dados Oracle acessível (padrão da aplicação), **ou** ajuste a configuração para usar o H2 em memória.

### Configuração do banco de dados

O arquivo `src/main/resources/application.properties` já vem configurado para Oracle:

```properties
spring.datasource.url=jdbc:oracle:thin:@//localhost:1521/FREEPDB1
spring.datasource.username=ninjas_app
spring.datasource.password=ninjas123
spring.datasource.driver-class-name=oracle.jdbc.OracleDriver

spring.jpa.database-platform=org.hibernate.dialect.OracleDialect
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
```

- `spring.jpa.hibernate.ddl-auto=update`: o Hibernate cria/atualiza automaticamente as tabelas (`tb_cadastro`, `tab_missoes`) com base nas entidades, sem necessidade de scripts SQL manuais.
- `spring.jpa.show-sql=true`: exibe no console as queries SQL geradas.

> ⚠️ As credenciais acima estão hardcoded no arquivo de propriedades. Para ambientes reais, recomenda-se externalizar essas informações (variáveis de ambiente, `application-{profile}.properties` ou um cofre de segredos).

Caso não tenha um Oracle disponível, é possível usar o **H2** (já incluso como dependência) substituindo as propriedades acima por uma configuração de banco em memória.

### Executando a aplicação

Usando o Maven Wrapper (recomendado, não requer Maven instalado):

```bash
./mvnw spring-boot:run       # Linux/Mac
mvnw.cmd spring-boot:run     # Windows
```

Ou gerando o `.jar` e executando manualmente:

```bash
./mvnw clean package
java -jar target/CadastroDeNinjas-0.0.1-SNAPSHOT.jar
```

A aplicação sobe, por padrão, em `http://localhost:8080`.

### Executando os testes

```bash
./mvnw test
```

## Endpoints da API

### Ninjas — `/ninjas`

| Método | Rota | Descrição |
|---|---|---|
| `POST` | `/ninjas` | Cria um novo ninja. Corpo validado via `@Valid`. |
| `GET` | `/ninjas` | Lista todos os ninjas cadastrados. |
| `GET` | `/ninjas/{id}` | Busca um ninja pelo ID. Retorna 404 se não existir. |
| `PUT` | `/ninjas/{id}` | Atualiza os dados de um ninja existente. |
| `DELETE` | `/ninjas/{id}` | Remove um ninja pelo ID. Retorna 404 se não existir. |

Exemplo de corpo para criação (`POST /ninjas`):

```json
{
  "nome": "Naruto Uzumaki",
  "email": "naruto@konoha.com",
  "idade": 17
}
```

### Missões — `/missoes`

| Método | Rota | Descrição |
|---|---|---|
| `POST` | `/missoes` | Cria uma nova missão. Corpo validado via `@Valid`. |
| `GET` | `/missoes` | Lista todas as missões cadastradas. |
| `GET` | `/missoes/{id}` | Busca uma missão pelo ID. Retorna 404 se não existir. |
| `PUT` | `/missoes/{id}` | Atualiza os dados de uma missão existente. |
| `DELETE` | `/missoes/{id}` | Remove uma missão pelo ID. Retorna 404 se não existir. |

Exemplo de corpo para criação (`POST /missoes`):

```json
{
  "nome": "Resgate no País das Ondas",
  "dificuldade": "Alta"
}
```

## Tratamento de erros

O tratamento de exceções é centralizado no `GlobalExceptionHandler` (`@RestControllerAdvice`), garantindo respostas padronizadas em todos os endpoints:

| Situação | Status HTTP | Exceção |
|---|---|---|
| Ninja não encontrado | `404 Not Found` | `NinjaNaoEncontradoException` |
| Missão não encontrada | `404 Not Found` | `MissaoNaoEncontradaException` |
| Falha de validação (`@Valid`) | `400 Bad Request` | `MethodArgumentNotValidException` |
| Qualquer outro erro não tratado | `500 Internal Server Error` | `Exception` (genérica) |

Formato padrão da resposta de erro (`ErroResposta`):

```json
{
  "timestamp": "2026-07-26T10:00:00",
  "status": 404,
  "mensagem": "Ninja com ID 5 não foi encontrado."
}
```

## Estrutura de pastas

```
CadastroDeNinjas/
├── pom.xml
├── mvnw / mvnw.cmd
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   ├── dev/jef/CadastroDeNinjas/
│   │   │   │   ├── CadastroDeNinjasApplication.java
│   │   │   │   ├── ninjas/
│   │   │   │   └── missoes/
│   │   │   └── exceptions/
│   │   └── resources/
│   │       └── application.properties
│   └── test/
│       └── java/dev/jef/CadastroDeNinjas/
│           └── CadastroDeNinjasApplicationTests.java
```
