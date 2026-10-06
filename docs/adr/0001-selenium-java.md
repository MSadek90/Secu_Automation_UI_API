# ADR-0001: Selenium WebDriver + Java for UI Tests

- **Status:** Accepted
- **Date:** 2026-10-06

## Context
SECU's web dashboards are regression-tested manually on every vendor release. We need browser automation for repeatable smoke and regression suites.

## Decision
Selenium WebDriver with Java.

## Alternatives Considered
| Option | Pros | Cons |
|---|---|---|
| **Selenium + Java** | Existing team expertise; W3C standard; all major browsers; strong demand in target job market | Explicit waits must be handled manually (main source of flakiness) |
| Playwright + TypeScript | Built-in auto-wait and API testing | New language and tool at the same time |
| Cypress | Fast onboarding | JavaScript only; limitations with multiple tabs/origins |

## Consequences
- Fast start on a known stack; skills transfer to Appium for mobile (same WebDriver protocol).
- A deliberate wait strategy is required (to be defined in Phase 1).
