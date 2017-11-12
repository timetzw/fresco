package dk.alexandra.fresco.tools.mascot;

import java.io.IOException;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import dk.alexandra.fresco.framework.Party;
import dk.alexandra.fresco.framework.configuration.NetworkConfiguration;
import dk.alexandra.fresco.framework.configuration.NetworkConfigurationImpl;
import dk.alexandra.fresco.suite.spdz.datatypes.SpdzElement;
import dk.alexandra.fresco.tools.mascot.field.FieldElement;
import dk.alexandra.fresco.tools.mascot.mult.MultiplyLeft;
import dk.alexandra.fresco.tools.mascot.mult.MultiplyRight;
import dk.alexandra.fresco.tools.mascot.net.ExtendedKryoNetNetworkImpl;
import dk.alexandra.fresco.tools.mascot.net.ExtendedNetwork;
import dk.alexandra.fresco.tools.mascot.share.ShareGen;


public class MascotDemo {

  public void runPartyOneMult(Integer pid) throws IOException {
    ExecutorService executor = Executors.newCachedThreadPool();
    ExtendedNetwork network = new ExtendedKryoNetNetworkImpl(executor, pid, Arrays.asList(1, 2));
    network.init(getNetworkConfiguration(pid), 1);
    network.connect(5000);
    System.out.println("Connected");
    BigInteger modulus = BigInteger.valueOf(997);
    int kBitLength = 10;
    int lambdaSecurityParam = 10;
    int numLeftFactors = 3;
    Random rand = new Random(42);
    MultiplyLeft mult = new MultiplyLeft(pid, 2, kBitLength, lambdaSecurityParam, numLeftFactors,
        rand, network, executor, modulus);
    FieldElement input1 = new FieldElement(12, modulus, kBitLength);
    FieldElement input2 = new FieldElement(45, modulus, kBitLength);
    FieldElement input3 = new FieldElement(78, modulus, kBitLength);
    List<FieldElement> inputs = Arrays.asList(input1, input2, input3);
    System.out.println("there");
    List<FieldElement> myShares = mult.multiply(inputs);
    System.out.println(myShares);
    for (FieldElement myShare : myShares) {
      FieldElement otherShare = new FieldElement(network.receive(0, 2), modulus, kBitLength);
      System.out.println("Recombined " + myShare.add(otherShare));
    }
    System.out.println("done");
  }

  public void runPartyTwoMult(int pid) throws IOException {
    ExecutorService executor = Executors.newCachedThreadPool();
    ExtendedNetwork network = new ExtendedKryoNetNetworkImpl(executor, pid, Arrays.asList(1, 2));
    network.init(getNetworkConfiguration(pid), 1);
    network.connect(5000);
    System.out.println("here");
    BigInteger modulus = BigInteger.valueOf(997);
    int kBitLength = 10;
    int lambdaSecurityParam = 10;
    int numLeftFactors = 3;
    Random rand = new Random(42);
    MultiplyRight mult = new MultiplyRight(pid, 1, kBitLength, lambdaSecurityParam, numLeftFactors,
        rand, network, executor, modulus);
    FieldElement input = new FieldElement(7, modulus, kBitLength);
    List<FieldElement> myShares = mult.multiply(input);
    for (FieldElement myShare : myShares) {
      network.send(0, 1, myShare.toByteArray());
    }
    System.out.println("done");
  }

  public void runPartyOne(Integer pid) throws IOException {
    ExecutorService executor = Executors.newCachedThreadPool();
    ExtendedNetwork network = new ExtendedKryoNetNetworkImpl(executor, pid, Arrays.asList(1, 2));
    network.init(getNetworkConfiguration(pid), 1);
    network.connect(5000);
    System.out.println("Connected");
    BigInteger modulus = BigInteger.valueOf(997);
    int kBitLength = 10;
    int lambdaSecurityParam = 12;
    Random rand = new Random(42);
    ShareGen shareGen = new ShareGen(modulus, kBitLength, 1, Arrays.asList(1, 2),
        lambdaSecurityParam, network, rand, executor);
    shareGen.initialize();
    FieldElement input = new FieldElement(123, modulus, kBitLength);
    SpdzElement res = shareGen.input(input);
    System.out.println(res);
    System.out.println("done");
    shareGen.shutdown();
    network.close();
  }

  public void runPartyTwo(int pid) throws IOException {
    ExecutorService executor = Executors.newCachedThreadPool();
    ExtendedNetwork network = new ExtendedKryoNetNetworkImpl(executor, pid, Arrays.asList(1, 2));
    network.init(getNetworkConfiguration(pid), 1);
    network.connect(5000);
    System.out.println("Connected");
    BigInteger modulus = BigInteger.valueOf(997);
    int kBitLength = 10;
    int lambdaSecurityParam = 12;
    Random rand = new Random(1);
    ShareGen shareGen = new ShareGen(modulus, kBitLength, 2, Arrays.asList(1, 2),
        lambdaSecurityParam, network, rand, executor);
    shareGen.initialize();
    SpdzElement res = shareGen.input(1);
    System.out.println(res);
    System.out.println("done");
    shareGen.shutdown();
    network.close();
  }

  public static void main(String[] args) {
    int pid = Integer.parseInt(args[0]);
    try {
      if (pid == 1) {
        new MascotDemo().runPartyOneMult(1);
      } else {
        new MascotDemo().runPartyTwoMult(2);
      }
    } catch (Exception e) {
      System.out.println("Failed to run: " + e);
      e.printStackTrace(System.out);
    }
  }

  private static NetworkConfiguration getNetworkConfiguration(int pid) {
    Map<Integer, Party> parties = new HashMap<>();
    parties.put(1, new Party(1, "localhost", 8001));
    parties.put(2, new Party(2, "localhost", 8002));
    return new NetworkConfigurationImpl(pid, parties);
  }
}
