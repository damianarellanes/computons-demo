package lstm.devices;

import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import synthesis.Parameters;
import synthesis.Device;

/**
 * @author Damian Arellanes
 * Inputs: Vector of real numbers (h_{t-1}), Vector of real numbers (x_t)
 * Output: Vector of real numbers
 * Behaviour:
 * 1. Computes the input contribution: inputWeights ⊙ x_t
 * 2. Computes the hidden contribution: hiddenWeights ⊙ h_{t-1}
 * 3. Adds bias to the input and hidden contributions
 * 4. Applies the tanh activation function to the result from 3
 */
@RestController
@Component("Tanh")
public class Tanh implements Device <Tanh.In, Double[]> {
  
  public record In(
    Double[] prevHiddenState, Double[] currentInput
  ) implements Parameters {}
  
  // Bias
  private Double[] bias = { 0.0, 0.2 }; // hiddenSize (2)  
  // Learned weights
  // hiddenSize x hiddenSize (2x2)
  private Double[][] hiddenWeights = { { 0.3, 0.2}, { -0.5, 0.4 } }; 
  // hiddenSize x inputSize (2x3)
  private Double[][] inputWeights = { { 0.6, -0.1, 0.2 }, { 0.1, 0.5, -0.3 } };
  
  @Override
  @PostMapping("/tanh")
  public Double[] exec(@RequestBody In in) {    
        
    Double[] inputContribution = multiply (inputWeights, in.currentInput());
    Double[] hiddenContribution = multiply (hiddenWeights, in.prevHiddenState());    
    Double[] z = addBias(inputContribution, hiddenContribution);    
    
    return applyTanh(z);
  }

  @Override
  public Class<In> getParametersClass() {
    return In.class;
  }
  
  @Override
  public Class<Double[]> getReturnType() {
    return Double[].class;
  }
  
  private Double[] multiply (Double[][] weights, Double[] state) {
    
    Double[] result = new Double[state.length];
    for (int row = 0; row < weights.length; row++) {
      
      Double rowVal = 0.0;
      for (int col = 0; col < weights[row].length; col++) 
        rowVal += weights[row][col] * state[col];
      result[row] = rowVal;
    }
    return result;
  }
  
  private Double[] addBias (Double[] inputContribution, 
    Double[] hiddenContribution) {
    
    Double[] result = new Double[bias.length];
    for (int i = 0; i < bias.length; i++) 
      result[i] = inputContribution[i] + hiddenContribution[i] + bias[i];
    return result;
  }
  
  private Double[] applyTanh (Double[] z) {
    
    Double[] result = new Double[z.length];
    for (int i = 0; i < z.length; i++) 
      result[i] = Math.tanh(z[i]);
    return result;
  }
}
