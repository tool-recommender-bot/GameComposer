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
package de.mirkosertic.gameengine.event;

import de.mirkosertic.gameengine.type.ClassInformation;

public class SystemException extends GameEvent {

    private static final GameEventClassInformation CIINSTANCE = new GameEventClassInformation();

    public static final String EVENTTYPE = "SystemException";

    public final Exception exception;

    public SystemException(Exception aException) {
        super(EVENTTYPE);
        exception = aException;
    }

    @Override
    public String toString() {
        return exception.getMessage();
    }

    @Override
    public ClassInformation getClassInformation() {
        return CIINSTANCE;
    }
}
