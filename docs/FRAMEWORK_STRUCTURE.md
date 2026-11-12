# Framework Structure

Layout of the test project. Decisions: ADR-0006 (Java 23, Maven, framework in `src/main`, tests in `src/test`, `ActionBot`), ADR-0008 (SOLID), ADR-0010 (settings from environment variables only). Rules: [FRAMEWORK_RULES.md](FRAMEWORK_RULES.md).

```
Secu_Automation_UI_API/
├── pom.xml                              # Maven: Selenium, REST Assured (compile); JUnit 6, Allure (test)
├── .env.example                         # names and defaults; copy to .env (ignored by git) (R-45)
├── CLAUDE.md
├── README.md
├── docs/
│   ├── FRAMEWORK_RULES.md               # R-01..R-46
│   ├── FRAMEWORK_STRUCTURE.md
│   ├── ROADMAP.md
│   ├── RUN_NOTES.md                     # problems met while running tests and how we handled them
│   └── adr/                             # 0001..0011
├── src/
│   ├── main/java/com/secu/qa/           # the framework
│   │   ├── driver/
│   │   │   └── DriverFactory.java       # the only place that creates a browser (R-38)
│   │   ├── routes/
│   │   │   └── Routes.java              # every URL path, shared by pages and api (R-37)
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
│       │   ├── ScreenshotOnFailure.java # attaches a screenshot to Allure when a test fails (R-24)
│       │   ├── BaseTest.java            # @BeforeEach driver, @AfterEach quit (R-27)
│       │   └── tests/
│       │       ├── ui/                  # LoginTest, ProjectTest
│       │       └── api/
│       └── resources/
│           ├── allure.properties
│           └── junit-platform.properties  # parallel execution: designed later
├── .github/workflows/ci.yml             # phase 2
└── performance/                         # k6, phase 4
```

## Flow of one test

1. `BaseTest` reads the environment variables it needs and creates a fresh browser through `DriverFactory.create(browser, headless)` (R-27, R-38, R-45).
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
- Allure `@Step` on page methods (R-24) needs the Allure annotations library available to `src/main`; the artifact name is checked in the Allure docs when `LoginPage` is built.
