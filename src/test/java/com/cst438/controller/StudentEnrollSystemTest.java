package com.cst438.controller;

import java.time.Duration;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class StudentEnrollSystemTest {

    static final String URL = "http://localhost:5173";
    static final int DELAY = 1000;
    WebDriver driver;
    WebDriverWait wait;

    @BeforeEach
    public void setUpDriver() {
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(3));
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        driver.get(URL);
    }

    @AfterEach
    public void quit() {
        if (driver != null) {
            driver.quit();
        }
    }

    @Test
    public void enrollSystemTest() throws InterruptedException {
        // --- STEP 1: LOGIN AS SAMA ---
        driver.findElement(By.id("email")).sendKeys("sam1@csumb.edu");
        driver.findElement(By.id("password")).sendKeys("sam2025");
        driver.findElement(By.id("loginButton")).click();
        
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h1[contains(text(),'Student Home')]")));

        // --- STEP 2: VIEW SCHEDULE & DROP CST599 ---
        driver.findElement(By.id("scheduleLink")).click();
        //WebElement viewScheduleBtn = wait.until(ExpectedConditions.elementToBeClickable(By.id("schedule")));
        //viewScheduleBtn.click();
        driver.findElement(By.id("year")).sendKeys("2026");
        driver.findElement(By.id("semester")).sendKeys("Fall");
        driver.findElement(By.id("selectTermButton")).click();
        Thread.sleep(DELAY);

        // Find the Drop button for CST599
        WebElement dropButton = driver.findElement(By.xpath("//tr[td[contains(text(), 'cst599')]]//button[contains(text(), 'Drop')]"));
        dropButton.click();

        // Use the React-Confirm Alert code provided in instructions
        wait.until(ExpectedConditions.visibilityOfElementLocated(
             By.xpath("//div[@class='react-confirm-alert-button-group']/button[text()='Yes']")));
        driver.findElement(By.xpath("//div[@class='react-confirm-alert-button-group']/button[text()='Yes']")).click();
        Thread.sleep(DELAY);

        // --- STEP 3: NAVIGATE TO ENROLL & RE-ENROLL ---
        //driver.findElement(By.id("/")).click();
        driver.findElement(By.id("addCourseLink")).click();
        Thread.sleep(DELAY);
        
        // Locate CST599 in the enrollment list and click Enroll
        WebElement enrollBtn = driver.findElement(By.xpath("//tr[td[contains(text(), '2026')] and td[contains(text(), 'Fall')] and td[contains(text(), 'cst599')]]//button[contains(text(), 'Add Course')]"));
        enrollBtn.click();
        Thread.sleep(DELAY);

         // Use the React-Confirm Alert code provided in instructions
        wait.until(ExpectedConditions.visibilityOfElementLocated(
             By.xpath("//div[@class='react-confirm-alert-button-group']/button[text()='Yes']")));
        driver.findElement(By.xpath("//div[@class='react-confirm-alert-button-group']/button[text()='Yes']")).click();
        Thread.sleep(DELAY);

        // --- STEP 4: VIEW TRANSCRIPT & VERIFY ---
        //driver.findElement(By.id("/")).click();
        driver.findElement(By.id("transcriptLink")).click();
        Thread.sleep(DELAY);
        
        boolean courseFound = !driver.findElements(By.xpath("//tr[td[contains(text(), 'cst599')]]")).isEmpty();
        assertTrue(courseFound, "CST599 should be listed on the transcript.");
        
        // Verify grade is blank (N/A)
        String grade = driver.findElement(By.xpath("//tr[td[contains(text(), 'cst599')]]/td[last()]")).getText();
        assertEquals("", grade.trim(), "Newly enrolled course should not have a grade.");

        // --- STEP 5: LOGOUT & LOGIN AS INSTRUCTOR TED ---
        driver.findElement(By.id("logoutLink")).click();
        
        driver.findElement(By.id("email")).sendKeys("ted@csumb.edu");
        driver.findElement(By.id("password")).sendKeys("ted2025");
        driver.findElement(By.id("loginButton")).click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h1[contains(text(),'Instructor Home')]")));
        driver.findElement(By.id("year")).sendKeys("2026");
        driver.findElement(By.id("semester")).sendKeys("Fall");
        driver.findElement(By.id("selectTermButton")).click();
        Thread.sleep(DELAY);

        // View enrollments for CST599
        driver.findElement(By.xpath("//tr[td[contains(text(), 'cst599')]]//a[contains(text(), 'Enrollments')]")).click();
        Thread.sleep(DELAY);

        // Verify student 'sama' appears exactly once
        List<WebElement> samaEntries = driver.findElements(By.xpath("//td[contains(text(), 'sama')]"));
        assertEquals(1, samaEntries.size(), "Student 'sama' should appear exactly once in the roster.");
    }
}