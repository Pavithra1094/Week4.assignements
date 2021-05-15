package week4.day2.assignment.incidentmgmt;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import io.github.bonigarcia.wdm.WebDriverManager;

public class NewIncidentErr {

	public static void main(String[] args) throws InterruptedException {
		WebDriverManager.chromedriver().setup();

		ChromeDriver driver = new ChromeDriver();

		driver.get("https://dev103117.service-now.com/");
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);


				driver.switchTo().frame(0);

				driver.findElement(By.id("user_name")).sendKeys("admin");
				driver.findElement(By.id("user_password")).sendKeys("India@123");
				driver.findElement(By.xpath("//button[text()='Log in']")).click();

				WebElement incident = driver.findElement(By.id("filter"));
				incident.sendKeys("Incident");
				incident.sendKeys(Keys.ENTER); 
		
				driver.findElement(By.xpath("//div[text()='Create New']")).click();
				driver.switchTo().frame(0);

				String incidentNum = driver.findElement(By.name("incident.number")).getAttribute("value");
				System.out.println("Incident Number: " + incidentNum);
				
				driver.findElement(By.xpath("(//button[text()='Submit'])[2]")).click();
				
		
				String errorMsg = driver.findElement(By.className("outputmsg_text")).getText();
				System.out.println(errorMsg);
		

				if (driver.findElement(By.className("outputmsg_text")).isDisplayed())
				{
					System.out.println("Error Message: " + errorMsg);
					System.out.println(" ");
					System.out.println("Pass");
					driver.quit();

				} else {
					System.err.println("Fail");
				}
				
				
	}

}
