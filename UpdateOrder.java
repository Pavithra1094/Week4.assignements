package week4.day2.assignment.servicecategory;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
//import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;

import io.github.bonigarcia.wdm.WebDriverManager;

public class UpdateOrder {

	public static void main(String[] args) throws Exception {
		WebDriverManager.chromedriver().setup();
		ChromeDriver driver = new ChromeDriver();
		//JavascriptExecutor js = (JavascriptExecutor) driver;
		
		driver.get("https://dev103117.service-now.com");
		driver.manage().window().maximize();
		
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		
		driver.switchTo().frame(0);
		driver.findElement(By.id("user_name")).sendKeys("admin");
		driver.findElement(By.id("user_password")).sendKeys("India@123");
		driver.findElement(By.id("sysverb_login")).click();
		WebElement findElement = driver.findElement(By.id("filter"));
		findElement.sendKeys("My Requests");
		Thread.sleep(3000);
		findElement.sendKeys(Keys.ENTER);
		//REQ0010185
		
		driver.findElement(By.xpath("//div[text()='My Requests']")).click();
		driver.switchTo().frame(0);
		driver.findElement(By.cssSelector("div#list_nav_task>div>div>h1>a")).click();
		driver.findElement(By.xpath("//div[text()='View']")).click();
		driver.findElement(By.xpath("//div[text()='Default view']")).click();
		
		
		WebElement findElement3 = driver.findElement(By.xpath("//input[@placeholder='Search']"));
		findElement3.click();
		findElement3.sendKeys("REQ0010185");
		
		Thread.sleep(2000);
		
		driver.findElement(By.xpath("//a[@class='linked formlink']")).click();
		
		WebElement screquestapproval = driver.findElement(By.id("sc_request.approval")); 
		new Select(screquestapproval).selectByIndex(1); 
		
		WebElement screquestrequeststate = driver.findElement(By.id("sc_request.request_state")); 
		new Select(screquestrequeststate).selectByIndex(2); 
		
		WebElement findElement4 = driver.findElement(By.id("sc_request.description"));
		findElement4.click();
		findElement4.sendKeys("Test");
		
		driver.findElement(By.id("sysverb_update_bottom")).click();
		String description = driver.findElement(By.cssSelector("table#task_table>tbody>tr>td:nth-of-type(7)")).getText();
		System.out.println("Order successfully updated" +description );

		
		
		
		
		
		
	}

}
