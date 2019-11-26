package game.entity.tower;

import game.Config;
import javafx.animation.Interpolator;
import javafx.animation.RotateTransition;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

public class MachineGunTower extends Tower {
    public MachineGunTower(){
        super(30, 75, 3, Config.SECOND * 0.2, 200);
        try {
            this.image = Config.MACHINE_GUN_TOWER_IMG;
            this.imageView = new ImageView(image);
            this.transition = new RotateTransition(Duration.millis(100), imageView);
            this.transition.setInterpolator(Interpolator.LINEAR);
        } catch (Exception e) {
            System.out.println("Error Loading Normal Tower Image:" + e.getMessage());
        }
    }
}
