package fk.core;

import org.apache.log4j.Logger;
import org.yaml.snakeyaml.Yaml;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by rajesh.kalloor on 11/05/16.
 */
public class ContextHelper {
  private static Logger logger = Logger.getLogger(ContextHelper.class);
  private static Yaml yaml = new Yaml();

  private static ThreadLocal threadSharedClass = new ThreadLocal(); // shared across entire class and among all threads
  private ThreadLocal threadSharedObject = new ThreadLocal(); // shared per each thread


  public static Object getCurrentContext() {
    return threadSharedClass.get();
  }

  public static void endContext() {
    threadSharedClass.remove();
  }

  public static void setContext(Object data) {
    threadSharedClass.set(data);
  }

  public static void dumpData(String fileName) throws IOException {
    String filePath = buildFilePath(fileName);
    if ((new File(filePath).exists())) {
      new File(filePath).delete();
    }
    FileWriter writer = new FileWriter(filePath);
    Object data = ContextHelper.getCurrentContext();
    logger.info("Dumping data to file :" + filePath);
    yaml.dump(data, writer);
    writer.close();
  }

  public static Object loadData(String fileName) throws FileNotFoundException {
    String filePath = buildFilePath(fileName);
    InputStream ios = new FileInputStream(filePath);
    Object object = yaml.load(ios);
    logger.info("Data Loaded from Yaml " + filePath + ": " + object);
    return object;
  }

  public static String buildFilePath(String fileName) {
    String filePath;
    String currentPath = System.getProperty("user.dir");
    String dirPath = "/tmp";
    File dir = new File(currentPath + dirPath);
    if (!dir.exists()) {
      dir.mkdir();
    }
    filePath = currentPath + dirPath + File.separator + fileName + ".yaml";
    return filePath;
  }

  public Object getThreadContext() {
    return threadSharedObject.get();
  }

  public void setThreadContext(Object data) {
    threadSharedObject.set(data);
  }

  public void endThreadContext() {
    threadSharedObject.remove();

  }



}
