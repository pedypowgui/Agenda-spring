# Agenda com Spring Boot

Aplicação local de agenda desenvolvida com Spring Boot, Thymeleaf, Spring Data JPA e MySQL. A interface web permite cadastrar clientes e profissionais e criar, consultar, atualizar e excluir agendamentos.

## Pré-requisitos

- JDK 21 instalado e configurado no `PATH`.
- MySQL instalado e em execução localmente.
- Git, caso ainda precise clonar o repositório.

O Maven Wrapper (`mvnw`/`mvnw.cmd`) já está incluído; não é necessário instalar o Maven separadamente.

## Passo a passo

### 1. Obtenha o projeto

Se ainda não tiver uma cópia local:

```bash
git clone https://github.com/pedypowgui/Agenda-spring.git
cd Agenda-spring
```

Se o projeto já estiver no computador, abra um terminal na pasta raiz do repositório.

### 2. Confira o Java

No terminal, confirme que o Java 21 está disponível:

```bash
java -version
```

Se o comando não for reconhecido, instale o JDK 21 e configure `JAVA_HOME` e o `PATH`. Feche e abra novamente o terminal depois de alterar as variáveis.

### 3. Inicie e configure o MySQL

Inicie o serviço do MySQL. Por padrão, a aplicação usa a configuração abaixo, definida em `backend/api_DS/src/main/resources/application.properties`:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/consumoapi?createDatabaseIfNotExist=true
spring.datasource.username=root
spring.datasource.password=
```

O banco `consumoapi` é criado automaticamente se o usuário configurado tiver permissão. Se o seu usuário, senha, porta ou endereço forem diferentes, atualize essas propriedades antes de iniciar a aplicação. O Hibernate está configurado com `ddl-auto=update` para criar ou atualizar as tabelas sem apagar os dados existentes.

### 4. Inicie a aplicação

No Windows, pelo PowerShell, na pasta raiz do repositório:

```powershell
cd .\backend\api_DS
.\mvnw.cmd spring-boot:run
```

No Linux ou macOS:

```bash
cd backend/api_DS
./mvnw spring-boot:run
```

Na primeira execução, o Maven Wrapper pode baixar o Maven e as dependências do projeto. Aguarde até o Spring Boot indicar que iniciou. Mantenha esse terminal aberto enquanto estiver usando a aplicação.

### 5. Acesse a aplicação

Abra no navegador:

- **Agenda de agendamentos:** <http://localhost:8080/>
- **Cadastro de clientes:** <http://localhost:8080/clientes>
- **Cadastro de profissionais:** <http://localhost:8080/profissionais>

Na página inicial, use **Novo agendamento** para selecionar um cliente e um profissional e informar a data e o horário. Para encerrar a aplicação, pressione `Ctrl+C` no terminal em que ela está rodando.

## Executar os testes

Na pasta `backend/api_DS`, execute:

```powershell
.\mvnw.cmd test
```

No Linux ou macOS, use `./mvnw test`.

## Problemas comuns

- **`JAVA_HOME` não está definido ou Java não é reconhecido:** instale o JDK 21, configure `JAVA_HOME` e o `PATH`, e reinicie o terminal.
- **Erro de conexão com o banco:** confirme que o MySQL está em execução e que endereço, porta, usuário e senha em `application.properties` correspondem à sua instalação.
- **Porta 8080 em uso:** encerre a outra aplicação que usa a porta ou configure outra porta no `application.properties`, por exemplo `server.port=8081`; nesse caso, acesse a aplicação pela porta configurada.
