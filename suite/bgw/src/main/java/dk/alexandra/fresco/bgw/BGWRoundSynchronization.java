package dk.alexandra.fresco.bgw;

import dk.alexandra.fresco.framework.ProtocolCollection;
import dk.alexandra.fresco.framework.network.Network;
import dk.alexandra.fresco.suite.ProtocolSuite.RoundSynchronization;

public class BGWRoundSynchronization implements RoundSynchronization<BGWResourcePool> {

  public void beforeBatch(ProtocolCollection<BGWResourcePool> nativeProtocols,
      BGWResourcePool resourcePool, Network network) {

  }

  public void finishedBatch(int gatesEvaluated, BGWResourcePool resourcePool, Network network) {

  }

  public void finishedEval(BGWResourcePool resourcePool, Network network) {

  }
}
