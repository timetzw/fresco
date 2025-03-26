package dk.alexandra.fresco.bgw.gates;

import dk.alexandra.fresco.bgw.BGWResourcePool;
import dk.alexandra.fresco.bgw.datatypes.BGWSInt;
import dk.alexandra.fresco.framework.DRes;
import dk.alexandra.fresco.framework.NativeProtocol;
import dk.alexandra.fresco.framework.network.Network;
import dk.alexandra.fresco.framework.value.SInt;
import java.math.BigInteger;

public class BGWAdditionKnownProtocol implements NativeProtocol<SInt, BGWResourcePool> {

  private final BigInteger x;
  private final DRes<SInt> y;
  private BGWSInt out;

  public BGWAdditionKnownProtocol(BigInteger x, DRes<SInt> y) {
    this.x = x;
    this.y = y;
  }

  @Override
  public EvaluationStatus evaluate(int round, BGWResourcePool resourcePool, Network network) {
    BGWSInt yShamir = (BGWSInt) y.out();
    this.out = yShamir.add(resourcePool.getFieldDefinition().createElement(x),
        resourcePool.getMyId());
    return EvaluationStatus.IS_DONE;
  }

  @Override
  public SInt out() {
    return out;
  }
}
