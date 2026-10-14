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
 * Behaviour: Returns the predecessor of an integer
 */
@RestController
@Component("Predecessor")
public class Predecessor implements Device <Predecessor.InPre, Integer> {

  public record InPre(Integer p1) implements Parameters {}  /* JSON Request */
  
  @Override
  @PostMapping("/prev")
  public Integer exec(@RequestBody InPre inputs) {    
    return inputs.p1() - 1;
  }

  @Override
  public Class<InPre> getParametersClass() {
    return InPre.class;
  }
  
  @Override
  public Class<Integer> getReturnType() {
    return Integer.class;
  }
}
