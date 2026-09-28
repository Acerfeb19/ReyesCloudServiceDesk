# Reyes Cloud Service Desk

A Java console project for tracking IT support tickets. It models requesters and technicians, enforces ticket status changes, and manages tickets by ID and priority. This is a standalone learning project and is not deployed as part of the planned Reyes Cloud platform.

## Features

- Tickets start as `PENDING` and can move through `IN_PROGRESS`, `ON_HOLD`, and `RESOLVED` under defined rules.
- `TicketService` rejects duplicate IDs and finds tickets by ID with a `HashMap`.
- Search matches title keywords; a separate insertion sort returns tickets in descending priority order.
- A `PriorityQueue` processes pending tickets by priority, with `CRITICAL` highest.
- Tickets record creation, transitions, and technician assignments in an in-memory audit history.
- `TicketFileRepository` saves and reloads ticket details to a pipe-delimited text file.

## Run

Requires JDK 17 or newer and Maven.

```sh
mvn test
mvn compile
java -cp target/classes Main
```

The demo prints users, ticket lifecycle changes, searches, priority processing, errors, and a file save/load example. It writes `tickets.txt` in the directory where you run it; this generated file is ignored by Git.

The three JUnit test classes cover ticket transitions, duplicate IDs and priority processing, and file save/load. The submitted project had six passing tests in the user's IDE. Run `mvn test` in your own environment to verify the packaged Maven build.

## Current limits

- The file format uses `|` as a separator and does not escape it inside fields.
- Audit records are kept in memory and are not restored by the file repository.
- The processing queue does not prevent adding the same pending ticket twice.

## Layout

- `src/main/java` — application classes and console demo
- `src/test/java` — JUnit tests
