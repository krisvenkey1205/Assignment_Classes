package pageclasses;

import org.openqa.selenium.NoSuchElementException;

import com.relevantcodes.extentreports.LogStatus;

import junit.framework.Assert;
import pageobjects.CheckOutPage;
import pageobjects.DisplayProductDetailsPage;
import utils.resources.BaseClass;

public class ShoppingCartClass extends BaseClass{

	public CheckOutPage checkShoppingCart(DisplayProductDetailsPage prodDetails){
		String msg = null;
		
		log.info("Create object for CheckOutPage class");
		CheckOutPage checkOut = new CheckOutPage(driver); 			//CheckOutPage object
		
/////////////////////////////////////////////////////////////////////////////////////////		
//Step 8: Verify that that the shopping card is showed
//Step 9: verify the information in Shopping Cart
//-	The product name is the same as the value you stored from last screen
//-	The price is same as the value you stored from last screen
//-	The seller name is same as the value you stored from last screen
//-	In Cart Summary: verify that the total price is equal to the single unit price
//-	Click on “Proceed to checkout” button
///////////////////////////////////////////////////////////////////////////////////////////		
		try{
			log.info("Assert is shopping cart page is displayed");
			msg = "Shopping cart message not displayed in screen";
			Assert.assertEquals("Shopping cart page is not displayed", "Your eBay Shopping Cart", prodDetails.getCart().getText());
			logger.log(LogStatus.PASS, "Shopping Cart page is displayed");
			
			log.info("Assert if product name is same in check out page");
			//Assert product name in chekout page
			msg = "Unable to find product name in Shopping cart page";
			Assert.assertEquals("Product name is not same in check out page", prodDetails.getProdName(), checkOut.getProdname().getText());
			logger.log(LogStatus.PASS, "Product name is same in shopping cart page");
			
			log.info("Assert if price is same in check out page");
			//Assert Price in check out page
			msg = "Unable to find price element in shopping cart page";
			try{
				Assert.assertEquals("Price is not same in check out page", prodDetails.getProdPrice(), checkOut.getPrice().getText());
				logger.log(LogStatus.PASS, "Price is same in shopping cart page");
			}
			catch (NoSuchElementException e)
			{
				Assert.assertEquals("Price is not same in check out page", prodDetails.getProdPrice(), checkOut.getPrice1().getText());
				logger.log(LogStatus.PASS, "Price is same in shopping cart page");
			}
			
			msg = "Unable to find seller name in shopping cart page";
			log.info("Assert Seller name is same in check out page");
			//Assert price in check out page
			Assert.assertEquals("Seller name is not same in check out page", prodDetails.getSellerName(), checkOut.getSellerName().getText());
			logger.log(LogStatus.PASS, "Seller name is same in shopping cart page");
			
			msg = "Unable to find total price in shopping cart page";
			log.info("Assert if total price in check outp page is equal to single unit price");
			//Assert total price in check out page
			Assert.assertEquals("Total price is not equal to single unit price", prodDetails.getProdPrice(), checkOut.getTotalPrice().getText());
			logger.log(LogStatus.PASS, "Total price in shopping cart page is equal to single unit price");
			
			msg = "Unable to find Proceed to Checkout button in the shopping cart page";
			log.info("Click proceed to checkout button");
			checkOut.getProceedButton().click();
		}
		catch (NoSuchElementException e)
		{
			logger.log(LogStatus.FAIL, msg);
			Assert.assertTrue(msg, false);
		}
		return checkOut;
	}
}
