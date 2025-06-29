package com.example;

import com.microsoft.playwright.*;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.*;
import static org.junit.Assert.*;

public class PCCSteps {
    private Playwright playwright;
    private Browser browser;
    private Page page;

    @Before
    public void setUp() {
        // Launch local browser instead of connecting to MCP server
        playwright = Playwright.create();
        browser = playwright.chromium().launch(
            new BrowserType.LaunchOptions()
                .setChannel("chrome")
                .setHeadless(false)
        );
        page = browser.newPage();
    }

    @After
    public void tearDown() {
        if (browser != null) browser.close();
        if (playwright != null) playwright.close();
    }

    @Given("Navigate to {string}")
    public void navigate_to(String url) {
        page.navigate(url);
        // Wait for the popup to appear (optional, e.g., page.waitForTimeout(1000);)
        Locator acceptBtn = page.locator("#onetrust-accept-btn-handler");
        if (acceptBtn.count() > 0 && acceptBtn.first().isVisible()) {
            acceptBtn.first().click();
        }
    }

    @And("Maximize the browser")
    public void maximize_the_browser() {
        page.setViewportSize(1920, 1080);
    }

    @When("I Click on Sign In")
    public void click_on_sign_in() {
        page.waitForSelector("//button[text()='Sign in']", new Page.WaitForSelectorOptions().setState(com.microsoft.playwright.options.WaitForSelectorState.VISIBLE));
        page.click("//button[text()='Sign in']");
    }

    @And("Click on Register")
    public void click_on_register() {
        page.click("//*[@data-testid='register-button']");
    }

    @Then("i should be able to click on Sign In")
    public void should_be_able_to_click_sign_in() {
        // Check if the Sign In button is still visible and enabled
        Locator signInBtn = page.locator("//button[text()='Sign in']");
        assertTrue(signInBtn.isVisible() && signInBtn.isEnabled());
    }

    @Then("i should be able to see the register page")
    public void should_see_register_page() {
        assertTrue(page.url().toLowerCase().contains("register"));
    }
}
