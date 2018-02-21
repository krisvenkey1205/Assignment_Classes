package pageclasses;

import org.openqa.selenium.NoSuchElementException;

import com.relevantcodes.extentreports.LogStatus;

import junit.framework.Assert;
import pageobjects.DisplayProductDetailsPage;
import utils.resources.BaseClass;

public class DisplayProdDetailsClass extends BaseClass{

	
	public DisplayProductDetailsPage checkProdDetails(){
		
		String itemCondition, timeLeft, price, msg = null;
		
		log.info("Create object for Display product details page class");
		DisplayProductDetailsPage prodDetails = new DisplayProductDetailsPage(driver); //DisplayProductDetailsPage object
		
/////////////////////////////////////////////////////////////////////////////////////////		
//7: Verify below information:
//-	Item condition: is not empty
//-	Time left: either in following format: xxd yyh (example 10d 08h), xxh yym (02h 35m), or xxm yys (30m 45s)
//-	Price in correct format: $X,XXX.YY
//-	Create an object to store: product name, price, seller name to compare with other screens
//-	Click on “Add to cart” button. If any popup for additional purchase showed, just close it
///////////////////////////////////////////////////////////////////////////////////////////		
		try{
		
			log.info("Get Item condition text");
			msg = "Unable to locate item condition in the Product details screen";
			itemCondition = prodDetails.getItemCondition().getText();
			
			log.info("Assert if item condition text is not empty");
			Assert.assertNotNull("Item condition of the product is empty", itemCondition);
			logger.log(LogStatus.PASS, "Item condition of product is not empty");
			log.info("Item condition text is not empty");
			
			log.info("Check if Time Left field is displayed");
			try{
				if(prodDetails.getTimeLeft().isDisplayed()==true){
				log.info("Get time Left vallue");
				timeLeft=prodDetails.getTimeLeft().getText();
			
				log.info("Assert if time left matches format");
				Assert.assertTrue("Time Left:"+timeLeft+" does not match format xxd yyh or xxh yym or xxm yys",checkTimeLeft(timeLeft));
				logger.log(LogStatus.PASS, "Time Left matches format");
				log.info("Time left matches format");
				}
			}
			catch (NoSuchElementException e){}
			
			msg = "Unable to locate price in product details page";
			log.info("Get price value of the product");
			try{
				price = prodDetails.getPrice().getText();
			}
			catch (NoSuchElementException e)
			{
				price = prodDetails.getBidPrice().getText();
			}
			
			log.info("Get price value without currency key");
			int index = price.indexOf("$");
			price = price.substring(index);
			
			log.info("Assert if price matches format");
			Assert.assertTrue("Price:"+price+" does not match format $X,XXX.YY",checkPriceFormat(price));
			logger.log(LogStatus.PASS, "Price matches format $X,XXX.YY");
			log.info("Price format check successful");
			
			log.info("Assign product name, price and seller information to objects");
			prodDetails.setProdName(prodName);	 							//Set Product name
			prodDetails.setProdPrice(price);								//Set Product prie
			prodDetails.setSellerName(prodDetails.getSeller().getText());	//Set Seller Name
			
			log.info("Click Add to cart button");
			msg = "Add to cart button not displayed in the screen";
			prodDetails.getAddToCart().click();	
			
			try{
			log.info("Switch to frame window if a pop up appears");
			//driver.switchTo().frame(driver.findElement(By.xpath("//*[@name='google_osd_static_frame']")));
			//driver.findElement(By.xpath("//*[@id='atcRedesignId_overlay-atc-container']/div/div[1]/div/div[2]/a[2]/span/span")).click();
			//driver.switchTo().defaultContent();
			prodDetails.getPopup().click();
			log.info("Exiting from frame window");
			}
			catch (NoSuchElementException e){}
		}
		catch (NoSuchElementException e)
		{
			logger.log(LogStatus.FAIL, msg);
			Assert.assertTrue(msg, false);
		}
		return prodDetails;
	}
}
