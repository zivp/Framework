package pages;

import java.io.File;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import Data.Log;

public class BasePage {
	
static WebDriver driver;
static String driverPath = "C:\\Users\\win-8\\Desktop\\SeleniumDrivers\\chromedriver.exe";
static JavascriptExecutor js;
static final Logger LOG = LogManager.getLogger(BasePage.class.getName());


public static WebDriver initChromeDriver(String appURL) {
	LOG.info("Launching google chrome with new profile..");
	// System.setProperty("http.proxyHost", "192.168.0.1");
  //   System.setProperty("http.proxyPort", "8080");
	System.setProperty("webdriver.chrome.driver", driverPath);
	WebDriver driver = new ChromeDriver();
	driver.get(appURL);
	driver.manage().window().maximize();
	driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
	return driver;
}
	


// return WebElement by locator and type
public static WebElement getElement(String locator, String type) {
	type = type.toLowerCase();
	if (type.equals("id")) {
		System.out.println("Element found with id: " + locator);
		return driver.findElement(By.id(locator));
	}
	else if (type.equals("name")) {
		System.out.println("Element found with name: " + locator);
		return driver.findElement(By.name(locator));
	}
	else if (type.equals("xpath")) {
		System.out.println("Element found with xpath: " + locator);
		return driver.findElement(By.xpath(locator));
	}
	else if (type.equals("css")) {
		System.out.println("Element found with css: " + locator);
		return driver.findElement(By.cssSelector(locator));
	}
	else if (type.equals("classname")) {
		System.out.println("Element found with classname: " + locator);
		return driver.findElement(By.className(locator));
	}
	else if (type.equals("tagname")) {
		System.out.println("Element found with tagname: " + locator);
		return driver.findElement(By.tagName(locator));
	}
	else if (type.equals("linktext")) {
		System.out.println("Element found with link text: " + locator);
		return driver.findElement(By.linkText(locator));
	}
	else if (type.equals("partiallinktext")) {
		System.out.println("Element found with partial link text: " + locator);
		return driver.findElement(By.partialLinkText(locator));
	}
	else {
		System.out.println("Locator type not supported");
		return null;
	}
}

// return List of WebElement
public static List<WebElement> getElementList(String locator, String type) {
	type = type.toLowerCase();
	List<WebElement> elementList = new ArrayList<WebElement>();
	if (type.equals("id")) {
		elementList = driver.findElements(By.id(locator));
	}
	else if (type.equals("name")) {
		elementList = driver.findElements(By.name(locator));
	}
	else if (type.equals("xpath")) {
		elementList = driver.findElements(By.xpath(locator));
	}
	else if (type.equals("css")) {
		elementList = driver.findElements(By.cssSelector(locator));
	}
	else if (type.equals("classname")) {
		elementList = driver.findElements(By.className(locator));
	}
	else if (type.equals("tagname")) {
		elementList = driver.findElements(By.tagName(locator));
	}
	else if (type.equals("linktext")) {
		elementList = driver.findElements(By.linkText(locator));
	}
	else if (type.equals("partiallinktext")) {
		elementList = driver.findElements(By.partialLinkText(locator));
	}
	else {
		System.out.println("Locator type not supported");
	}
	if (elementList.isEmpty()) {
		System.out.println("Element not found with " + type +": " + locator);
		
	} else {
		System.out.println("Element found with " + type +": " + locator);
	}
	return elementList;
}

// if size of List WebElement > 0 
public static boolean isElementPresent(String locator, String type) {
	List<WebElement> elementList = getElementList(locator, type);
	
	int size = elementList.size();
	
	if (size > 0) {
		return true;
	}
	else {
		return false;
	}
}

public static WebElement waitForElement(By locator, int timeout) {
	WebElement element = null;
	try {
		LOG.info("Waiting for max:: " + timeout + " seconds for element to be available");
		
		WebDriverWait wait = new WebDriverWait(driver, 3);
		element = wait.until(
				ExpectedConditions.visibilityOfElementLocated(locator));
		LOG.info("Element appeared on the web page");	
	} catch(Exception e) {
		LOG.error("Element not appeared on the web page");
	}
	return element;
}

public static void clickWhenReady(By locator, int timeout) {
	try {
		WebElement element = null;
		LOG.info("Waiting for max:: " + timeout + " seconds for element to be clickable");
		
		WebDriverWait wait = new WebDriverWait(driver, 3);
		element = wait.until(
				ExpectedConditions.elementToBeClickable(locator));
		element.click();
		LOG.info("Element clicked on the web page");	
	} catch(Exception e) {
		LOG.error("Element not appeared on the web page");
	}
}


public static String getRandomString(int length) {
	StringBuilder sb = new StringBuilder();
	String characters = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
	for (int i = 0; i < length; i++) {
		int index = (int)(Math.random() * characters.length());
		sb.append(characters.charAt(index));
	}
	return sb.toString();
}

	
public static void tearDown() throws Exception {
	String filename = getRandomString(10) + ".png";
	String directory = System.getProperty("user.dir") + "//screenshots//";
	File sourceFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
	FileUtils.copyFile(sourceFile, new File(directory + filename));


	
	
}

//Screen Shot
public static void takeSnapShot(WebDriver driver) throws Exception{
	{
		File srcFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
		try {
		FileUtils.copyFile(srcFile, new File("C:/Users/win-8/Erorr_Png/tests.jpg"));
		} catch (IOException e) {
		e.printStackTrace();
		}
	}

}

public static void ClickJS(WebElement Elament,WebDriver driver ) throws Exception{

	try {
	WebDriverWait wait = new WebDriverWait(driver, 3);
	Elament = wait.until(
			ExpectedConditions.elementToBeClickable(Elament));
	js.executeScript("arguments[0].click();", Elament);
	LOG.info("Element clicked on the web page");	} 
	catch(Exception e) {
		LOG.error("Element not appeared on the web page");
	}
	
	
	
}

//get all links To Click ('a') and put them in list
public static List<WebElement> clickableLinks(WebDriver driver) {
	List<WebElement> linksToClick = new ArrayList<WebElement>();
	List<WebElement> elements = driver.findElements(By.tagName("a"));
	elements.addAll(driver.findElements(By.tagName("img")));
	
	for (WebElement e : elements) {
		if (e.getAttribute("href") != null) {
			linksToClick.add(e);
		}
	}
	return linksToClick;
}

//return response message from url and disconnect.
public static String linkStatus(URL url) {
	try {
		HttpURLConnection http = (HttpURLConnection) url.openConnection();
		http.connect();
		String responseMessage = http.getResponseMessage();
		http.disconnect();
		return responseMessage;
	}
	catch (Exception e) {
		return e.getMessage();
	}
}






}
