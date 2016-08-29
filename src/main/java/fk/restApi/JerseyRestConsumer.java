package fk.restApi;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.api.client.filter.HTTPBasicAuthFilter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriBuilder;

/**
 * Created by rajesh.kalloor on 06/05/16.
 */
public class JerseyRestConsumer {

  private static Logger logger = LoggerFactory.getLogger(JerseyRestConsumer.class);

  protected Client client = null;
  protected MediaType responseType = null;

  public JerseyRestConsumer() {
    initConsumer();
  }

  public void initConsumer(){

    ClientConfig config = new DefaultClientConfig();
//    JacksonJsonProvider jacksonJsonProvider =
//        new JacksonJsonProvider()
//            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
//    config.getSingletons().add(jacksonJsonProvider);
    client = Client.create(config);
  }

  public URI constructURI(String path, Map<String, String> queryParams) {
    UriBuilder uriBuilder = UriBuilder.fromPath(path);
    if(queryParams != null) {
      for(Map.Entry<String, String> e : queryParams.entrySet()) {
        uriBuilder.queryParam(e.getKey(), e.getValue());
      }
    }
    return uriBuilder.build();

  }

//  protected ClientRequest buildRequest(URI uri,Method m, Map<String, String> headers, Object requestEntity) {
//
//    //Log.info("Creating request... Url - "+ uri + "and method - "+m.getMethodName() );
//    ClientRequest request = ClientRequest.create().type(requestMediaType).accept(responseMediaType).build(uri, m.getMethodName());
//    if(requestEntity!=null)
//      request.setEntity(requestEntity);
//    if(headers!=null)
//      for(Map.Entry<String, String> e : headers.entrySet()){
//        request.getHeaders().add(e.getKey(), e.getValue());
//      }
//    return request;
//  }


  public ClientResponse buildRequest(String uri, String body) {

    return client.resource(uri).accept("application/json").type("application/json").header("Content-Type", "application/json").post(ClientResponse.class, body);
  }




  public ClientResponse doPost(JerseyRequest request) {

    logger.info("Hitting url: " + request.getUrl());
    WebResource webResource = client.resource(request.getUrl());

    ClientResponse response = webResource.accept(request.getType())
        .header("X-SELLER-ID", "68kr7n99cgxbyuiq")
        .header("Content-Type", "application/json")
        .post(ClientResponse.class, request.getBody());

    if (response.getStatus() < 200 || response.getStatus() >= 300) {
      throw new RuntimeException("Failed : HTTP error code : "
                                 + response.getStatus());
    }

    return response;
  }


  public String doGetCall(String requestURL, String contentType) {
    try {
      Client client = Client.create();

      logger.info("Hitting url: " + requestURL);

      WebResource webResource = client.resource(requestURL);

      ClientResponse response = webResource.accept(contentType).get(
          ClientResponse.class);

      if (response.getStatus() < 200 || response.getStatus() >= 300) {
        throw new RuntimeException("Failed : HTTP error code : "
                                   + response.getStatus());
      }

      String output = response.getEntity(String.class);
      return output;
    } catch (Exception e) {
      logger.error("There was no response for URL", e.getStackTrace());
    }
    return null;
  }

  public String doPostCall(String requestURL, String contentType, String body) {
    try {

      Client client = Client.create();

      WebResource webResource = client.resource(requestURL);

      ClientResponse response = null;

      logger.info("Hitting url: " + requestURL + " with body: " + body);

      if (contentType != "" || contentType != null) {
        response = webResource.type(contentType).post(
            ClientResponse.class, body);
      } else {
        response = webResource.post(ClientResponse.class, body);
      }

      logger.info("The call was made with response status code: " + response.getStatus());

      if (response.getStatus() < 200 || response.getStatus() >= 300) {
        throw new RuntimeException("Failed : HTTP error code : "
                                   + response.getStatus());
      }

      String output = response.getEntity(String.class);
      logger.info("Output of the post call was: " + output);
      return output;

    } catch (Exception e) {
      logger.error("There was no response for URL", e.getStackTrace());
    }
    return null;
  }

