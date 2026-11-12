# ADR-0011: Local .env File With dotenv-java

- **Status:** Accepted (extends ADR-0010)
- **Date:** 2026-10-12

## Context
With environment variables only (ADR-0010), every value had to be set with `setx` and a new terminal opened before it was visible. A local file next to the project is easier to edit, and `.gitignore` already ignores `.env`.

## Decision
`BaseTest` reads settings through `dotenv-java` 3.2.0 (test scope): `Dotenv.configure().ignoreIfMissing().load()`, then `ENV.get(name)` / `ENV.get(name, default)`. Per the library README, host environment variables override `.env`, and `ignoreIfMissing()` avoids a failure when the file does not exist (CI). Each developer copies `.env.example` to `.env` and fills in the values.

## Alternatives Considered
| Option | Pros | Cons |
|---|---|---|
| **dotenv-java + local `.env`** | One file per project, edit and run | One extra test dependency |
| Environment variables only (`setx`) | No library | New terminal after each change; values spread outside the project |
| Own `.env` reader | No library | Code to write and maintain |

## Consequences
- CI keeps using environment variables (they win over the file, and the file is absent there).
- `.env` must never be committed; `.env.example` holds names and defaults only.
