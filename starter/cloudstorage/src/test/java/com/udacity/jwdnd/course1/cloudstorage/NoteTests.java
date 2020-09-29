package com.udacity.jwdnd.course1.cloudstorage;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class NoteTests {

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
		driver.get(NoteTests.BASEURL+"/login");
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
	public void testAddNote() {
		WebDriverWait wait = new WebDriverWait(driver, 30);
		JavascriptExecutor executor = (JavascriptExecutor) driver;
		addNote(wait, executor);
		WebElement btn;
		btn = driver.findElement(By.id("back-home"));
		executor.executeScript("arguments[0].click();", btn);
		btn = driver.findElement(By.id("nav-notes-tab"));
		executor.executeScript("arguments[0].click();", btn);
		btn = wait.until(webDriver -> webDriver.findElement(By.id("note-1")));
		executor.executeScript("arguments[0].click();", btn);
		WebElement newtitle = wait.until(webDriver->webDriver.findElement(By.id("note-title")));
		WebElement newdescription = wait.until(webDriver->webDriver.findElement(By.id("note-description")));
		Assertions.assertEquals("Title", newtitle.getAttribute("value"));
		Assertions.assertEquals("Description", newdescription.getAttribute("value"));
	}

	private void addNote(WebDriverWait wait, JavascriptExecutor executor) {
		executor.executeScript("arguments[0].click();", driver.findElement(By.id("nav-notes-tab")));
		WebElement btn = wait.until(webDriver -> webDriver.findElement(By.id("note-modal")));
		try{
			wait.until(ExpectedConditions.visibilityOf(btn)).click();
			btn.click();
		}catch(ElementClickInterceptedException e){
			System.out.println("Nani");
		}
		WebElement title = wait.until(webDriver->webDriver.findElement(By.id("note-title")));
		WebElement description = wait.until(webDriver->webDriver.findElement(By.id("note-description")));
		WebElement save = wait.until(webDriver->webDriver.findElement(By.id("note-btn")));
		title.sendKeys("Title");
		description.sendKeys("Description");
		save.click();
	}

	private void editNote(WebDriverWait wait, JavascriptExecutor executor) {
		executor.executeScript("arguments[0].click();", driver.findElement(By.id("nav-notes-tab")));
		WebElement btn = wait.until(webDriver -> webDriver.findElement(By.id("note-modal")));
		try{
			wait.until(ExpectedConditions.visibilityOf(btn)).click();
			btn.click();
		}catch(ElementClickInterceptedException e){
			System.out.println("Nani");
		}
		WebElement title = wait.until(webDriver->webDriver.findElement(By.id("note-title")));
		title.clear();
		WebElement description = wait.until(webDriver->webDriver.findElement(By.id("note-description")));
		description.clear();
		WebElement save = wait.until(webDriver->webDriver.findElement(By.id("note-btn")));
		title.sendKeys("new Title");
		description.sendKeys("new Description");
		save.click();
	}

	@Test
	@Order(2)
	public void testEditNote() {
		WebDriverWait wait = new WebDriverWait(driver, 30);
		JavascriptExecutor executor = (JavascriptExecutor) driver;
		addNote(wait, executor);
		gotoNotes(wait, executor);
		editNote(wait, executor);
		gotoNotes(wait, executor);
		WebElement newTitle = wait.until(webDriver->webDriver.findElement(By.id("note-title")));
		WebElement newDescription = wait.until(webDriver->webDriver.findElement(By.id("note-description")));
		Assertions.assertEquals("new Title", newTitle.getAttribute("value"));
		Assertions.assertEquals("new Description", newDescription.getAttribute("value"));
	}

	@Test
	@Order(3)
	public void testDeleteNote() {
		WebDriverWait wait = new WebDriverWait(driver, 30);
		JavascriptExecutor executor = (JavascriptExecutor) driver;
		addNote(wait, executor);
		WebElement btn;
		btn = driver.findElement(By.id("back-home"));
		executor.executeScript("arguments[0].click();", btn);
		btn = driver.findElement(By.id("nav-notes-tab"));
		executor.executeScript("arguments[0].click();", btn);
		btn = driver.findElement(By.id("delete-note-1"));
		executor.executeScript("arguments[0].click();", btn);
		btn = driver.findElement(By.id("back-home"));
		executor.executeScript("arguments[0].click();", btn);
		btn = driver.findElement(By.id("nav-notes-tab"));
		executor.executeScript("arguments[0].click();", btn);
		String rst = null;
		try{
			WebElement title = driver.findElement(By.id("note-view-title-1"));
			wait.until(ExpectedConditions.visibilityOf(title));
			rst = title.getText();
		}catch(Exception e){
			System.out.println("Null");
		}
		Assertions.assertNull(rst);
	}


	private void gotoNotes(WebDriverWait wait, JavascriptExecutor executor) {
		WebElement btn;
		btn = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("back-home")));
		executor.executeScript("arguments[0].click();", btn);
		btn = driver.findElement(By.id("nav-notes-tab"));
		executor.executeScript("arguments[0].click();", btn);
		btn = wait.until(webDriver -> webDriver.findElement(By.id("note-1")));
		executor.executeScript("arguments[0].click();", btn);
	}


}
