package dk.alexandra.fresco.bgw.gates;

import dk.alexandra.fresco.bgw.BGWResourcePool;
import dk.alexandra.fresco.bgw.datatypes.BGWSInt;
import dk.alexandra.fresco.framework.DRes;
import dk.alexandra.fresco.framework.NativeProtocol;
import dk.alexandra.fresco.framework.network.Network;
import dk.alexandra.fresco.framework.value.SInt;
import java.math.BigInteger;

public class BGWConstantMultiplicationProtocol implements NativeProtocol<SInt, BGWResourcePool> {

  private final DRes<SInt> x;
  private final BigInteger s;
  private BGWSInt out;

  public BGWConstantMultiplicationProtocol(BigInteger s, DRes<SInt> x) {
    this.s = s;
    this.x = x;
  }

  @Override
  public EvaluationStatus evaluate(int round, BGWResourcePool resourcePool, Network network) {
    BGWSInt xShamir = (BGWSInt) x.out();
    this.out = xShamir.multiply(resourcePool.getFieldDefinition().createElement(s));
    return EvaluationStatus.IS_DONE;
  }

  @Override
  public SInt out() {
    return out;
  }
}
