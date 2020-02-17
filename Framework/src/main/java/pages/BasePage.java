package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class BasePage {
	
static WebDriver driver;
static String driverPath = "C:\\Users\\win-8\\Desktop\\SeleniumDrivers\\chromedriver.exe";

private static WebDriver initChromeDriver(String appURL) {
	System.out.println("Launching google chrome with new profile..");
	System.setProperty("webdriver.chrome.driver", driverPath
			+ "chromedriver.exe");
	WebDriver driver = new ChromeDriver();
	driver.manage().window().maximize();
	driver.navigate().to(appURL);
	return driver;
}








}
