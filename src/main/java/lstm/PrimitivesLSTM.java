package lstm;

import category.comp.objects.Primitive;
import org.springframework.stereotype.Service;
import synthesis.DeviceRegistry;
import synthesis.Generator;
import synthesis.TypeConfig;

/**
 *
 * @author Damian Arellanes
 */
@Service
public class PrimitivesLSTM {
  
  private final DeviceRegistry devReg;
  private final TypeConfig typeConfig;
  
  public PrimitivesLSTM (DeviceRegistry devReg, TypeConfig typeConfig) {
    
    this.devReg = devReg;
    this.typeConfig = typeConfig;
  }
  
  /*
  SIGMOID PRIMITIVES
  |P|=5, |I|=3, |O|=2, |N|=4, |D|=11
  */
  public Primitive sigmoidInput() {
    return Generator.buildPrimitive(typeConfig, 5, 3, 2, 4, 11, devReg, 0, 10);
  }
  public Primitive sigmoidForget() {
    return Generator.buildPrimitive(typeConfig, 5, 3, 2, 4, 17, devReg, 0, 16);
  }
  public Primitive sigmoidOutput() {
    return Generator.buildPrimitive(typeConfig, 5, 3, 2, 4, 18, devReg, 0, 17);
  }
  
  /*
  MULTIPLICATIVE PRIMITIVE (with 1 control inport)
  |P|=5, |I|=3, |O|=2, |N|=4, |D|=12
  */
  public Primitive multiplicative1() {
    return Generator.buildPrimitive(typeConfig, 5, 3, 2, 4, 12, devReg, 0, 11);
  }
  
  /*
  MULTIPLICATIVE PRIMITIVE 2 (with 2 control inports)
  |P|=7, |I|=4, |O|=3, |N|=4, |D|=12
  */
  public Primitive multiplicative2() {
    return Generator.buildPrimitive(typeConfig, 7, 4, 3, 4, 12, devReg, 0, 11, 0);
  }
  
  /*
  TANH PRIMITIVE
  |P|=5, |I|=3, |O|=2, |N|=4, |D|=13
  */
  public Primitive tanh() {
    return Generator.buildPrimitive(typeConfig, 5, 3, 2, 4, 13, devReg, 0, 12);
  }  
  
  /*
  ADDITIVE PRIMITIVE (with 2 control inports)
  |P|=7, |I|=5, |O|=3, |N|=4, |D|=14
  */
  public Primitive additive() {
    return Generator.buildPrimitive(typeConfig, 9, 5, 4, 4, 14, devReg, 0, 13, 0, 0);
  }
  
  /*
  TANH2 PRIMITIVE
  |P|=4, |I|=2, |O|=2, |N|=4, |D|=15
  */
  public Primitive tanh2() {
    return Generator.buildPrimitive(typeConfig, 4, 2, 2, 4, 15, devReg, 0, 14);
  }  
}
