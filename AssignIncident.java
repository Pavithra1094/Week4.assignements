	package week4.day2.assignment.incidentmgmt;


	import java.util.ArrayList;
	import java.util.List;
	import java.util.Set;
	import java.util.concurrent.TimeUnit;

	import org.openqa.selenium.By;
	import org.openqa.selenium.Keys;
	import org.openqa.selenium.WebElement;
	import org.openqa.selenium.chrome.ChromeDriver;
	import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;

import io.github.bonigarcia.wdm.WebDriverManager;

	public class AssignIncident {

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
			driver.findElement(By.xpath("//input[@class='form-control']")).sendKeys(NewIncidentCreation.incidentNum);
			driver.findElement(By.xpath("//input[@class='form-control']")).sendKeys(Keys.ENTER);


			Thread.sleep(2000);

			WebElement resultantNumber = driver.findElement(By.xpath("//a[@class='linked formlink']"));
			System.out.println("Incident Number to be update: " + resultantNumber.getText());
			System.out.println(" ");
			resultantNumber.click();

			// 5. Assign the incident to  Software group

			// driver.findElement(By.xpath("//button[@id='lookup.incident.caller_id']//span[1]")).click();
			
			driver.findElement(By.id("lookup.incident.assignment_group")).click();
			
			Set<String> allWindowHandles = driver.getWindowHandles();
			System.out.println("WindowHandling: " + allWindowHandles.size());
			List<String> lstWindowHandles = new ArrayList<String>(allWindowHandles);
			System.out.println(" ");
			
			driver.switchTo().window(lstWindowHandles.get(1));
			
			driver.findElement(By.xpath("(//label[text()='Search'])[2]/following::input")).sendKeys("Software");
			driver.findElement(By.xpath("(//label[text()='Search'])[2]/following::input")).sendKeys(Keys.ENTER);
			
			driver.findElement(By.linkText("Software")).click();
			
			driver.switchTo().window(lstWindowHandles.get(0));
			
			System.out.println("Updated incident Group as Software");
			
			
			//driver.findElement(By.id("sys_display.incident.assigned_to")).click();
			
			driver.switchTo().window(lstWindowHandles.get(0));

			System.out.println("Parent-Window Title: " + driver.getTitle());
			System.out.println("Parent-Window URL: " + driver.getCurrentUrl());
			System.out.println(" ");
			driver.switchTo().frame(0);

			System.out.println("Updated incident Group as Software");
			System.out.println(" ");

			// Enter AssignedTo value

			// driver.findElement(By.id("lookup.incident.assigned_to")).click();
			driver.findElement(By.xpath("//button[@id='lookup.incident.assigned_to']//span[1]")).click(); // Lookup using
			// List

			// Switch to sub-window
			allWindowHandles = driver.getWindowHandles();
			lstWindowHandles = new ArrayList<String>(allWindowHandles);
			System.out.println("Window Handling: " + allWindowHandles.size());
			driver.switchTo().window(lstWindowHandles.get(1));
			
		
			WebElement assignmentTo = driver.findElement(By.xpath("(//label[text()='Search'])[2]/following::input"));
			assignmentTo.sendKeys("ITIL");
			assignmentTo.sendKeys(Keys.ENTER);
			driver.findElement(By.linkText("ITIL User")).click();
			
			driver.switchTo().window(lstWindowHandles.get(0));
			driver.switchTo().frame(0);
			
			Actions actions = new Actions(driver);
			
			WebElement point1=driver.findElement(By.id("cxs_maximize_results"));
			
			actions.moveToElement(point1);
			
			driver.findElement(By.xpath("//textarea[@id='activity-stream-textarea']")).sendKeys("notes-updated-test");
			
			driver.findElement(By.id("sysverb_update")).click();
			
			String verifyAssignmentGroup = driver
					.findElement(By.cssSelector("table#incident_table>tbody>tr>td:nth-of-type(10)")).getText();
			String verifyAssignedT0 = driver.findElement(By.cssSelector("table#incident_table>tbody>tr>td:nth-of-type(11)"))
					.getText();

			if ((verifyAssignmentGroup.equalsIgnoreCase("Software")) && (verifyAssignedT0.equalsIgnoreCase("ITIL User"))) {
				System.out.println(
						"Assignment Group: " + verifyAssignmentGroup + " & " + " Assigned To: " + verifyAssignedT0);
				System.out.println(" ");
				System.out.println("Assign Incident >> Test Case passed - closing window");
				driver.quit();

			} else {
				System.err.println("Assign Incident - TestCase failed");
			}
				
			
		}
		
	}
	
			
			
			
			

			