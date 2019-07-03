/*
 * Copyright © 2019 minCloud and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package org.opendaylight.example.cli.impl;

import org.opendaylight.controller.md.sal.binding.api.DataBroker;
import org.opendaylight.example.cli.api.ExampleCliCommands;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ExampleCliCommandsImpl implements ExampleCliCommands {

    private static final Logger LOG = LoggerFactory.getLogger(ExampleCliCommandsImpl.class);
    private final DataBroker dataBroker;

    public ExampleCliCommandsImpl(final DataBroker db) {
        this.dataBroker = db;
        LOG.info("ExampleCliCommandImpl initialized");
    }

    @Override
    public Object testCommand(Object testArgument) {
        return "This is a test implementation of test-command";
    }
}
