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
package de.mirkosertic.gameengine.core;

import de.mirkosertic.gameengine.annotations.InheritedClassInformation;
import de.mirkosertic.gameengine.annotations.ReflectiveField;
import de.mirkosertic.gameengine.event.GameEvent;
import de.mirkosertic.gameengine.event.GameEventType;

@InheritedClassInformation
public class EventSheetRemovedFromScene extends GameEvent {

    public static final GameEventType TYPE = new GameEventType("EventSheetRemovedFromScene");

    private static final EventSheetRemovedFromSceneClassInformation CIINSTANCE = new EventSheetRemovedFromSceneClassInformation();

    @ReflectiveField
    public final EventSheet eventSheet;

    public EventSheetRemovedFromScene(EventSheet aEventSheet) {
        super(TYPE);
        eventSheet = aEventSheet;
    }

    @Override
    public EventSheetRemovedFromSceneClassInformation getClassInformation() {
        return CIINSTANCE;
    }
}