  public ClientResponse doPostCallForMad(String requestURL, String contentType, HashMap<String, String> header) {
    try {

      Client client = Client.create();

      WebResource webResource = client.resource(requestURL);

      ClientResponse response = null;

      logger.info("Hitting url: " + requestURL);

      Set<String> keySet = header.keySet();
      String key = "";

      if (!keySet.isEmpty()) {
        for (String val : keySet) {
          key = val;
        }
        if (contentType != "" || contentType != null) {
          response = webResource.header(key, header.get(key)).type(contentType).post(
              ClientResponse.class);
        } else {
          response = webResource.header(key, header.get(key)).post(ClientResponse.class);
        }
      }

      logger.info("The call was made with response status code: " + response.getStatus());
      return response;

    } catch (Exception e) {
      logger.error("There was no response for URL", e.getStackTrace());
    }
    return null;
  }

  public ClientResponse doPostCallForMad(String requestURL, String contentType, String body) {
    try {

      Client client = Client.create();

      WebResource webResource = client.resource(requestURL);

      ClientResponse response = null;

      logger.info("Hitting url: " + requestURL);

      if (contentType != "" || contentType != null) {
        response = webResource.type(contentType).post(
            ClientResponse.class, body);
      } else {
        response = webResource.post(ClientResponse.class, body);
      }

      logger.info("The call was made with response status code: " + response.getStatus());
      return response;

    } catch (Exception e) {
      logger.error("There was no response for URL", e.getStackTrace());
    }
    return null;
  }

  public ClientResponse doPostCallWithMultipleHeader(String requestURL, String contentType, HashMap<String, String> header) {
    try {

      Client client = Client.create();

      WebResource webResource = client.resource(requestURL);

      ClientResponse response = null;

      logger.info("Hitting url: " + requestURL);

      Set<String> keySet = header.keySet();
//            String key = "";
//            String key2 = "";

      List<String> key = new ArrayList<String>();
      int headerLength = keySet.size();


      if (!keySet.isEmpty()) {
        for (String val : keySet) {
          key.add(val);
        }
        if (contentType != "" || contentType != null) {
//                    response = webResource.header(key, header.get(key)).header(key2, header.get(key2)).type(contentType).post(
//                            ClientResponse.class);
          response = webResource.header(key.get(0), header.get(key.get(0))).header(key.get(1), header.get(key.get(1))).type(contentType).post(
              ClientResponse.class);
        } else {
//                    response = webResource.header(key, header.get(key)).header(key2, header.get(key2)).post(ClientResponse.class);
          response = webResource.header(key.get(0), header.get(key.get(0))).header(key.get(1), header.get(key.get(1))).post(
              ClientResponse.class);
        }
      }
      logger.info("The call was made with response status code: " + response.getStatus());
      return response;

    } catch (Exception e) {
      logger.error("There was no response for URL", e.getStackTrace());
    }
    return null;
  }

  public ClientResponse doPostCallForMad(String requestURL, String contentType, HashMap<String, String> header, String body) {
    try {

      Client client = Client.create();

      WebResource webResource = client.resource(requestURL);

      ClientResponse response = null;

      logger.info("Hitting url: " + requestURL);

      Set<String> keySet = header.keySet();
      String key = "";

      if (!keySet.isEmpty()) {
        for (String val : keySet) {
          key = val;
        }
        if (contentType != "" || contentType != null) {
          response = webResource.header(key, header.get(key)).type(contentType).post(
              ClientResponse.class, body);
        } else {
          response = webResource.header(key, header.get(key)).post(ClientResponse.class, body);
        }
      }
      logger.info("The call was made with response status code: " + response.getStatus());
      return response;

    } catch (Exception e) {
      logger.error("There was no response for URL", e.getStackTrace());
    }
    return null;
  }

  public ClientResponse doGetCallForMad(String requestURL, String contentType, HashMap<String, String> header) {
    try {

      Client client = Client.create();

      WebResource webResource = client.resource(requestURL);

      ClientResponse response = null;

      logger.info("Hitting url: " + requestURL);

      Set<String> keySet = header.keySet();
      String key = "";

      if (!keySet.isEmpty()) {
        for (String val : keySet) {
          key = val;
        }
        if (contentType != "" || contentType != null) {
          response = webResource.header(key, header.get(key)).type(contentType).get(
              ClientResponse.class);
        } else {
          response = webResource.header(key, header.get(key)).get(ClientResponse.class);
        }
      }
      logger.info("The call was made with response status code: " + response.getStatus());
      return response;

    } catch (Exception e) {
      logger.info("There was no response for URL", e.getStackTrace());
    }
    return null;
  }

