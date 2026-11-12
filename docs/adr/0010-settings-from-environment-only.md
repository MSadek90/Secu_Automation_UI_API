# ADR-0010: Settings From Environment Variables Only

- **Status:** Accepted (supersedes ADR-0009 and the settings part of ADR-0008)
- **Date:** 2026-10-12

## Context
After removing `Config` (ADR-0009), a setting could still live in three places: `config.properties`, an environment variable, and a default in code. The properties file added nothing, because the defaults are already in code, and every setting is read once, in `BaseTest`.

## Decision
Environment variables are the only source. `BaseTest` reads them with `System.getenv` and passes the values down. `BASE_URL`, `ADMIN_EMAIL`, `ADMIN_PASSWORD` are required and stop the run with a clear message when missing. `BROWSER` (chrome), `HEADLESS` (false) and `TIMEOUT_SECONDS` (10) have defaults in code. `SettingsSource`, `EnvironmentSettings`, `PropertiesSettings` and `config.properties` are removed.

## Alternatives Considered
| Option | Pros | Cons |
|---|---|---|
| **Environment variables only, defaults in code** | One source, no settings classes | Defaults are read in code, not in a file |
| Environment + `config.properties` (ADR-0009) | Defaults visible in a file | Same value in up to three places |

## Consequences
- Locally only the three required variables are set; CI sets `HEADLESS=true` as well.
- `.env.example` lists every name with its default.
