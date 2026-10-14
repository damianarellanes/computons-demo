package lstm.devices;

import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import synthesis.Parameters;
import synthesis.Device;

/**
 * @author Damian Arellanes
 * Inputs: Vector of real numbers (c_t)
 * Output: Vector of real numbers
 * Behaviour: Applies the tanh function to the cell state c_t
 */
@RestController
@Component("Tanh2")
public class Tanh2 implements Device <Tanh2.In, Double[]> {
  
  public record In(
    Double[] cellState
  ) implements Parameters {}  
  
  @Override
  @PostMapping("/tanh2")
  public Double[] exec(@RequestBody In in) {    
    return applyTanh(in.cellState());
  }

  @Override
  public Class<In> getParametersClass() {
    return In.class;
  }
  
  @Override
  public Class<Double[]> getReturnType() {
    return Double[].class;
  }
  
  private Double[] applyTanh (Double[] z) {
    
    Double[] result = new Double[z.length];
    for (int i = 0; i < z.length; i++) 
      result[i] = Math.tanh(z[i]);
    return result;
  }
}
