package game.entity.enemy;

import game.Config;
import game.entity.GameEntity;
import javafx.animation.SequentialTransition;
import javafx.beans.property.DoubleProperty;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public abstract class Enemy extends GameEntity {
    private int speed;  // pixels/second
    private int health;
    private int armor;
    private int reward;
    private boolean dead;
    private int maxHealth;

    protected SequentialTransition transition;
    private Rectangle healthBar = new Rectangle(0, 0, 75, 5);
    private Rectangle currentHealthBar = new Rectangle(0, 0, healthBar.getWidth(), healthBar.getHeight());

    public Enemy(int speed, int health, int armor, int reward) {
        this.speed = speed;
        this.health = health;
        this.maxHealth = health;
        this.armor = armor;
        this.reward = reward;
        this.dead = false;
        createHealthBar();
    }

    public int getSpeed() {
        return speed;
    }

    public Rectangle getHealthBar() {
        return healthBar;
    }

    public Rectangle getCurrentHealthBar() {
        return currentHealthBar;
    }

    public int getMaxHealth() {
        return maxHealth;
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

    public int getArmor() {
        return armor;
    }

    public void setArmor(int armor) {
        this.armor = armor;
    }

    public int getReward() {
        return reward;
    }

    public void setReward(int reward) {
        this.reward = reward;
    }

    public boolean isDead() {
        return dead;
    }

    public void setDead(boolean dead) {
        this.dead = dead;
    }

    public SequentialTransition getTransition() {
        return transition;
    }

    public double getX() {
        return imageView.getTranslateX() + (double) Config.TILE_SIZE / 2;
    }

    public double getY() {
        return imageView.getTranslateY() + (double) Config.TILE_SIZE / 2;
    }

    public void renderAnimation() {
        try {
            this.transition.play();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private void createHealthBar() {
        healthBar.setStroke(Color.BLACK);
        healthBar.setFill(Color.rgb(0, 0, 0, 0.5));
        currentHealthBar.setFill(Color.GREEN);
        healthBar.setVisible(false);
        currentHealthBar.visibleProperty().bind(healthBar.visibleProperty());
    }

    public void showHealthBar() {
        healthBar.translateXProperty().bind(imageView.translateXProperty().subtract(5));
        healthBar.translateYProperty().bind(imageView.translateYProperty().add(55));
        currentHealthBar.translateXProperty().bind(imageView.translateXProperty().subtract(5));
        currentHealthBar.translateYProperty().bind(imageView.translateYProperty().add(55));
        if (health < maxHealth) {
            healthBar.setVisible(true);
        }
    }

}
