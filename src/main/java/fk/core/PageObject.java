package fk.core;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.concurrent.TimeUnit;

import fk.configuration.Configuration;

/**
 * Created by rajesh.kalloor on 07/05/16.
 */
public class PageObject extends BaseSetup {

  protected WebDriver driver;
  JavascriptExecutor jsExecutor;
  private long waitTime = 60;

  public PageObject(Configuration conf) {
    this.driver = webDriver;
    jsExecutor = (JavascriptExecutor) driver;
    driver.manage().timeouts().pageLoadTimeout(this.waitTime, TimeUnit.SECONDS);
    driver.manage().timeouts()
        .implicitlyWait(this.waitTime, TimeUnit.SECONDS);

  }

  public void openUrl(String url) { driver.get(url);}

  public void click(WebElement element) { element.click();}

  public void type(WebElement element, String text) { element.sendKeys(new CharSequence[]{text});}


}
