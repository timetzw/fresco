package dk.alexandra.fresco.bgw;

import dk.alexandra.fresco.bgw.utils.Utils;
import dk.alexandra.fresco.framework.builder.numeric.NumericResourcePool;
import dk.alexandra.fresco.framework.builder.numeric.field.BigIntegerFieldDefinition;
import dk.alexandra.fresco.framework.builder.numeric.field.FieldDefinition;
import dk.alexandra.fresco.framework.builder.numeric.field.FieldElement;
import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.List;
import java.util.Random;

public class BGWResourcePool implements NumericResourcePool {

  private final int id;
  private final int parties;
  private final FieldDefinition fieldDefinition;
  private final int t;
  private final List<FieldElement> reconstructionCoefficients;

  public Random getRandom() {
    return random;
  }

  private final SecureRandom random;

  public BGWResourcePool(int id, int parties, int t, BigInteger modulus) {
    this.id = id;
    this.parties = parties;
    this.t = t;
    this.fieldDefinition = new BigIntegerFieldDefinition(modulus);

    if (2 * t + 1 > parties) {
      throw new IllegalArgumentException("Need 2*t + 1 <= parties");
    }

    this.random = new SecureRandom();
    this.reconstructionCoefficients = Utils.lagrangeCoefficients(parties, fieldDefinition); //Utils.lagrangeCoefficients(t + 1, fieldDefinition);
  }

  public FieldDefinition getFieldDefinition() {
    return fieldDefinition;
  }

  public int getMyId() {
    return id;
  }

  public int getNoOfParties() {
    return parties;
  }


  public int getT() {
    return t;
  }

  public List<FieldElement> getReconstructionCoefficients() {
    return reconstructionCoefficients;
  }

}
