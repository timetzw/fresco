package dk.alexandra.fresco.bgw.gates;

import dk.alexandra.fresco.bgw.BGWResourcePool;
import dk.alexandra.fresco.bgw.datatypes.BGWSInt;
import dk.alexandra.fresco.bgw.utils.Utils;
import dk.alexandra.fresco.framework.DRes;
import dk.alexandra.fresco.framework.NativeProtocol;
import dk.alexandra.fresco.framework.builder.numeric.field.FieldElement;
import dk.alexandra.fresco.framework.network.Network;
import dk.alexandra.fresco.framework.value.SInt;
import java.math.BigInteger;
import java.util.List;
import java.util.stream.Collectors;

public class BGWOutputToPartyProtocol implements NativeProtocol<BigInteger, BGWResourcePool> {

  private final DRes<SInt> secret;
  private final int party;
  private BigInteger open;

  public BGWOutputToPartyProtocol(DRes<SInt> secret, int party) {
    this.secret = secret;
    this.party = party;
  }

  @Override
  public EvaluationStatus evaluate(int round, BGWResourcePool resourcePool,
      Network network) {

    if (round == 0) {
      BGWSInt out = (BGWSInt) secret.out();
      network.send(party, resourcePool.getFieldDefinition().serialize(out.getShare()));
      return EvaluationStatus.HAS_MORE_ROUNDS;
    } else {

      if (resourcePool.getMyId() == party) {
        List<byte[]> shares = network.receiveFromAll();
        assert (shares.size() >= resourcePool.getT());

        List<FieldElement> coefficients = resourcePool.getReconstructionCoefficients();
        List<FieldElement> sharesNum = shares.stream()
            .map(resourcePool.getFieldDefinition()::deserialize).collect(
                Collectors.toList());

        FieldElement innerProduct = Utils
            .innerProduct(coefficients, sharesNum);

        this.open = innerProduct.toBigInteger().mod(resourcePool.getModulus());
      }
      return EvaluationStatus.IS_DONE;
    }

  }

  @Override
  public BigInteger out() {
    return open;
  }

}
