package pageclasses;

import org.openqa.selenium.NoSuchElementException;

import com.relevantcodes.extentreports.LogStatus;

import junit.framework.Assert;
import pageobjects.LandingPage;
import utils.resources.BaseClass;

public class LandingPageClass extends BaseClass{

	public void checkLandingPage(){
		String msg = null;
		
		log.info("Create object for landing page object class");
		LandingPage landpg = new LandingPage(driver);				//Landing Page object
		
/////////////////////////////////////////////////////////////////////////////////////////		
//1: go to www.ebay.com and search for “sony tv” (this text is should be reading from file)
//2: check that the list of related products are showed, like below screenshot 
///////////////////////////////////////////////////////////////////////////////////////////		
		try{
			msg = "Unable to locate search text bar in the page";
			landpg.getSearchText().sendKeys(searchTerm);
			msg = "Unable to locate Search button";
			log.info("Click on search button");
			landpg.getSearchButton().click();
		}
		catch (NoSuchElementException e)
		{
			logger.log(LogStatus.FAIL, msg);
			Assert.assertTrue(msg, false);
		}
	}	
}
