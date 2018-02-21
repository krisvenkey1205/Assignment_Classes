package utils.resources;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;

import junit.framework.Assert;

public class BaseClass {
	
	public static Logger log = LogManager.getLogger(BaseClass.class.getName());
	public static WebDriver driver;
	public static Properties prop;
	public static String searchTerm, prodName;
	public static ExtentReports extent;
	public static ExtentTest logger;
	public static JavascriptExecutor jse;
	
	//MEthod to set searchTerm
	public void setSearchterm(String str){
		searchTerm = str;
	}
	
	//MEthod to initialize property
	public void initialilzeProperty() throws IOException
	{
		//Load properties file through File Input Stream	
				prop = new Properties();
				log.info("Assign properties file to prop");		
						
				FileInputStream fis = new FileInputStream("C:\\workspace\\Assignment_Classes\\src\\main\\java\\utils\\resources\\PropertyFile.properties");
				prop.load(fis);
	}
	
	//Method to initialize extent report objects
	public void initializeReport(){
		DateFormat dateFormat = new SimpleDateFormat("ddMMyyyy_HHmmss");
		Date date = new Date();
		
		log.info("Create extent report objects");
		extent = new ExtentReports (System.getProperty("user.dir") +"/ExtentReports/ExtentReport_"+dateFormat.format(date)+".html", true);
		extent.addSystemInfo("OS", System.getProperty("os.name"))
              .addSystemInfo("Java Version", System.getProperty("java.version"))
              .addSystemInfo("User Name", System.getProperty("user.name"));
        extent.loadConfig(new File(System.getProperty("user.dir")+"\\src\\main\\java\\utils\\resources\\extent-config.xml"));
	}
	
	//Method to initialize WebDriver 
	public void initializeDriver() throws IOException{
		
		String browserName = null;
		int implicitWait = 0;
		
		//Read browser name from properties file
		log.info("Get property value of browser");
		browserName=prop.getProperty("browser");
		log.info("Browser property value:"+browserName);
		
		//Assert if browser name is initial
		log.info("Assert if browser value is maintained");
		Assert.assertNotNull(browserName,"Please maintain browser property in propeties file");
		
		//Create web driver based on browser value
		if(browserName.equals("chrome")){
		//Create chrome driver and set chrome options to disable
		//infobars
			log.info("Creating chrome driver");
			System.setProperty("webdriver.chrome.driver", "C:\\chromedriver.exe");
			ChromeOptions options = new ChromeOptions();
			options.addArguments("disable-infobars");
			options.addArguments("--incognito");
			driver = new ChromeDriver(options);
			log.info("Chrome Driver created successfully");
		}
		
		else if(browserName.equals("firefox")){
		//Create Firefox webdriver
			log.info("Create Firefox Driver");
			System.setProperty("webdriver.gecko.driver","C:\\geckodriver.exe");
			driver = new FirefoxDriver();
			log.info("Firefox driver created successfully");
		}
		
		else if(browserName.equals("ie")){
		//Create Internet Explorer driver 
			log.info("Create IE Driver");
			System.setProperty("webdriver.ie.driver", "C:\\IEDriverServer.exe");
			driver = new InternetExplorerDriver();
			log.info("IE Driver created successfully");
		}	
		
		//Maximize the brower window on load
		log.info("Maximizing driver window");
		driver.manage().window().maximize();
		
		//Check if timeout property is maintained in properties file
		//If yes, set the implicit wait time
		log.info("Checking if implicit wait property is available");
		implicitWait = Integer.parseInt(prop.getProperty("timeout"));
		if(implicitWait >0){
			driver.manage().timeouts().implicitlyWait(implicitWait, TimeUnit.SECONDS);
		}	
		log.info("Returning driver object");
	}
	
	//Method to check price format
	public boolean checkPriceFormat(String price){
		return price.matches("^\\$(([1-9]\\d{0,2}(,\\d{3})*)|(([1-9]\\d{3})?\\d))(\\.\\d{2})?$");
	}
	
	//Method to check timeleft format
	public boolean checkTimeLeft(String timeLeft){
		return timeLeft.matches("\\d{1,2}[dhm] \\d{1,2}[hms]");
	}
	
	//Method to get screen shot
	public String getScreenshot(String name) throws IOException{
		DateFormat dateFormat = new SimpleDateFormat("ddMMyyyy_HHmmss");
		Date date = new Date();
		String str = "C:\\workspace\\Assignment_Classes\\Screenshot\\"+name+"_"+dateFormat.format(date)+".png";
		File src = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
		FileUtils.copyFile(src,new File(str));
		return str;
	}
}
