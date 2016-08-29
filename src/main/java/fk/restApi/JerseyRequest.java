package fk.restApi;

import java.util.HashMap;

import lombok.Data;

/**
 * Created by rajesh.kalloor on 11/05/16.
 */
@Data
public class JerseyRequest {

  private String url;
  private String body;
  private String type;
  private HashMap<String, String> headers = new HashMap<String, String>();
  private Method method;

  public JerseyRequest(JerseyRequestBuilder jerseyRequestBuilder) {

    this.url = jerseyRequestBuilder.url;
    this.body = jerseyRequestBuilder.body;
    this.type = jerseyRequestBuilder.type;
    this.headers = jerseyRequestBuilder.headers;
    this.method = jerseyRequestBuilder.method;

  }

  public static class JerseyRequestBuilder {

    private String url;
    private String body;
    private String type;
    private HashMap<String, String> headers = new HashMap<String, String>();
    private Method method;

    public JerseyRequestBuilder(String url, String type, Method method) {
      this.url = url;
      this.type = type;
      this.method = method;
    }

    public JerseyRequestBuilder body(String body) {
      this.body = body;
      return this;
    }

    public JerseyRequestBuilder headers(HashMap<String, String> headers) {
      this.headers = headers;
      return this;
    }

    public JerseyRequest build() {
      return new JerseyRequest(this);
    }
  }
}
