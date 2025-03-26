package dk.alexandra.fresco.bgw.gates;

import dk.alexandra.fresco.bgw.BGWResourcePool;
import dk.alexandra.fresco.bgw.datatypes.BGWSInt;
import dk.alexandra.fresco.framework.DRes;
import dk.alexandra.fresco.framework.NativeProtocol;
import dk.alexandra.fresco.framework.network.Network;
import dk.alexandra.fresco.framework.value.SInt;
import java.math.BigInteger;

public class BGWSubtractionKnownLeftProtocol implements NativeProtocol<SInt, BGWResourcePool> {

  private final BigInteger x;
  private final DRes<SInt> y;
  private BGWSInt out;

  public BGWSubtractionKnownLeftProtocol(BigInteger x, DRes<SInt> y) {
    this.x = x;
    this.y = y;
  }

  @Override
  public EvaluationStatus evaluate(int round, BGWResourcePool resourcePool, Network network) {
    BGWSInt yOut = (BGWSInt) y.out();
    this.out = new BGWSInt(
        resourcePool.getFieldDefinition().createElement(x).subtract(yOut.getShare()));
    return EvaluationStatus.IS_DONE;
  }

  @Override
  public SInt out() {
    return out;
  }
}
