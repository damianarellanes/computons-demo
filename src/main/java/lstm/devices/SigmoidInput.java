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
 * 4. Applies the sigmoid function to the result from 3
 */
@RestController
@Component("SigmoidInput")
public class SigmoidInput implements Device <SigmoidInput.In, Double[]> {
  
  // Bias
  private Double[] bias = { 0.1, -0.2 }; // hiddenSize (2)
  
  // Learned weights
  // hiddenSize x hiddenSize (2x2)
  private Double[][] hiddenWeights = { { 0.5, -0.3}, { 0.8, 0.2 } }; 
  // hiddenSize x inputSize (2x3)
  private Double[][] inputWeights = { { 0.1, 0.4, -0.2 }, { -0.5, 0.3, 0.6 } };
  

  public record In(
    Double[] prevHiddenState, Double[] currentInput
  ) implements Parameters {}
  
  @Override
  @PostMapping("/sigmoidInput")
  public Double[] exec(@RequestBody In in) {    
        
    Double[] inputContribution = multiply (inputWeights, in.currentInput());
    Double[] hiddenContribution = multiply (hiddenWeights, in.prevHiddenState());    
    Double[] z = addBias(inputContribution, hiddenContribution);    
    
    return applySigmoid(z);
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
  
  private Double[] applySigmoid (Double[] z) {
    
    Double[] result = new Double[z.length];
    for (int i = 0; i < z.length; i++) 
      result[i] = 1.0 / (1.0 + Math.exp(-z[i]));
    return result;
  }
}
