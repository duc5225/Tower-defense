package game.entity.tower;

import game.GameField;
import game.GameStage;
import javafx.animation.PathTransition;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

public class NormalTower extends Tower {
    public NormalTower(){
        super(10, 2, 2, 5, 100);
        try {
            this.image = new Image("file:src/game/resources/assets/PNG/Default size/towerDefense_tile203.png");
            this.imageView = new ImageView(image);
        } catch (Exception e) {
            System.out.println("Error Loading Normal Tower Image:" + e.getMessage());
        }
    }
}
