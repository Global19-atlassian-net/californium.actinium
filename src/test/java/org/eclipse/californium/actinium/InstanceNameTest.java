/*******************************************************************************
 * Copyright (c) 2014 Institute for Pervasive Computing, ETH Zurich and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * and Eclipse Distribution License v1.0 which accompany this distribution.
 * 
 * The Eclipse Public License is available at
 *    http://www.eclipse.org/legal/epl-v20.html
 * and the Eclipse Distribution License is available at
 *    http://www.eclipse.org/org/documents/edl-v10.html.
 * 
 * Contributors:
 *    Yassin N. Hassan - initial implementation
 ******************************************************************************/
package org.eclipse.californium.actinium;

import org.eclipse.californium.elements.rule.ThreadsRule;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

@RunWith(Parameterized.class)
public class InstanceNameTest extends BaseServerTest {
	@ClassRule
	public static ThreadsRule cleanup = new ThreadsRule();

	private final String instanceName;

	@Parameterized.Parameters(name = "{0}")
	public static Collection<Object[]> data() {
		return Arrays.asList(new Object[][]{
				{"hello-1"}, {"Hello-1"}, {"a=a"}, {"a?"}, {"zürich"}, {"sd-sd"}, {"-a"}, {"4number"}, {"test/test"},
				{"test~test"}, {"test?test"}, {"test#test"}, {"test!test"}, {"test@test"}, {"test&test"}, {"test'test"},
				{"test/test"}, {"test test"}, {"test+test"}, {"test,test"}, {"test*test"}, {"test;test"},
				{"test)test"}, {"test(test"}, {"test[test"},
		});
	}


	public InstanceNameTest(String instanceName) {
		this.instanceName = instanceName;
	}

	@Test
	public void testIsAppRunningIfCreateIsSuccessful() throws Exception {
		String scriptName = "helloWorld";
		testInstallHelloWorld(scriptName);
		boolean successful = createInstance(scriptName, instanceName, false);
		if (successful) {
			testCheckIfInstanceExists(instanceName);
			testCheckInstance(scriptName, instanceName);
			Thread.sleep(2000);
			testCheckIfInstanceIsRunning(instanceName);
			testGET("apps/running/"+instanceName, "Hello World");
		}
	}

}
