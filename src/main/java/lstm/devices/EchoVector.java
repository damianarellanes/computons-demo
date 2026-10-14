package lstm.devices;

import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import synthesis.Parameters;
import synthesis.Device;

/**
 * @author Damian Arellanes
 * Inputs: Vector of real numbers
 * Output: Vector of real numbers
 * Behaviour: Echoes the input vector
 */
@RestController
@Component("EchoVector")
public class EchoVector implements Device <EchoVector.In, Double[]> {
    
  public record In(Double[] p5) implements Parameters {}
  
  @Override
  @PostMapping("/echoVector")
  public Double[] exec(@RequestBody In in) {    
    return in.p5();
  }

  @Override
  public Class<In> getParametersClass() {
    return In.class;
  }
  
  @Override
  public Class<Double[]> getReturnType() {
    return Double[].class;
  }
}