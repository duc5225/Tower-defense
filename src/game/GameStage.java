package game;

import javafx.scene.Group;
import javafx.scene.canvas.Canvas;

import java.io.IOException;

public class GameStage {
    public static int stage = Config.ORIGINAL_STAGE;
    public static int money = 1000;
    private static int roadLength = 37 * Config.TILE_SIZE;

    private GameField gameField;
    private Group root;
    private Store store;

    public GameStage(Group root, Canvas canvas, Store store) {
        gameField = new GameField(root, canvas, store);
        this.root = root;
        this.store = store;
    }

    public GameStage(int stage) {
        GameStage.stage = stage;
        if (stage == 1) {
            money = 100;
            roadLength = 37 * Config.TILE_SIZE;
        }
        if (stage == 2) {
            money = 200;
        }
    }

    public static int getRoadLength() {
        return roadLength;
    }

    public int getStartMoney() {
        return money;
    }

    public void setStartMoney(int startMoney) {
        this.money = startMoney;
    }

    private void renderGameField() {
        gameField.renderMap();
    }

    public void start() throws IOException {
        renderGameField();
        store.init();
        switch (stage) {
            case 1:
                gameField.play();
                break;
            default:
                System.out.println("Nothing called ");
        }
    }

//    private void createButtons() {
//        Button button = new Button("Play");
//        button.setOnAction(event -> {
//            GameStage.stage = 1;
//            root.getChildren().remove(button);
//            start();
//        });
//        root.getChildren().add(button);
//    }
}
