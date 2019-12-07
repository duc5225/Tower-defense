package game.entity.tower;

import game.Config;
import game.Sound;
import game.entity.bullet.Bullet;
import javafx.animation.Interpolator;
import javafx.animation.RotateTransition;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

public class MachineGunTower extends Tower {

    public MachineGunTower() {
        super(Config.MACHINE_GUN_TOWER_PRICE, Config.MACHINE_GUN_TOWER_DAMAGE, 3, Config.MACHINE_GUN_TOWER_DELAY_TIME, Config.MACHINE_GUN_TOWER_RANGE);
        try {
            this.image = Config.MACHINE_GUN_TOWER_IMG;
            this.imageView = new ImageView(image);
            this.transition = new RotateTransition(Duration.millis(100), imageView);
            this.transition.setInterpolator(Interpolator.LINEAR);
        } catch (Exception e) {
            System.out.println("Error Loading Machine Tower Image:" + e.getMessage());
        }
    }

    @Override
    public Bullet getBullet() {
        return new Bullet(Config.MACHINE_GUN_BULLET_IMG, this.nextAngle);
    }

    @Override
    public Sound getShootingSound() {
        return Config.MACHINE_GUN_TOWER_SOUND;
    }
}