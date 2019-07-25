# OpenDayLight_Neon Project
ODL Neon SR1 Example Project

## ■ Pre requisites
* Apache Maven 3.6.1
* Java version: 1.8.0_212
* Default OpenDayLight [settings.xml]
```
$ cp -n ~/.m2/settings.xml{,.orig} ; wget -q -O - https://raw.githubusercontent.com/opendaylight/odlparent/master/settings.xml > ~/.m2/settings.xml
```
___

## ■ Building an Example Module
To develop an app perform the following steps.

1. Create an Example project using Maven and an archetype called the opendaylight-startup-archetype. If you are downloading this project for the first time, then it will take sometime to pull all the code from the remote repository.

[Neon SR1 Development Version]
```
$ mvn archetype:generate -DarchetypeGroupId=org.opendaylight.archetypes -DarchetypeArtifactId=opendaylight-startup-archetype -DarchetypeCatalog=remote -DarchetypeVersion=1.1.2-SNAPSHOT
```
[Carbon Version : Stable]
```
$ mvn org.apache.maven.plugins:maven-archetype-plugin:2.4:generate -DarchetypeGroupId=org.opendaylight.controller -DarchetypeArtifactId=opendaylight-startup-archetype -DarchetypeRepository=http://nexus.opendaylight.org/content/repositories/public/ -DarchetypeCatalog=http://nexus.opendaylight.org/content/repositories/public/archetype-catalog.xml -DarchetypeVersion=1.3.3-Carbon
```
![result1](img/result1.png)

2. Build the Project
```
$ mvn clean install -DskipTests -Dcheckstyle.skip
```
![result2](img/result2.png)

3. Run Karaf
```
$ cd /karaf/target/assembly/bin
$ ./karaf
```
![result3](img/result3.png)
___

## ■ Defining a RPC
1. Now view the entry point to understand where the log line came from. The entry point is in the impl project:
```
$ impl/src/main/java/org/opendaylight/example/impl/ExampleProvider.java
```
2. Add any new things that you are doing in your implementation by using the [ExampleProvider.onSessionInitiate] method.
```js
@Override
public void onSessionInitiated(ProviderContext session) {
    LOG.info("ExampleProvider Session Initiated");
}
```
___

## ■ Add a RPC API
```
$ cd api/src/main/yang/example.yang
```
1. Edit this file as follows. In the following example, we are adding the code in a YANG module to define the exampl RPC:
```js
module example {
    yang-version 1;
    namespace "urn:opendaylight:params:xml:ns:yang:example";
    prefix "example";

    revision "2015-01-05" {
        description "Initial revision of example model";
    }

    rpc example-test {
        input {
            leaf name {
                type string;
            }
        }
        output {
            leaf greeting {
                type string;
            }
        }
    }
}
````

2. Return to the example/api directory and build your API as follows.
```
$ cd api
$ mvn clean install -DskipTests -Dcheckstyle.skip
```
![result4](img/result4.png)
___

## ■ Implement a RPC API
1. Define the ExampleService, which is invoked through the Example API.
```
$ cd ../impl/src/main/java/org/opendaylight/example/impl/
```
2. Create a new file called [ExampleTestImpl.java] and add in the code below.
```js
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
```
3. The ExampleProvider.java file is in the current directory. Register the RPC that you created in the [example.yang] file in the [ExampleProvider.java] file.
```js
package per.mincloud.example.impl;

import org.opendaylight.controller.md.sal.binding.api.DataBroker;
import org.opendaylight.controller.sal.binding.api.BindingAwareBroker.ProviderContext;
import org.opendaylight.controller.sal.binding.api.BindingAwareBroker.RpcRegistration;
import org.opendaylight.controller.sal.binding.api.BindingAwareProvider;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.example.rev150105.ExampleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ExampleProvider implements BindingAwareProvider, AutoCloseable {

    private static final Logger LOG = LoggerFactory.getLogger(ExampleProvider.class);
    private RpcRegistration<ExampleService> exampleService;

    @Override
    public void onSessionInitiated(ProviderContext session) {
        LOG.info("ExampleProvider Session Initiated");
        this.exampleService = session.addRpcImplementation(ExampleService.class, new ExampleTestImpl());
    }

    @Override
    public void close() throws Exception {
        LOG.info("ExampleProvider Closed");
        if (exampleService != null) {
            exampleService.close();
        }
    }
}
```
4. Optionally, you can also build the Java classes which will register the new RPC. This is useful to test the edits you have made to ExampleProvider.java and ExampleTestImpl.java.
```
$ cd impl
$ mvn clean install -DskipTests -Dcheckstyle.skip
```
5. Build the entire example again, which will pickup the changes you have made and build them into your project:
```
$ mvn clean install -DskipTests -Dcheckstyle.skip
```

* Tree Architecture

![result6](img/result6.png)
___
## ■ Execute the Example Project
```
$ cd /karaf/target/assembly/bin
$ ./karaf
$ opendaylight-user@root> log:display | grep example
```
___
## ■ Test the example-test RPC via REST
```
1. Connect http://127.0.0.1:8181/apidoc/explorer/index.html (ID/PWD : admin/admin)

```
![result8](img/result8.png)
___
## Bugs

Please report bugs to mincloud@sk.com

## Contributing

The github repository is at https://github.com/mincloud1501/OpenDayLight_Neon.git

## See Also

Some other stuff.

## Author

J.Ho Moon, <mincloud@sk.com>

## Copyright and License

(c) Copyright 1997~2019 by SK Broadband Co.LTD