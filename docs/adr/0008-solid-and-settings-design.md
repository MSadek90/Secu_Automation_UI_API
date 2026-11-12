# ADR-0008: SOLID Principles and Settings Design

- **Status:** Accepted
- **Date:** 2026-10-12

## Context
The first `Config` read environment variables, loaded the properties file, applied defaults and converted types in one static class, and `DriverFactory` called it directly. One class had several reasons to change, nothing could be replaced or checked on its own, and SOLID had never been written into the plan.

## Decision
Adopt SOLID as a rule for all framework classes (R-44) and split settings accordingly (R-45):

```
config/
├── SettingsSource.java        one contract: get(key)
├── EnvironmentSettings.java   environment variables only
├── PropertiesSettings.java    .properties files only
└── Config.java                precedence + typed getters, built from two sources
```

`DriverFactory.create(browser, headless)` receives its inputs; it does not read settings. Waits will receive the timeout the same way.

## Alternatives Considered
| Option | Pros | Cons |
|---|---|---|
| **Sources behind one interface + injected `Config`** | One job per class, new source (JSON) without editing existing code, each class checkable alone | Four small files instead of one |
| One static `Config` class (the first version) | Least code | Several reasons to change, hidden dependency in `DriverFactory`, hard to check |
| Dependency-injection framework | Wiring is automatic | Extra library and concepts for a project this size |

## Consequences
- Passing `Config` values down starts in `BaseTest`; pages and utilities never call `Config` themselves.
- A JSON settings source needs a JSON library chosen from its documentation first; not added until a test needs it.
- Source for the principles: TechTarget and Simple English Wikipedia (see R-44). The Selenium documentation does not cover SOLID, so this is a team decision.
