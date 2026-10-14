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
 * Behaviour: Echoes an integer
 */
@RestController
@Component("EchoNat")
public class EchoNat implements Device <EchoNat.InEchoNat, Integer> {

  public record InEchoNat(Integer p1) implements Parameters {}  /* JSON Request */ 
  
  @Override
  @PostMapping("/echoNat")
  public Integer exec(@RequestBody InEchoNat inputs) {    
    return inputs.p1();
  }

  @Override
  public Class<InEchoNat> getParametersClass() {
    return InEchoNat.class;
  }
  
  @Override
  public Class<Integer> getReturnType() {
    return Integer.class;
  }
}
