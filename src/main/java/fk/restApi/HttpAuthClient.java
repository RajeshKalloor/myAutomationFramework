package fk.restApi;

//import com.flipkart.casclient.CASClientException;
import com.flipkart.casclient.client.CASClient;
import com.flipkart.casclient.entity.Request;
import com.flipkart.casclient.util.HttpUtil;
import com.ning.http.client.AsyncHttpClient;
import com.ning.http.client.ListenableFuture;
import com.ning.http.client.Response;

import org.apache.log4j.Logger;
import org.jboss.netty.handler.codec.http.HttpResponseStatus;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import fk.exceptions.CASClientException;
import flipkart.platform.cachefarm.Cache;

/**
 * Created by rajesh.kalloor on 06/05/16.
 */
public class HttpAuthClient {
  private AsyncHttpClient asyncHttpClient;
  private CASClient casClient;
  private static final String SESSION_COOKIE = "_session_id";
  private static Logger logger = Logger.getLogger(HttpAuthClient.class);

  public HttpAuthClient(String casUrl, String user, String password, boolean enableAuth, Cache cache) {
    this(new CASClient(casUrl, user, password, enableAuth, cache), new AsyncHttpClient());
  }

  public HttpAuthClient(String casUrl, String user, String password, boolean enableAuth, Cache cache, long expirationTimeInSecs) {
    this(new CASClient(casUrl, user, password, enableAuth, cache, expirationTimeInSecs), new AsyncHttpClient());
  }

  public HttpAuthClient(CASClient casClient, AsyncHttpClient asyncHttpClient) {
    this.casClient = casClient;
    this.asyncHttpClient = asyncHttpClient;
  }

  public HttpAuthClient() {
  }

  public Response executeGet(Request req) throws CASClientException {
    return this.executeMethod(req, HttpAuthClient.Method.GET);
  }

  public Response executePost(Request req) throws CASClientException {
    return this.executeMethod(req, HttpAuthClient.Method.POST);
  }

  public Response executePut(Request req) throws CASClientException {
    return this.executeMethod(req, HttpAuthClient.Method.PUT);
  }

  public Response executeDelete(Request req) throws CASClientException {
    return this.executeMethod(req, HttpAuthClient.Method.DELETE);
  }

  public Response executeMethod(Request req, HttpAuthClient.Method method) throws CASClientException {
    logger.info("Executing " + method + " " + req.getUrl());
    String username = this.casClient.getUsername();
    String stUrl = this.casClient.appendServiceTickets(req.getUrl(), username);
    logger.debug("Appenders: " + logger.getAllAppenders());
    logger.debug("Url with service ticket : " + stUrl);
    Response response = this.execute(req, stUrl, method);
    if(this.hasAuthenticationFailed(response)) {
      this.casClient.invalidateServiceTicket(req.getUrl(), username);
      logger.info("Authentication failed so trying for new service ticket");
      stUrl = this.casClient.appendServiceTickets(req.getUrl(), username);
      logger.debug("Url with service ticket : " + stUrl);
      response = this.execute(req, stUrl, method);
    }

    if(response != null) {
      this.updateCookie(req, response, stUrl, username);
    }

    return response;
  }

  protected Response execute(Request request, String url, HttpAuthClient.Method method) throws CASClientException {
    AsyncHttpClient.BoundRequestBuilder builder = method.getBuilder(this.asyncHttpClient, url);
    Response response = null;

    try {
      response = this.execute(request, builder);
      return response;
    } catch (Exception var7) {
      logger.error("Exception occurred when executing the request " + var7.getMessage());
      throw new CASClientException(var7);
    }
  }

  private Response execute(Request req, AsyncHttpClient.BoundRequestBuilder builder) throws
                                                                                     IOException, CASClientException {
    String url = req.getUrl();
    String cookie = this.casClient.getCookie(url, this.casClient.getUsername());
    if(cookie != null) {
      HashMap extraHeaders = new HashMap();
      extraHeaders.put("_session_id", cookie);
      builder.addHeader("Cookie", HttpUtil.createCookie(extraHeaders));
    }

    Map extraHeaders1 = req.getAllHeaders();
    String response;
    if(extraHeaders1 != null) {
      Iterator future = extraHeaders1.keySet().iterator();

      while(future.hasNext()) {
        response = (String)future.next();
        builder.addHeader(response, (String)extraHeaders1.get(response));
      }
    }

    if(req.getBody() != null) {
      builder.setBody((String)req.getBody());
    }

    ListenableFuture future1 = builder.execute();
    response = null;

    try {
      Response response1 = (Response)future1.get();
      logger.trace(response1);
      return response1;
    } catch (Exception var9) {
      logger.error("Error while receiving the response", var9);
      throw new CASClientException(var9);
    }
  }

  private void updateCookie(Request req, Response response, String stUrl, String user) {
    Map cookies = HttpUtil.getCookies(response.getHeader("Set-Cookie"));
    Map params = HttpUtil.getQueryParams(stUrl);
    String ticket = (String)params.get("ticket");
    if(cookies.get("_session_id") != null) {
      logger.debug("Session cookie found. Will be used in next request");
      this.casClient.updateTicket(req.getUrl(), user, ticket, (String)cookies.get("_session_id"));
    }

  }

  private boolean hasAuthenticationFailed(Response response) {
    return response != null && (response.getStatusCode() == HttpResponseStatus.UNAUTHORIZED.getCode() || response.getStatusCode() == HttpResponseStatus.UNPROCESSABLE_ENTITY.getCode());
  }

  public static enum Method {
    GET("GET") {
      public AsyncHttpClient.BoundRequestBuilder getBuilder(AsyncHttpClient client, String url) {
        return client.prepareGet(url);
      }
    },
    POST("POST") {
      public AsyncHttpClient.BoundRequestBuilder getBuilder(AsyncHttpClient client, String url) {
        return client.preparePost(url);
      }
    },
    PUT("PUT") {
      public AsyncHttpClient.BoundRequestBuilder getBuilder(AsyncHttpClient client, String url) {
        return client.preparePut(url);
      }
    },
    DELETE("DELETE") {
      public AsyncHttpClient.BoundRequestBuilder getBuilder(AsyncHttpClient client, String url) {
        return client.prepareDelete(url);
      }
    };

    private final String METHOD;

    private Method(String method) {
      this.METHOD = method;
    }

    public String toString() {
      return this.METHOD;
    }

    public AsyncHttpClient.BoundRequestBuilder getBuilder(AsyncHttpClient client, String url) {
      return client.prepareGet(url);
    }
  }

}
