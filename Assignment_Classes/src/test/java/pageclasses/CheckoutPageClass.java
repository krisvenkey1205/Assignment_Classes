package pageclasses;

import org.openqa.selenium.NoSuchElementException;

import com.relevantcodes.extentreports.LogStatus;

import junit.framework.Assert;
import pageobjects.CheckOutPage;
import pageobjects.DisplayProductDetailsPage;
import utils.resources.BaseClass;

public class CheckoutPageClass extends BaseClass{
	
	public void checkCheckoutPage(DisplayProductDetailsPage prodDetails, CheckOutPage checkOut){
		String msg = null;
		
/////////////////////////////////////////////////////////////////////////////////////////		
//11: check that the guest checkout screen is showed
///////////////////////////////////////////////////////////////////////////////////////////
		try{
		if(checkOut.getProdNameCheckout().isDisplayed() == false){
			jse.executeScript("window.scrollTo(0, document.body.scrollHeight);");
		}
		
		msg = "Product name element not found in checkout page";
		Assert.assertEquals("Product name is not same in checkout screen", prodDetails.getProdName() , checkOut.getProdNameCheckout().getText());
		logger.log(LogStatus.PASS, "Product name is same in checkout screen");
		
		msg = "Order total element not found in checkout page";
		Assert.assertEquals("Order total is not equal to single unit in checkout page",prodDetails.getProdPrice() , checkOut.getOrderTotal().getText());
		logger.log(LogStatus.PASS, "Order total is equal to single unit price in checkout page");
		logger.log(LogStatus.PASS,"Test Case passed");
		}
		catch (NoSuchElementException e)
		{
			logger.log(LogStatus.FAIL, msg);
			Assert.assertTrue(msg, false);
		}
	}
	
}
