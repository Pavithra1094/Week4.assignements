package week4.day2.assignment.changemgmt;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;

import io.github.bonigarcia.wdm.WebDriverManager;

public class CreateNewChange {

	public static void main(String[] args) throws InterruptedException {

		WebDriverManager.chromedriver().setup();

		ChromeDriver driver = new ChromeDriver();
// 1. Launch ServiceNow application - https://dev103117.service-now.com
		driver.get("https://dev103117.service-now.com");
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);

// 2. Login with valid credentials username as admin and password as India@123

		// Switch to frame
		driver.switchTo().frame(0);

		driver.findElement(By.id("user_name")).sendKeys("admin");
		driver.findElement(By.id("user_password")).sendKeys("India@123");
		driver.findElement(By.xpath("//button[text()='Log in']")).click();

// 3. Enter Change in filter navigator and press enter"
		WebElement changeManagement = driver.findElement(By.id("filter"));
		changeManagement.sendKeys("Change");
		changeManagement.sendKeys(Keys.ENTER);

// 4. Click Create New and click Normal change

		driver.findElement(By.xpath("(//div[text()='Create New'])[3]")).click(); // Create New

		driver.switchTo().frame(0);
		driver.findElement(By.xpath("(//div[@class='chg-model-model-description change-model-truncate'])[2]")).click(); // Normal
																														// Change

// 5. create new normal Change by filling mandatory fields and click 'Submit' button.

		String changeNumber = driver.findElement(By.name("change_request.number")).getAttribute("value");
		System.out.println(" ");
		System.out.println("Change Management Number: " + changeNumber);
		System.out.println(" ");

		WebElement shortDescription = driver.findElement(By.id("change_request.short_description"));
		shortDescription.click();
		shortDescription.sendKeys("Sample - New Change Creation");

		driver.findElement(By.id("sysverb_insert_bottom")).click();
		System.out.println("New change created");
		System.out.println(" ");

// 6. Click 'Open' and Enter the Number in 'Search Box'.

		driver.switchTo().defaultContent();

		/*
		 * WebElement changeMgmnt =
		 * driver.findElement(By.xpath("//input[@id='filter']")); changeMgmnt.clear();
		 * changeMgmnt.sendKeys("Change"); changeMgmnt.sendKeys(Keys.ENTER);
		 */
		driver.findElement(By.xpath("(//div[text()='Open'])[3]")).click();
		// driver.findElement(By.xpath("//input[@placeholder='Search']")).click();

		System.out.println("Opening to view the created change request: " + changeNumber);
		System.out.println(" ");

		driver.switchTo().frame(0);

		Select searchNumberOption = new Select(
				driver.findElement(By.cssSelector("span#change_request_hide_search>div>div>span>span>select")));
		searchNumberOption.selectByIndex(1);

		WebElement searchCHG = driver.findElement(By.xpath("(//label[text()='Search'])[2]/following::input"));
		searchCHG.click();
		searchCHG.sendKeys(changeNumber);
		searchCHG.sendKeys(Keys.ENTER);
		System.out.println("Next step is to verify the creation status");
		System.out.println(" ");

// 7. Verify the successful creation of change in All Changes with the Change number [Change request should be created successfully]

		String changeNo = driver.findElement(By.cssSelector("table#change_request_table>tbody>tr>td:nth-of-type(3)"))
				.getText();

		// String shortDesc =
		// driver.findElement(By.cssSelector("table#change_request_table>tbody>tr>td:nth-of-type(4)")).getText();

		// String requestedBy =
		// driver.findElement(By.cssSelector("table#change_request_table>tbody>tr>td:nth-of-type(9)")).getText();

		if (changeNo.equalsIgnoreCase(changeNumber)) {
			System.out.println(changeNo + " is created >> Test Case passed - closing window");

			driver.quit();

		} else {
			System.err.println("Failed to create new change, Error occurred");
		}

	}

}


//CHG0030929