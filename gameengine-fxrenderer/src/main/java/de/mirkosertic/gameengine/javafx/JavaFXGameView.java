package de.mirkosertic.gameengine.javafx;

import de.mirkosertic.gameengine.camera.CameraComponent;
import de.mirkosertic.gameengine.core.*;
import de.mirkosertic.gameengine.sprites.SpriteComponentTemplate;
import de.mirkosertic.gameengine.types.Position;
import de.mirkosertic.gameengine.types.Size;
import javafx.animation.AnimationTimer;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.transform.Rotate;

import java.io.IOException;

public class JavaFXGameView extends Canvas implements GameView {

    private AnimationTimer animationTimer;
    private GameScene gameScene;
    private GameRuntime gameRuntime;
    private CameraComponent cameraComponent;

    public JavaFXGameView(GameRuntime aRuntime, CameraComponent aCameraComponent) {
        cameraComponent = aCameraComponent;
        animationTimer = new AnimationTimer() {
            @Override
            public void handle(long l) {
                if (gameScene != null) {
                    renderScene();
                }
            }
        };
        gameRuntime = aRuntime;
    }

    private void renderScene() {
        GraphicsContext theContext = getGraphicsContext2D();

        de.mirkosertic.gameengine.types.Color theBGColor = gameScene.getBackgroundColor();

        Color theFXColor = Color.rgb(theBGColor.getR(), theBGColor.getG(), theBGColor.getB());
        theContext.setFill(theFXColor);
        theContext.setStroke(theFXColor);
        theContext.fillRect(0, 0, getWidth(), getHeight());

        for (GameObjectInstance theInstance : cameraComponent.getObjectsToDrawInRightOrder(gameScene)) {
            Position thePosition = cameraComponent.transformToScreenPosition(theInstance.positionProperty().get());

            Size theSize = theInstance.getOwnerGameObject().sizeProperty().get();

            theContext.save();

            float theHalfWidth = theSize.width / 2;
            float theHalfHeight = theSize.height / 2;

            Rotate r = new Rotate(theInstance.rotationAngleProperty().get().angleInDegrees, thePosition.x + theHalfWidth, thePosition.y + theHalfHeight);
            theContext.setTransform(r.getMxx(), r.getMyx(), r.getMxy(), r.getMyy(), r.getTx(), r.getTy());

            SpriteComponentTemplate theTemplateComponent = theInstance.getOwnerGameObject().getComponentTemplate(SpriteComponentTemplate.class);
            if (theTemplateComponent != null && theTemplateComponent.getResourceName() != null) {
                try {
                    JavaFXBitmapResource theBitmap = gameRuntime.getResourceCache().getResourceFor(theTemplateComponent.getResourceName());
                    drawGameObjectInstance(theContext, theInstance, thePosition, theSize, theBitmap);
                    //drawGameObjectInstance(theContext, theInstance, new Position(theSize.width / 2, theSize.height / 2), theSize, theBitmap);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            } else {
                theContext.setFill(Color.WHITE);
                theContext.setStroke(Color.WHITE);
                theContext.setLineWidth(1);
                theContext.strokeRect(thePosition.x, thePosition.y, theSize.width, theSize.height);
            }

            theContext.restore();
        }
        afterRendering(theContext);
    }

    protected void afterRendering(GraphicsContext aContext) {
    }

    protected void drawGameObjectInstance(GraphicsContext aContext, GameObjectInstance aInstance, Position aPosition, Size aSize, JavaFXBitmapResource aBitmapResource) {
        aContext.drawImage(aBitmapResource, aPosition.x, aPosition.y);
    }

    public void startTimer() {
        animationTimer.start();
    }

    public void stopTimer() {
        animationTimer.stop();
    }

    public void renderGame(long aGameTime, long aElapsedTimeSinceLastLoop, GameScene aScene) {
        gameScene = aScene;
    }

    protected GameScene getGameScene() {
        return gameScene;
    }

    protected CameraComponent getCameraComponent() {
        return cameraComponent;
    }
}
