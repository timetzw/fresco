package dk.alexandra.fresco.bgw.gates;

import dk.alexandra.fresco.bgw.BGWResourcePool;
import dk.alexandra.fresco.bgw.datatypes.BGWSInt;
import dk.alexandra.fresco.bgw.utils.Utils;
import dk.alexandra.fresco.framework.NativeProtocol;
import dk.alexandra.fresco.framework.network.Network;
import dk.alexandra.fresco.framework.value.SInt;

public class BGWRandomProtocol implements NativeProtocol<SInt, BGWResourcePool> {

  private BGWSInt out;

  public EvaluationStatus evaluate(int round, BGWResourcePool resourcePool, Network network) {
    this.out = new BGWSInt(Utils.getRandomFieldElement(resourcePool.getRandom(), resourcePool.getFieldDefinition()));
    return EvaluationStatus.IS_DONE;
  }

  public BGWSInt out() {
    return out;
  }
}
