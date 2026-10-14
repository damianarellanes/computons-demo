package arith.devices;

import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import synthesis.Parameters;
import synthesis.Device;

/**
 * @author Damian Arellanes
 * Inputs: Integer, Integer
 * Output: Integer
 * Behaviour: Multiplies two integers
 */
@RestController
@Component("Multiplication")
public class Multiplication implements Device <Multiplication.InMult, Integer> {

  /* JSON Request */
  public record InMult(Integer p1, Integer p2) implements Parameters {} 
  
  @Override
  @PostMapping("/multiply")
  public Integer exec(@RequestBody InMult inputs) {    
    return inputs.p1() * inputs.p2();
  }

  @Override
  public Class<InMult> getParametersClass() {
    return InMult.class;
  }
  
  @Override
  public Class<Integer> getReturnType() {
    return Integer.class;
  }
}
