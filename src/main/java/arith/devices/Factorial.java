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
 * Behaviour: Computes the factorial of an integer
 */
@RestController
@Component("Factorial")
public class Factorial implements Device <Factorial.InFact, Integer> {

  public record InFact(Integer p1) implements Parameters {}  /* JSON Request */
  
  @Override
  @PostMapping("/factorial")
  public Integer exec(@RequestBody InFact inputs) {    
    
    int n = inputs.p1(), f = 1;
    for (int i = 2; i <=n; i++) f *= i; 
    return f;
  }

  @Override
  public Class<InFact> getParametersClass() {
    return InFact.class;
  }
  
  @Override
  public Class<Integer> getReturnType() {
    return Integer.class;
  }
}
