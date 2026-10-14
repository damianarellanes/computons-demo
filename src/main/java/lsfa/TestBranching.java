package lsfa;

import category.comp.colimits.Operators;
import category.comp.diagrams.BranSpanBuilder;
import category.comp.objects.Computon;
import category.comp.objects.Primitive;
import category.comp.objects.TrivialBuilder;
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
 * (Open Branching) Composite: add1 ? minus1
 */
@Service
@Profile("test-branching")
public final class TestBranching {
  
  public TestBranching (DeviceRegistry devReg, TypeConfig typeConfig, 
    PrimitivesLSFA primitives) throws ExecutionException {           
    
    /*******************************************************/
    /* COMPOSITION */
    /*******************************************************/
    
    Computon composite = branching(
      primitives.successor(), primitives.predecessor()
    );
    
    System.out.println(composite);
    new ComputonDiagram(composite, devReg).generate("branching-composite");    
    
    /*******************************************************/
    /* EXECUTION */
    /*******************************************************/
    
    FlatComputon flatComposite = new FlatComputon(composite);
    Config config = new Config (typeConfig.getColour(), devReg.getEndpoints());
    
    State initialState = new State(flatComposite, config);
    initialState.map[0] = Control.SIGNAL;
    initialState.map[2] = 7;
    
    Runtime runtime = new Runtime (flatComposite, config);
    try {
      
      State finalState = runtime.step(initialState);
      
    } catch (InterruptedException | ExecutionException ex) {
      System.err.println(ex);
    }
  }
  
  public Computon branching(Primitive left, Primitive right) {
    
    TrivialBuilder apex = new TrivialBuilder(2, 2, 0);    
    apex.c(0, 0);apex.c(1, 1);
    
    BranSpanBuilder span = new BranSpanBuilder(
      apex.build(), left, right);
    span.set_P(0, 0, 0);  // 0<--0-->0
    span.set_P(2, 1, 2);  // 2<--1-->2
    
    return Operators.BRAN(span.build());
  }  
}
