/*
 * Copyright © 2019 minCloud and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package org.opendaylight.example.cli.api;

public interface ExampleCliCommands {

    /**
     * Define the Karaf command method signatures and the Javadoc for each.
     * Below method is just an example
     */
    Object testCommand(Object testArgument);
}
