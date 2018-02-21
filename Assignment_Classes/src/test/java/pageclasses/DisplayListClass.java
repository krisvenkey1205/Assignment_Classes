package pageclasses;

import java.util.Random;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.relevantcodes.extentreports.LogStatus;

import junit.framework.Assert;
import pageobjects.DisplayListPage;
import utils.resources.BaseClass;

public class DisplayListClass extends BaseClass {

	public void checkDisplayList(){
		
		String[] words;
		String listText, screenText, msg = null;
		int listSize, screenSize;
		Boolean b;
		WebElement we = null;
		
		log.info("Create object for DisplayList Page class");
		DisplayListPage listPage = new DisplayListPage(driver);		//Display List Page object
		
/////////////////////////////////////////////////////////////////////////////////////////		
//3. Check that all the listed product has the word “tv” and “sony” in it – read the items 
///////////////////////////////////////////////////////////////////////////////////////////
		try{
			log.info("Split the search term");
			words = searchTerm.split("\\s+");
			
			log.info("Get the size of the product list displayed");
			msg = "Unable to locate product list element";
			listSize=listPage.getListPanel().findElements(By.tagName("h3")).size();
			
			if(prop.getProperty("checklist").equals("true")){
				log.info("Loop throught the list of product displayed");
				for(int i=1; i<listSize;i++){
					log.info("Get the text of the list displayed");
					msg = "Unable to locate list text";
					listText=listPage.getListPanel().findElements(By.tagName("h3")).get(i).getText();
		
					log.info("Inititlize boolean variable as true");
					b=true;
					log.info("Loop through the search term");
					for(int j=0; j<words.length;j++){
						log.info("Check if the search term is present in list text");
						if(listText.toLowerCase().contains(words[j].toLowerCase()) != true){
							log.info("If search term is not present, set boolean variable as false and exit the loop");
							b=false;
							break;
						}
					}		
					log.info("Assert true/false based on boolean variable");
					Assert.assertTrue("Listed Product:"+listText+",does not contain all the search term:"+searchTerm, b);
					}
				}
			logger.log(LogStatus.PASS, "Listed Products contain the search term");
			
/////////////////////////////////////////////////////////////////////////////////////////		
//4. Select the screen size 50” – 60” (in red square of step 2) 
//5. Check that the new list is showed for screen size 50” – 60”
///////////////////////////////////////////////////////////////////////////////////////////
			log.info("Get the list of screen sizes displayed");
			msg = "Unable to locate Screen size element";
			screenSize = listPage.getScreenSize().findElements(By.tagName("li")).size();
			
			log.info("Loop through the list and find 50\"-60\"");
			b= false;
			for(int i=0;i<screenSize;i++){
				log.info("Assign the list tag to we object");
				we=listPage.getScreenSize().findElements(By.tagName("li")).get(i);
				screenText=we.getText();
				log.info("Compare if screen size text");
				if(screenText.equals(prop.getProperty("screensize"))){
					b = true;	
					log.info("If found, click and break the loop");
					we.click();
					break;
				}
			}
			
			if(b==false){
				msg = "Unable to locate screen size scroll button";
				listPage.getClick().click();
				//Thread.sleep(2000);
				we = (WebElement)(new WebDriverWait(driver,10)).until(ExpectedConditions.visibilityOf(listPage.getScreenSize().findElement(By.xpath("//*[text()='"+prop.getProperty("screensize")+"']"))));
				we.click();
			}
			
			we = null;
			
			log.info("Check if filter criteria applied for selected screen size");
			msg = "Unable to locate screen size filters in the screen";
			Assert.assertEquals("Filter criteria is not applied for screen size", prop.getProperty("screensize"), listPage.getFilter().getText());
			logger.log(LogStatus.PASS, "Screen Size filter selected");
	
/////////////////////////////////////////////////////////////////////////////////////////		
//6: Randomly select a product in the current listed page, check that the product detail page is showed 
///////////////////////////////////////////////////////////////////////////////////////////		
			log.info("Get a random number to click in the displayed list");
			Random ran = new Random();
			int random = ran.nextInt(listSize)+1;
			log.info("Randomly selected listnumber:"+random);
			
			log.info("Assign the random list to webelement");
			msg = "Unable to select random product from the displayed list";
			we=listPage.getListPanel().findElement(By.xpath("//ul[@id='ListViewInner']/li["+random+"]/h3/a")); 
			//we= listPage.getListPanel().findElement(
			//		By.linkText("Sony Bravia XBR-52HX909 52\" Full 3D 1080p HD LED LCD Internet TV"));
			prodName = we.getText();
			while(true){
				log.info("Check if the web element is diplayed. If not scroll down the page");
				if(we.isDisplayed()==true){
					log.info("If element found, click and break the loop");
					msg = "Unable to click rando product from the list";
					we.click();
					break;
				}else{
					log.info("Continue until web element is displayed in page");
					jse.executeScript("window.scrollTo(0, document.body.scrollHeight);");
				}
			}		
		}
		catch (NoSuchElementException e)
		{
			logger.log(LogStatus.FAIL, msg);
			Assert.assertTrue(msg, false);
		}
	}
}
