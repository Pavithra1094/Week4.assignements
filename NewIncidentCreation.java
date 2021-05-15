package week4.day2.assignment.incidentmgmt;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;

import io.github.bonigarcia.wdm.WebDriverManager;

public class NewIncidentCreation {
	public static String incidentNum;

	public static void main(String[] args) throws InterruptedException {
		WebDriverManager.chromedriver().setup();

		ChromeDriver driver = new ChromeDriver();

		driver.get("https://dev103117.service-now.com/");
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);

		// 2. Login with valid credentials user name as admin and password as India@123

				// Switch to frame
				driver.switchTo().frame(0);

				driver.findElement(By.id("user_name")).sendKeys("admin");
				driver.findElement(By.id("user_password")).sendKeys("India@123");
				driver.findElement(By.xpath("//button[text()='Log in']")).click();

		// 3. Enter Incident in filter navigator and press enter"
				WebElement incident = driver.findElement(By.id("filter"));
				incident.sendKeys("Incident");
				incident.sendKeys(Keys.ENTER);
				
				

		// 4. Click on Create new option and fill the mandatory fields

				// driver.switchTo().frame(0);
				driver.findElement(By.xpath("//div[text()='Create New']")).click();
				driver.switchTo().frame(0);

				String incidentNum = driver.findElement(By.name("incident.number")).getAttribute("value");
				System.out.println("Incident Number: " + incidentNum);

				// Assign Caller Info
				driver.findElement(By.xpath("//button[@id='lookup.incident.caller_id']//span[1]")).click(); // Lookup using List

				Set<String> allWindowHandles = driver.getWindowHandles();
				System.out.println("WindowHandling: " + allWindowHandles.size());
				List<String> lstWindowHandles = new ArrayList<String>(allWindowHandles);
				System.out.println(" ");

				// Switch to sub-window 1
				driver.switchTo().window(lstWindowHandles.get(1));
				System.out.println("Sub-Window Title: " + driver.getTitle());
				System.out.println("Sub-Window URL: " + driver.getCurrentUrl());
				System.out.println(" ");

				WebElement searchUser = driver.findElement(By.xpath("(//label[text()='Search'])[2]/following::input"));
				searchUser.sendKeys("System");
				searchUser.sendKeys(Keys.ENTER);
				driver.findElement(By.linkText("System Administrator")).click();
				// driver.close();

				driver.switchTo().window(lstWindowHandles.get(0));

				System.out.println("Parent-Window Title: " + driver.getTitle());
				System.out.println("Parent-Window URL: " + driver.getCurrentUrl());
				System.out.println(" ");
				driver.switchTo().frame(0);
				// driver.findElement(By.id("incident.short_description")).sendKeys("Sample -
				// New Incident Creation");
				WebElement shortDescription = driver.findElement(By.xpath("(//input[@class='form-control'])[2]"));
				shortDescription.click();
				shortDescription.sendKeys("Sample - New Incident Creation");

				driver.findElement(By.xpath("(//button[text()='Submit'])[2]")).click();
				
				// 5. Verify the newly created incident ( copy the incident number and paste it in search field and 
				// enter then verify the instance number created or not)
				Thread.sleep(2000);
				WebElement forTextNum = driver.findElement(By.xpath("//select[@class='form-control default-focus-outline']")); 
				new Select(forTextNum).selectByVisibleText("Number"); 
				driver.findElement(By.xpath("//input[@class='form-control']")).sendKeys(incidentNum);
				driver.findElement(By.xpath("//input[@class='form-control']")).sendKeys(Keys.ENTER);

				Thread.sleep(2000);

				String resultantNumber = driver.findElement(By.xpath("//a[@class='linked formlink']")).getText();
				/*
				 * System.out.println("Incident Number: " + incidentNumber);
				 * System.out.println("Resultant Number: " + resultantNumber);
				 */
				if (resultantNumber.equalsIgnoreCase(incidentNum)) {
					System.out.println(resultantNumber + " has been created >> Test Case passed - closing window");
					driver.quit();

				} else {
					System.err.println("Failed to create Incident, Error occurred");
				}

			}

		}