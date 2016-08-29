package fk.configuration;

import org.openqa.selenium.firefox.FirefoxProfile;

/**
 * Created by rajesh.kalloor on 06/05/16.
 */
public class WebDriverConfig {


  public static FirefoxProfile getFirefoxProfile() {

    FirefoxProfile profile = new FirefoxProfile();
    //profile.setPreference("browser.download.dir", );
    profile.setPreference("browser.download.folderList", 2);
    profile.setPreference("geo.enabled", false);
    profile.setPreference("browser.helperApps.alwaysAsk.force", false);
    profile.setPreference("browser.download.manager.showWheneStarting", false);
    profile.setPreference("browser.helperApps.neverAsk.saveToDisk", "application/csv");

    return profile;
  }


}
