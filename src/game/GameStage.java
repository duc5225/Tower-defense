package game;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class GameStage {
    private static int stage;
    private int money;
    private static int roadLength;

    public static int getRoadLength() {
        return roadLength;
    }

    private GameField gameField;

    public GameStage() {
        this.stage = 0;
        this.money = 0;
    }

    public GameStage(int stage) {
        this.stage = stage;
        if (stage == 1) {
            money = 100;
            roadLength = 37 * Config.TILE_SIZE;
        }
        if (stage == 2) {
            money = 200;
        }
        gameField = new GameField();
    }

    public static int getStage() {
        return stage;
    }

    public void setStage(int stage) {
        this.stage = stage;
        if (stage == 1) {
            money = 100;
        }
        if (stage == 2) {
            money = 200;
        }
    }

    public int getStartMoney() {
        return money;
    }

    public void setStartMoney(int startMoney) {
        this.money = startMoney;
    }

    public void renderGameField(GraphicsContext gc) throws Exception {
        gameField.renderMap(gc, this.stage);
    }
}
