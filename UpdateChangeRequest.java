package week4.day2.assignment.changemgmt;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import io.github.bonigarcia.wdm.WebDriverManager;

public class UpdateChangeRequest {

	public static void main(String[] args) throws InterruptedException {
		WebDriverManager.chromedriver().setup();
		ChromeDriver driver = new ChromeDriver();
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        //Login
		driver.get("https://dev103117.service-now.com");

		driver.switchTo().frame(0);

		driver.findElement(By.id("user_name")).sendKeys("admin");

		driver.findElement(By.id("user_password")).sendKeys("India@123");

		driver.findElement(By.id("sysverb_login")).click();

		//Open existing Change 
		Thread.sleep(2000);
		driver.findElement(By.xpath("//input[@id='filter']")).sendKeys("change", Keys.ENTER);

		driver.findElement(By.xpath("(//div[text()='Open'])[3]")).click();

		WebElement iframe = driver.findElement(By.xpath("//iframe[@id='gsft_main']"));
		driver.switchTo().frame(iframe);

		WebElement search = driver.findElement(By.xpath("(//input[@class='form-control'])[1]"));
		search.sendKeys("CHG0030929", Keys.ENTER);
		
		
		Thread.sleep(2000);
		String firstEle = driver.findElement(By.xpath("(//td[@class='vt'])[1]/a")).getText();
		System.out.println(firstEle);
		Thread.sleep(2000);
		driver.findElement(By.xpath("(//td[@class='vt'])[1]/a")).click();
		
		

		// Click on schedule option
		driver.findElement(By.xpath("//span[text()='Schedule']")).click();

		// Click on start date - calender icon
		// driver.findElement(By.xpath("(//button[@id='change_request.start_date.ui_policy_sensitive']//span)[3]")).click();
		driver.findElement(By.xpath("//span[@class='icon-calendar icon']")).click();

		// click on start date
		// driver.findElement(By.xpath("(//a[@class='calOtherMonthDateAnchor'])[2]")).click();
		driver.findElement(By.xpath("//td[@class='calText calCurrentDate']//a[1]")).click();

		// Save start date
		WebElement dateTimePickerFrom = driver.findElement(By.id("GwtDateTimePicker_ok"));
		dateTimePickerFrom.click();

		// Get Planned Start date/time
		String startdate = driver.findElement(By.id("change_request.start_date")).getAttribute("value");

		// click on end date
		driver.findElement(By.xpath("(//button[@id='change_request.end_date.ui_policy_sensitive'])[2]")).click();
		// driver.findElement(By.xpath("//a[@id='GwtDateTimePicker_day24']")).click();
		WebElement dateTimePickerTo = driver.findElement(By.id("GwtDateTimePicker_ok"));
		dateTimePickerTo.click();

		// Get planned end date/time
		String enddate = driver.findElement(By.id("change_request.end_date")).getAttribute("value");

		// Select CAB Required and CAB date

		// Click on CSB required check box
		driver.findElement(By.id("label.ni.change_request.cab_required")).click();

		// Click on CAB date calendar icon and select date
		driver.findElement(By.xpath("//a[@data-date_time_format='yyyy-MM-dd']//span[1]")).click();
		driver.findElement(By.xpath("//a[@id='GwtDateTimePicker_day36']")).click();

		System.out.println("Schedule has been updated with date");
		System.out.println(" ");

		// Update the Change Request and verify the changes

		driver.findElement(By.id("sysverb_update_bottom")).click();
		
		//String changeNum = driver.findElement(By.xpath("//input[@id='change_request.number']")).getAttribute("value");
		String changeNum = driver.findElement(By.xpath("//td[@class='vt']//a[1]")).getAttribute("value");
		System.out.println(changeNum);
		
		String plannedStartDate = driver.findElement(By.xpath("(//div[@class='datex date-calendar'])[1]")).getText();
	    String plannedEndDate =  driver.findElement(By.xpath("(//div[@class='datex date-calendar'])[2]")).getText();
	    
	    if(plannedStartDate.equalsIgnoreCase(startdate)&&plannedEndDate.equalsIgnoreCase(enddate))
	    {
	    	System.out.println("Updated schedule for the existing Change");
			driver.quit();

	    }
	    else
	    {
	    	System.out.println("Not Updated");
	    }

	}

}