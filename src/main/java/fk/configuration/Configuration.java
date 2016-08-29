package fk.configuration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.Test;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * Created by rajesh.kalloor on 06/05/16.
 */

public class Configuration {

  private static Logger logger = LoggerFactory.getLogger(Configuration.class);
  private Properties properties = new Properties();
  private String env;

  public Configuration() {
//    BasicConfigurator.configure();
//    if (System.getProperty("env") != null) {
//      this.env = System.getProperty("env");
//    }
    this.env = "stage";  //set default as stage
    logger.info("Fetching the environmental details from :"+this.env);
  }

  public Configuration(String env) {
    this.env = env;
  }


  public String getConfig(String property) {
    String config = null;
    System.out.println("current directory is ::: " + System.getProperty("user.dir"));
    try {
      properties.load(new FileInputStream("src/main/java/fk/configuration/properties/env.properties"));
      config = properties.getProperty(env);
      properties.load(new FileInputStream(config));
    } catch (IOException e) {
      e.printStackTrace();
    }
    return properties.getProperty(property);
  }


  public String getConfig(String property, String envConfigPath) {
    String config = null;
    try {
      properties.load(new FileInputStream(envConfigPath));
      config = properties.getProperty(env);
      properties.load(new FileInputStream(config));
    } catch (IOException e) {
      e.printStackTrace();
    }
    return properties.getProperty(property);
  }

  //TODO revist the Context part. Also getting the Environment from an enum


  @Test
  public void test()  {

    Configuration conf = new Configuration();
    System.out.println(conf.getConfig("uiUrl"));

  }
}
