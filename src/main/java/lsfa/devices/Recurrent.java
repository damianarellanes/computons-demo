package lsfa.devices;

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
 * Behaviour: Adds 5 to an integer
 */
@RestController
@Component("Recurrent")
public class Recurrent implements Device <Recurrent.InF, Integer> {

  public record InF(Integer p1) implements Parameters {}  /* JSON Request */
  
  @Override
  @PostMapping("/recurrent")
  public Integer exec(@RequestBody InF inputs) {    
    return inputs.p1() + 5;
  }

  @Override
  public Class<InF> getParametersClass() {
    return InF.class;
  }
  
  @Override
  public Class<Integer> getReturnType() {
    return Integer.class;
  }
}
