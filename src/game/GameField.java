package game;

import com.sun.istack.internal.NotNull;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;

import javafx.scene.shape.Path;

public final class GameField {
//    int scenery[] = {};
//    int road[] = {};
//
//    public GameField(int[] scenery, int[] road) {
//        this.scenery = scenery;
//        this.road = road;
//    }

    public GameField() {
    }

    public void renderMap(GraphicsContext gc, int stage) throws Exception {
        try {
            gc.drawImage(new Image("file:src/game/resources/map/map" + stage + ".png"), 0, 0);
        } catch (Exception e) {
            System.out.println("Error Loading Map");
        }
    }

    public static Path createPath(int stage) {
        Path path = new Path();
        if (stage == 1) {
            int s = Config.TILE_SIZE;
            MoveTo spawn = new MoveTo(2.5 * s, 15 * s);
            LineTo line1 = new LineTo(2.5 * s, 8.5 * s);
            LineTo line2 = new LineTo(6.5 * s, 8.5 * s);
            LineTo line3 = new LineTo(6.5 * s, 3.5 * s);
            LineTo line4 = new LineTo(11.5 * s, 3.5 * s);
            LineTo line5 = new LineTo(11.5 * s, 11.5 * s);
            LineTo line6 = new LineTo(20.0 * s, 11.5 * s);
            path.getElements().addAll(spawn, line1, line2, line3, line4, line5, line6);
        }
        return path;
    }


}
