/*
 * Copyright 2017 Mirko Sertic
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
package de.mirkosertic.gameengine.teavmwasm;

import de.mirkosertic.gameengine.annotations.ReflectiveMethod;
import de.mirkosertic.gameengine.type.BuiltInFunctions;
import de.mirkosertic.gameengine.type.BuiltInFunctionsClassInformation;
import de.mirkosertic.gameengine.type.ClassInformation;

public class WASMBuiltInFunctions extends BuiltInFunctions {

    private static final WASMBuiltInFunctionsClassInformation CIINSTANCE = new WASMBuiltInFunctionsClassInformation();

    @Override
    public ClassInformation getClassInformation() {
        return CIINSTANCE;
    }

    @Override
    @ReflectiveMethod
    public String formatTime(Number aTimeInMilis, String aPattern) {
        return "";
    }
}
