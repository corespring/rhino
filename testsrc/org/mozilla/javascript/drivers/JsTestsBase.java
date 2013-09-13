/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. */

package org.corespring.javascript.drivers;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import junit.framework.TestCase;

import org.corespring.javascript.Context;
import org.corespring.javascript.ContextFactory;
import org.corespring.javascript.Scriptable;

public class JsTestsBase extends TestCase {
    private int optimizationLevel;

    public void setOptimizationLevel(int level) {
        this.optimizationLevel = level;
    }

    public void runJsTest(Context cx, Scriptable shared, String name, String source) {
        // create a lightweight top-level scope
        Scriptable scope = cx.newObject(shared);
        scope.setPrototype(shared);
        System.out.print(name + ": ");
        Object result;
        try {
            result = cx.evaluateString(scope, source, "jstest input", 1, null);
        } catch (RuntimeException e) {
            System.out.println("FAILED");
            throw e;
        }
        assertTrue(result != null);
        assertTrue("success".equals(result));
        System.out.println("passed");
    }

    public void runJsTests(File[] tests) throws IOException {
        ContextFactory factory = ContextFactory.getGlobal();
        Context cx = factory.enterContext();
        try {
            cx.setOptimizationLevel(this.optimizationLevel);
            Scriptable shared = cx.initStandardObjects();
            for (File f : tests) {
                int length = (int) f.length(); // don't worry about very long
                                               // files
                char[] buf = new char[length];
                new FileReader(f).read(buf, 0, length);
                String session = new String(buf);
                runJsTest(cx, shared, f.getName(), session);
            }
        } finally {
            Context.exit();
        }
    }
}
