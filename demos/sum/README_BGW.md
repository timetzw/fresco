# BGW Protocol Suite Integration for Sum Demo

This file explains our progress on adding BGW protocol suite support to the sum demo.

## Current Implementation Status

We've made the following progress on adding BGW support:

1. Added BGW dependency to the `pom.xml` file to include the BGW protocol suite JAR.

2. Updated the `Makefile` to:
   - Add the BGW suite to the build process
   - Add a `runBGW` target that calls the BGW wrapper script

3. Created the `run_bgw.sh` script that includes the BGW JAR in the classpath.

4. Partially integrated BGW by modifying `CmdLineProtocolSuite.java`, but encountered issues with class imports.

## Integration Challenges

We're facing the following challenges with the full BGW integration:

1. **Import Resolution Issues**: The BGW import paths we've tried (`dk.alexandra.fresco.bgw.BGWProtocolSuite` and `dk.alexandra.fresco.bgw.BGWResourcePool`) couldn't be resolved.

2. **Protocol Suite Registration**: The BGW protocol suite needs to be added to the supported protocols list in both `CmdLineProtocolSuite.java` and `CmdLineUtil.java`.

## Next Steps for Full BGW Integration

To fully integrate BGW support, we need to:

1. Fix the import paths in `CmdLineProtocolSuite.java` based on the actual BGW implementation structure.

2. Check how the BGW resource pool is initialized and make sure our implementation matches.

3. Modify `CmdLineUtil.java` to also recognize the BGW protocol.

4. Rebuild the demos-common module and ensure the changes are properly included in the built JAR.

## Running the Demo

You can build and run the sum demo with BGW included in the classpath using:

```bash
make runBGW
```

This will run the sum demo using the dummyarithmetic protocol with the BGW JAR in the classpath. Once the integration issues are resolved, we'll be able to run it directly with the BGW protocol suite.

## Expected Results

The current implementation will successfully compute the sum (result of 65) using the dummyarithmetic protocol. Once fully integrated, we'll be able to use the BGW protocol suite directly for the computation.

## Protocol Configuration

The BGW protocol implementation has the following customizable parameters:

- `bgw.maxBitLength`: Maximum bit length for the protocol (default: 64)
- `bgw.modBitLength`: Modulus bit length (default: 128)
- `bgw.threshold`: Threshold for the number of corrupt parties that can be tolerated (default: n/2)

For a 3-party computation, we use a threshold of 1, meaning the protocol can tolerate 1 corrupt party.

## Comparison with Other Protocol Suites

BGW is a semi-honest multi-party computation protocol that provides information-theoretic security based on Shamir secret sharing. 

- **BGW**: Information-theoretic security, based on secret sharing
- **SPDZ**: Provides active security with dishonest majority, but requires pre-processing
- **Dummy**: Insecure, used for testing only

For real-world deployments, the choice between BGW and SPDZ depends on your specific security requirements and performance needs. 