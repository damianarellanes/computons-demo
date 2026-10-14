package lstm;

import category.comp.colimits.Operators;
import category.comp.diagrams.PartialSeqSpanBuilder;
import category.comp.diagrams.TotalSeqSpanBuilder;
import category.comp.objects.Computon;
import category.comp.objects.Primitive;
import category.comp.objects.Atomic;
import category.comp.objects.AtomicBuilder;
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
 * Memory Cell Composite: 
 * ((((σ > ⊙) | ((σ | tanh) >> ⊙)) >> +) >> replicator) > ((σ | tanh) >> ⊙)
 */
@Service
@Profile("test-lstm")
public final class TestLSTM {
  
  public TestLSTM (DeviceRegistry devReg, TypeConfig typeConfig, 
    PrimitivesLSTM primitives) throws ExecutionException {           
    
    /*******************************************************/
    /* COMPOSITION */
    /*******************************************************/
            
    Primitive multiplicative1 = primitives.multiplicative1();
    Primitive multiplicative2 = primitives.multiplicative2();
    Primitive tanh = primitives.tanh();
    Primitive tanh2 = primitives.tanh2();
    Primitive additive = primitives.additive();
    Atomic replicator = replicator();
    
    Computon forgetGate = buildForgetGate(
      primitives.sigmoidForget(), multiplicative1
    );
    Computon inputGate = buildInputGate(
      primitives.sigmoidInput(), tanh, multiplicative2
    );        
    Computon cellState = buildCellState(
      forgetGate, inputGate, additive
    );
    Computon cellStateReplicated = buildCellStateReplicated(
      cellState, replicator
    );
    Computon outputGate = buildOutputGate(
      primitives.sigmoidOutput(), tanh2, multiplicative2
    ); 
    Computon memoryCell = buildMemoryCell(cellStateReplicated, outputGate);
    
    System.out.println(memoryCell);
    new ComputonDiagram(memoryCell, devReg).generate("memory-cell");
        
    /*******************************************************/
    /* EXECUTION */
    /*******************************************************/
    Double[] prevHiddenState = new Double[]{1.0, 2.0}; // h_{t-1}
    Double[] input = new Double[]{3.0, -1.0, 0.0}; // x_t
    Double[] prevCellState = new Double[]{10.0, -4.0}; // c_{t-1}
    
    FlatComputon flatComposite = new FlatComputon(memoryCell);
    Config config = new Config (typeConfig.getColour(), devReg.getEndpoints());
    
    State initialState = new State(flatComposite, config);    
    // Cell State Inputs
    initialState.map[0] = Control.SIGNAL; // Activates the forget gate
    initialState.map[2] = prevHiddenState;
    initialState.map[3] = input;
    initialState.map[15] = prevHiddenState;
    initialState.map[16] = input;
    initialState.map[13] = Control.SIGNAL; // Activates the input gate
    initialState.map[8] = Control.SIGNAL; // Activates the input gate
    initialState.map[10] = prevHiddenState;
    initialState.map[11] = input;    
    initialState.map[6] = prevCellState;
    // Output Gate Inputs
    initialState.map[30] = prevHiddenState;
    initialState.map[31] = input;
    
    Runtime runtime = new Runtime (flatComposite, config);
    try {
      
      State finalState = runtime.step(initialState);
      
    } catch (InterruptedException | ExecutionException ex) {
      System.err.println(ex);
    }
  }
  
  // Atomic computon which has a device to replicate a single value
  // It also has an epsilon device to join control from two different sources
  // And an epsilon device to echo control from another source
  public static Atomic replicator() {
    
    AtomicBuilder rep = new AtomicBuilder(8, 5, 4, 4, 16);
    rep.s(0, 0);rep.s(1, 1);rep.s(2, 3);rep.s(3, 5);rep.s(4, 5);
    rep.t(0, 2);rep.t(1, 4);rep.t(2, 6);rep.t(3, 7);
    rep.c(0, 0);rep.c(1, 0);rep.c(2, 0);rep.c(3, 0);rep.c(4, 0);rep.c(5, 3);
    rep.c(6, 3);rep.c(7, 3);
    rep.r(0, 0);rep.r(1, 0);rep.r(2, 1);rep.r(3, 2);rep.r(4, 3);
    rep.f(0, 0);rep.f(1, 0);rep.f(2, 15);rep.f(3, 15);
    rep.sigma(0, 0);rep.sigma(1, 0);rep.sigma(2, 0);rep.sigma(3, 0);
    rep.tau(0, 0);rep.tau(1, 0);rep.tau(2, 0);rep.tau(3, 0);rep.tau(4, 0);
    
    rep.label(0, "p0");rep.label(1, "p1");rep.label(2, "p2");rep.label(3, "p3");
    rep.label(4, "p4");rep.label(5, "p5");rep.label(6, "p6");rep.label(7, "p7");
    
    return rep.build();
  }
  
  // sigmoid > multiplicative
  public Computon buildForgetGate(Primitive sigmoid, Primitive multiplicative) {
    
    TrivialBuilder apex = new TrivialBuilder(2, 4, 0);    
    apex.c(0, 0);apex.c(1, 3);
    
    PartialSeqSpanBuilder span = new PartialSeqSpanBuilder(
      apex.build(), sigmoid, multiplicative);
    span.set_P(1, 0, 0);  // 1<--0-->0
    span.set_P(4, 1, 2);  // 4<--1-->2
    
    return Operators.PARTIAL_SEQ(span.build());
  } 
  
