package game.entity.tower;

import game.Config;
import game.Sound;
import game.entity.bullet.Bullet;
import javafx.animation.Interpolator;
import javafx.animation.RotateTransition;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

public class NormalTower extends Tower {
    public NormalTower() {
        super(Config.NORMAL_TOWER_PRICE, Config.NORMAL_TOWER_DAMAGE, 2, Config.NORMAL_TOWER_DELAY_TIME, Config.NORMAL_TOWER_RANGE);
        try {
            this.image = Config.NORMAL_TOWER_IMG;
            this.imageView = new ImageView(image);
            this.transition = new RotateTransition(Duration.millis(100), imageView);
            this.transition.setInterpolator(Interpolator.LINEAR);
        } catch (Exception e) {
            System.out.println("Error Loading Normal Tower Image:" + e.getMessage());
        }
    }

    @Override
    public Bullet getBullet() {
        return new Bullet(Config.NORMAL_BULLET_IMG, this.nextAngle);
    }

    @Override
    public Sound getShootingSound() {
        return Config.NORMAL_TOWER_SOUND;
    }
}
