# Roadmap

| Phase | Goal | Deliverable | Status |
|---|---|---|---|
| 0. Setup | Tooling decisions, repository | ADR-0001..0003, this repo | ✅ Done |
| 1. Design | Framework design from official docs | Framework design + ADRs (build tool, runner, reporting, locators) | 🔄 In progress |
| 2. Walking skeleton | One test running end-to-end in CI | Green CI pipeline | ⏳ |
| 3. Expansion | Smoke → API → Regression | Smoke suite running on every deployment | ⏳ |
| 4. Performance | k6 scenarios | Capacity report | ⏳ |
| 5. Full CI/CD | Smoke per deploy, regression nightly, performance pre-release | Release gate | ⏳ |
| 6. Mobile | Guard app automation with Appium | Mobile smoke suite | ⏳ |

## Phase 1 — Reading Plan

**Selenium** ([Test Practices](https://www.selenium.dev/documentation/test_practices/))

- [ ] Overview
- [ ] Testing Types
- [ ] Design Strategies + Page Object Models
- [ ] Locators
- [ ] Generating Application State + Avoid Sharing State + Test Independency
- [ ] Fresh Browser per Test + Improved Reporting
- [ ] Discouraged Behaviors

**k6** ([Docs](https://grafana.com/docs/k6/latest/)) — Phase 4

- [ ] Get Started
- [ ] Test Types
- [ ] Thresholds
- [ ] Automated Performance Testing

## Open Items

- [ ] Compare Demo vs Live `.env` (Pusher app, Firebase project) before any load test
