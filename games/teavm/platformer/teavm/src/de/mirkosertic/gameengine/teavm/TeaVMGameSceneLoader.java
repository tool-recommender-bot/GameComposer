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
import de.mirkosertic.gameengine.core.Game;
import de.mirkosertic.gameengine.core.GameScene;
import org.teavm.jso.ajax.XMLHttpRequest;
import org.teavm.jso.json.JSON;

import java.util.Map;

public class TeaVMGameSceneLoader {

    public interface GameSceneLoadedListener {
        void onGameSceneLoaded(GameScene aScene);
        void onGameSceneLoadedError(Throwable aThrowable);
    }

    protected final GameSceneLoadedListener listener;
    private final AbstractGameRuntimeFactory runtimeFactory;

    public TeaVMGameSceneLoader(GameSceneLoadedListener aListener, AbstractGameRuntimeFactory aRuntimeFactory) {
        listener = aListener;
        runtimeFactory = aRuntimeFactory;
    }

    public void loadFromServer(final Game aGame, String aSceneName, final TeaVMGameResourceLoader aResourceLoader) {
        final XMLHttpRequest theRequest = XMLHttpRequest.create();
        theRequest.overrideMimeType("text/plain");
        theRequest.open("GET", aSceneName+"/scene.json");
        theRequest.onComplete(() -> listener.onGameSceneLoaded(parse(aGame, theRequest.getResponseText(), aResourceLoader)));
        theRequest.send();
    }

    protected GameScene parse(Game aGame, String aResponse, TeaVMGameResourceLoader aResourceLoader) {
        Map<String, Object> theResult = new TeaVMMap((TeaVMMap.JSDelegate) JSON.parse(aResponse).cast());
        return GameScene.deserialize(aGame, runtimeFactory.create(aResourceLoader, new TeaVMGameSoundSystemFactory()), theResult);
    }
}