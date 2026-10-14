package arith;

import category.comp.colimits.Operators;
import category.comp.diagrams.PartialSeqSpanBuilder;
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
 * Partial Sequential Composite: multiply > add
 */
@Service
@Profile("test-partialseq")
public final class TestPartialSeq {
  
  public TestPartialSeq (DeviceRegistry devReg, TypeConfig typeConfig, 
    PrimitivesArith primitives) throws ExecutionException {           
    
    /*******************************************************/
    /* COMPOSITION */
    /*******************************************************/
    
    Computon composite = partialSeq(
      primitives.multiplication(), primitives.addition()
    );
    
    System.out.println(composite);
    new ComputonDiagram(composite, devReg).generate("partial-seq-composite");
    
    /*******************************************************/
    /* EXECUTION */
    /*******************************************************/
    
    FlatComputon flatComposite = new FlatComputon(composite);
    Config config = new Config (typeConfig.getColour(), devReg.getEndpoints());
    
    State initialState = new State(flatComposite, config);
    initialState.map[0] = Control.SIGNAL;
    initialState.map[2] = 7;
    initialState.map[3] = 4;
    initialState.map[6] = 13.21;
    
    Runtime runtime = new Runtime (flatComposite, config);
    try {
      
      State finalState = runtime.step(initialState);
      
    } catch (InterruptedException | ExecutionException ex) {
      System.err.println(ex);
    }
  }
  
  public Computon partialSeq(Primitive left, Primitive right) {
    
    TrivialBuilder apex = new TrivialBuilder(2, 2, 0);    
    apex.c(0, 0);apex.c(1, 1);
    
    PartialSeqSpanBuilder span = new PartialSeqSpanBuilder(
      apex.build(), left, right);
    span.set_P(1, 0, 0);  // 1<--0-->0
    span.set_P(4, 1, 2);  // 4<--1-->2
    
    return Operators.PARTIAL_SEQ(span.build());
  }  
}
