package lsfa;

import category.comp.colimits.Operators;
import category.comp.diagrams.HDiagramBuilder;
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
 * Head-Iterative Composite: *(4,rec,corr,echoNat)
 */
@Service
@Profile("test-head")
public final class TestHead {
  
  public TestHead (DeviceRegistry devReg, TypeConfig typeConfig, 
    PrimitivesLSFA primitives) {           
    
    /*******************************************************/
    /* COMPOSITION */
    /*******************************************************/
    
    Primitive entry = primitives.constant4();
    Primitive it = primitives.recurrent();
    Primitive postIt = primitives.correction();
    Primitive exit = primitives.echoNat();
    
    Computon composite = tailHead(entry, it, postIt, exit);    
    
    System.out.println(composite);
    new ComputonDiagram(composite, devReg).generate("head-iterative-composite");
    
    /*******************************************************/
    /* EXECUTION */
    /*******************************************************/
    
    FlatComputon flatComposite = new FlatComputon(composite);
    Config config = new Config (typeConfig.getColour(), devReg.getEndpoints());
    
    State initialState = new State(flatComposite, config);
    initialState.map[6] = Control.SIGNAL;
    initialState.map[7] = Control.SIGNAL;
    
    Runtime runtime = new Runtime (flatComposite, config);
    try {
      
      State finalState = runtime.step(initialState);
      
    } catch (InterruptedException | ExecutionException ex) {
      System.err.println(ex);
    } 
  }
  
  public Computon tailHead(Primitive constant4, Primitive recurrent, 
    Primitive correction, Primitive echoNum) {
    
    TrivialBuilder apex0 = new TrivialBuilder(2, 2, 0);    
    apex0.c(0, 0);apex0.c(1, 1);
    TrivialBuilder apex1 = new TrivialBuilder(2, 2, 0);    
    apex1.c(0, 0);apex1.c(1, 1);
    
    HDiagramBuilder hBuilder = new HDiagramBuilder(
      apex0.build(), apex1.build(), constant4, recurrent, correction, echoNum);
    
    // Branching links to merge inports 
    // echoNum <- apex0 -> recurrent
    hBuilder.set_Span_Down(0, 0, 0); // 0<--0-->0
    hBuilder.set_Span_Down(2, 1, 2); // 2<--1-->2
    
    // Continuation/Join links to merge outports 
    // constant4 <- apex0 -> correct
    hBuilder.set_Span_Up(1, 0, 1); // 1<--0-->1
    hBuilder.set_Span_Up(3, 1, 3); // 3<--1-->3
    
    // Loop links to merge left inports with right outports 
    // correct <- apex1 -> recurrent
    hBuilder.set_Span_Right(0, 0, 1); // 0<--0-->1
    hBuilder.set_Span_Right(2, 1, 3); // 2<--1-->3        
    
    return Operators.H_IT(hBuilder.build());
  }
}
