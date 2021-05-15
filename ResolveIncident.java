package week4.day2.assignment.incidentmgmt;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;

import io.github.bonigarcia.wdm.WebDriverManager;

public class ResolveIncident {

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

		
		WebElement incidentstate = driver.findElement(By.id("incident.state")); 
		new Select(incidentstate).selectByVisibleText("Resolved"); 
		
		driver.findElement(By.xpath("//span[text()='Resolution Information']")).click();
		
		WebElement incidentclosecode = driver.findElement(By.id("incident.close_code")); 
		new Select(incidentclosecode).selectByValue("Closed/Resolved by Caller"); 
		
		
		driver.findElement(By.id("incident.close_notes")).sendKeys("Resolving the incident");
		
		driver.findElement(By.id("resolve_incident")).click();
		
		//driver.findElement(By.id("resolve_incident_bottom")).click();
		
		//driver.switchTo().frame(0);
		
		String incidentMsg = driver.findElement(By.className("outputmsg_text")).getText();
		System.out.println(incidentMsg);

		if (incidentMsg.contains("resolved")) 
		{
			System.out.println(" ");
			System.out.println(incidentMsg);
			System.out.println("Pass");
			driver.quit();

		} else {
			System.err.println("Fail");
		}
		
		

	}

}
