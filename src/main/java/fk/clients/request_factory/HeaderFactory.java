package fk.clients.request_factory;

import java.util.HashMap;

/**
 * Created by rajesh.kalloor on 11/05/16.
 */
public class HeaderFactory {
  public static final String CONTENT_TYPE = "Content-Type";
  public static final String APPLICATION_JSON = "application/json";

  public static HashMap<String, String> sellerHeader(String sellerId) {
    HashMap<String, String> headers = new HashMap<String, String>();
    headers.put(CONTENT_TYPE, APPLICATION_JSON);
    headers.put("X-SELLER-ID", sellerId);
    return headers;
  }

  public static HashMap<String, String> defaultHeader() {
    HashMap<String, String> headers = new HashMap<String, String>();
    headers.put(CONTENT_TYPE, APPLICATION_JSON);
    return headers;
  }

}
