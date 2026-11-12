package com.secu.qa.tests.ui;

import com.secu.qa.BaseTest;
import com.secu.qa.pages.DashboardPage;
import org.junit.jupiter.api.Test;

class DashboardTest extends BaseTest {

    // passes when the dashboard loads without the login screen
    @Test
    void adminLoggedInByApiReachesDashboard() {
        loginAsAdminViaApi();
        DashboardPage.open(driver, baseUrl, timeout);
    }
}
