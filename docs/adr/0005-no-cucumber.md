# ADR-0005: No BDD Layer (Cucumber)

- **Status:** Accepted
- **Date:** 2026-10-10

## Context
Cucumber adds Gherkin feature files on top of the tests. The team does not have Cucumber experience, and business readability is already covered by domain-language page methods (R-20).

## Decision
Do not use Cucumber. Tests are plain JUnit 5 methods written in business language.

## Alternatives Considered
| Option | Pros | Cons |
|---|---|---|
| **No Cucumber** | One less layer to learn, write and maintain | No Gherkin files for non-technical readers |
| Cucumber | Business-readable specs | Extra glue code and step-definition maintenance; the team has not used it |

## Consequences
- Revisit only if non-technical stakeholders need to read or write test scenarios.
