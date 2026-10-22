# Service health

Let operators determine whether the application and its database are available.

## Sources

- Main: `src/main/resources/application.properties`
- `src/test/java/com/example/app/NotesHttpTests.java`

## Public contract

A health request to `/actuator/health` accepts no input and needs no sign-in.
It returns a successful JSON status of `UP` when the application can handle
requests and reach its database. It has no product-side effects.
