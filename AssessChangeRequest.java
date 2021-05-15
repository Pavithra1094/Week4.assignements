package week4.day2.assignment.changemgmt;


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

	public class AssessChangeRequest {

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

	// 3. Enter Change in filter navigator and press enter"
			WebElement changeManagement = driver.findElement(By.id("filter"));
			changeManagement.sendKeys("Change");
			changeManagement.sendKeys(Keys.ENTER);

	// 4. Click open and search existing change

			driver.findElement(By.xpath("(//div[text()='Open'])[3]")).click();

			driver.switchTo().frame(0);

			Select searchNumberOption = new Select(
					driver.findElement(By.cssSelector("span#change_request_hide_search>div>div>span>span>select")));
			searchNumberOption.selectByIndex(1);

			WebElement searchCHG = driver.findElement(By.xpath("(//label[text()='Search'])[2]/following::input"));
			searchCHG.click();
			searchCHG.sendKeys("CHG0030929");
			searchCHG.sendKeys(Keys.ENTER);

			// Click on Number hyperlink
			String changeNum = driver.findElement(By.cssSelector("table#change_request_table>tbody>tr>td:nth-of-type(3)"))
					.getText();
			driver.findElement(By.cssSelector("table#change_request_table>tbody>tr>td:nth-of-type(3)>a")).click();

	// 5. update the State as "Assess"

			Select changeRequestState = new Select(driver.findElement(By.id("change_request.state")));
			changeRequestState.selectByIndex(1);
			System.out.println("Updated Change Request state value");
			System.out.println(" ");

	// 6. Assign the request to 'ITIL User' and 'Software' in Assignment group and click 'Update'.

			driver.findElement(By.id("lookup.change_request.assignment_group")).click(); // Lookup using List

			Set<String> allWindowHandles = driver.getWindowHandles();
			System.out.println("WindowHandling: " + allWindowHandles.size());
			List<String> lstWindowHandles = new ArrayList<String>(allWindowHandles);
			System.out.println(" ");

			// Switch to sub-window
			driver.switchTo().window(lstWindowHandles.get(1));
			System.out.println("Sub-Window Title: " + driver.getTitle());
			System.out.println("Sub-Window URL: " + driver.getCurrentUrl());
			System.out.println(" ");

			driver.findElement(
					By.xpath("//div[@id='treenode_0']/div[1]/div[1]/div[29]/table[1]/tbody[1]/tr[1]/td[1]/img[1]")).click(); // click
																																// on
																																// Softwaredropdown
			WebElement assignGroup = driver.findElement(By.cssSelector(
					"div#treenode_0>div>div>div:nth-of-type(29)>div>div:nth-of-type(5)>table>tbody>tr>td:nth-of-type(3)>a"));
			assignGroup.click();
			// driver.close();

			driver.switchTo().window(lstWindowHandles.get(0));

			System.out.println("Parent-Window Title: " + driver.getTitle());
			System.out.println("Parent-Window URL: " + driver.getCurrentUrl());
			System.out.println(" ");
			driver.switchTo().frame(0);

			System.out.println("Updated assign Group as Software");
			System.out.println(" ");

			// Enter AssignedTo value

			// driver.findElement(By.id("lookup.incident.assigned_to")).click();
			driver.findElement(By.xpath("//button[@id='lookup.change_request.assigned_to']//span[1]")).click(); // Lookup
																												// using
			// List

			// Switch to sub-window
			allWindowHandles = driver.getWindowHandles();
			lstWindowHandles = new ArrayList<String>(allWindowHandles);
			System.out.println("Window Handling: " + allWindowHandles.size());
			driver.switchTo().window(lstWindowHandles.get(1));
			System.out.println("Sub-Window Title: " + driver.getTitle());
			System.out.println("Sub-Window URL: " + driver.getCurrentUrl());
			System.out.println(" ");

			WebElement assignmentTo = driver.findElement(By.xpath("(//label[text()='Search'])[2]/following::input"));
			assignmentTo.sendKeys("ITIL");
			assignmentTo.sendKeys(Keys.ENTER);
			driver.findElement(By.linkText("ITIL User")).click();

			Thread.sleep(1000);

			driver.switchTo().window(lstWindowHandles.get(0));

			driver.switchTo().frame(0);

			driver.findElement(By.cssSelector("button#sysverb_update_bottom")).click();

	// 7. Verify the State and Assigned to update [The Change should have state as "Assess" and assigned to ITIL user]

			String verifychangeNo = driver
					.findElement(By.cssSelector("table#change_request_table>tbody>tr>td:nth-of-type(3)")).getText();

			String verifyAssignedT0 = driver
					.findElement(By.cssSelector("table#change_request_table>tbody>tr>td:nth-of-type(10)")).getText();

			String verifyStateValue = driver
					.findElement(By.cssSelector("table#change_request_table>tbody>tr>td:nth-of-type(6)")).getText();

			if ((verifychangeNo.equalsIgnoreCase(changeNum)) && (verifyAssignedT0.equalsIgnoreCase("ITIL User"))
					&& verifyStateValue.equalsIgnoreCase("Assess")) {
				System.out.println("Change Reuest: " + verifychangeNo + " ; " + " Assigned To: " + verifyAssignedT0 + " ; "
						+ "State: " + verifyStateValue);
				System.out.println(" ");
				System.out.println("Assess Change Request >> Test Case passed - closing window");
				driver.quit();

			} else {
				System.err.println("Error!! " + "Assess Change Request >> TestCase failed");
			}

		}

	}
