# App Agent Instructions

Use current project context and the official Spring documentation for the
Spring Boot version in `pom.xml` as the source of truth for application work:

- `https://docs.spring.io/spring-boot/`
- `https://docs.spring.io/spring-security/reference/`
- `https://docs.spring.io/spring-data/jpa/reference/`

Change the schema only with a new Flyway migration in
`src/main/resources/db/migration`; never edit an applied one. Keep
`spring.jpa.hibernate.ddl-auto=validate`.

Keep tests off development data: they use the `test` profile and
`TEST_DB_NAME`. Extend `NotesHttpTests`-style HTTP tests for new pages and
forms, including the CSRF token in posts.

Pages start with useful content and actions. Do not add a page header, welcome
heading, or standalone title unless the user asks for one. Keep changes scoped
to the user request and verify runtime behavior directly.
