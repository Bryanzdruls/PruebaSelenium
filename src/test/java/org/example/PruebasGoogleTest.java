package org.example;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.time.Duration;

class PruebasGoogleTest {

    @Test
    void happy() {
        WebDriver driver = new ChromeDriver();
        driver.get("https://www.google.com");
        driver.manage().timeouts().implicitlyWait(Duration.ofMillis(500));

        String title = "nintendo";
        String titleBase = " - Buscar con Google";

        WebElement textBox = driver.findElement(By.name("q"));
        WebElement submitButton = driver.findElement(By.name("btnK"));

        textBox.sendKeys(title);
        submitButton.click();

        String titleName = title + titleBase;

        Assertions.assertEquals(titleName,driver.getTitle());
        driver.quit();
    }

    @Test
    void noHappy() {
        WebDriver driver = new ChromeDriver();
        driver.get("https://www.google.com");
        driver.manage().timeouts().implicitlyWait(Duration.ofMillis(500));

        String testTitle = "Google";

        WebElement submitButton = driver.findElement(By.xpath("/html/body/div[1]/div[3]/form/div[1]/div[1]/div[4]/center/input[1]"));

        submitButton.click();

        Assertions.assertEquals(testTitle,driver.getTitle());

        driver.quit();
    }
}