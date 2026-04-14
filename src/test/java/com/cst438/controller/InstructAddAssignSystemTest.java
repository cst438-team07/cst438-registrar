package com.cst438.controller;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

public class InstructAddAssignSystemTest {

    static final String URL = "http://localhost:5173";   // react dev server
    static final int DELAY = 1000;
    WebDriver driver;
    Wait<WebDriver> wait;
    Random random = new Random();

    @BeforeEach
    public void setUpDriver() {
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));
        wait = new WebDriverWait(driver, Duration.ofSeconds(2));
        driver.get(URL);
    }

    @AfterEach
    public void quit() {
        driver.quit();
    }

    @Test
    public void instructorAddAssignmentTest() throws InterruptedException {
        // Reece
        // instructorAddAssignmentTest.

        // Added to data.sql
        // Make class 2025 Fall CST599
        // add 3 users to class as students

        // Done below
        // Instructor ted@csumb.edu logins.
        // On the home page for instructor enter 2026 and Fall to view the list of sections.
        // Select view assignment for the section CST599 and add a new assignment.
        // Enter a random title and due date for the assignment.
        // Save the assignment then close the dialog.
        // Verify that the new assignment title shows on the assignments page.
        // Select the new assignment for grading.
        // Enter scores of 60, 88 and 98 for the 3 students enrolled in the section.
        // Save the grades and close the dialog.
        // Grade the assignment again and verify the scores.
        // Close the dialog.

        // To dismiss a react-confirm alert
        // None in this code so not used
        // wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class='react-confirm-alert-button-group']/button[@label='Yes']")));
        // WebElement yesButton = driver.findElement(By.xpath("//div[@class='react-confirm-alert-button-group']/button[@label='Yes']")).click();

        // Alert alert;
        int randomString = random.nextInt(100000, 999999); // 789423
        String HWName = "assignment"+randomString;
        // String HWName = "HW dupe name test";
        String courseId = "cst599";
        String courseYear = "2026"; // 2025
        String courseSemester = "Fall";
        String asignDate = "10072026"; // 10072025

        // Instructor ted@csumb.edu logins.
        driver.findElement(By.id("email")).sendKeys("ted@csumb.edu");
        driver.findElement(By.id("password")).sendKeys("ted2025");
        driver.findElement(By.id("loginButton")).click();
        // Thread.sleep(DELAY);
        wait.until(ExpectedConditions.visibilityOfElementLocated( By.xpath("//*[contains(text(),'Instructor Home')]")));

        // On the home page for instructor enter 2025 and Fall to view the list of sections.
        driver.findElement(By.id("year")).sendKeys(courseYear); // 2025
        driver.findElement(By.id("semester")).sendKeys(courseSemester); // Fall
        driver.findElement(By.id("selectTermButton")).click();
        Thread.sleep(DELAY);

        // Select view assignment for the section CST599 and add a new assignment.
        driver.findElement(By.xpath("//tr[td[contains(text(), '" + courseId + "')]]//a[contains(text(), 'Assignments')]")).click(); // CST599
        Thread.sleep(DELAY);
        driver.findElement(By.id("addAssignmentButton")).click();
        Thread.sleep(DELAY);
        driver.findElement(By.xpath("//button[text()='Add Assignment']/following::dialog[1]//input[@name='title']")).sendKeys(HWName);
        driver.findElement(By.xpath("//button[text()='Add Assignment']/following::dialog[1]//input[@name='dueDate']")).sendKeys(asignDate);

        // Save the assignment then close the dialog.
        driver.findElement(By.xpath("//button[text()='Add Assignment']/following::dialog[1]//button[contains(text(),'Save')]")).click();
        Thread.sleep(DELAY);
        driver.findElement(By.xpath("//button[text()='Add Assignment']/following::dialog[1]//button[contains(text(),'Close')]")).click();
        Thread.sleep(DELAY);

        // Verify that the new assignment title shows on the assignments page. (AI assisted)
        List<WebElement> titleMatches = driver.findElements(By.xpath("//*[contains(text(),'" + HWName + "')]"));
        // int titleMatchesSize = titleMatches.size();
        assertFalse(titleMatches.isEmpty(), HWName + "' not found on page!");
            // Get ID for new assignment to prevent name dupe issues that can happen if the assignment name is
            // already present or the same name is used to create an assignment after this one was graded
        String assignmentId = driver.findElement(By.xpath("(//tr[td[contains(text(),'" + HWName + "')]])[last()]/td[1]")).getText();

        // Select the new assignment for grading.
        driver.findElement(By.xpath("//tr[td[1][text()='" + assignmentId + "']]/td[4]//button")).click();
        Thread.sleep(DELAY);

        // Enter scores of 60, 88 and 98 for the 3 students enrolled in the section.
        List<WebElement> scoreInputs = driver.findElements(
                By.cssSelector("dialog table input[type='number']")
        );
        scoreInputs.get(0).clear(); scoreInputs.get(0).sendKeys("60");
        scoreInputs.get(1).clear(); scoreInputs.get(1).sendKeys("88");
        scoreInputs.get(2).clear(); scoreInputs.get(2).sendKeys("98");
        Thread.sleep(DELAY);

        // Save the grades and close the dialog.
        driver.findElement(By.xpath("//dialog[@open]//h2[contains(text(),'Grade Assignments')]//following::button[contains(text(),'Save')]")).click();
        Thread.sleep(DELAY);

        // Grade the assignment again and verify the scores.
        driver.findElement(By.xpath("//tr[td[1][text()='" + assignmentId + "']]/td[4]//button")).click();
        // driver.findElement(By.xpath("//tr[td[2][contains(text(), '" + HWName + "')]]/td[4]")).click();
        Thread.sleep(DELAY);

        scoreInputs = driver.findElements(
                By.cssSelector("dialog table input[type='number']")
        );
        String score0 = scoreInputs.get(0).getAttribute("value");
        String score1 = scoreInputs.get(1).getAttribute("value");
        String score2 = scoreInputs.get(2).getAttribute("value");

        assertEquals("60", score0, "Student 0 Score mismatch");  // expected first
        assertEquals("88", score1, "Student 1 Score mismatch");
        assertEquals("98", score2, "Student 2 Score mismatch");

        Thread.sleep(DELAY);

        // Close the dialog.
        driver.findElement(By.xpath("//dialog[@open]//h2[contains(text(),'Grade Assignments')]//following::button[contains(text(),'Close')]")).click();
        Thread.sleep(DELAY);

    }

}
