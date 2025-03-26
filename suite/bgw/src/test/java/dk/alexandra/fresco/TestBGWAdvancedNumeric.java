package dk.alexandra.fresco;

import dk.alexandra.fresco.BGWTests.TestKnown;
import dk.alexandra.fresco.BGWTests.TestShift;
import dk.alexandra.fresco.lib.common.collections.MatrixTest;
import dk.alexandra.fresco.lib.common.math.AdvancedNumericTests.TestModulus;
import dk.alexandra.fresco.lib.common.math.integer.division.DivisionTests.TestDivision;
import dk.alexandra.fresco.lib.common.math.integer.division.DivisionTests.TestKnownDivisorDivision;
import dk.alexandra.fresco.lib.common.math.integer.exp.ExponentiationTests.TestExponentiation;
import dk.alexandra.fresco.lib.common.math.integer.linalg.LinAlgTests;
import dk.alexandra.fresco.lib.common.math.polynomial.PolynomialTests.TestPolynomialEvaluator;
import dk.alexandra.fresco.lib.fixed.BasicFixedPointTests.TestRepeatedMultiplication;
import dk.alexandra.fresco.suite.dummy.arithmetic.BasicArithmeticTests;
import dk.alexandra.fresco.suite.dummy.arithmetic.BasicArithmeticTests.TestInputFromAll;
import dk.alexandra.fresco.suite.dummy.arithmetic.BasicArithmeticTests.TestInputFromDifferentParties;
import dk.alexandra.fresco.suite.dummy.arithmetic.BasicArithmeticTests.TestOutputToSingleParty;
import dk.alexandra.fresco.suite.dummy.arithmetic.BasicArithmeticTests.TestSimpleMultAndAdd;
import dk.alexandra.fresco.suite.dummy.arithmetic.BasicArithmeticTests.TestSumAndMult;
import dk.alexandra.fresco.suite.dummy.arithmetic.ParallelAndSequenceTests.TestSumAndProduct;
import org.junit.Test;

public class TestBGWAdvancedNumeric extends AbstractBGWTest {

  @Test
  public void test_Division() {
    runTest(new TestDivision<>());
  }

  @Test
  public void test_Division_Known_Denominator() {
    runTest(new TestKnownDivisorDivision<>());
  }

  @Test
  public void test_input_from_all() {
    runTest(new TestInputFromAll<>());
  }

  @Test
  public void test_input_from_different_parties() {
    runTest(new TestInputFromDifferentParties<>());
  }

  @Test
  public void test_known() {
    runTest(new TestKnown());
  }

  @Test
  public void test_shift() {
    runTest(new TestShift<>());
  }

  @Test
  public void test_InnerProductClosed() {
    runTest(new LinAlgTests.TestInnerProductClosed<>());
  }

  @Test
  public void test_TestAdd() {
    runTest(new BasicArithmeticTests.TestAdd<>());
  }

  @Test
  public void test_TestAddWithOverflow() {
    runTest(new BasicArithmeticTests.TestAddWithOverflow<>());
  }


  @Test
  public void test_TestMultiply() {
    runTest(new BasicArithmeticTests.TestMultiply<>());
  }

  @Test
  public void test_TestMultiplyByZero() {
    runTest(new BasicArithmeticTests.TestMultiplyByZero<>());
  }

  @Test
  public void test_TestSubtract() {
    runTest(new BasicArithmeticTests.TestSubtract<>());
  }

  @Test
  public void test_TestSubtractNegative() {
    runTest(new BasicArithmeticTests.TestSubtractNegative<>());
  }

  @Test
  public void test_TestSubtractPublic() {
    runTest(new BasicArithmeticTests.TestSubtractPublic<>());
  }

  @Test
  public void test_TestSubtractFromPublic() {
    runTest(new BasicArithmeticTests.TestSubtractFromPublic<>());
  }

  @Test
  public void test_TestMultiplyByPublicValue() {
    runTest(new BasicArithmeticTests.TestMultiplyByPublicValue<>());
  }

  @Test
  public void test_TestOpenNoConversionByDefault() {
    runTest(new BasicArithmeticTests.TestOpenNoConversionByDefault<>());
  }

  @Test
  public void test_Modulus() {
    runTest(new TestModulus<>());
  }

  //@Test
  public void test_exponentiation() {
    runTest(new TestExponentiation<>());
  }

  @Test
  public void test_polynomial() {
    runTest(new TestPolynomialEvaluator<>());
  }

  @Test
  public void test_TestSumAndMult() {
    runTest(new TestSumAndMult<>());
  }

  @Test
  public void test_TestSimpleMultAndAdd() {
    runTest(new TestSimpleMultAndAdd<>());
  }

  @Test
  public void test_TestLotsMult() {
    runTest(new BasicArithmeticTests.TestLotsMult<>());
  }

  @Test
  public void test_TestAlternatingMultAdd() {
    runTest(new BasicArithmeticTests.TestAlternatingMultAdd<>());
  }

  @Test
  public void test_TestMultiplyWithOverflow() {
    runTest(new BasicArithmeticTests.TestMultiplyWithOverflow<>());
  }

  @Test
  public void testSumAndProduct() {
    runTest(new TestSumAndProduct<>());
  }

  @Test
  public void test_TestRandomBit() {
    runTest(new BasicArithmeticTests.TestRandomBit<>());
  }

  @Test
  public void test_TestRandomElement() {
    runTest(new BasicArithmeticTests.TestRandomElement<>());
  }
}
