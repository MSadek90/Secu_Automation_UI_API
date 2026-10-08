# SECU Test Automation

Automated UI, API and performance testing for **SECU**, a security-guard management and live-tracking platform (web dashboards + Flutter mobile app, Laravel backend).

## Tech Stack

| Layer | Tool | Why |
|---|---|---|
| UI (web dashboards) | Selenium WebDriver + Java | [ADR-0001](docs/adr/0001-selenium-java.md) |
| API | REST Assured | [ADR-0002](docs/adr/0002-rest-assured.md) |
| Performance | k6 | [ADR-0003](docs/adr/0003-k6.md) |
| CI | GitHub Actions | Planned (Phase 2) |

## Test Suites

| Suite | Purpose | Runs |
|---|---|---|
| Smoke | Critical business flows; a failure blocks the release | Every deployment (< 5 min) |
| Regression | Full functional coverage | Nightly |
| Performance | Capacity and breaking point under realistic load | Before major releases |

## Repository Structure

```
.
├── docs/
│   ├── TEST_STRATEGY.md   # scope, levels, suites, environments, data rules
│   ├── FRAMEWORK_RULES.md # coding rules from the official docs
│   ├── ROADMAP.md         # phases and current status
│   └── adr/               # architecture decision records
├── src/test/java/         # UI + API tests            (Phase 2)
├── performance/           # k6 scripts                (Phase 4)
└── .github/workflows/     # CI pipelines              (Phase 2)
```

## Status

**Phase 1 — Framework design.** Strategy and tooling decisions are in place; framework architecture is being designed from the official Selenium and k6 documentation before implementation. See the [Roadmap](docs/ROADMAP.md).

## Documentation

- [Test Strategy](docs/TEST_STRATEGY.md)
- [Framework Rules](docs/FRAMEWORK_RULES.md)
- [Roadmap](docs/ROADMAP.md)
- [Architecture Decision Records](docs/adr/)
