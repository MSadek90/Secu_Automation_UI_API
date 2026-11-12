# ADR-0009: No Central Config Class

- **Status:** Accepted (supersedes the `Config` part of ADR-0008)
- **Date:** 2026-10-12

## Context
ADR-0008 added a `Config` class that combined the environment and the properties file, applied defaults and converted types. The team found it too complex for the current size of the project.

## Decision
Remove `Config`. Keep `SettingsSource`, `EnvironmentSettings` and `PropertiesSettings`. The code that needs a setting (starting with `BaseTest`) reads it from the source it needs and passes the value down. `config.properties` uses the same names as the environment variables (`BROWSER`, `HEADLESS`, `TIMEOUT_SECONDS`).

## Alternatives Considered
| Option | Pros | Cons |
|---|---|---|
| **No `Config`, read sources directly** | Fewer classes, easy to follow | Order (environment, then file, then default) and type conversion are written wherever a setting is read |
| Keep `Config` | One place for order, defaults and types | One more class to understand now |

## Consequences
- If the same reading logic starts repeating in several places, bring back a single class for it.
