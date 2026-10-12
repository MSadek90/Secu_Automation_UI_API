# ADR-0007: Test Data Cleanup Over HTTP

- **Status:** Accepted
- **Date:** 2026-10-12

## Context
Each test creates its own data (R-25) and must remove it afterwards (R-26, from the Selenium "Avoid Sharing State" page). Two ways exist: the application's own HTTP routes, or direct database deletes.

## Decision
Cleanup uses the application's HTTP routes (REST Assured), the same way a user deletes. The database is not opened from the tests.

## Facts from the backend code (`routes/admin.php`, `ProjectController.php`)
- `Route::resource('projects', ...)` exposes `DELETE /admin/projects/{id}`, handled by `destroy`.
- `destroy` restores the project if it is already deleted (`deleted_at` set), so calling it twice undoes the cleanup.
- `destroy` refuses to delete a project that has active contracts.
- Otherwise it calls `projectRepository->remove`. Whether that is a soft or a hard delete is **not yet verified**.
- `POST /admin/projects/{id}/archive` exists, but only completed projects can be archived.

## Consequences
- `Cleanup` must call delete once per project and check the response, never twice.
- Projects with contracts need their contracts cancelled first (route `projects/{project}/contracts/{contract}/cancel`).
- Verify `remove` (soft vs. hard delete) before relying on cleanup to leave the demo data clean.
- No database credentials are needed by the tests.
