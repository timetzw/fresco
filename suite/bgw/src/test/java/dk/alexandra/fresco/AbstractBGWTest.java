package dk.alexandra.fresco;

import dk.alexandra.fresco.bgw.BGWProtocolSuite;
import dk.alexandra.fresco.bgw.BGWResourcePool;
import dk.alexandra.fresco.framework.ProtocolEvaluator;
import dk.alexandra.fresco.framework.TestThreadRunner;
import dk.alexandra.fresco.framework.builder.numeric.ProtocolBuilderNumeric;
import dk.alexandra.fresco.framework.builder.numeric.field.BigIntegerFieldDefinition;
import dk.alexandra.fresco.framework.configuration.NetworkConfiguration;
import dk.alexandra.fresco.framework.configuration.NetworkUtil;
import dk.alexandra.fresco.framework.network.Network;
import dk.alexandra.fresco.framework.network.socket.SocketNetwork;
import dk.alexandra.fresco.framework.sce.SecureComputationEngine;
import dk.alexandra.fresco.framework.sce.SecureComputationEngineImpl;
import dk.alexandra.fresco.framework.sce.evaluator.BatchEvaluationStrategy;
import dk.alexandra.fresco.framework.sce.evaluator.BatchedProtocolEvaluator;
import dk.alexandra.fresco.framework.sce.evaluator.EvaluationStrategy;
import dk.alexandra.fresco.logging.BatchEvaluationLoggingDecorator;
import dk.alexandra.fresco.logging.DefaultPerformancePrinter;
import dk.alexandra.fresco.logging.EvaluatorLoggingDecorator;
import dk.alexandra.fresco.logging.NetworkLoggingDecorator;
import dk.alexandra.fresco.logging.NumericSuiteLogging;
import dk.alexandra.fresco.logging.PerformanceLogger;
import dk.alexandra.fresco.logging.PerformanceLoggerCountingAggregate;
import dk.alexandra.fresco.logging.PerformancePrinter;
import dk.alexandra.fresco.suite.ProtocolSuiteNumeric;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * Abstract class which handles a lot of boiler plate testing code. This makes running a single test
 * using different parameters quite easy.
 */
public abstract class AbstractBGWTest {

  // TODO hack hack hack
  private static final int DEFAULT_MOD_BIT_LENGTH = 256;
  private static final int DEFAULT_MAX_BIT_LENGTH = 180;
  private static final int DEFAULT_FIXED_POINT_PRECISION = 16;
  private Map<Integer, PerformanceLogger> performanceLoggers = new HashMap<>();
  private int modBitLength = DEFAULT_MOD_BIT_LENGTH;
  private int maxBitLength = DEFAULT_MAX_BIT_LENGTH;
  private int fixedPointPrecision = DEFAULT_FIXED_POINT_PRECISION;

  public void runTest(
      TestThreadRunner.TestThreadFactory<BGWResourcePool, ProtocolBuilderNumeric> f) {
    runTest(f, EvaluationStrategy.SEQUENTIAL, 3, 1, false, DEFAULT_MOD_BIT_LENGTH,
        DEFAULT_MAX_BIT_LENGTH, DEFAULT_FIXED_POINT_PRECISION);
  }

  public void runTest(
      TestThreadRunner.TestThreadFactory<BGWResourcePool, ProtocolBuilderNumeric> f,
      EvaluationStrategy evalStrategy, int noOfParties, int threshold,
      boolean logPerformance, int modBitLength, int maxBitLength, int fixedPointPrecision) {
    this.modBitLength = modBitLength;
    this.maxBitLength = maxBitLength;
    this.fixedPointPrecision = fixedPointPrecision;

    BigInteger modulus = BigInteger.probablePrime(modBitLength, new Random(1234)); //BigInteger.TWO.pow(128).subtract(BigInteger.valueOf(159));

    List<Integer> ports = new ArrayList<>(noOfParties);
    for (int i = 1; i <= noOfParties; i++) {
      ports.add(9000 + i * (noOfParties - 1));
    }

    Map<Integer, NetworkConfiguration> netConf =
        NetworkUtil.getNetworkConfigurations(ports);
    Map<Integer, TestThreadRunner.TestThreadConfiguration<BGWResourcePool, ProtocolBuilderNumeric>> conf =
        new HashMap<>();
    for (int playerId : netConf.keySet()) {
      PerformanceLoggerCountingAggregate aggregate = new PerformanceLoggerCountingAggregate();

      ProtocolSuiteNumeric<BGWResourcePool> protocolSuite = new BGWProtocolSuite(maxBitLength,
          fixedPointPrecision);
      BatchEvaluationStrategy<BGWResourcePool> batchEvalStrat = evalStrategy.getStrategy();
      if (logPerformance) {
        protocolSuite = new NumericSuiteLogging<>(protocolSuite);
        aggregate.add((PerformanceLogger) protocolSuite);
        batchEvalStrat = new BatchEvaluationLoggingDecorator<>(batchEvalStrat);
        aggregate.add((PerformanceLogger) batchEvalStrat);
      }

      ProtocolEvaluator<BGWResourcePool> evaluator =
          new BatchedProtocolEvaluator<>(batchEvalStrat, protocolSuite);

      if (logPerformance) {
        evaluator = new EvaluatorLoggingDecorator<>(evaluator);
        aggregate.add((PerformanceLogger) evaluator);
      }

      SecureComputationEngine<BGWResourcePool, ProtocolBuilderNumeric> sce =
          new SecureComputationEngineImpl<>(protocolSuite, evaluator);

      TestThreadRunner.TestThreadConfiguration<BGWResourcePool, ProtocolBuilderNumeric> ttc =
          new TestThreadRunner.TestThreadConfiguration<>(sce, () -> createResourcePool(playerId, modulus,
              noOfParties, threshold), () -> {
            Network network = new SocketNetwork(netConf.get(playerId));
            if (logPerformance) {
              network = new NetworkLoggingDecorator(network);
              aggregate.add((NetworkLoggingDecorator) network);
              return network;
            } else {
              return network;
            }
          });
      conf.put(playerId, ttc);
      performanceLoggers.putIfAbsent(playerId, aggregate);
    }
    TestThreadRunner.run(f, conf);
    PerformancePrinter printer = new DefaultPerformancePrinter();
    for (PerformanceLogger pl : performanceLoggers.values()) {
      printer.printPerformanceLog(pl);
    }
  }

  private BGWResourcePool createResourcePool(int myId, BigInteger modulus,
      int numberOfParties, int threshold) {
    return new BGWResourcePool(myId, numberOfParties, threshold, modulus);
  }

}
