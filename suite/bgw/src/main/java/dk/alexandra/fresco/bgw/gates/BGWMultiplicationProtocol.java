package dk.alexandra.fresco.bgw.gates;

import dk.alexandra.fresco.bgw.BGWResourcePool;
import dk.alexandra.fresco.bgw.datatypes.BGWSInt;
import dk.alexandra.fresco.bgw.utils.Polynomial;
import dk.alexandra.fresco.bgw.utils.Utils;
import dk.alexandra.fresco.framework.DRes;
import dk.alexandra.fresco.framework.NativeProtocol;
import dk.alexandra.fresco.framework.builder.numeric.field.FieldElement;
import dk.alexandra.fresco.framework.network.Network;
import dk.alexandra.fresco.framework.value.SInt;
import java.util.List;
import java.util.stream.Collectors;

public class BGWMultiplicationProtocol implements NativeProtocol<SInt, BGWResourcePool> {

  private final DRes<SInt> x, y;
  private SInt out;

  public BGWMultiplicationProtocol(DRes<SInt> x, DRes<SInt> y) {
    this.x = x;
    this.y = y;
  }

  @Override
  public EvaluationStatus evaluate(int round, BGWResourcePool resourcePool,
      Network network) {

    if (round == 0) {
      BGWSInt xOut = (BGWSInt) x.out();
      BGWSInt yOut = (BGWSInt) y.out();
      FieldElement value = xOut.getShare().multiply(yOut.getShare());

      // Create secret sharing of my share
      Polynomial polynomial = new Polynomial(resourcePool.getT(), i -> i == 0 ? value : Utils.getRandomFieldElement(
          resourcePool.getRandom(), resourcePool.getFieldDefinition()));
      for (int i = 1; i <= resourcePool.getNoOfParties(); i++) {
        FieldElement share = polynomial
            .evaluate(resourcePool.getFieldDefinition().createElement(i));
        network.send(i, resourcePool.getFieldDefinition().serialize(share));
      }
      return EvaluationStatus.HAS_MORE_ROUNDS;

    } else {

      // Receive shares from all other parties
      List<FieldElement> myShares = network.receiveFromAll().stream()
          .map(resourcePool.getFieldDefinition()::deserialize).collect(
              Collectors.toList());

      // Precompute, maybe into ResourcePool?
      List<FieldElement> coefficients = resourcePool.getReconstructionCoefficients();

      // Reconstruct secret sharing of degree t polynomial
      FieldElement myShare = Utils.innerProduct(myShares, coefficients);
      this.out = new BGWSInt(myShare);

      return EvaluationStatus.IS_DONE;
    }
  }

  @Override
  public SInt out() {
    return out;
  }

}
