package game.entity.enemy;

import game.Config;
import game.GameField;
import javafx.animation.Interpolator;
import javafx.animation.SequentialTransition;
import javafx.scene.image.ImageView;

public class TankerEnemy extends Enemy {
    public TankerEnemy() {
        super(Config.TANKER_ENEMY_SPEED, Config.TANKER_ENEMY_HEALTH, Config.TANKER_ENEMY_ARMOR, Config.TANKER_ENEMY_REWARD);
        try {
            this.image = Config.TANKER_ENEMY_IMG;
            this.imageView = new ImageView(image);
            this.transition = new SequentialTransition(imageView, createTransition(imageView, getSpeed()));
            this.transition.setInterpolator(Interpolator.LINEAR);

            initImgViewPos();
        } catch (Exception e) {
            System.out.println("Error Loading Tanker Enemy Image:" + e.getMessage());
        }
    }
}
