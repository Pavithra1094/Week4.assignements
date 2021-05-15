package week4.day2.assignment.proposal;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;

import io.github.bonigarcia.wdm.WebDriverManager;

public class NewProposal {

	public static void main(String[] args) {

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

// 3. Enter Proposal in filter navigator and press enter"
		WebElement filterNav = driver.findElement(By.id("filter"));
		filterNav.sendKeys("Proposal");
		filterNav.sendKeys(Keys.ENTER);

		driver.findElement(By.xpath("//div[text()='Open Proposals']")).click();
		
// 4. Open new Proposal by filling mandatory fields and click 'Submit' Button.

		// Switch to frame
		driver.switchTo().frame(0);

		driver.findElement(By.id("sysverb_new")).click();

		String proposalNumber = driver.findElement(By.id("std_change_proposal.number")).getAttribute("value");
		System.out.println("Number: " + proposalNumber);
		System.out.println(" ");

		driver.findElement(By.id("std_change_proposal.short_description")).sendKeys("Test - New proposal creation");

		// Update Mandatory field
		driver.findElement(By.xpath("//span[text()='Change Request values']")).click();

		WebElement shortDesc = driver.findElement(By.xpath("//td[@data-value='Short description']//input[1]"));
		shortDesc.click();
		shortDesc.clear();
		shortDesc.sendKeys("Test - New proposal creation");

		// Submit
		driver.findElement(By.cssSelector("button#sysverb_insert_bottom")).click();

// 5. Verify the successful creation of new Proposal

		WebElement searchFilterOption = driver
				.findElement(By.cssSelector("span#std_change_proposal_hide_search>div>div>span>span>select"));
		new Select(searchFilterOption).selectByIndex(1);

		WebElement eleNumber = driver.findElement(By.cssSelector("span#std_change_proposal_hide_search>div>div>input"));
		eleNumber.click();
		eleNumber.sendKeys(proposalNumber);
		eleNumber.sendKeys(Keys.ENTER);

		String resultantNumber = driver.findElement(By.xpath("//a[@class='linked formlink']")).getText();
		/*
		 * System.out.println("Incident Number: " + incidentNumber);
		 * System.out.println("Resultant Number: " + resultantNumber);
		 */
		if (resultantNumber.equalsIgnoreCase(proposalNumber)) {
			System.out.println(resultantNumber + " has been created >> Test Case passed - closing window");
			driver.quit();

		} else {
			System.err.println("Failed to create new Proposal, Error occurred");
		}

	}

}