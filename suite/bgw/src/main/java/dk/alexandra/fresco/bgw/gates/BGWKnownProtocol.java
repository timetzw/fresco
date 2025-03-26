package dk.alexandra.fresco.bgw.gates;

import dk.alexandra.fresco.bgw.BGWResourcePool;
import dk.alexandra.fresco.bgw.datatypes.BGWSInt;
import dk.alexandra.fresco.framework.NativeProtocol;
import dk.alexandra.fresco.framework.network.Network;
import dk.alexandra.fresco.framework.value.SInt;
import java.math.BigInteger;

public class BGWKnownProtocol implements NativeProtocol<SInt, BGWResourcePool> {

  private final BigInteger value;
  private BGWSInt out;

  public BGWKnownProtocol(BigInteger value) {
    this.value = value;
  }

  public EvaluationStatus evaluate(int round, BGWResourcePool resourcePool, Network network) {
    // We let the constant polynomial p(x) = value represent a secret sharing of the given value
    this.out = new BGWSInt(resourcePool.getFieldDefinition().createElement(value));
    return EvaluationStatus.IS_DONE;
  }

  public BGWSInt out() {
    return out;
  }
}