  public ClientResponse doGetCallForMad(String requestURL, String contentType) {
    try {

      Client client = Client.create();

      WebResource webResource = client.resource(requestURL);

      ClientResponse response = null;

      logger.info("Hitting url: " + requestURL);

      if (contentType != "" || contentType != null) {

        response = webResource.type(contentType).get(ClientResponse.class);
      } else {
        response = webResource.get(ClientResponse.class);
      }
      logger.info("The call was made with response status code: " + response.getStatus());
      return response;

    } catch (Exception e) {
      logger.error("There was no response for URL", e.getStackTrace());
    }
    return null;
  }

  public ClientResponse doPostCall(String requestURL, HashMap<String, String> header, String contentType,
                                   String body) {
    try {

      Client client = Client.create();

      WebResource webResource = client.resource(requestURL);

      ClientResponse response = null;

      logger.info("Hitting url: " + requestURL + " with body: " + body);

      Set<String> keySet = header.keySet();
      String key = "";

      if (!keySet.isEmpty()) {
        for (String val : keySet) {
          key = val;
        }
        if (contentType != "" || contentType != null) {
          response = webResource.header(key, header.get(key)).type(contentType).post(
              ClientResponse.class, body);
        } else {
          response = webResource.header(key, header.get(key)).post(ClientResponse.class, body);
        }
      }

      logger.info("The call was made with response status code: " + response.getStatus());

      if (response.getStatus() < 200 || response.getStatus() >= 300) {
        throw new RuntimeException("Failed : HTTP error code : "
                                   + response.getStatus());
      }

      String output = response.getEntity(String.class);
      logger.info("Output of the post call was: " + output);
      return response;

    } catch (Exception e) {
      logger.error("There was no response for URL", e.getStackTrace());
    }
    return null;
  }

  public String doPostCall(String requestURL, String contentType, String body, String userName,
                           String password) {
    try {

      Client client = Client.create();

      client.addFilter(new HTTPBasicAuthFilter(userName, password));

      WebResource webResource = client.resource(requestURL);

      ClientResponse response = null;

      logger.info("Hitting url: " + requestURL + " with body: " + body);

      if (contentType != "" || contentType != null) {
        response = webResource.type(contentType).post(
            ClientResponse.class, body);
      } else {
        response = webResource.post(ClientResponse.class, body);
      }

      logger.info("The call was made with response status code: " + response.getStatus());

      if (response.getStatus() < 200 || response.getStatus() >= 300) {
        throw new RuntimeException("Failed : HTTP error code : "
                                   + response.getStatus());
      }

      String output = response.getEntity(String.class);
      logger.info("Output of the post call was: " + output);
      return output;

    } catch (Exception e) {
      logger.error("There was no response for URL", e.getStackTrace());
    }
    return null;
  }

  public String doPutCall(String requestURL, String contentType, String body) {
    try {

      Client client = Client.create();

      WebResource webResource = client.resource(requestURL);

      logger.info("Hitting url: " + requestURL + " with body: " + body);

      ClientResponse response = webResource.type(contentType).put(
          ClientResponse.class, body);

      if (response.getStatus() < 200 || response.getStatus() >= 300) {
        throw new RuntimeException("Failed : HTTP error code : "
                                   + response.getStatus());
      }

      String output = response.getEntity(String.class);
      return output;

    } catch (Exception e) {
      logger.error("There was no response for URL", e.getStackTrace());
    }
    return null;
  }

  public ClientResponse doPutCallForMad(String requestURL, String contentType, String body) {
    try {

      Client client = Client.create();

      WebResource webResource = client.resource(requestURL);

      logger.info("Hitting url: " + requestURL + " with body: " + body);

      ClientResponse response = webResource.type(contentType).put(
          ClientResponse.class, body);

      if (response.getStatus() < 200 || response.getStatus() >= 300) {
        throw new RuntimeException("Failed : HTTP error code : "
                                   + response.getStatus());
      }
      logger.info("The call was made with response status code: " + response.getStatus());
      return response;

    } catch (Exception e) {
      logger.error("There was no response for URL", e.getStackTrace());
    }
    return null;
  }

}
