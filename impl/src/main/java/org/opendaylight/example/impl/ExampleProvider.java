/*
 * Copyright © 2019 minCloud and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
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