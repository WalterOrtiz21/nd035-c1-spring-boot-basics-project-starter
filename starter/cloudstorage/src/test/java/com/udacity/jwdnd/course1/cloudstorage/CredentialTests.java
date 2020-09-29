package com.udacity.jwdnd.course1.cloudstorage;

import com.udacity.jwdnd.course1.cloudstorage.services.CredentialServices;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class CredentialTests {

	@LocalServerPort
	private int port;

	private WebDriver driver;

	@Autowired
	private CredentialServices credentialServices;

	public static String BASEURL;
	@BeforeAll
	static void beforeAll() {
		WebDriverManager.chromedriver().setup();
	}

	@BeforeEach
	public void beforeEach() {
		this.driver = new ChromeDriver();
		BASEURL = "http://localhost:"+ port;
		driver.get(CredentialTests.BASEURL+"/login");
		WebElement user = driver.findElement(By.id("inputUsername"));
		user.sendKeys("admin");
		WebElement password = driver.findElement(By.id("inputPassword"));
		password.sendKeys("admin");
		driver.findElement(By.id("submit-button")).click();
	}

	@AfterEach
	public void afterEach() {
		if (this.driver != null) {
			driver.quit();
		}
	}

	@Test
	@Order(1)
	public void testAddCredential() {
		WebDriverWait wait = new WebDriverWait(driver, 30);
		JavascriptExecutor executor = (JavascriptExecutor) driver;
		addCredential(wait, executor);
		WebElement btn;
		btn = driver.findElement(By.id("back-home"));
		executor.executeScript("arguments[0].click();", btn);
		btn = driver.findElement(By.id("nav-credentials-tab"));
		executor.executeScript("arguments[0].click();", btn);
		WebElement pass = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("encrypted-credential-1")));
		Assertions.assertEquals(pass.getText(),credentialServices.getCredential(1).getPassword() );
		btn = wait.until(webDriver -> webDriver.findElement(By.id("credential-1")));
		executor.executeScript("arguments[0].click();", btn);
		WebElement url = wait.until(webDriver->webDriver.findElement(By.id("credential-url")));
		WebElement userName = wait.until(webDriver->webDriver.findElement(By.id("credential-username")));
		WebElement password = wait.until(webDriver->webDriver.findElement(By.id("credential-password")));
		Assertions.assertEquals("url", url.getAttribute("value"));
		Assertions.assertEquals("userName", userName.getAttribute("value"));
		Assertions.assertEquals("password", password.getAttribute("value"));
	}

	@Test
	@Order(2)
	public void testEditCredential() {
		WebDriverWait wait = new WebDriverWait(driver, 30);
		JavascriptExecutor executor = (JavascriptExecutor) driver;
		addCredential(wait, executor);
		backToCredential(wait, executor);
		editCredential(wait);
		backToCredential(wait, executor);
		WebElement url = wait.until(webDriver->webDriver.findElement(By.id("credential-url")));
		WebElement userName = wait.until(webDriver->webDriver.findElement(By.id("credential-username")));
		WebElement password = wait.until(webDriver->webDriver.findElement(By.id("credential-password")));
		Assertions.assertEquals("new Url", url.getAttribute("value"));
		Assertions.assertEquals("new Password", password.getAttribute("value"));
		Assertions.assertEquals("new User Name", userName.getAttribute("value"));
	}

	@Test
	@Order(3)
	public void testDeleteCredential() {
		WebDriverWait wait = new WebDriverWait(driver, 30);
		JavascriptExecutor executor = (JavascriptExecutor) driver;
		addCredential(wait, executor);
		WebElement btn;
		btn = driver.findElement(By.id("back-home"));
		executor.executeScript("arguments[0].click();", btn);
		btn = driver.findElement(By.id("nav-credentials-tab"));
		executor.executeScript("arguments[0].click();", btn);
		btn = driver.findElement(By.id("delete-credential-1"));
		executor.executeScript("arguments[0].click();", btn);
		btn = driver.findElement(By.id("back-home"));
		executor.executeScript("arguments[0].click();", btn);
		btn = driver.findElement(By.id("nav-credentials-tab"));
		executor.executeScript("arguments[0].click();", btn);
		String rst = null;
		try{
			WebElement title = driver.findElement(By.id("credential-view-user-1"));
			wait.until(ExpectedConditions.visibilityOf(title));
			rst = title.getText();
		}catch(Exception e){
			System.out.println("Null");
		}
		Assertions.assertNull(rst);
	}

	private void addCredential(WebDriverWait wait, JavascriptExecutor executor) {
		executor.executeScript("arguments[0].click();", driver.findElement(By.id("nav-credentials-tab")));
		WebElement btn = wait.until(webDriver -> webDriver.findElement(By.id("newCredential")));
		try{
			wait.until(ExpectedConditions.visibilityOf(btn)).click();
			btn.click();
		}catch(ElementClickInterceptedException e){
			System.out.println("Nani");
		}
		WebElement url = wait.until(webDriver->webDriver.findElement(By.id("credential-url")));
		WebElement userName = wait.until(webDriver->webDriver.findElement(By.id("credential-username")));
		WebElement password = wait.until(webDriver->webDriver.findElement(By.id("credential-password")));
		WebElement save = wait.until(webDriver->webDriver.findElement(By.id("credential-btn")));
		url.sendKeys("url");
		userName.sendKeys("userName");
		password.sendKeys("password");
		save.click();
	}

	private void editCredential(WebDriverWait wait) {
		WebElement btn = wait.until(webDriver -> webDriver.findElement(By.id("credential-1")));
		try{
			wait.until(ExpectedConditions.visibilityOf(btn)).click();
			btn.click();
		}catch(ElementClickInterceptedException e){
			System.out.println("Nani");
		}
		WebElement url = wait.until(webDriver->webDriver.findElement(By.id("credential-url")));
		WebElement userName = wait.until(webDriver->webDriver.findElement(By.id("credential-username")));
		WebElement password = wait.until(webDriver->webDriver.findElement(By.id("credential-password")));
		WebElement save = wait.until(webDriver->webDriver.findElement(By.id("credential-btn")));
		url.clear();
		userName.clear();
		password.clear();
		url.sendKeys("new Url");
		password.sendKeys("new Password");
		userName.sendKeys("new User Name");
		save.click();
	}

	private void backToCredential(WebDriverWait wait, JavascriptExecutor executor) {
		WebElement btn;
		btn = driver.findElement(By.id("back-home"));
		executor.executeScript("arguments[0].click();", btn);
		btn = driver.findElement(By.id("nav-credentials-tab"));
		executor.executeScript("arguments[0].click();", btn);
		btn = wait.until(webDriver -> webDriver.findElement(By.id("credential-1")));
		executor.executeScript("arguments[0].click();", btn);
	}

}
