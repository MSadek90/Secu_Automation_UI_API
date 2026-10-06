# ADR-0002: REST Assured for API Tests

- **Status:** Accepted
- **Date:** 2026-10-06

## Context
Most SECU business rules (check-in acceptance, contract states, permissions) are enforced by the backend. Verifying them through the UI is slow and fails for reasons unrelated to the rule.

## Decision
REST Assured (Java DSL for REST API testing).

## Alternatives Considered
| Option | Pros | Cons |
|---|---|---|
| **REST Assured** | Same language and project as UI tests; single build and report; readable given/when/then style | Code required |
| Postman + Newman | No code; familiar | Separate project and report; no reuse with UI layer |
| Java HttpClient | No extra dependency | Verbose; no assertion DSL |

## Consequences
- Fast, stable tests for business rules; API calls also used to set up UI test data.
- Requires mapping SECU's API (routes, auth token, payloads) from the backend source.
