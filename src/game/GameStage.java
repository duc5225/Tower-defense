package game;

import game.entity.Hill;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;

import java.util.ArrayList;
import java.util.List;

public class GameStage {
    public static int stage = Config.ORIGINAL_STAGE;
    private int money = 0;
    private static int roadLength = 37 * Config.TILE_SIZE;

    private GameField gameField;
    private Group root;
    private GraphicsContext graphicsContext;

    public static List<Hill> hills = new ArrayList<>();

    public GameStage(Group root, GraphicsContext graphicsContext) {
        gameField = new GameField(root, graphicsContext);
        this.root = root;
        this.graphicsContext = graphicsContext;
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

    public void renderGameField() {
        gameField.renderMap();
    }

    public void start() {
        renderGameField();
        switch (stage) {
            case Config.ORIGINAL_STAGE:
                createButtons();
                break;
            case 1:
                gameField.play();
                break;
            default:
                System.out.println("Nothing called ");
        }
    }

    private void createButtons() {
        Button button = new Button("Play");
        button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                GameStage.stage = 1;
                root.getChildren().remove(button);
                start();
            }
        });
        root.getChildren().add(button);
    }
}
