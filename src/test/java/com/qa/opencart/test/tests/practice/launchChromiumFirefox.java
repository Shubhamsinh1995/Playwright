package com.qa.opencart.test.tests.practice;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;
import com.microsoft.playwright.assertions.PlaywrightAssertions;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

public class launchChromiumFirefox {
    @Test
    public void launch(){
        Playwright playwright = Playwright.create();  // create() method start the PlayWrite server
        Browser browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(false));
        //Browser browser = playwright.firefox().launch(new BrowserType.LaunchOptions().setHeadless(false));
        //Browser browser = playwright.webkit().launch(new BrowserType.LaunchOptions().setHeadless(false));  // launch safari on windows
        Page page = browser.newPage();

        page.navigate("https://ui.cogmento.com/?lang=en");
        String title= page.title();
        System.out.println("Title is :"+title);
        PlaywrightAssertions.assertThat(page).hasTitle(title); // By-default PlaywrightAssertions wait for 5 seconds

        String url = page.url();
        System.out.println("URL is :"+url);
        PlaywrightAssertions.assertThat(page).hasURL(url);

        page.locator("xpath=//input[@name='email']").fill("baiyasshubham@gmail.com");
        page.locator("xpath=//input[@type='password']").fill("Mahol@1234");
        page.locator("xpath=//div[text()='Login']").click();

        PlaywrightAssertions.assertThat(page).not().hasTitle("Free CRM");

        browser.close();
        playwright.close();
    }

    @Test
    public void launchActualBrowser(){
        Playwright playwright = Playwright.create();  // create() method start the PlayWrite server

        BrowserType.LaunchOptions lp = new BrowserType.LaunchOptions().setChannel("chrome").setHeadless(false);
        //lp.setChannel("chrome"); // to launch the actual Chrome browser
        //lp.setHeadless(false);
        Browser browser = playwright.chromium().launch(lp);
        Page page = browser.newPage();

        page.navigate("https://www.google.co.in/");
        String title= page.title();
        System.out.println("Title is :"+title);
        PlaywrightAssertions.assertThat(page).hasTitle(title);

        // Instantiate SoftAssert
        SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(title, "Google", "Title does not match!");

        String url = page.url();
        System.out.println("URL is :"+url);
        PlaywrightAssertions.assertThat(page).hasURL(url);

        page.close();
        browser.close();
        softAssert.assertAll();
    }
}
