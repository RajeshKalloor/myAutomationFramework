package fk.core;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import org.openqa.selenium.WebDriver;

import fk.common.WebDriverManager;
import fk.configuration.Configuration;
import fk.constants.Browser;
import fk.restApi.JerseyRestConsumer;

/**
 * Created by rajesh.kalloor on 06/05/16.
 */
public class BaseSetup {

  //protected static CasClient casClient;
  protected static Configuration conf;
  protected static ObjectMapper mapper;
  protected static ObjectMapper override_mapper;
  protected static ObjectMapper reverseMapper;
  //protected static HttpAuthClient httpAuthClient;
  protected static JerseyRestConsumer jerseyRestConsumer;
  protected static WebDriver webDriver;


  static {
    conf = new Configuration();
    mapper = new ObjectMapper();
    mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
    mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    //httpAuthClient = new HttpAuthClient();
    jerseyRestConsumer = new JerseyRestConsumer();
    webDriver = WebDriverManager.getWebDriver(Browser.FIREFOX);
  }

}
