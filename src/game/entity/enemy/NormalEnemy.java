package game.entity.enemy;

import game.Config;
import game.GameField;
import game.GameStage;
import javafx.animation.Interpolator;
import javafx.animation.ParallelTransition;
import javafx.animation.PathTransition;
import javafx.animation.SequentialTransition;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

public final class NormalEnemy extends Enemy {
    public NormalEnemy() {
        super(80, 300, 2, Config.NORMAL_ENEMY_REWARD);
        try {
            this.image = Config.NORMAL_ENEMY_IMG;
            this.imageView = new ImageView(image);
//            this.transition = new PathTransition(Duration.seconds((double) GameStage.getRoadLength() / this.getSpeed()), GameField.createPath(), this.imageView);
            this.transition = new SequentialTransition(imageView, GameField.createTransition(imageView, getSpeed()));
            this.transition.setInterpolator(Interpolator.LINEAR);

            initImgViewPos();
        } catch (Exception e) {
            System.out.println("Error Loading Normal Enemy Image:" + e.getMessage());
        }
    }

    public void rotateLeft() {
        imageView.setRotate(imageView.getRotate() - 90);
    }

    public void rotateRight() {
        imageView.setRotate(imageView.getRotate() + 90);
    }
}
