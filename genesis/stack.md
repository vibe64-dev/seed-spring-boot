# Stack

## Stack packages
- `genesis-stack`

## Components
- `java`
- `spring-boot`
- `mysql`
- `spring-boot-mysql`

## Resources

```json genesis-resource
{
  "id": "database",
  "kind": "mysql",
  "environmentAlternatives": [
    {
      "bindings": {
        "database": "DB_NAME",
        "host": "DB_HOST",
        "password": "DB_PASSWORD",
        "port": "DB_PORT",
        "username": "DB_USERNAME"
      },
      "allowEmpty": [
        "password"
      ],
      "preferred": true
    }
  ],
  "optionalBindings": {
    "testDatabase": "TEST_DB_NAME"
  }
}
```

## Environment defaults

- Default `DB_HOST`: `127.0.0.1`
- Default `DB_NAME`: `spring`
- Default `DB_PORT`: `3306`
- Default `DB_USERNAME`: `root`

## Verification

- Verify `application`: `./mvnw` `-B` `test`

## Connected development

- Default package type: `maven`

## Deployment

- Runtimes: `java`
- Ready when: `GET` `/actuator/health` returns `200`
- Build `Package`: `./mvnw` `-B` `-DskipTests` `package`
- Serve `Start`: `java` `-jar` `target/app.jar`

## Outputs

### Target `app`: Run Spring Boot app

- Default.
- Mode: `interactive`
- Runtimes: `java`
- Run `Develop`: `./mvnw` `-B` `spring-boot:run` `-Dspring-boot.run.profiles=dev` `-Dspring-boot.run.arguments=--server.address={host} --server.port={port}`

#### Presentation

- Kind: `web`
- Preferred port: `8080`
- URL path: `/`
- Ready when: `GET` `/actuator/health` returns `200`

## Resource estimates

### Output `app`

- Startup typical MiB: `768`
- Startup high MiB: `1280`
- Running typical MiB: `640`
- Running high MiB: `1280`

### Workspace setup

- Typical MiB: `512`
- High MiB: `1024`

## Workspace setup

- Prepare `Compile application` with `java` when `pom.xml` exists: `./mvnw` `-B` `-DskipTests` `compile`
