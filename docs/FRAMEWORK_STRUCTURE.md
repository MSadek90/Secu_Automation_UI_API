# Framework Structure

Layout of the test project. Decisions: ADR-0006 (Java 23, Maven, framework in `src/main`, tests in `src/test`, `ActionBot`). Rules: [FRAMEWORK_RULES.md](FRAMEWORK_RULES.md).

```
Secu_Automation_UI_API/
├── pom.xml                              # Maven: Selenium, REST Assured (compile); JUnit 5, Allure (test)
├── .env.example                         # env var names only, no values
├── CLAUDE.md
├── README.md
├── docs/
│   ├── FRAMEWORK_RULES.md               # R-01..R-43
│   ├── FRAMEWORK_STRUCTURE.md
│   ├── ROADMAP.md
│   └── adr/                             # 0001..0006
├── src/
│   ├── main/java/com/secu/qa/           # the framework
│   │   ├── config/
│   │   │   └── Config.java              # base url, browser, timeout, credentials from env
│   │   ├── driver/
│   │   │   └── DriverFactory.java       # the only place that creates a browser (R-38)
│   │   ├── utils/
│   │   │   ├── ActionBot.java           # click, type, text (R-36)
│   │   │   └── Waits.java               # explicit waits only (R-41, R-42)
│   │   ├── pages/
│   │   │   ├── BasePage.java            # driver + bot + waits
│   │   │   ├── LoginPage.java           # /admin/login
│   │   │   ├── DashboardPage.java       # /admin/dashboard
│   │   │   ├── CreateProjectPage.java   # /admin/projects/create
│   │   │   ├── ProjectsPage.java        # /admin/projects (Livewire list)
│   │   │   └── components/
│   │   │       └── BaseComponent.java   # root element + root.findElement (R-15)
│   │   ├── api/
│   │   │   ├── AuthApi.java             # login by API, returns cookies (R-22)
│   │   │   └── ProjectApi.java          # create/delete project by HTTP (R-39)
│   │   └── data/
│   │       ├── ProjectData.java         # unique data per test (R-25)
│   │       └── Cleanup.java             # R-26 (HTTP delete, ADR-0007)
│   └── test/
│       ├── java/com/secu/qa/
│       │   ├── BaseTest.java            # @BeforeEach driver, @AfterEach quit (R-27)
│       │   └── tests/
│       │       ├── ui/                  # LoginTest, ProjectTest
│       │       └── api/
│       └── resources/
│           ├── config.properties        # no secrets
│           ├── allure.properties
│           └── junit-platform.properties  # parallel execution: designed later
├── .github/workflows/ci.yml             # phase 2
└── performance/                         # k6, phase 4
```

## Flow of one test

1. `BaseTest` creates a fresh browser through `DriverFactory` (R-27, R-38).
2. `data/` and `api/` prepare test data over HTTP before the browser opens a page (R-39).
3. `AuthApi` logs in and returns the session cookies; the test adds them to the browser (R-22).
4. The test calls `Page.open(...)`; the constructor waits for a critical element to be displayed (R-37, R-43).
5. Page services use `ActionBot` and `Waits` (R-36).
6. The test makes the assertions (R-11).
7. Cleanup removes the data, then `quit()` closes the browser (R-26, R-27).

## Open points

- Verify whether `ProjectController::destroy` is a soft or hard delete (ADR-0007).
- Parallel execution: designed after the first tests run.
- Which external services to mock (the code shows Pusher, Firebase, Mapbox).
- Method signatures of `ActionBot` and `Waits` come from the Java API docs when the classes are built (R-40).
