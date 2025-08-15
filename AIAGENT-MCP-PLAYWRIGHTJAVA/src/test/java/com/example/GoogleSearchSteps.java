package com.example;

import com.microsoft.playwright.*;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.*;
import static org.junit.Assert.*;

public class GoogleSearchSteps {
    private Playwright playwright;
    private Browser browser;
    private Page page;

    @Before
    public void setUp() {
        playwright = Playwright.create();
        browser = playwright.chromium().launch();
        page = browser.newPage();
    }

    @After
    public void tearDown() {
        browser.close();
        playwright.close();
    }

    @Given("I am on the Google search page")
    public void i_am_on_the_google_search_page() {
        page.navigate("https://www.google.com");
    }

    @When("I search for {string}")
    public void i_search_for(String query) {
        page.fill("input[name='q']", query);
        page.keyboard().press("Enter");
        page.waitForSelector("#search");
    }

    @Then("the results should contain {string}")
    public void the_results_should_contain(String expected) {
        String content = page.content();
        assertTrue(content.contains(expected));
    }
}
