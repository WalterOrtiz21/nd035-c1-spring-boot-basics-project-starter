package com.udacity.jwdnd.course1.cloudstorage;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CloudStorageApplicationTests {

	@LocalServerPort
	private int port;

	private WebDriver driver;

	@BeforeAll
	static void beforeAll() {
		WebDriverManager.chromedriver().setup();
	}

	@BeforeEach
	public void beforeEach() {
		this.driver = new ChromeDriver();
	}

	@AfterEach
	public void afterEach() {
		if (this.driver != null) {
			driver.quit();
		}
	}

	@Test
	public void getLoginPage() {
		driver.get("http://localhost:" + this.port + "/login");
		Assertions.assertEquals("Login", driver.getTitle());
	}

	@Test
	public void getSignPage() throws InterruptedException {
		driver.get("http://localhost:" + this.port + "/signup");
		Thread.sleep(2000);
		Assertions.assertEquals("Sign Up", driver.getTitle());
	}

	@Test
	public void getHomePage() throws InterruptedException {
		driver.get("http://localhost:" + this.port + "/home");
		Thread.sleep(2000);
		Assertions.assertEquals("Login", driver.getTitle());

	}

	@Test
	public void getResultPage() throws InterruptedException {
		driver.get("http://localhost:" + this.port + "/result");
		Thread.sleep(2000);
		Assertions.assertEquals("Login", driver.getTitle());

	}
}
