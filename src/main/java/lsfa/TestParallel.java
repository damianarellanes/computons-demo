package lsfa;

import category.comp.colimits.Operators;
import category.comp.objects.Computon;
import graph.ComputonDiagram;
import java.util.concurrent.ExecutionException;
import operation.Runtime;
import operation.Config;
import operation.Control;
import operation.FlatComputon;
import operation.State;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import synthesis.DeviceRegistry;
import synthesis.TypeConfig;

/**
 * @author Damian Arellanes
 * Async Parallel Copmposite: multiply | add
 */
@Service
@Profile("test-parallel")
public final class TestParallel {
  
  public TestParallel (DeviceRegistry devReg, TypeConfig typeConfig, 
    PrimitivesLSFA primitives) throws ExecutionException {           
    
    /*******************************************************/
    /* COMPOSITION */
    /*******************************************************/
    
    Computon composite = Operators.PAR(
      primitives.multiplication(), primitives.addition()
    );
    
    System.out.println(composite);
    new ComputonDiagram(composite, devReg).generate("async-parallel-composite");
    
    /*******************************************************/
    /* EXECUTION */
    /*******************************************************/
    
    FlatComputon flatComposite = new FlatComputon(composite);
    Config config = new Config (typeConfig.getColour(), devReg.getEndpoints());
    
    State initialState = new State(flatComposite, config);
    initialState.map[0] = Control.SIGNAL;
    initialState.map[2] = 7;
    initialState.map[3] = 4;
    initialState.map[5] = Control.SIGNAL;
    initialState.map[7] = 3;
    initialState.map[8] = 0.2;
    
    Runtime runtime = new Runtime (flatComposite, config);
    try {
      
      State finalState = runtime.step(initialState);
      
    } catch (InterruptedException | ExecutionException ex) {
      System.err.println(ex);
    }
  }  
}
