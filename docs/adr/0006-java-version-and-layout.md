# ADR-0006: Java Version and Project Layout

- **Status:** Accepted
- **Date:** 2026-10-12

## Context
The project contains framework code (pages, utilities, API clients) and tests. The team machine already runs Java 23.

## Decision
- Java 23, built with Maven.
- Root package `com.secu.qa`.
- Framework code (`config`, `driver`, `utils`, `pages`, `api`, `data`) under `src/main/java`. Tests (`BaseTest`, `tests/ui`, `tests/api`) only under `src/test/java`. Settings under `src/test/resources`.
- The raw-actions utility is named `ActionBot` to avoid clashing with Selenium's own `Actions` class.
- Parallel test execution is part of the plan, designed once the first tests run.

## Source and status of this decision
- Maven defines `src/main/java` as "Application/Library sources" and `src/test/java` as "Test sources" ([Standard Directory Layout](https://maven.apache.org/guides/introduction/introduction-to-the-standard-directory-layout.html)).
- No Selenium or Maven document says where page objects must live. Putting the framework in `src/main` and only tests in `src/test` is a **Team decision**, taken because the folder boundary keeps browser code out of tests (R-05, R-11).

## Alternatives Considered
| Option | Pros | Cons |
|---|---|---|
| **Framework in `src/main`, tests in `src/test`** | Physical separation: a test file cannot hold page code | Selenium and REST Assured become compile-scope dependencies |
| Everything in `src/test` | Dependencies stay test-scope; simpler `pom.xml` | Pages and tests share one folder, so the boundary relies on review only |
| Java 21 (LTS) instead of 23 | Longer support | Requires installing another JDK |

## Consequences
- CI runner and local machines must use JDK 23 (not an LTS release).
- `pom.xml`: Selenium and REST Assured at compile scope; JUnit 6 and Allure at test scope.

## Library versions (set in `pom.xml`, each with its source in a comment there)
- Selenium 4.51.0: latest stable Java binding on selenium.dev/downloads.
- REST Assured 6.1.0: latest on Maven Central. Other pages disagree (wiki 6.0.1, javadoc.io 5.5.7); the team chose the latest.
- JUnit 6.1.3: latest stable on docs.junit.org (needs Java 17+ and Surefire 3.0.0+). Changed from JUnit 5 on 2026-10-12.
- Allure 3.0.0 (`allure-jupiter`, works with JUnit 6 and 5) and AspectJ 1.9.25: allurereport.org/docs/junit5.
