/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. */

package org.corespring.javascript.tests;

import java.io.InputStreamReader;

import junit.framework.TestCase;

import org.corespring.javascript.Callable;
import org.corespring.javascript.Context;
import org.corespring.javascript.Script;
import org.corespring.javascript.Scriptable;
import org.corespring.javascript.ScriptableObject;

public class Bug482203Test extends TestCase {
    
    public void testJsApi() throws Exception {
        Context cx = Context.enter();
        try {
            cx.setOptimizationLevel(-1);
            Script script = cx.compileReader(new InputStreamReader(
                    Bug482203Test.class.getResourceAsStream("Bug482203.js")),
                    "", 1, null);
            Scriptable scope = cx.initStandardObjects();
            script.exec(cx, scope);
            int counter = 0;
            for(;;)
            {
                Object cont = ScriptableObject.getProperty(scope, "c");
                if(cont == null)
                {
                    break;
                }
                counter++;
                ((Callable)cont).call(cx, scope, scope, new Object[] { null });
            }
            assertEquals(counter, 5);
            assertEquals(Double.valueOf(3), ScriptableObject.getProperty(scope, "result"));
        } finally {
            Context.exit();
        }
    }
    
    public void testJavaApi() throws Exception {
        Context cx = Context.enter();
        try {
            cx.setOptimizationLevel(-1);
            Script script = cx.compileReader(new InputStreamReader(
                    Bug482203Test.class.getResourceAsStream("Bug482203.js")),
                    "", 1, null);
            Scriptable scope = cx.initStandardObjects();
            cx.executeScriptWithContinuations(script, scope);
            int counter = 0;
            for(;;)
            {
                Object cont = ScriptableObject.getProperty(scope, "c");
                if(cont == null)
                {
                    break;
                }
                counter++;
                cx.resumeContinuation(cont, scope, null);
            }
            assertEquals(counter, 5);
            assertEquals(Double.valueOf(3), ScriptableObject.getProperty(scope, "result"));
        } finally {
        	Context.exit();
        }
    }
}
