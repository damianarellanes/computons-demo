

import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import synthesis.DevicesConfig;

/**
 * @author Damian Arellanes
 * This executes the LSTM and LSFA test cases using application.properties
 */
@SpringBootApplication(scanBasePackages = {"lstm", "lsfa", "synthesis"})
@EnableConfigurationProperties(DevicesConfig.class)
public class Main {
  
  public static void main(String[] args) {
    
    SpringApplication app = new SpringApplication(Main.class);
    app.setWebApplicationType(WebApplicationType.NONE);
    app.run(args);
  }
}
