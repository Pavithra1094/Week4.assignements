package week4.day2.assignment.servicecategory;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;

import io.github.bonigarcia.wdm.WebDriverManager;

public class Deleteorder {

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

// 3. Enter Service catalog in filter navigator and press enter
		WebElement filterNavigator = driver.findElement(By.id("filter"));
		filterNavigator.sendKeys("Service Catalog");
		filterNavigator.sendKeys(Keys.ENTER);
		// Thread.sleep(1000);

// 4. Click on mobiles
		// driver.findElement(By.cssSelector("td#dropzone2>div:nth-of-type(4)>div>div:nth-of-type(2)>div>a>table>tbody>tr>td:nth-of-type(2)>a>span>h2")).click();
		driver.switchTo().frame(0);
		driver.findElement(By.xpath("//h2[text()[normalize-space()='Mobiles']]")).click();

// 5.Select Apple iphone6s
		driver.findElement(By.xpath("//strong[text()='Apple iPhone 6s']")).click();

// 6.Update Monthly allowances field to Unlimited ,color field to rose gold and storage field to 128GB

		WebElement monthlyAllowances = driver.findElement(By.xpath("//div[contains(@class,'col-xs-6 ')]//select"));
		new Select(monthlyAllowances).selectByIndex(2); // Unlimited

		WebElement mobileColor = driver.findElement(By.xpath("(//div[contains(@class,'col-xs-6 ')]//select)[2]"));
		new Select(mobileColor).selectByIndex(3); // Rose Gold

		WebElement mobileStorage = driver.findElement(By.xpath("(//div[contains(@class,'col-xs-6 ')]//select)[3]"));
		new Select(mobileStorage).selectByIndex(2); // 128 GB

// 7.Select Order now option
		driver.findElement(By.id("oi_order_now_button")).click();

// 8.Verify order is placed and copy the request numbe
		String mobileBrand = driver.findElement(By.cssSelector("div#sc_cart_view>table>tbody>tr>td>div>a")).getText();

		String orderStatusMsg = driver.findElement(By.cssSelector("div#sc_order_status_intro_text>div>span")).getText();

		String orderRequestNumber = driver.findElement(By.cssSelector("a#requesturl>b")).getText();

		String orderPlacedRequest = driver.findElement(By.cssSelector("div#sc_order_status_intro_text>dl")).getText();

		String expectedResult = "No records to display";

		// Click on Back to catalog button

		// Click on Requests option
		driver.switchTo().defaultContent();
		driver.findElement(By.xpath("//div[text()='Requests']")).click();
		driver.switchTo().frame(0);

		WebElement enterOrderNum = driver.findElement(By.xpath("//input[@placeholder='Search']"));
		enterOrderNum.click();
		enterOrderNum.sendKeys(orderRequestNumber);
		enterOrderNum.sendKeys(Keys.ENTER);

		// Select the shown request number
		WebElement resultantNumber = driver.findElement(By.xpath("//a[@class='linked formlink']"));
		System.out.println("Incident Number to be delete: " + resultantNumber.getText());
		System.out.println(" ");
		resultantNumber.click();

		// Delete the order
		driver.findElement(By.id("sysverb_delete")).click();
		driver.findElement(By.id("ok_button")).click();

		driver.switchTo().defaultContent();
		driver.findElement(By.xpath("//div[text()='Requests']")).click();
		driver.switchTo().frame(0);

		WebElement enterOrderNum1 = driver.findElement(By.xpath("//input[@placeholder='Search']"));
		enterOrderNum1.click();
		enterOrderNum1.sendKeys(orderRequestNumber);
		enterOrderNum1.sendKeys(Keys.ENTER);

		String verifyDeleteStatus = driver.findElement(By.xpath("//td[text()='No records to display']")).getText();

		if (verifyDeleteStatus.equalsIgnoreCase(expectedResult)) {
			System.out.println("Your request number: " + orderRequestNumber + " has been deleted successfully");
			System.out.println(" ");
			System.out.println(mobileBrand + orderPlacedRequest);
			System.out.println(" ");
			System.out.println(orderStatusMsg);
			System.out.println(" ");
			System.out.println("Test Case passed - closing window");
			driver.quit();

		} else {
			System.err.println("TestCase failed");
		}
	}
}