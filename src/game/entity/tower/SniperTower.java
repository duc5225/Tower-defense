package game.entity.tower;

import game.Config;
import game.entity.enemy.Enemy;
import javafx.animation.Interpolator;
import javafx.animation.RotateTransition;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

public class SniperTower extends Tower {
    public SniperTower(){
        super(20, 600, 3, Config.SECOND * 2.5, 400);
        try {
            this.image = Config.SNIPER_TOWER_IMG;
            this.imageView = new ImageView(image);
            this.transition = new RotateTransition(Duration.millis(100), imageView);
            this.transition.setInterpolator(Interpolator.LINEAR);
        } catch (Exception e) {
            System.out.println("Error Loading Normal Tower Image:" + e.getMessage());
        }
    }
}
