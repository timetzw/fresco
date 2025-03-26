package dk.alexandra.fresco.bgw.utils;

import dk.alexandra.fresco.framework.builder.numeric.field.FieldElement;
import java.util.List;
import java.util.function.IntFunction;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Instances of this class represents immutable polynomials of {@link
 * dk.alexandra.fresco.framework.builder.numeric.field.FieldElement}s.
 */
public class Polynomial {

  private final int degree;
  private final List<FieldElement> coefficients;

  public Polynomial(int degree, IntFunction<FieldElement> populator) {
    this.degree = degree;
    this.coefficients = IntStream.rangeClosed(0, degree).mapToObj(populator)
        .collect(Collectors.toList());
  }

  public FieldElement getCoefficient(int i) {
    return coefficients.get(i);
  }

  public FieldElement evaluate(FieldElement x0) {
    FieldElement b = coefficients.get(degree);
    for (int i = degree - 1; i >= 0; i--) {
      b = coefficients.get(i).add(b.multiply(x0));
    }
    return b;
  }

  public FieldElement constantTerm() {
    return coefficients.get(0);
  }

}
