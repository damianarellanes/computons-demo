package lsfa;

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
public class PrimitivesLSFA {
  
  private final DeviceRegistry devReg;
  private final TypeConfig typeConfig;
  
  public PrimitivesLSFA (DeviceRegistry devReg, TypeConfig typeConfig) {
    
    this.devReg = devReg;
    this.typeConfig = typeConfig;
  }
  
  /*
  MULTIPLICATION PRIMITIVE
  |P|=5, |I|=3, |O|=2, |N|=2, |D|=2
  */
  public Primitive multiplication() {
    return Generator.buildPrimitive(typeConfig, 5, 3, 2, 2, 2, devReg, 0, 1);
  }
  
  /*
  ADDITION PRIMITIVE
  |P|=5, |I|=3, |O|=2, |N|=3, |D|=3
  */
  public Primitive addition() {
    return Generator.buildPrimitive(typeConfig, 5, 3, 2, 3, 3, devReg, 0, 2);
  }
  
  /*
  SUCCESSOR PRIMITIVE
  |P|=4, |I|=2, |O|=2, |N|=2, |D|=4
  */
  public Primitive successor() {        
    return Generator.buildPrimitive(typeConfig, 4, 2, 2, 2, 4, devReg, 0, 3);
  }
  
  /*
  PREDECESSOR PRIMITIVE
  |P|=4, |I|=2, |O|=2, |N|=3, |D|=5
  */
  public Primitive predecessor() {        
    return Generator.buildPrimitive(typeConfig, 4, 2, 2, 3, 5, devReg, 0, 4);
  }
  
  /*
  FACTORIAL PRIMITIVE
  |P|=4, |I|=2, |O|=2, |N|=2, |D|=6
  */
  public Primitive factorial() {    
    return Generator.buildPrimitive(typeConfig, 4, 2, 2, 2, 6, devReg, 0, 5);        
  }
  
  /*
  CONSTANT 4 PRIMITIVE
  |P|=4, |I|=2, |O|=2, |N|=2, |D|=7
  */
  public Primitive constant4() {        
    return Generator.buildPrimitive(typeConfig, 4, 2, 2, 2, 7, devReg, 0, 6);
  }
  
  /*
  RECURRENT PRIMITIVE
  |P|=4, |I|=2, |O|=2, |N|=2, |D|=8
  */
  public Primitive recurrent() {
    return Generator.buildPrimitive(typeConfig, 4, 2, 2, 2, 8, devReg, 0, 7);
  }
  
  /*
  CORRECTION PRIMITIVE
  |P|=4, |I|=2, |O|=2, |N|=2, |D|=9
  */
  public Primitive correction() {        
    return Generator.buildPrimitive(typeConfig, 4, 2, 2, 2, 9, devReg, 0, 8);
  }
  
  /*
  ECHO_NUM PRIMITIVE
  |P|=4, |I|=2, |O|=2, |N|=2, |D|=10
  */
  public Primitive echoNat() {        
    return Generator.buildPrimitive(typeConfig, 4, 2, 2, 2, 10, devReg, 0, 9);
  }  
}
