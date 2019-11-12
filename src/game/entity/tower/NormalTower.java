package game.entity.tower;

import game.GameField;
import game.GameStage;
import javafx.animation.Interpolator;
import javafx.animation.PathTransition;
import javafx.animation.RotateTransition;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

public class NormalTower extends Tower {
    public NormalTower(){
        super(10, 2, 2, 5, 280);
        try {
            this.image = new Image("file:src/game/resources/assets/PNG/Default size/towerDefense_tile249.png");
            this.imageView = new ImageView(image);
            this.transition = new RotateTransition(Duration.millis(10), imageView);
            this.transition.setInterpolator(Interpolator.LINEAR);
        } catch (Exception e) {
            System.out.println("Error Loading Normal Tower Image:" + e.getMessage());
        }
    }
}
