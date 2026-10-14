package lsfa.devices;

import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import synthesis.Parameters;
import synthesis.Device;

/**
 * @author Damian Arellanes
 * Inputs: Integer, Double
 * Output: Double
 * Behaviour: Adds an integer and a real number
 */
@RestController
@Component("Addition")
public class Addition implements Device <Addition.InAdd, Double> {

  public record InAdd(Integer p1, Double p2) implements Parameters {}  /* JSON Request */
  
  @Override
  @PostMapping("/add")
  public Double exec(@RequestBody InAdd inputs) {    
    return inputs.p1() + inputs.p2();
  }

  @Override
  public Class<InAdd> getParametersClass() {
    return InAdd.class;
  }
  
  @Override
  public Class<Double> getReturnType() {
    return Double.class;
  }
}
