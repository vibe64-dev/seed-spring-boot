# Start from the Spring Boot Basic app

In an empty project's Preview, choose **Spring Boot · Basic app** and
**Use this starter**. This works on desktop and phone. Successful import adds
the application source and its complete Genesis contracts to the session;
use **Save** to keep that work in the project. Setup and Preview run the
operations declared in the starter's Stack.

The starter needs Java 21 and a MySQL-compatible database. Workspace setup
compiles the application with the Maven wrapper; the first run downloads Maven
and the dependencies, so it takes a few minutes. The application creates its
`notes` table on startup and Preview opens the notes page once
`/actuator/health` reports the application and database as up.

If import reports missing Stack contracts while the destination project is
still empty, the rejected starter needs repair. Migrating that empty project
cannot repair the downloaded starter. Once the starter is corrected, retry
**Use this starter**. Do not add placeholder Stack sections to the destination
merely to suppress the error.

Colleague can explain the choices and offer to apply the selected starter.
Only after your direct request or accepted offer may it apply that exact
configured starter through its existing onboarding action, then recheck the
result. Application source remains in the session for review and **Save**.
