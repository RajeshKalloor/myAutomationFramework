package fk.clients;

import com.google.gson.Gson;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.sun.jersey.api.client.ClientResponse;


import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import fk.clients.models.FAListingDetailsResponse;
import fk.clients.request_factory.FaListingDetailsFactory;
import fk.clients.request_factory.HeaderFactory;
import fk.clients.request_factory.UpdateTaxFactory;
import fk.core.BaseSetup;
import fk.restApi.JerseyRequest;
import fk.restApi.Method;

/**
 * Created by rajesh.kalloor on 06/05/16.
 */
public class ApiAction extends BaseSetup {

  public void updateTaxRule(Double rate, String listing) throws IOException {

    UpdateTaxFactory updateTaxFactory = new UpdateTaxFactory();
    //String updateTaxRequest = mapper.writeValueAsString(updateTaxFactory.updateTaxRequest(rate, listing));
    String updateTaxRequest = new Gson().toJson(updateTaxFactory.updateTaxRequest(rate, listing));
    System.out.println(updateTaxRequest);

    String uri = jerseyRestConsumer.constructURI("http://sp-ff-mpio.stage.ch.flipkart.com:7070/tax_rules/IN-KA", null).toString();


    //client.resource("").accept("application/json").post();

    ClientResponse response = jerseyRestConsumer.buildRequest(uri, updateTaxRequest);
    //Assert.assertEquals(response.getStatus(), 200);

  }

  public void getFaListingDetails(List<String> listings, String warehouseId) throws JsonProcessingException {
    FaListingDetailsFactory faListingDetailsFactory = new FaListingDetailsFactory();
    String faDetailsRequest = mapper.writeValueAsString(faListingDetailsFactory.getFaListingDetails(warehouseId, "12628", listings));
    System.out.println("Request is ::: " + faDetailsRequest);

    String uri = jerseyRestConsumer.constructURI("http://sp-ff-im1.stage.ch.flipkart.com:8080/consignments/12628/listing_fa_details", null).toString();
    //ClientResponse response = jerseyRestConsumer.buildRequest(uri, faDetailsRequest);

    JerseyRequest jRequest = new JerseyRequest.JerseyRequestBuilder(uri, "application/json", Method.POST)
        .body(faDetailsRequest)
        .headers(HeaderFactory.sellerHeader("68kr7n99cgxbyuiq"))
        .build();

    ClientResponse response = jerseyRestConsumer.doPost(jRequest);
    FAListingDetailsResponse faResponse = new Gson().fromJson(response.getEntity(String.class),
                                                           FAListingDetailsResponse.class);
//    FAListingDetailsResponse faResponse = null;
//    try {
//      faResponse = mapper.readValue(response.getEntity(String.class), FAListingDetailsResponse.class);
//      Assert.assertEquals(
//          faResponse.getListing_fadetails().getLSTMOBCZYHG4DCPVYGXAYSTPD().getListing_id(),
//          "LSTMOBCZYHG4DCPVYGXAYSTPD");
//      System.out.println("Response was ::: " + faResponse.toString());
//    } catch (IOException e) {
//      e.printStackTrace();
//    }

    Assert.assertEquals(
        faResponse.getListing_fadetails().getLSTMOBCZYHG4DCPVYGXAYSTPD().getListing_id(),
        "LSTMOBCZYHG4DCPVYGXAYSTPD");
    System.out.println("Response was ::: " + faResponse.toString());
  }


  @Test
  public void check() {
    try {
      //updateTaxRule(2.0, "listing1");

      getFaListingDetails(Arrays.asList("LSTMOBCZYHG4DCPVYGXAYSTPD"), "blr_wfld");
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

}
