package dk.alexandra.fresco.bgw;

import dk.alexandra.fresco.framework.builder.numeric.BuilderFactoryNumeric;
import dk.alexandra.fresco.lib.field.integer.BasicNumericContext;
import dk.alexandra.fresco.suite.ProtocolSuiteNumeric;
import java.security.SecureRandom;

/**
 * Implementation of the BGW protocol as described in "A Pragmatic Introduction to Secure
 * Multi-Party Computation" by Evans, Kolesnikov and Rosulek. The original protocol was described in
 * Ben-Or, M., S. Goldwasser, and A. Wigderson. 1988. “Completeness Theorems for Non-Cryptographic
 * Fault-Tolerant Distributed Computation (Extended Abstract)”. In: 20th Annual ACM Symposium on
 * Theory of Computing. ACM Press. 1–10.
 */
public class BGWProtocolSuite implements ProtocolSuiteNumeric<BGWResourcePool> {

  private final int maxBitLength;
  private final int fixedPointPrecision;

  public BGWProtocolSuite(int maxBitLength, int fixedPointPrecision) {
    this.maxBitLength = maxBitLength;
    this.fixedPointPrecision = fixedPointPrecision;
  }

  public BuilderFactoryNumeric init(BGWResourcePool resourcePool) {
    BasicNumericContext numericContext = createNumericContext(resourcePool);
    return new BGWBuilder(numericContext, resourcePool.getT(), resourcePool.getRandom());
  }

  BasicNumericContext createNumericContext(BGWResourcePool resourcePool) {
    return new BasicNumericContext(maxBitLength, resourcePool.getMyId(),
        resourcePool.getNoOfParties(), resourcePool.getFieldDefinition(), fixedPointPrecision);
  }

  public RoundSynchronization<BGWResourcePool> createRoundSynchronization() {
    return new BGWRoundSynchronization();
  }
}
