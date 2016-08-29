package fk.restApi;

import com.flipkart.casclient.client.HttpAuthClient;
import com.flipkart.casclient.entity.Request;
import com.flipkart.casclient.util.InMemoryCache;
import com.ning.http.client.Response;

import org.apache.log4j.Logger;

import fk.exceptions.WebServiceException;

/**
 * Created by rajesh.kalloor on 06/05/16.
 */
public class CasClient {
  private static Logger logger = Logger.getLogger(CasClient.class);

  private com.flipkart.casclient.client.HttpAuthClient httpClient;
  private boolean isAuthEnabled = true;
  private String casUrl;
  private String casUsername;
  private String casPassword;

  public CasClient(){

  }

  public CasClient(String casUrl, String casUsername, String casPassword, boolean isAuthEnabled) {
    this.casUrl = casUrl;
    this.casUsername = casUsername;
    this.casPassword = casPassword;
    this.isAuthEnabled = isAuthEnabled;
    this.httpClient =
        new com.flipkart.casclient.client.HttpAuthClient(this.casUrl, this.casUsername, this.casPassword, this.isAuthEnabled, new InMemoryCache());
  }

  public CasClient(String casUrl, String casUsername, String casPassword) {
    this.casUrl = casUrl;
    this.casUsername = casUsername;
    this.casPassword = casPassword;
    this.httpClient =
        new com.flipkart.casclient.client.HttpAuthClient(this.casUrl, this.casUsername, this.casPassword, true, new InMemoryCache());
  }

  public Response execute(Request request, HttpAuthClient.Method httpMthod, int statusCode) {
    logger.info("hitting url: " + request.getUrl() + " with payload: " + request.getBody());
    Response response = httpClient.executeMethod(request, httpMthod);
    try {
      logger.info("response Status code: " + response.getStatusCode());
    } catch (Exception e) {

      e.printStackTrace();
    }
    if (response.getStatusCode() == statusCode) {
      return response;
    } else {
      throw new WebServiceException(response);
    }

  }

  public Response execute(Request request, HttpAuthClient.Method httpMthod) {
    logger.info("hitting url: " + request.getUrl() + " with payload: " + request.getBody());
    //    System.out.println("hitting url: " + request.getUrl() + " with payload: " + request.getBody());
    Response response = httpClient.executeMethod(request, httpMthod);
    try {
      //      System.out.println("response: " + response.getResponseBody());
      logger.info("response Status Code: " + response.getStatusCode());

    } catch (Exception e) {

      e.printStackTrace();
    }
    if (response.getStatusCode() >= 200 && response.getStatusCode() < 300) {
      return response;
    } else {
      throw new WebServiceException(response);
    }
  }

  //DO NOT REMOVE THIS .... THIS IS A WORKAROUND TO LOOP THROUGH WH API CALL FAILURES
  //HAVE OPENED A BUG FOR SAME. NEED TO REMOVE THIS ONCE THAT IS FIXED.
  //PASSING CONSIGNMENT PARAMETER WHILE OVERLOADING SO THAT OTHERS DONT OVERLOAD IT BY MISTAKE
  public Response execute(Request request, HttpAuthClient.Method httpMthod, String consignment) {
    logger.info("hitting url: " + request.getUrl() + " with payload: " + request.getBody());
    //    System.out.println("hitting url: " + request.getUrl() + " with payload: " + request.getBody());
    Response response = httpClient.executeMethod(request, httpMthod);

    return response;
  }

}
