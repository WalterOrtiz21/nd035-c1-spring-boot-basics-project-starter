package com.udacity.jwdnd.course1.cloudstorage;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.FindBy;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class SignUpLoginTests {

	@LocalServerPort
	private int port;

	private WebDriver driver;

	public static String BASEURL;
	@BeforeAll
	static void beforeAll() {
		WebDriverManager.chromedriver().setup();
	}

	@BeforeEach
	public void beforeEach() {
		this.driver = new ChromeDriver();
		BASEURL = "http://localhost:"+ port;
	}

	@AfterEach
	public void afterEach() {
		if (this.driver != null) {
			driver.quit();
		}
	}

	@Test
	@Order(1)
	public void loginFailed() throws InterruptedException{
		driver.get(SignUpLoginTests.BASEURL + "/login");
		WebElement user = driver.findElement(By.id("inputUsername"));
		user.sendKeys("admin");
		WebElement password = driver.findElement(By.id("inputPassword"));
		password.sendKeys("password");
		driver.findElement(By.id("submit-button")).click();
		WebElement errorMsgDiv = driver.findElement(By.id("error-msg"));
		Assertions.assertEquals(SignUpLoginTests.BASEURL+ "/login?error", driver.getCurrentUrl());
		String expected = "Invalid username or password";
		Assertions.assertEquals(expected, errorMsgDiv.getText());
		Thread.sleep(5000);
	}

	@Test
	@Order(2)
	public void testSignupUser() throws InterruptedException {
		driver.get(SignUpLoginTests.BASEURL+"/signup");
		WebElement firstName = driver.findElement(By.id("inputFirstName"));
		firstName.sendKeys("admin");
		WebElement lastName = driver.findElement(By.id("inputLastName"));
		lastName.sendKeys("admin");
		WebElement uName = driver.findElement(By.id("inputUsername"));
		uName.sendKeys("admins");
		WebElement password = driver.findElement(By.id("inputPassword"));
		password.sendKeys("admin");
		driver.findElement(By.id("submit-button")).click();
		Thread.sleep(3000);
	}

	@Test
	@Order(3)
	public void testLoginUser() throws InterruptedException {
		driver.get(SignUpLoginTests.BASEURL+"/login");
		WebElement user = driver.findElement(By.id("inputUsername"));
		user.sendKeys("admin");
		WebElement password = driver.findElement(By.id("inputPassword"));
		password.sendKeys("admin");
		driver.findElement(By.id("submit-button")).click();
		Thread.sleep(3000);
		Assertions.assertEquals(SignUpLoginTests.BASEURL+ "/home", driver.getCurrentUrl());
	}

	@Test
	@Order(4)
	public void testLogout() throws InterruptedException {
		driver.get(SignUpLoginTests.BASEURL+"/login");
		WebElement user = driver.findElement(By.id("inputUsername"));
		user.sendKeys("admin");
		WebElement password = driver.findElement(By.id("inputPassword"));
		password.sendKeys("admin");
		driver.findElement(By.id("submit-button")).click();
		Thread.sleep(3000);
		Assertions.assertEquals(SignUpLoginTests.BASEURL+ "/home", driver.getCurrentUrl());

		driver.get(SignUpLoginTests.BASEURL+"/home");
		WebElement logout = driver.findElement(By.id("logout-btn"));
		logout.click();
		Assertions.assertEquals(SignUpLoginTests.BASEURL+ "/login?logout", driver.getCurrentUrl());

		driver.get(SignUpLoginTests.BASEURL+"/home");
		Assertions.assertEquals(SignUpLoginTests.BASEURL+ "/login", driver.getCurrentUrl());
	}
}
