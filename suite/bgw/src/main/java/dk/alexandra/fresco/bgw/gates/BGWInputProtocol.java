package dk.alexandra.fresco.bgw.gates;

import dk.alexandra.fresco.bgw.BGWResourcePool;
import dk.alexandra.fresco.bgw.datatypes.BGWSInt;
import dk.alexandra.fresco.bgw.utils.Polynomial;
import dk.alexandra.fresco.bgw.utils.Utils;
import dk.alexandra.fresco.framework.NativeProtocol;
import dk.alexandra.fresco.framework.builder.numeric.field.FieldElement;
import dk.alexandra.fresco.framework.network.Network;
import dk.alexandra.fresco.framework.value.SInt;
import java.math.BigInteger;

public class BGWInputProtocol implements NativeProtocol<SInt, BGWResourcePool> {

  private final BigInteger value;
  private final int inputParty;
  private BGWSInt out;

  public BGWInputProtocol(BigInteger value, int inputParty) {
    this.value = value;
    this.inputParty = inputParty;
  }

  public EvaluationStatus evaluate(int round, BGWResourcePool resourcePool, Network network) {
    if (round == 0) {
      if (resourcePool.getMyId() == inputParty) {

        Polynomial polynomial = new Polynomial(resourcePool.getT(), i -> {
          if ( i == 0) {
            return resourcePool.getFieldDefinition().createElement(value);
          } else {
            return Utils.getRandomFieldElement(resourcePool.getRandom(), resourcePool.getFieldDefinition());
          }
        });
        for (int i = 1; i <= resourcePool.getNoOfParties(); i++) {
          FieldElement value = polynomial.evaluate(resourcePool.getFieldDefinition().createElement(i));
          network.send(i, resourcePool.getFieldDefinition().serialize(value));
        }
      }
      return EvaluationStatus.HAS_MORE_ROUNDS;
    } else {
      byte[] bytes = network.receive(inputParty);
      FieldElement share = resourcePool.getFieldDefinition().deserialize(bytes);
      this.out = new BGWSInt(share);
      return EvaluationStatus.IS_DONE;
    }

  }

  public BGWSInt out() {
    return out;
  }
}
