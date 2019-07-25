package per.mincloud.example.impl;

import java.util.concurrent.Future;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.example.rev150105.ExampleService;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.example.rev150105.ExampleTestInput;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.example.rev150105.ExampleTestOutput;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.example.rev150105.ExampleTestInputBuilder;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.example.rev150105.ExampleTestOutputBuilder;
import org.opendaylight.yangtools.yang.common.RpcResult;
import org.opendaylight.yangtools.yang.common.RpcResultBuilder;

public class ExampleTestImpl implements ExampleService {

    @Override
    public Future<RpcResult<ExampleTestOutput>> exampleTest(ExampleTestInput input) {
        ExampleTestOutputBuilder exampleBuilder = new ExampleTestOutputBuilder();
        exampleBuilder.setGreeting("This is ExampleTest Message " + input.getName());
        return RpcResultBuilder.success(exampleBuilder.build()).buildFuture();
    }
}