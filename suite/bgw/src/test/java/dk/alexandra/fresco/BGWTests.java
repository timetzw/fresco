package dk.alexandra.fresco;

import dk.alexandra.fresco.bgw.BGWResourcePool;
import dk.alexandra.fresco.framework.Application;
import dk.alexandra.fresco.framework.DRes;
import dk.alexandra.fresco.framework.TestThreadRunner.TestThread;
import dk.alexandra.fresco.framework.TestThreadRunner.TestThreadFactory;
import dk.alexandra.fresco.framework.builder.numeric.ProtocolBuilderNumeric;
import dk.alexandra.fresco.framework.sce.resources.ResourcePool;
import dk.alexandra.fresco.framework.value.SInt;
import dk.alexandra.fresco.lib.common.math.AdvancedNumeric;
import java.math.BigInteger;
import java.util.List;
import java.util.stream.Collectors;
import org.junit.Assert;

public class BGWTests {

  public static class TestKnown<ResourcePoolT extends ResourcePool> extends TestThreadFactory<ResourcePoolT, ProtocolBuilderNumeric> {
    @Override
    public TestThread<ResourcePoolT, ProtocolBuilderNumeric> next() {

      List<BigInteger> openInputs = List.of(0, 1, -1, 7, 1000).stream().map(BigInteger::valueOf).collect(
          Collectors.toList());

      return new TestThread<ResourcePoolT, ProtocolBuilderNumeric>() {
        @Override
        public void test() {
          Application<List<BigInteger>, ProtocolBuilderNumeric> app = producer -> {
            List<DRes<SInt>> closed =
                openInputs.stream().map(producer.numeric()::known).collect(Collectors.toList());

            List<DRes<BigInteger>> opened =
                closed.stream().map(producer.numeric()::open).collect(Collectors.toList());
            return () -> opened.stream().map(DRes::out).collect(Collectors.toList());
          };
          List<BigInteger> output = runApplication(app);

          System.out.println(output);
        }
      };
    }
  }

  public static class TestShift<ResourcePoolT extends ResourcePool> extends TestThreadFactory<ResourcePoolT, ProtocolBuilderNumeric> {
    @Override
    public TestThread<ResourcePoolT, ProtocolBuilderNumeric> next() {

      return new TestThread<ResourcePoolT, ProtocolBuilderNumeric>() {
        @Override
        public void test() {
          Application<BigInteger, ProtocolBuilderNumeric> app = producer -> {
            DRes<SInt> closed = producer.numeric().known(1234);
            DRes<SInt> result = AdvancedNumeric.using(producer).mod2m(closed, 3);
            return producer.numeric().open(result);
          };
          BigInteger output = runApplication(app);

          System.out.println(output);
          Assert.assertEquals(BigInteger.valueOf(1234).mod(BigInteger.valueOf(8)), output);
        }
      };
    }
  }

}
