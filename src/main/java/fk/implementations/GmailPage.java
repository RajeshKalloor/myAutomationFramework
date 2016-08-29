package fk.implementations;

import org.openqa.selenium.By;

import fk.core.PageObject;

/**
 * Created by rajesh.kalloor on 06/05/16.
 */
public class GmailPage extends PageObject {

  //WebDriver driver;
  public GmailPage() {
    super(conf);
  }


  By searchText = By.id("lst-ib");
  By submitButton = By.xpath("//*[@id='tsf']/div[2]/div[3]/center/input[1]");

  public void typeSearchtext(String s) {
    type(driver.findElement(searchText), s);
  }

  public void clickOnSubmit() {
    click(driver.findElement(submitButton));
  }

  public void login(String username, String password) {
    driver.findElement(searchText);

  }
}
