package tests;

import java.io.IOException;

import org.openqa.selenium.JavascriptExecutor;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.relevantcodes.extentreports.LogStatus;

import pageclasses.CheckoutPageClass;
import pageclasses.DisplayListClass;
import pageclasses.DisplayProdDetailsClass;
import pageclasses.LandingPageClass;
import pageclasses.ShoppingCartClass;
import pageclasses.SignInClass;
import pageobjects.CheckOutPage;
import pageobjects.DisplayProductDetailsPage;
import utils.resources.BaseClass;
import utils.resources.DataProviderClass;

public class TS01_LandingPageTest extends BaseClass{
	
	LandingPageClass lpc = null;
	DisplayListClass dlc = null;
	DisplayProdDetailsClass dpdc = null;
	ShoppingCartClass spc = null;
	CheckoutPageClass cpc = null;
	DisplayProductDetailsPage prodDetails = null;
	SignInClass sic = null;
	CheckOutPage checkOut = null;
	
	@BeforeTest
	public void initializeProperty() throws IOException{
		log.info("Callig initialize property to initialize property class");
		initialilzeProperty();
		log.info("Property class created");
		
		log.info("Initialize extent report object");
		initializeReport();
	}
	
	@BeforeMethod
	public void initializeDriv() throws IOException{
		log.info("Calling Initialize driver before test in LandingPageTest class");
		initializeDriver();
		log.info("Driver initialized successfully");	
		
		log.info("Call URL from properties file");
		driver.get(prop.getProperty("url"));
		
		log.info("Assign Javascript Executor");
		jse = ((JavascriptExecutor) driver);
		
		log.info("Create objects for all the page classes");
		lpc = new LandingPageClass();			//Landing page object
		dlc = new DisplayListClass();			//Display List object
		dpdc = new DisplayProdDetailsClass();   //Display prod details object
		spc = new ShoppingCartClass();			//Shopping cart object
		sic = new SignInClass();
		cpc = new CheckoutPageClass();			//Checkoutpage class
		log.info("Objects for all page classes created");
	}
	
	@Test(dataProvider="getData")
	public void ebayCheckout(String searchTerm){
		System.out.println(searchTerm);
		logger = extent.startTest("ebay checkout. Search Term:"+searchTerm);
		setSearchterm(searchTerm);
		lpc.checkLandingPage();   //Call method to check landing page
		dlc.checkDisplayList();   //Call method to check displayed list
		prodDetails = dpdc.checkProdDetails(); //Call method to check product details page
		checkOut = spc.checkShoppingCart(prodDetails);  //call method to check shopping cart page
		sic.signIn();                                  //Call method to signin
		cpc.checkCheckoutPage(prodDetails,checkOut);  //Call method to check checkout page
	}
	
	@AfterMethod
	public void handleError(ITestResult itr){
		if(itr.getStatus() == ITestResult.FAILURE){
			logger.log(LogStatus.FAIL, "Test Case Failed is "+itr.getName());
			logger.log(LogStatus.FAIL, "Test Case Failed is "+itr.getThrowable()); 			
            String screenshotPath = null;
			try {
				screenshotPath = getScreenshot(itr.getName());
			} catch (IOException e) {}
			
			logger.log(LogStatus.FAIL, logger.addScreenCapture(screenshotPath));
		}
		extent.endTest(logger);
		jse = null;
		lpc = null;
		dlc = null;
		dpdc = null;
		spc = null;
		cpc =  null;
		
		//Thread.sleep(3000);
		driver.close();
		driver = null;
	}	
	
	@DataProvider
	public String[] getData() throws IOException{
		String[] result;
		log.info("Start @DataProvider");
		DataProviderClass dpc = new DataProviderClass();
		log.info("Call GetData method in DPC class to read excel file");
		result= dpc.getData();
		log.info("No. of entries in input file:"+result.length);
		log.info("Returning result");
		return result;
	}
	
	@AfterTest
	public void tearDown(){
		extent.flush();
		extent.close();
	}
}	