package game.entity.tower;

import game.Config;
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
        super(10, 102, 2, 5, 200);
        try {
            this.image = Config.NORMAL_TOWER_IMG;
            this.imageView = new ImageView(image);
            this.transition = new RotateTransition(Duration.millis(100), imageView);
            this.transition.setInterpolator(Interpolator.LINEAR);
        } catch (Exception e) {
            System.out.println("Error Loading Normal Tower Image:" + e.getMessage());
        }
    }
}
