package fk;

import org.testng.annotations.Test;

import fk.core.BaseSetup;
import fk.implementations.GmailPage;

/**
 * Created by rajesh.kalloor on 06/05/16.
 */
public class UITest extends BaseSetup {

  @Test
  public void UITest() {

    webDriver.get(conf.getConfig("uiUrl"));
    GmailPage gmailPage = new GmailPage();
    gmailPage.typeSearchtext("selenium");
    gmailPage.clickOnSubmit();

  }

}
