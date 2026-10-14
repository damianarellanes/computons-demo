package lstm.devices;

import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import synthesis.Parameters;
import synthesis.Device;

/**
 * @author Damian Arellanes
 * Inputs: Vector of real numbers, Vector of real numbers
 * Output: Vector of real numbers
 * Behaviour: Adds the two input vectors
 */
@RestController
@Component("Additive")
public class Additive implements Device <Additive.In, Double[]> {
  
  // The dimensions of v1 and v2 are the same
  public record In(Double[] v1, Double[] v2) implements Parameters {}
  
  @Override
  @PostMapping("/addVectors")
  public Double[] exec(@RequestBody In in) {    
        
    Double[] result = new Double[in.v1().length];
    for (int i = 0; i < in.v1().length; i++)      
      result[i] = in.v1()[i] + in.v2()[i];
    
    return result;
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
