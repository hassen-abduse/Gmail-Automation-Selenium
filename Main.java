package com.company;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.opera.OperaDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.openqa.selenium.support.ui.ExpectedConditions.presenceOfElementLocated;

public class Main {

    public static void main(String[] args) {
        System.setProperty("webdriver.chrome.driver", "C:\\chrome-webdriver\\chromedriver.exe");

        WebDriver driver = new ChromeDriver();

        driver.get("https://accounts.google.com/signin/v2/identifier?service=mail&passive=true&rm=false&continue=https%3A%2F%2Fmail.google.com%2Fmail%2F&ss=1&scc=1&ltmpl=default&ltmplcache=2&emr=1&osid=1&flowName=GlifWebSignIn&flowEntry=AddSession&cid=1&navigationDirection=forward");

        //Email here
        driver.findElement(By.xpath("//*[@id=\"identifierId\"]")).sendKeys("Your Email");

        driver.findElement(By.xpath("//*[@id=\"identifierNext\"]/span/span")).click();
        System.out.println("Redirecting To Password Field");

        WebDriverWait wait = new WebDriverWait(driver, 100);
        wait.until(presenceOfElementLocated(By.xpath("//*[@id=\"password\"]/div[1]/div/div[1]/input")));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"password\"]/div[1]/div/div[1]/input")));

        //Password Here
        driver.findElement(By.xpath("//*[@id=\"password\"]/div[1]/div/div[1]/input")).sendKeys("Your Password");
        System.out.println("Filling Password Field...");

        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        driver.findElement(By.xpath("//*[@id=\"passwordNext\"]/span/span")).click();

        String expectedUrl = "https://mail.google.com/mail/u/0/";
        if (driver.getCurrentUrl().equals(expectedUrl)) {
            System.out.println("Successfully Redirected");
            List<WebElement> emails = driver.findElements(By.className("zE"));
            StringBuilder builder = new StringBuilder();
            String emailamount = "Number Of Email" + emails.size() + "";
            builder.append(emailamount);

            for (WebElement mess : emails) {
                builder.append(mess.getText());
            }
            String filename = "unreadMessage.txt";
            try {
                Writer writer = new FileWriter(filename);
                writer.write(builder.toString());
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
