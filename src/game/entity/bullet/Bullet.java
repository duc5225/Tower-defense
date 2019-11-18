package game.entity.bullet;

import game.Config;
import game.entity.GameEntity;
import javafx.animation.Interpolator;
import javafx.animation.RotateTransition;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

public final class Bullet extends GameEntity {
    public Bullet() {
        this.image = Config.BULLET_IMG;
        this.imageView = new ImageView(this.image);
    }
}