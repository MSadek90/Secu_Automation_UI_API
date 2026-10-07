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

Pages are studied when they are about to be applied: framework-design fundamentals before implementation, the rest during the build.

**Before implementation** — [Selenium docs](https://www.selenium.dev/documentation/)

- [x] Test Practices: Overview
- [x] Test Practices: Testing Types
- [ ] Encouraged: Page Object Models
- [ ] Test Practices: Design Strategies (builds on Page Objects)
- [ ] Encouraged: Locators
- [ ] WebDriver: Waits
- [ ] Encouraged: Generating Application State
- [ ] Encouraged: Avoid Sharing State · Test Independency · Fresh Browser per Test
- [ ] Discouraged (all)
- [ ] AI Agents → project rules file (`CLAUDE.md`)

**During the build**

- [ ] Encouraged: Improved Reporting (when selecting the reporting tool)
- [ ] Encouraged: Mock External Services (if isolating third-party services)
- [ ] Encouraged: Domain Specific Language · Fluent API (as the suite grows)

**k6** ([Docs](https://grafana.com/docs/k6/latest/)) — Phase 4

- [ ] Get Started
- [ ] Test Types
- [ ] Thresholds
- [ ] Automated Performance Testing

## Pending Decisions

- [ ] Browser matrix
- [ ] Test data setup strategy (form requests via REST Assured vs. direct DB)
- [ ] BDD layer (Cucumber): adopt or not

## Open Items

- [ ] Compare Demo vs Live `.env` (Pusher app, Firebase project) before any load test