  // (sigmoid | tanh) >> multiplicative2
  public Computon buildInputGate(Primitive sigmoid, Primitive tanh, 
    Primitive multiplicative) {
    
    // Parallel computon to trigger the σ and tanh functions simultaneously
    Computon sigmoidTanhPar = Operators.PAR(sigmoid, tanh);
    
    // Total sequential composite to multiply the σ and tanh function outputs
    TrivialBuilder apex = new TrivialBuilder(4, 4, 0);    
    apex.c(0, 0);apex.c(1, 3);apex.c(2, 3);apex.c(3, 0);
     
    TotalSeqSpanBuilder span = new TotalSeqSpanBuilder(
      apex.build(), sigmoidTanhPar, multiplicative
    );
    span.set_P(1, 0, 0);  // 1<--0-->0
    span.set_P(4, 1, 2);  // 4<--1-->2
    span.set_P(9, 2, 3);  // 9<--2-->3
    span.set_P(6, 3, 5);  // 6<--3-->5
    
    return Operators.TOTAL_SEQ(span.build());
  }
  
  // (sigmoid | tanh2) >> multiplicative2
  public Computon buildOutputGate(Primitive sigmoid, Primitive tanh2, 
    Primitive multiplicative) {
    
    // Parallel computon to trigger the σ and tanh2 functions simultaneously
    Computon sigmoidTanhPar = Operators.PAR(sigmoid, tanh2);
    
    // Total sequential composite to multiply the σ and tanh2 function outputs
    TrivialBuilder apex = new TrivialBuilder(4, 4, 0);    
    apex.c(0, 0);apex.c(1, 3);apex.c(2, 3);apex.c(3, 0);
     
    TotalSeqSpanBuilder span = new TotalSeqSpanBuilder(
      apex.build(), sigmoidTanhPar, multiplicative
    );
    span.set_P(1, 0, 0);  // 1<--0-->0
    span.set_P(4, 1, 2);  // 4<--1-->2
    span.set_P(8, 2, 3);  // 8<--2-->3
    span.set_P(6, 3, 5);  // 6<--3-->5
    
    return Operators.TOTAL_SEQ(span.build());
  }
  
  // (buildForgetGate | buildInputGate) >> additive
  public Computon buildCellState(Computon forgetGate, Computon inputGate, 
    Primitive additive) {
    
    // Parallel computon to trigger the forget and input gates simultaneously
    Computon forgetInputPar = Operators.PAR(forgetGate, inputGate);
    
    // Total sequential composite to add the results of forget and input gates
    TrivialBuilder apex = new TrivialBuilder(5, 4, 0);    
    apex.c(0, 0);apex.c(1, 3);apex.c(2, 0);apex.c(3, 3);apex.c(4, 0);
    
    TotalSeqSpanBuilder span = new TotalSeqSpanBuilder(
      apex.build(), forgetInputPar, additive
    );
    span.set_P(5, 0, 0);   //  5<--0-->0
    span.set_P(7, 1, 2);   //  7<--1-->2
    span.set_P(20, 2, 5);  // 20<--2-->5
    span.set_P(19, 3, 3);  // 19<--3-->3
    span.set_P(18, 4, 7);  // 18<--4-->7
    
    return Operators.TOTAL_SEQ(span.build());
  }
  
  // buildCellState >> replicator
  public Computon buildCellStateReplicated(Computon cellState, 
    Atomic replicator) {
    
    // Total sequential composite to replicate the cell state
    TrivialBuilder apex = new TrivialBuilder(4, 4, 0);    
    apex.c(0, 0);apex.c(1, 0);apex.c(2, 0);apex.c(3, 3);
     
    TotalSeqSpanBuilder span = new TotalSeqSpanBuilder(
      apex.build(), cellState, replicator
    );
    span.set_P(23, 0, 0);  // 23<--0-->0
    span.set_P(24, 1, 1);  // 24<--1-->1
    span.set_P(21, 2, 3);  // 21<--2-->3
    span.set_P(22, 3, 5);  // 22<--3-->5
    
    return Operators.TOTAL_SEQ(span.build());
  }
  
  // buildCellStateReplicated > buildOutputGate
  public Computon buildMemoryCell(Computon cellStateReplicated, 
    Computon outputGate) {
        
    // Partial sequential composite to pass one cell state to the output gate
    // The other (identical) cell state is passed to the external world
    TrivialBuilder apex = new TrivialBuilder(3, 4, 0);    
    apex.c(0, 0);apex.c(1, 3);apex.c(2, 0);
     
    PartialSeqSpanBuilder span = new PartialSeqSpanBuilder(
      apex.build(), cellStateReplicated, outputGate
    );
    span.set_P(25, 0, 5);  // 25<--0-->5
    span.set_P(27, 1, 7);  // 27<--1-->7
    span.set_P(26, 2, 0);  // 26<--2-->0
    
    return Operators.PARTIAL_SEQ(span.build());
  }
}
