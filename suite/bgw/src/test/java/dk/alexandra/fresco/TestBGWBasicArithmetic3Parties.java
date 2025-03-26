package dk.alexandra.fresco;

import dk.alexandra.fresco.suite.dummy.arithmetic.BasicArithmeticTests;
import org.junit.Test;

public class TestBGWBasicArithmetic3Parties extends AbstractBGWTest {

  @Test
  public void test_Input_Sequential() {
    runTest(new BasicArithmeticTests.TestInput<>());
  }

  @Test
  public void test_Input_SequentialBatched() {
    runTest(new BasicArithmeticTests.TestInput<>());
  }

  @Test
  public void test_Sum_And_Output_Sequential() {
    runTest(new BasicArithmeticTests.TestSumAndMult<>());
  }
//
//  @Test
//  public void test_Lots_Of_Mults_Sequential() {
//    runTest(new BasicArithmeticTests.TestLotsMult<>(),
//        PreprocessingStrategy.DUMMY, 3);
//  }
//
//  @Test
//  public void test_Lots_Of_Mults_Sequential_Batched() {
//    runTest(new BasicArithmeticTests.TestLotsMult<>(),
//        PreprocessingStrategy.DUMMY, 3);
//  }
//
//  @Test
//  public void test_Alternating() {
//    runTest(new BasicArithmeticTests.TestAlternatingMultAdd<>(),
//        PreprocessingStrategy.DUMMY, 3);
//  }
//
//  @Test
//  public void test_Alternating_Sequential_Batched() {
//    runTest(new BasicArithmeticTests.TestAlternatingMultAdd<>(),
//        PreprocessingStrategy.DUMMY, 3);
//  }
//
  @Test
  public void testInputFromAll() {
    runTest(new BasicArithmeticTests.TestInputFromAll<>());
  }

}
