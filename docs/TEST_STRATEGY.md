# Test Strategy

## 1. Objective

Detect regressions in SECU releases delivered by the vendor within minutes, and establish the platform's load capacity before guard volume grows.

## 2. Scope

| In scope | Out of scope (for now) |
|---|---|
| Web dashboards (Admin, Provider, Client, PMO) | Mobile app automation (planned with Appium, Phase 6) |
| Backend REST API | Production environment |
| API load and performance | Load testing of third-party realtime services (Pusher) |

## 3. Test Levels

Following the test pyramid: most business rules are verified at the API level; UI tests cover critical end-to-end user journeys only.

| Level | Covers | Tool |
|---|---|---|
| API | Business rules enforced by the backend: check-in acceptance, contract states, permissions | REST Assured |
| UI | Critical user journeys across dashboards | Selenium WebDriver |
| Performance | Response time and error rate under load | k6 |

API calls are also used to **prepare test data** for UI tests, keeping UI tests short and focused.

## 4. Suites

| Suite | Selection rule | Trigger | Target duration |
|---|---|---|---|
| Smoke | A failure means the release cannot be accepted | Every deployment | < 5 min |
| Regression | All functional tests | Nightly | — |
| Performance | Load scenarios (section 7) | Before major releases | — |

Smoke candidates (to be finalised in Phase 1):
- Login for each role
- Live tracking map loads with active guards
- Create project and contract

## 5. Environments

| Environment | Used by | Automation |
|---|---|---|
| Demo | QA only | ✅ All suites |
| Live | Clients | ❌ Never |

Pre-condition for performance testing: confirm the Demo environment does not share realtime-service (Pusher) or Firebase credentials with Live.

## 6. Test Data Rules

1. **Independent data** — each test creates its own data with unique identifiers (e.g. timestamp suffix).
2. **Explicit state** — each test sets up the state it needs instead of assuming it.
3. **Cleanup** — each test removes the data it created.
4. **No secrets in code** — credentials and URLs live in an untracked local config and in GitHub Secrets.

## 7. Performance Scenarios

| Scenario | Rationale |
|---|---|
| Shift change: mass check-in with selfie upload in a short window | Highest sudden load of the day |
| Shift end: bulk upload of a full shift's location history | Largest single request in the system |
| Dashboards: per-event requests from every open dashboard | Constant background load |

Load is ramped progressively (100 → 250 → 500 → 1000 virtual users) to find the breaking point. Results are reported relative to the Demo server's capacity.

## 8. To Be Decided (Phase 1)

Build tool, test runner, reporting tool, locator strategy, framework layering. Each will be recorded as an ADR.
