package game;

import game.store.Store;
import javafx.scene.Group;
import javafx.scene.canvas.Canvas;

import java.io.IOException;

public class GameStage {
    public static int money;
    public static int health;

    private GameField gameField;
    private Group root;
    private Store store;

    public GameStage(Group root, Canvas canvas, Store store) {
        gameField = new GameField(root, canvas, store);
        this.root = root;
        this.store = store;
        GameStage.money = 1000;
        GameStage.health = 10;
    }

    private void renderGameField() {
        gameField.renderMap();
    }

    public void start() throws IOException {
        renderGameField();
        store.init();
        gameField.play();
    }
}
