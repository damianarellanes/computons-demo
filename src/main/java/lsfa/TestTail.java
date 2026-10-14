package lsfa;

import category.comp.colimits.Operators;
import category.comp.diagrams.TDiagramBuilder;
import category.comp.objects.Computon;
import category.comp.objects.Primitive;
import category.comp.objects.TrivialBuilder;
import graph.ComputonDiagram;
import java.util.concurrent.ExecutionException;
import operation.Config;
import operation.Control;
import operation.FlatComputon;
import operation.State;
import operation.Runtime;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import synthesis.DeviceRegistry;
import synthesis.TypeConfig;

/**
 * @author Damian Arellanes
 * Tail-Iterative Composite: (4,rec,corr,echoNat)*
 */
@Service
@Profile("test-tail")
public final class TestTail {
  
  public TestTail (DeviceRegistry devReg, TypeConfig typeConfig, 
    PrimitivesLSFA primitives) {           
    
    /*******************************************************/
    /* COMPOSITION */
    /*******************************************************/
    
    Primitive entry = primitives.constant4();
    Primitive it = primitives.recurrent();
    Primitive postIt = primitives.correction();
    Primitive exit = primitives.echoNat();
    
    Computon composite = tailIterative(entry, it, postIt, exit);
    
    System.out.println(composite);
    new ComputonDiagram(composite, devReg).generate("tail-iterative-composite");
    
    /*******************************************************/
    /* EXECUTION */
    /*******************************************************/
    
    FlatComputon flatComposite = new FlatComputon(composite);
    Config config = new Config (typeConfig.getColour(), devReg.getEndpoints());
    
    State initialState = new State(flatComposite, config);
    initialState.map[0] = Control.SIGNAL;
    initialState.map[2] = Control.SIGNAL;
    
    Runtime runtime = new Runtime (flatComposite, config);
    try {
      
      State finalState = runtime.step(initialState);
      
    } catch (InterruptedException | ExecutionException ex) {
      System.err.println(ex);
    } 
  }
  
  public Computon tailIterative(Primitive constant4, Primitive recurrent, 
    Primitive correction, Primitive echoNum) {
    
    TrivialBuilder apex0 = new TrivialBuilder(2, 2, 0);    
    apex0.c(0, 0);apex0.c(1, 1);
    TrivialBuilder apex1 = new TrivialBuilder(2, 2, 0);    
    apex1.c(0, 0);apex1.c(1, 1);
    
    TDiagramBuilder tBuilder = new TDiagramBuilder(
      apex0.build(), apex1.build(), constant4, recurrent, correction, echoNum);
    
    // Constructs the in- and out-markers of the iterative computon
    tBuilder.set_It_In(0, 0); tBuilder.set_It_In(1, 2);
    tBuilder.set_It_Out(0, 1); tBuilder.set_It_Out(1, 3);
    
    // Continuation/Join links
    tBuilder.set_Span_Left(1, 0, 1); // 1<--0-->1
    tBuilder.set_Span_Left(3, 1, 3); // 3<--1-->3
    
    // Branching links
    tBuilder.set_Span_Right(0, 0, 0); // 0<--0-->0
    tBuilder.set_Span_Right(2, 1, 2); // 2<--1-->2
            
    return Operators.T_IT(tBuilder.build());
  }
}
