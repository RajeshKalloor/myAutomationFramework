package fk;

import org.testng.annotations.Test;

import java.io.IOException;
import java.util.Arrays;

import fk.clients.ApiAction;
import fk.clients.dataProviders.FaListingDetailsDP;

/**
 * Created by rajesh.kalloor on 11/05/16.
 */
public class ApiTest {


  @Test(dataProvider = "warehouseProvider",dataProviderClass = FaListingDetailsDP.class)
  public void apiTest(String warehouseId) {
    try {
      ApiAction apiAction = new ApiAction();
      apiAction.getFaListingDetails(Arrays.asList("LSTMOBCZYHG4DCPVYGXAYSTPD"), warehouseId);
    } catch (IOException e) {
      e.printStackTrace();
    }

  }

}
