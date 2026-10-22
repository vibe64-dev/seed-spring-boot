# Spring Boot Basic starter

A small Spring Boot 4.1 application: a public home page with a form that saves
notes to MySQL or MariaDB. It uses Spring MVC with Thymeleaf, Spring Data JPA,
Flyway migrations, Spring Security (CSRF protection and security headers, no
accounts) and Actuator health. It was generated with
[start.spring.io](https://start.spring.io) (Maven, Java 21, Boot 4.1.1) and
trimmed to one feature.

Branches:

- [basic](https://github.com/vibe64-dev/seed-spring-boot/tree/basic): notes app, no accounts.

## Run locally

Install a JDK 21 (with `javac`, not only a Java runtime), Git, and MySQL 8 or
MariaDB 10.6 or later. Maven comes with the committed wrapper (`./mvnw`).

Create a development database and a separate test database, then provide the
connection through the environment:

```sh
export DB_HOST=127.0.0.1 DB_PORT=3306 DB_NAME=spring DB_USERNAME=spring DB_PASSWORD=...
export TEST_DB_NAME=spring_test
```

```sh
./mvnw spring-boot:run -Dspring-boot.run.profiles=dev
```

Open http://localhost:8080. `HOST` and `PORT` choose the listen address and
port. Flyway creates the `notes` table on startup.

`./mvnw -DskipTests package` builds `target/app.jar`; `java -jar target/app.jar`
serves it without the development profile.

## Use through Genesis or Vibe64

See [starting the app](docs/colleague-usage/starting-app.md).

Install `genesis-compiler` and `genesis-stack`, run `genesis init` in an empty
Git repository, then choose `official:spring-boot/basic` with
`genesis templates apply`. Vibe64 offers the same starter in the Preview pane.

The starter owns its dependencies and the setup, output, and verification
commands in `genesis/stack.md`. Genesis describes those requirements; Vibe64
runs the declared operations. Choosing a starter adds source to the session;
it does not create external services or invent connection values.

This variant needs Java 21 and a MySQL-compatible database (the `database`
resource, read through `DB_HOST`, `DB_PORT`, `DB_NAME`, `DB_USERNAME`,
`DB_PASSWORD`, and `TEST_DB_NAME` for tests). It has no accounts and no
application secret.

## Check changes

```sh
./mvnw test
```

`NoteFormTests` checks note validation. `NotesHttpTests` starts the application
on a random port against `TEST_DB_NAME` and drives it over HTTP: health, saving
a note with the form's CSRF token behind a forwarding proxy, refusing a post
without the token, and validation errors.

## Database driver

The MariaDB Connector/J driver (`jdbc:mariadb://`) works with both MariaDB and
MySQL servers. For a MySQL-only deployment you may switch to
`com.mysql:mysql-connector-j` and a `jdbc:mysql://` URL; keep `flyway-mysql`.

## Keep dependencies current

The Spring Boot parent in `pom.xml` pins every Spring-managed dependency
version; the Maven wrapper pins Maven. To upgrade, change the parent version,
read the Spring Boot release notes, and run the tests.
