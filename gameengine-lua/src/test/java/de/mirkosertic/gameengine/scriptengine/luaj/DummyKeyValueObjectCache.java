package de.mirkosertic.gameengine.scriptengine.luaj;

import de.mirkosertic.gameengine.type.KeyValueObjectCache;

import java.util.HashMap;

public class DummyKeyValueObjectCache extends HashMap<Object, Object> implements KeyValueObjectCache {

    @Override
    public Object getObjectForKey(Object aKey) {
        return get(aKey);
    }

    @Override
    public void setObjectForKey(Object aKey, Object aValue) {
        put(aKey, aValue);
    }
}
