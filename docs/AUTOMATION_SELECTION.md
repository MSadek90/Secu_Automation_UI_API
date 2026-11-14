# Automation Selection

Not every test case is automated. A case is automated only when the automation pays back more than it costs. This is the return on investment (ROI) of the test.

## Return on investment

- **Cost:** the time to write the test, plus the time to maintain it whenever the screen or the code changes.
- **Return:** the manual time of one run, multiplied by the number of times the test runs.
- A case is worth automating when the return passes the cost within a few weeks.

## The four questions

Ask these for every manual case before it is automated.

| # | Question | Yes means |
|---|---|---|
| 1 | Will it run again often? | It is part of regression: re-checked on every vendor release or on every push in CI. |
| 2 | Is the expected result fixed and agreed? | There is one correct outcome. A disputed or unclear result is tested manually and logged first. |
| 3 | Does a failure hurt the business? | A break would stop daily work, for example creating a project or checking in. |
| 4 | Can it be set up and cleaned up cheaply? | Its data is created and deleted through the API (R-04, R-25). Cases that need old data, real notifications or a specific date stay manual. |

A case is automated when questions 2 and 4 are yes, and at least one of 1 or 3 is yes. Question 3 also sets the order: business-critical cases are automated first.

The level then follows R-01: through the API when possible, through the browser only for what the user has to see.

## Recording the decision

Each manual case carries one decision and the question that decided it:

- **Automate (API)**
- **Automate (UI)**
- **Manual**: say which question failed.
- **Waiting**: the expected result is not agreed yet (question 2). The case is automated once it is.

## Examples (Project module)

The times are estimates, to show the calculation.

- **Create a project (P-01).** About 3 minutes by hand. It runs on every push, about 20 times a week, so it saves about an hour a week. Writing it takes about 2 hours, and the form rarely changes. It pays back in about 2 weeks. **Automate (UI).**
- **Archive a finished project (P-26).** About 5 minutes by hand, checked once every few months. It needs a project whose end date has passed, which the create form does not allow, so the setup is costly to build and maintain. It never pays back. **Manual** (question 4).
- **Guard count with a fraction (P-15).** The rule accepts any number, and the expected result is not agreed with the vendor. **Waiting** (question 2): tested by hand and logged, automated after the decision.

## Sources

- [Selenium: Overview of test automation](https://www.selenium.dev/documentation/test_practices/overview/): use the browser only when there is no alternative, and manual testing can be the better choice when the user interface will change considerably.
- [Martin Fowler: The Practical Test Pyramid](https://martinfowler.com/articles/practical-test-pyramid.html): many fast low-level tests, few UI tests.
- The four questions and the automation condition are a **Team decision**.
