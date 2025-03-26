package dk.alexandra.fresco.bgw.utils;

import dk.alexandra.fresco.framework.builder.numeric.Addable;
import dk.alexandra.fresco.framework.builder.numeric.field.FieldDefinition;
import dk.alexandra.fresco.framework.builder.numeric.field.FieldElement;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;

public class Utils {

  /**
   * Compute Lagrange coefficients for the first k shares. The coefficients L_1, ldots, L_k satisfy
   * the equation p(0) = L_1 p(1) + ... L_k p(k) assuming that k shares are enough to uniquely
   * determine p.
   */
  public static List<FieldElement> lagrangeCoefficients(int k, FieldDefinition field) {
    return lagrangeCoefficients(k, field.createElement(0), field);
  }

  public static List<FieldElement> lagrangeCoefficients(int k, FieldElement x, FieldDefinition field) {
    List<FieldElement> coefficients = new ArrayList<>();
    for (int i = 1; i <= k; i++) {
      FieldElement coefficient = field.createElement(1);
      for (int m = 1; m <= k; m++) {
        if (m != i) {
          FieldElement factor = x.subtract(field.createElement(m))
              .multiply(field.createElement(i - m).modInverse());
          coefficient = factor.multiply(coefficient);
        }
      }
      coefficients.add(coefficient);
    }
    return coefficients;
  }

  /** Compute the inner product of two vectors. */
  public static FieldElement innerProduct(List<FieldElement> x, List<FieldElement> y) {
    if (x.size() != y.size()) {
      throw new IllegalArgumentException("Lists must have same size");
    }
    return IntStream.range(1, x.size()).mapToObj(i -> x.get(i).multiply(y.get(i)))
        .reduce(x.get(0).multiply(y.get(0)), Addable::add);
  }

  public static FieldElement getRandomFieldElement(Random random, FieldDefinition fieldDefinition) {
    int length = fieldDefinition.getModulus().bitLength();

    BigInteger value = new BigInteger(length, random);
    while (value.compareTo(fieldDefinition.getModulus()) >= 0) {
      value = new BigInteger(length, random);
    }
    return fieldDefinition.createElement(value);
  }


}
