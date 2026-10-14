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
 * Behaviour: Returns the successor of an integer
 */
@RestController
@Component("Successor")
public class Successor implements Device <Successor.InSucc, Integer> {

  public record InSucc(Integer p1) implements Parameters {}  /* JSON Request */
  
  @Override
  @PostMapping("/next")
  public Integer exec(@RequestBody InSucc inputs) {    
    return inputs.p1() + 1;
  }

  @Override
  public Class<InSucc> getParametersClass() {
    return InSucc.class;
  }
  
  @Override
  public Class<Integer> getReturnType() {
    return Integer.class;
  }
}
