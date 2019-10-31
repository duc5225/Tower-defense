package game;

public class GameStage {
    private int stage = 0;
    private int startMoney = 0;

    public GameStage() {
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
}
