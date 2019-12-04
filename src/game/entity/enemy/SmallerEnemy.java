package game.entity.enemy;

import game.Config;
import game.GameField;
import javafx.animation.Interpolator;
import javafx.animation.SequentialTransition;
import javafx.scene.image.ImageView;

public class SmallerEnemy extends Enemy {
    public SmallerEnemy() {
        super(Config.SMALLER_ENEMY_SPEED, Config.SMALLER_ENEMY_HEALTH, Config.SMALLER_ENEMY_ARMOR, Config.SMALLER_ENEMY_REWARD);
        try {
            this.image = Config.SMALLER_ENEMY_IMG;
            this.imageView = new ImageView(image);
//            this.transition = new PathTransition(Duration.seconds((double) GameStage.getRoadLength() / this.getSpeed()), GameField.createPath(), this.imageView);
            this.transition = new SequentialTransition(imageView, GameField.createTransition(imageView, getSpeed()));
            this.transition.setInterpolator(Interpolator.LINEAR);

            initImgViewPos();
        } catch (Exception e) {
            System.out.println("Error Loading Smaller Enemy Image:" + e.getMessage());
        }
    }
}
