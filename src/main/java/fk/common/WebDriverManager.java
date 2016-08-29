package fk.common;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import fk.configuration.WebDriverConfig;
import fk.constants.Browser;

/**
 * Created by rajesh.kalloor on 06/05/16.
 */
public class WebDriverManager {

  public static WebDriver getWebDriver(Browser browser) {
    WebDriver webDriver = null;
    switch (browser) {
      case FIREFOX: {
        webDriver = new FirefoxDriver(WebDriverConfig.getFirefoxProfile());
      }
    }



    return webDriver;
  }

}
