package game.entity.enemy;

import game.GameField;
import game.GameStage;
import game.entity.GameEntity;
import javafx.animation.PathTransition;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

public abstract class Enemy extends GameEntity {
    private int speed;  // pixels/second
    private int health;
    private int armor;
    private int reward;
    protected ImageView imageView;
    protected PathTransition transition;

    public Enemy(int speed, int health, int armor, int reward) {
        this.speed = speed;
        this.health = health;
        this.armor = armor;
        this.reward = reward;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public int getarmor() {
        return armor;
    }

    public void setarmor(int armor) {
        this.armor = armor;
    }

    public int getReward() {
        return reward;
    }

    public void setReward(int reward) {
        this.reward = reward;
    }

    public ImageView getImageView() {
        return imageView;
    }

    public void setImageView(ImageView imageView) {
        this.imageView = imageView;
    }

    public void renderAnimation() throws Exception {
        try {
//            PathTransition pathTransition = new PathTransition(Duration.seconds(currentGameStage.getRoadLength()/this.getSpeed()), GameField.createPath(currentGameStage.getStage()), this.imageView);
//            pathTransition.play();
            this.transition.play();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

}