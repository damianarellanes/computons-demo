package lstm.devices;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author Damian Arellanes
 * Publishes all devices in this package as web services
 */
@SpringBootApplication
public class PublishDevicesLSTM {
  
  public static void main(String[] args) {
    SpringApplication.run(PublishDevicesLSTM.class, args);
  }  
}