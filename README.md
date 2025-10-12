# eco-route

Sistema inteligente de coleta de resíduos, projetado para otimizar rotas, gerenciar coletas e promover a sustentabilidade através da tecnologia.

O **eco-route** é uma API RESTful construída com as tecnologias mais modernas do ecossistema Java e Spring. O objetivo é fornecer uma plataforma robusta e escalável para o gerenciamento completo do ciclo de coleta de resíduos, desde a autenticação de usuários e operadores até a otimização de rotas em tempo real.

## Tecnologias Utilizadas

* **Linguagem:** [Java 25](https://www.oracle.com/java/)
* **Framework:** [Spring Boot 3](https://spring.io/projects/spring-boot)
* **Segurança:** [Spring Security 6](https://spring.io/projects/spring-security) (Autenticação JWT)
* **Acesso a Dados:** [Spring Data JPA](https://spring.io/projects/spring-data-jpa) (Hibernate)
* **Banco de Dados:** [MySQL 8](https://www.mysql.com/)
* **Gerenciador de Dependências:** [Maven](https://maven.apache.org/)
* **Migrações de Banco:** [Flyway](https://flywaydb.org/)
* **Containerização:** [Docker](https://www.docker.com/) & [Docker Compose](https://docs.docker.com/compose/)
* **Utilitários:** [Lombok](https://projectlombok.org/), [java-jwt](https://github.com/auth0/java-jwt)

## Estrutura do Projeto

Abaixo está uma representação da organização de pacotes e arquivos principais do projeto, seguindo as melhores práticas de arquitetura em camadas.

```
eco-route/
├── .env                  # Arquivo para variáveis de ambiente (NÃO versionado)
├── .gitignore            # Arquivos e pastas a serem ignorados pelo Git
├── Dockerfile            # Define como construir a imagem Docker da API
├── docker-compose.yml    # Orquestra os contêineres da API e do Banco de Dados
├── pom.xml               # Arquivo de configuração do Maven
└── src/
    └── main/
        ├── java/
        │   └── com/
        │       └── devpro/
        │           └── ecoroute/
        │               ├── EcorouteApplication.java  # Classe principal da aplicação
        │               ├── config/                   # Configurações do Spring (SecurityConfig)
        │               ├── controllers/              # Camada de API (Controllers REST)
        │               │   ├── auth/
        │               │   └── user/
        │               ├── dto/                      # Data Transfer Objects (DTOs)
        │               │   ├── auth/
        │               │   └── user/
        │               ├── infra/                    # Infraestrutura (Filtros de segurança)
        │               │   └── security/
        │               ├── models/                   # Entidades JPA (User, Role, BaseEntity)
        │               ├── repositories/             # Interfaces do Spring Data JPA
        │               └── services/                 # Lógica de negócio (TokenService, etc.)
        └── resources/
            ├── application.properties  # Configurações da aplicação
            └── db/
                └── migration/
                    └── V1__... .sql      # Scripts de migração do Flyway
```

## Como Executar o Projeto

Para executar o projeto em seu ambiente local, você precisará ter o Docker e o Git instalados. Todo o ambiente (API + Banco de Dados) é orquestrado pelo Docker Compose.

### Pré-requisitos

* [Git](https://git-scm.com/)
* [Java JDK 25](https://adoptium.net/) (Opcional, se for rodar fora do Docker)
* [Docker e Docker Compose](https://www.docker.com/products/docker-desktop/)

### Passos para Instalação

1.  **Clone o repositório:**

    ```bash
    git clone https://github.com/LuizJarduli/eco-route.git
    cd eco-route
    ```

2.  **Crie o arquivo de variáveis de ambiente:**
    O `TokenService` precisa de uma chave secreta para assinar os tokens JWT. Crie um arquivo chamado `.env` na raiz do projeto.

    Use o comando abaixo para gerar uma chave segura:

    ```bash
    openssl rand -base64 32
    ```

    Copie a chave gerada e cole no arquivo `.env`:

    ```
    # .env
    API_SECURITY_TOKEN_SECRET=sua-chave-secreta-gerada-aqui
    ```

3.  **Suba os contêineres com Docker Compose:**
    Este comando irá construir a imagem da sua API e iniciar os contêineres da aplicação e do banco de dados MySQL.

    ```bash
    docker-compose up --build
    ```

    Para rodar em segundo plano, adicione a flag `-d`.

4.  **Verifique se tudo está funcionando:**
    A API estará disponível em `http://localhost:8080`. Você pode verificar o status com o endpoint de health check:

    ```bash
    curl http://localhost:8080/actuator/health
    ```

    A resposta esperada é `{"status":"UP"}`.

## 📚 Links Úteis e Documentação

* [Documentação Oficial do Spring Boot](https://docs.spring.io/spring-boot/docs/current/reference/htmlsingle/)
* [Guia de Autenticação com JWT e Spring Security](https://www.baeldung.com/spring-security-oauth-jwt)
* [Documentação do Docker Compose](https://docs.docker.com/compose/)
* [Documentação do FlywayDB](https://flywaydb.org/documentation/)

-----