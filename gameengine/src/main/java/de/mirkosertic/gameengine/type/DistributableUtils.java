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
package de.mirkosertic.gameengine.type;

import de.mirkosertic.gameengine.event.Property;
import de.mirkosertic.gameengine.event.ReadOnlyProperty;

import java.util.Map;

public final class DistributableUtils {

    public static Object convert(Object aValue) {
        if (aValue instanceof Distributable) {
            return ((Distributable) aValue).serialize();
        }
        return aValue;
    }

    public static Object convertToType(Object aData, Class aTargetType) {
        if (Position.class.equals(aTargetType)) {
            return Position.deserialize((Map<String, Object>) aData);
        }
        if (Font.class.equals(aTargetType)) {
            return Font.deserialize((Map<String, Object>) aData);
        }
        if (Color.class.equals(aTargetType)) {
            return Color.deserialize((Map<String, Object>) aData);
        }
        if (Animation.class.equals(aTargetType)) {
            return Animation.deserialize((Map<String, Object>) aData);
        }
        if (Script.class.equals(aTargetType)) {
            return Script.deserialize((Map<String, Object>) aData);
        }
        if (Angle.class.equals(aTargetType)) {
            return Angle.deserialize((Map<String, Object>) aData);
        }
        if (Size.class.equals(aTargetType)) {
            return Size.deserialize((Map<String, Object>) aData);
        }
        if (TextExpression.class.equals(aTargetType)) {
            return TextExpression.deserialize((Map<String, Object>) aData);
        }
        if (CustomProperties.class.equals(aTargetType)) {
            return CustomProperties.deserialize((Map<String, Object>) aData);
        }
        if (Rectangle.class.equals(aTargetType)) {
            return Rectangle.deserialize((Map<String, Object>) aData);
        }
        if (ScoreValue.class.equals(aTargetType)) {
            return ScoreValue.deserialize((Map<String, Object>) aData);
        }
        if (ResourceName.class.equals(aTargetType)) {
            return ResourceName.deserialize((Map<String, Object>) aData);
        }
        if (Speed.class.equals(aTargetType)) {
            return Speed.deserialize((Map<String, Object>) aData);
        }
        return aData;
    }

    public static void setField(Reflectable aInstance, String aPropertyName, Object aValue, long aEventTimestamp) {
        Field theField = aInstance.getClassInformation().getFieldByName(aPropertyName);
        if (theField == null) {
            theField = aInstance.getClassInformation().getFieldByName(aPropertyName + "Property");
        }
        if (theField != null && (theField.getType().equals(Property.class)) || (theField.getType().equals(ReadOnlyProperty.class))) {
            Property theProperty = (Property) theField.getValue(aInstance);
            //if (aEventTimestamp > theProperty.getLastChanged()) {
                theProperty.set(convertToType(aValue, theProperty.getType()));
            //}
        }
    }

    private DistributableUtils() {
    }
}