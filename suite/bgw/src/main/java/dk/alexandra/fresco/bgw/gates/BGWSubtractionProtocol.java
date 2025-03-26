package dk.alexandra.fresco.bgw.gates;

import dk.alexandra.fresco.bgw.BGWResourcePool;
import dk.alexandra.fresco.bgw.datatypes.BGWSInt;
import dk.alexandra.fresco.framework.DRes;
import dk.alexandra.fresco.framework.NativeProtocol;
import dk.alexandra.fresco.framework.network.Network;
import dk.alexandra.fresco.framework.value.SInt;

public class BGWSubtractionProtocol implements NativeProtocol<SInt, BGWResourcePool> {

  private final DRes<SInt> x, y;
  private BGWSInt out;

  public BGWSubtractionProtocol(DRes<SInt> x, DRes<SInt> y) {
    this.x = x;
    this.y = y;
  }

  @Override
  public EvaluationStatus evaluate(int round, BGWResourcePool resourcePool, Network network) {
    BGWSInt xShamir = (BGWSInt) x.out();
    BGWSInt yShamir = (BGWSInt) y.out();
    this.out = new BGWSInt(xShamir.getShare().subtract(yShamir.getShare()));
    return EvaluationStatus.IS_DONE;
  }

  @Override
  public SInt out() {
    return out;
  }
}
