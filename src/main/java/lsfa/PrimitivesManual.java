package lsfa;

import category.comp.objects.Primitive;
import category.comp.objects.PrimitiveBuilder;

/**
 * @author Damian Arellanes
 * Colours: 0(Ctr), 1(Nat), 2(Real)
 * Devices: 0(_), 1(X), 2(+), 3(++), 4(--), 5(!), 6(4_const), 7(rec), 
 * 8(correction), 9(echoNum)
 */
public final class PrimitivesManual {
  
  /*
  MULTIPLICATION PRIMITIVE
  |P|=5, |I|=3, |O|=2, |N|=2, |D|=2
  */
  private PrimitivesManual () {}
  
  public static Primitive multiplication() {
    
    PrimitiveBuilder mult = new PrimitiveBuilder(5, 3, 2, 2, 2);
    mult.s(0, 0);mult.s(1, 1);mult.s(2, 2);mult.finalise_s();
    mult.t(0, 3);mult.t(1, 4);mult.finalise_t();
    mult.c(0, 0);mult.c(1, 1);mult.c(2, 1);mult.c(3, 0);mult.c(4, 1);mult.finalise_c();
    mult.r(0, 0);mult.r(1, 1);mult.r(2, 1);
    mult.f(0, 0);mult.f(1, 1);
    mult.sigma(0, 0);mult.sigma(1, 0);
    mult.tau(0, 0);mult.tau(1, 0);mult.tau(2, 0);
    
    for (int port = 0; port < 5; port++) mult.label(port, String.valueOf(port));
    
    return mult.build();
  }
  
  /*
  ADDITION PRIMITIVE
  |P|=5, |I|=3, |O|=2, |N|=3, |D|=3
  */
  public static Primitive add() {

    PrimitiveBuilder add = new PrimitiveBuilder(5, 3, 2, 3, 3);
    add.s(0, 0);add.s(1, 1);add.s(2, 2);add.finalise_s();
    add.t(0, 3);add.t(1, 4);add.finalise_t();
    add.c(0, 0);add.c(1, 1);add.c(2, 2);add.c(3, 0);add.c(4, 2);add.finalise_c();
    add.r(0, 0);add.r(1, 1);add.r(2, 1);
    add.f(0, 0);add.f(1, 2);
    add.sigma(0, 0);add.sigma(1, 0);
    add.tau(0, 0);add.tau(1, 0);add.tau(2, 0);
    
    for (int port = 0; port < 5; port++) add.label(port, String.valueOf(port));

    return add.build();
  }
  
  /*
  SUCCESSOR PRIMITIVE
  |P|=4, |I|=2, |O|=2, |N|=2, |D|=4
  */
  public static Primitive add1() {
        
    PrimitiveBuilder add1 = new PrimitiveBuilder(4, 2, 2, 2, 4);
    add1.s(0, 0);add1.s(1, 1);add1.finalise_s();
    add1.t(0, 2);add1.t(1, 3);add1.finalise_t();
    add1.c(0, 0);add1.c(1, 1);add1.c(2, 0);add1.c(3, 1);add1.finalise_c();
    add1.r(0, 0);add1.r(1, 1);
    add1.f(0, 0);add1.f(1, 3);
    add1.sigma(0, 0);add1.sigma(1, 0);
    add1.tau(0, 0);add1.tau(1, 0);
    
    for (int port = 0; port < 4; port++) add1.label(port, String.valueOf(port));

    return add1.build();
  }
  
  /*
  PREDECESSOR PRIMITIVE
  |P|=4, |I|=2, |O|=2, |N|=3, |D|=5
  */
  public static Primitive minus1() {
        
    PrimitiveBuilder minus1 = new PrimitiveBuilder(4, 2, 2, 3, 5);
    minus1.s(0, 0);minus1.s(1, 1);minus1.finalise_s();
    minus1.t(0, 2);minus1.t(1, 3);minus1.finalise_t();
    minus1.c(0, 0);minus1.c(1, 1);minus1.c(2, 0);minus1.c(3, 2);minus1.finalise_c();
    minus1.r(0, 0);minus1.r(1, 1);
    minus1.f(0, 0);minus1.f(1, 4);
    minus1.sigma(0, 0);minus1.sigma(1, 0);
    minus1.tau(0, 0);minus1.tau(1, 0);
    
    for (int port = 0; port < 4; port++) minus1.label(port, String.valueOf(port));

    return minus1.build();
  }
  
