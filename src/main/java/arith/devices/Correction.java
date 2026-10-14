package arith.devices;

import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import synthesis.Parameters;
import synthesis.Device;

/**
 * @author Damian Arellanes
 * Inputs: Integer
 * Output: Integer
 * Behaviour: Subtracts 2 from an integer
 */
@RestController
@Component("Correction")
public class Correction implements Device <Correction.InC, Integer> {

  public record InC(Integer p1) implements Parameters {}  /* JSON Request */
  
  @Override
  @PostMapping("/correct") 
  public Integer exec(@RequestBody InC inputs) {    
    return inputs.p1() - 2;
  }

  @Override
  public Class<InC> getParametersClass() {
    return InC.class;
  }
  
  @Override
  public Class<Integer> getReturnType() {
    return Integer.class;
  }
}
