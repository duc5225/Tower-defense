package game;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class GameStage {
    private int stage;
    private int startMoney;

    public GameStage() {
        this.stage=0;
        this.startMoney=0;

    }

    public GameStage(int stage) {
        this.stage = stage;
        if (stage == 1){
            startMoney = 100;
        }
        if (stage == 2){
            startMoney = 200;
        }
    }

    public int getGameStage() {
        return stage;
    }

    public void setGameStage(int stage) {
        this.stage = stage;
        if (stage == 1){
            startMoney = 100;
        }
        if (stage == 2){
            startMoney = 200;
        }
    }

    public int getStartMoney() {
        return startMoney;
    }

    public void setStartMoney(int startMoney) {
        this.startMoney = startMoney;
    }

    public void renderMap(GraphicsContext gc) {
        gc.drawImage(new Image("file:src/game/resources/map/map"+this.stage+".png"),0,0);
    }
}
