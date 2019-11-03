package game;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public final class GameField {
    int scenery[] = {};
    int road[] = {};

    public GameField(int[] scenery, int[] road) {
        this.scenery = scenery;
        this.road = road;
    }

    public void renderMap(GraphicsContext gc) {
        gc.drawImage(new Image("src/game/resources/map/map1.png"),0,0);
    }


}
