package arith.devices;

import operation.Control;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import synthesis.Parameters;
import synthesis.Device;

/**
 * @author Damian Arellanes
 * Inputs: Control
 * Output: Integer
 * Behaviour: Produces the constant 4
 */
@RestController
@Component("Const4")
public class Const4 implements Device <Const4.In4, Integer> {

  public record In4(Control p1) implements Parameters {}  /* JSON Request */
  
  @Override
  @PostMapping("/4")
  public Integer exec(@RequestBody In4 inputs) {    
    return 4;
  }

  @Override
  public Class<In4> getParametersClass() {
    return In4.class;
  }
  
  @Override
  public Class<Integer> getReturnType() {
    return Integer.class;
  }
}
