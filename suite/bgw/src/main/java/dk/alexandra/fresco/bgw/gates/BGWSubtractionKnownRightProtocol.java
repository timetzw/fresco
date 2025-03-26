package dk.alexandra.fresco.bgw.gates;

import dk.alexandra.fresco.bgw.BGWResourcePool;
import dk.alexandra.fresco.bgw.datatypes.BGWSInt;
import dk.alexandra.fresco.framework.DRes;
import dk.alexandra.fresco.framework.NativeProtocol;
import dk.alexandra.fresco.framework.network.Network;
import dk.alexandra.fresco.framework.value.SInt;
import java.math.BigInteger;

public class BGWSubtractionKnownRightProtocol implements NativeProtocol<SInt, BGWResourcePool> {

  private final DRes<SInt> x;
  private final BigInteger y;
  private BGWSInt out;

  public BGWSubtractionKnownRightProtocol(DRes<SInt> x, BigInteger y) {
    this.x = x;
    this.y = y;
  }

  @Override
  public EvaluationStatus evaluate(int round, BGWResourcePool resourcePool, Network network) {
    BGWSInt xShamir = (BGWSInt) x.out();
    this.out = xShamir.subtract(resourcePool.getFieldDefinition().createElement(y),
        resourcePool.getMyId());
    return EvaluationStatus.IS_DONE;
  }

  @Override
  public SInt out() {
    return out;
  }
}
