package game.entity.enemy;

import game.GameField;
import game.GameStage;
import javafx.animation.PathTransition;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

public final class NormalEnemy extends Enemy {
    public NormalEnemy() {
        super(80, 100, 2, 5);
        try {
            this.image = new Image("file:src/game/resources/assets/PNG/Default size/towerDefense_tile245.png");
            this.imageView = new ImageView(image);
            this.transition = new PathTransition(Duration.seconds((double) GameStage.getRoadLength() / this.getSpeed()), GameField.createPath(GameStage.getStage()), this.imageView);
        } catch (Exception e) {
            System.out.println("Error Loading Normal Enemy Image:" + e.getMessage());
        }
    }
    public void RotateLeft(){
        imageView.setRotate(imageView.getRotate()-90);
    }
    public void RotateRight(){
        imageView.setRotate(imageView.getRotate()+90);
    }
}
