package game.entity.bullet;

import game.Config;
import game.entity.GameEntity;
import javafx.animation.Interpolator;
import javafx.animation.RotateTransition;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

public final class Bullet extends GameEntity {
    public Bullet(Image image, double angle) {
        this.image = image;
        this.imageView = new ImageView(this.image);
        this.imageView.setRotate(angle);
        initImgViewPos();
    }
}