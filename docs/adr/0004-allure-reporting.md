# ADR-0004: Allure for Test Reports

- **Status:** Accepted
- **Date:** 2026-10-10

## Context
CI needs a pass/fail exit code and per-test results; people need a readable report with steps and failure screenshots (R-24). Selenium does not report; the test framework does.

## Decision
Allure Report on top of JUnit 5.

## Alternatives Considered
| Option | Pros | Cons |
|---|---|---|
| **Allure** | Named steps (`@Step`), attached screenshots, history, readable HTML | Extra dependency and AspectJ agent configuration |
| JUnit XML + Surefire HTML only | No extra tooling | Plain report, no steps or attachments |

## Consequences
- Page services are annotated as steps; a screenshot is attached on failure.
- The Allure CLI is needed in CI to generate the HTML report.
