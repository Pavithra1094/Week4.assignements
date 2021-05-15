package week4.day2.assignment.servicecategory;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;

import io.github.bonigarcia.wdm.WebDriverManager;

//import java.util.concurrent.TimeUnit;



public class OrderNewMobile {

	public static void main(String[] args) throws Exception {
		WebDriverManager.chromedriver().setup();
		ChromeDriver driver = new ChromeDriver();
		JavascriptExecutor js = (JavascriptExecutor) driver;
		
		driver.get("https://dev103117.service-now.com");
		driver.manage().window().maximize();
		
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		
		driver.switchTo().frame(0);
		driver.findElement(By.id("user_name")).sendKeys("admin");
		driver.findElement(By.id("user_password")).sendKeys("India@123");
		driver.findElement(By.id("sysverb_login")).click();
		WebElement findElement = driver.findElement(By.id("filter"));
		findElement.sendKeys("Service catalog");
		Thread.sleep(3000);
		findElement.sendKeys(Keys.ENTER);
		
		driver.findElement(By.xpath("//div[text()='Service Catalog']")).click();
		driver.switchTo().frame(0);
		driver.findElement(By.xpath("//h2[text()[normalize-space()='Mobiles']]")).click();
		
		WebElement mobiles = driver.findElement(By.xpath("//strong[text()='Samsung Galaxy S7']"));
		js.executeScript("arguments[0].scrollIntoView();", mobiles);
		
		WebElement findElement2 = driver.findElement(By.xpath("//strong[text()='Apple iPhone 6s']"));
		findElement2.click();
		
		
		WebElement appleMobile =driver.findElement(By.xpath("//div[text()='Exists in categories']"));
		js.executeScript("arguments[0].scrollIntoView();", appleMobile);
		
		WebElement monthlyDataAllowance = driver.findElement(By.xpath("//select[@class='form-control cat_item_option ']")); 
		new Select(monthlyDataAllowance).selectByIndex(2); 
		
		WebElement color = driver.findElement(By.xpath("(//select[@class='form-control cat_item_option '])[2]")); 
		new Select(color).selectByIndex(3); 
		
		WebElement storage = driver.findElement(By.xpath("(//select[@class='form-control cat_item_option '])[3]")); 
		new Select(storage).selectByIndex(2); 
		
		
		driver.findElement(By.id("oi_order_now_button")).click();
		
		String Ordermsg = driver.findElement(By.xpath("//span[text()='Thank you, your request has been submitted']")).getText();
		
		System.out.println(Ordermsg);
		String requestNumber = driver.findElement(By.xpath("//a[@class='linked requestItemUrl']//b[1]")).getText();
		
		System.out.println("Order Number : " +requestNumber);

		
		
	}

}