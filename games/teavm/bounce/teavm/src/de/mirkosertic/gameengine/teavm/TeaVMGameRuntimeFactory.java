/*
 * Copyright 2016 Mirko Sertic
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package de.mirkosertic.gameengine.teavm;

import de.mirkosertic.gameengine.AbstractGameRuntimeFactory;
import de.mirkosertic.gameengine.core.Logger;
import de.mirkosertic.gameengine.core.NoThreadingThreadingManager;
import de.mirkosertic.gameengine.core.ThreadingManager;
import de.mirkosertic.gameengine.physics.jbox2d.JBox2DGamePhysicsManagerFactory;
import de.mirkosertic.gameengine.scriptengine.luaj.LuaJScriptEngineFactory;

public class TeaVMGameRuntimeFactory extends AbstractGameRuntimeFactory {

    private final boolean multithreaded;
    private final boolean profiling;

    public TeaVMGameRuntimeFactory(boolean aMultithreaded, boolean aProfiling) {
        multithreaded = aMultithreaded;
        profiling = aProfiling;
    }

    @Override
    protected Logger createLogger() {
        return new Logger() {
            @Override
            public void info(String aMessage) {
                TeaVMLogger.info(Thread.currentThread().getName() + ": " + aMessage);
            }

            @Override
            public void error(String aMessage) {
                TeaVMLogger.error(Thread.currentThread().getName() + ": " + aMessage);
            }

            @Override
            public void time(String aLabel) {
                if (profiling) {
                    TeaVMLogger.time(aLabel);
                }
            }

            @Override
            public void timeEnd(String aLabel) {
                if (profiling) {
                    TeaVMLogger.timeEnd(aLabel);
                }
            }
        };
    }

    @Override
    protected ThreadingManager createThreadingManager() {
//        if (multithreaded) {
//            return new TeaVMThreadingManager();
//        }
        // No Green Treads -> No real performance improvements
        return new NoThreadingThreadingManager();
    }

    @Override
    protected LuaJScriptEngineFactory createScriptEngine() {
        return new LuaJScriptEngineFactory(new TeaVMBuildInFunctions());
    }

    @Override
    protected JBox2DGamePhysicsManagerFactory createPhysicsManagerFactory() {
        return new JBox2DGamePhysicsManagerFactory();
    }
}