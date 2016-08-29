package fk.clients.dataProviders;

import org.testng.annotations.DataProvider;

/**
 * Created by rajesh.kalloor on 11/05/16.
 */
public class FaListingDetailsDP {

  //@DataProvider(name = "warehouseProvider", parallel = true)
  @DataProvider(name = "warehouseProvider")
  public static Object[][] warehouseProvider() {
    return new Object[][] {{"blr_wfld"}, {"del"}, {"kol"}};
  }
}
