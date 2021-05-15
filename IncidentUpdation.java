package week4.day2.assignment.incidentmgmt;

import java.util.concurrent.TimeUnit;

//import org.apache.bcel.generic.Select;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;

import io.github.bonigarcia.wdm.WebDriverManager;

public class IncidentUpdation {


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

	// 3. Enter Incident in filter navigator and press enter"
			WebElement incident = driver.findElement(By.id("filter"));
			incident.sendKeys("Incident");
			incident.sendKeys(Keys.ENTER);

	// 4. Search for the existing incident and click on the incident

			driver.findElement(By.xpath("//div[text()='Open']")).click();
			driver.switchTo().frame(0);

			Thread.sleep(2000);
			WebElement forTextNum = driver.findElement(By.xpath("//select[@class='form-control default-focus-outline']")); 
			new Select(forTextNum).selectByVisibleText("Number"); 
			driver.findElement(By.xpath("//input[@class='form-control']")).sendKeys("INC0011295");
			driver.findElement(By.xpath("//input[@class='form-control']")).sendKeys(Keys.ENTER);

			Thread.sleep(2000);

			WebElement resultantNumber = driver.findElement(By.xpath("//a[@class='linked formlink']"));
			System.out.println("Incident Number to be update: " + resultantNumber.getText());
			System.out.println(" ");
			resultantNumber.click();

	// 5. Update the incidents with Urgency as High and State as In Progress

			Select incidentState = new Select(driver.findElement(By.id("incident.state")));
			incidentState.selectByIndex(1);
			System.out.println("Updated incident state value as In-progress");
			System.out.println(" ");

			Select incidentUrgency = new Select(driver.findElement(By.id("incident.urgency")));
			incidentUrgency.selectByIndex(0);
			System.out.println("Updated the incident with Urgency as High");
			System.out.println(" ");

			driver.findElement(By.id("sysverb_update_bottom")).click();

	// 6. Verify the priority and state [Priority should be moderate and State should be In Progress]

			String verifyPriority = driver.findElement(By.cssSelector("table#incident_table>tbody>tr>td:nth-of-type(7)"))
					.getText();
			String verifyState = driver.findElement(By.cssSelector("table#incident_table>tbody>tr>td:nth-of-type(8)"))
					.getText();

			if ((verifyPriority.equalsIgnoreCase("3 - moderate")) && (verifyState.equalsIgnoreCase("In Progress"))) {
				System.out.println("Priority value: " + verifyPriority + " & " + "State value: " + verifyState);
				System.out.println(" ");
				System.out.println("Update Existing Incident >> Test Case passed - closing window");
				driver.close();

			} else {
				System.err.println("Update Existing Incident - TestCase failed");
			}
		}

	}
					
					
