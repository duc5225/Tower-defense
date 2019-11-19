package game;

import javafx.scene.canvas.GraphicsContext;

public class GameStage {
    public static int stage = Config.ORIGINAL_STAGE;
    private int money;
    private static int roadLength=37 * Config.TILE_SIZE;;

    public static int getRoadLength() {
        return roadLength;
    }

    private static GameField gameField = new GameField();

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

    public int getStartMoney() {
        return money;
    }

    public void setStartMoney(int startMoney) {
        this.money = startMoney;
    }

    public static void renderGameField(GraphicsContext gc) {
        gameField.renderMap(gc);
    }
}