  /*
  FACTORIAL PRIMITIVE
  |P|=4, |I|=2, |O|=2, |N|=2, |D|=6
  */
  public static Primitive factorial() {
        
    PrimitiveBuilder f = new PrimitiveBuilder(4, 2, 2, 2, 6);
    f.s(0, 0);f.s(1, 1);f.finalise_s();
    f.t(0, 2);f.t(1, 3);f.finalise_t();
    f.c(0, 0);f.c(1, 1);f.c(2, 0);f.c(3, 1);f.finalise_c();
    f.r(0, 0);f.r(1, 1);
    f.f(0, 0);f.f(1, 5);
    f.sigma(0, 0);f.sigma(1, 0);
    f.tau(0, 0);f.tau(1, 0);
    
    for (int port = 0; port < 4; port++) f.label(port, String.valueOf(port));

    return f.build();
  }
  
  /*
  CONSTANT 4 PRIMITIVE
  |P|=4, |I|=2, |O|=2, |N|=2, |D|=7
  */
  public static Primitive constant4() {
        
    PrimitiveBuilder four = new PrimitiveBuilder(4, 2, 2, 2, 7);
    four.s(0, 0);four.s(1, 1);four.finalise_s();
    four.t(0, 2);four.t(1, 3);four.finalise_t();
    four.c(0, 0);four.c(1, 0);four.c(2, 0);four.c(3, 1);four.finalise_c();
    four.r(0, 0);four.r(1, 1);
    four.f(0, 0);four.f(1, 6);
    four.sigma(0, 0);four.sigma(1, 0);
    four.tau(0, 0);four.tau(1, 0);
    
    for (int port = 0; port < 4; port++) four.label(port, String.valueOf(port));

    return four.build();
  }
  
  /*
  RECURRENT PRIMITIVE
  |P|=4, |I|=2, |O|=2, |N|=2, |D|=8
  */
  public static Primitive recurrent() {
        
    PrimitiveBuilder rec = new PrimitiveBuilder(4, 2, 2, 2, 8);
    rec.s(0, 0);rec.s(1, 1);rec.finalise_s();
    rec.t(0, 2);rec.t(1, 3);rec.finalise_t();
    rec.c(0, 0);rec.c(1, 1);rec.c(2, 0);rec.c(3, 1);rec.finalise_c();
    rec.r(0, 0);rec.r(1, 1);
    rec.f(0, 0);rec.f(1, 7);
    rec.sigma(0, 0);rec.sigma(1, 0);
    rec.tau(0, 0);rec.tau(1, 0);
    
    for (int port = 0; port < 4; port++) rec.label(port, String.valueOf(port));

    return rec.build();
  }
  
  /*
  CORRECTION PRIMITIVE
  |P|=4, |I|=2, |O|=2, |N|=2, |D|=8
  */
  public static Primitive correction() {
        
    PrimitiveBuilder cor = new PrimitiveBuilder(4, 2, 2, 2, 9);
    cor.s(0, 0);cor.s(1, 1);cor.finalise_s();
    cor.t(0, 2);cor.t(1, 3);cor.finalise_t();
    cor.c(0, 0);cor.c(1, 1);cor.c(2, 0);cor.c(3, 1);cor.finalise_c();
    cor.r(0, 0);cor.r(1, 1);
    cor.f(0, 0);cor.f(1, 8);
    cor.sigma(0, 0);cor.sigma(1, 0);
    cor.tau(0, 0);cor.tau(1, 0);
    
    for (int port = 0; port < 4; port++) cor.label(port, String.valueOf(port));

    return cor.build();
  }
  
  /*
  ECHO_NUM PRIMITIVE
  |P|=4, |I|=2, |O|=2, |N|=2, |D|=8
  */
  public static Primitive echoNum() {
        
    PrimitiveBuilder echo = new PrimitiveBuilder(4, 2, 2, 2, 10);
    echo.s(0, 0);echo.s(1, 1);echo.finalise_s();
    echo.t(0, 2);echo.t(1, 3);echo.finalise_t();
    echo.c(0, 0);echo.c(1, 1);echo.c(2, 0);echo.c(3, 1);echo.finalise_c();
    echo.r(0, 0);echo.r(1, 1);
    echo.f(0, 0);echo.f(1, 9);
    echo.sigma(0, 0);echo.sigma(1, 0);
    echo.tau(0, 0);echo.tau(1, 0);
    
    for (int port = 0; port < 4; port++) echo.label(port, String.valueOf(port));

    return echo.build();
  }
}
