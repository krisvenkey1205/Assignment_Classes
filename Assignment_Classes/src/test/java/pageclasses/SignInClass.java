package pageclasses;

import org.openqa.selenium.NoSuchElementException;

import com.relevantcodes.extentreports.LogStatus;

import junit.framework.Assert;
import pageobjects.EbayLoginPage;
import utils.resources.BaseClass;

public class SignInClass extends BaseClass{
	
	public void signIn(){
		String msg = null;
		
		log.info("Create object for EbayLoginPage class");
		EbayLoginPage login = new EbayLoginPage(driver); 			//Login page object
		
/////////////////////////////////////////////////////////////////////////////////////////		
//Step 10: Proceed checkout as guest
///////////////////////////////////////////////////////////////////////////////////////////		
		try{
			log.info("Sign in to review cart");
			msg = "Unable to find username element in SignIn page";
			login.getUsername().sendKeys(prop.getProperty("username"));
			msg = "Unable to find password element in SignIn page";
			login.getPassword().sendKeys(prop.getProperty("password"));
			msg = "Unable to find Stay signed in checkbox element in SignIn page";
			login.getStaySign().click();
			msg = "Unable to find Signin button in SignIn page";
			login.getSubmit().click();
		}
		catch (NoSuchElementException e)
		{
			logger.log(LogStatus.FAIL, msg);
			Assert.assertTrue(msg, false);
		}
	}

}
