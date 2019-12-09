package game.entity.tower;

import game.Config;
import game.Sound;
import game.entity.GameEntity;
import game.entity.Hill;
import game.entity.bullet.Bullet;
import game.entity.enemy.Enemy;
import javafx.animation.RotateTransition;
import javafx.scene.image.ImageView;

import java.util.ArrayList;
import java.util.List;


public abstract class Tower extends GameEntity {
    private int price;
    private int damage;
    private long delayTime;
    private int range;

    private int level = 0;
    public List<ImageView> stars = new ArrayList<>();

    // update every time a tower shoot a bullet
    private long startDelayTime;

    private int x;
    private int y;

    public Hill hill;

    protected RotateTransition transition;

    public Tower() {
    }

    public Tower(int price, int damage, long delayTime, int range) {
        this.price = price;
        this.damage = damage;
        this.delayTime = delayTime;
        this.range = range;
        this.startDelayTime = 0;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getDamage() {
        return damage;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }

    public long getDelayTime() {
        return delayTime;
    }

    public void setDelayTime(long delayTime) {
        this.delayTime = delayTime;
    }

    public int getRange() {
        return range;
    }

    public void setRange(int range) {
        this.range = range;
    }

    public long getStartDelayTime() {
        return startDelayTime;
    }

    public void setStartDelayTime(long startDelayTime) {
        this.startDelayTime = startDelayTime;
    }

    public abstract Bullet getBullet();

    public abstract Sound getShootingSound();

    public abstract void upgrade();

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Hill getHill() {
        return hill;
    }

    public void setHill(Hill hill) {
        this.hill = hill;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public RotateTransition getTransition() {
        return transition;
    }

    public void setImageView(ImageView imageView) {
        this.imageView = imageView;
    }

    private void setCenter(int x, int y) {
        this.x = (int) ((x - 0.5) * Config.TILE_SIZE);
        this.y = (int) ((y - 0.5) * Config.TILE_SIZE);
    }

    public void setPosition(int x, int y) {
        setCenter(x, y);
        imageView.setX((x - 1) * Config.TILE_SIZE);
        imageView.setY((y - 1) * Config.TILE_SIZE);
    }

    public boolean canReach(Enemy enemy) {
        return Math.sqrt(Math.pow(enemy.getX() - this.x, 2) + Math.pow(enemy.getY() - this.y, 2)) <= range;
    }

    private double prevAngle = 0;
    protected double nextAngle = 0;

    public void rotateTo(Enemy enemy) {
        //set angle depending of its relative position to tower's position
        if (enemy.getX() <= x) {
            if (enemy.getY() >= y)
                // Quadrant III
                nextAngle = -90 - Math.toDegrees(Math.atan((enemy.getY() - y) / (x - enemy.getX())));
            else
                // Quadrant II
                nextAngle = -Math.toDegrees(Math.atan((x - enemy.getX()) / (y - enemy.getY())));
        } else {
            if (enemy.getY() >= y)
                //Quadrant IV
                nextAngle = 90 + Math.toDegrees(Math.atan((enemy.getY() - y) / (enemy.getX() - x)));
            else
                //Quadrant I
                nextAngle = Math.toDegrees(Math.atan((enemy.getX() - x) / (y - enemy.getY())));

        }
        // pick the direction to rotate, avoid rotating more than 180 degree
        if (nextAngle - prevAngle > 180) {
            nextAngle -= 360;
        } else if (nextAngle - prevAngle < -180) {
            nextAngle += 360;
        }
        transition.setToAngle(nextAngle);
        prevAngle = nextAngle;
        transition.play();
    }

    public void dealDamageTo(Enemy enemy) {
        int damageDeal = damage - enemy.getArmor() <= 0 ? 1 : damage - enemy.getArmor();
        enemy.setHealth(enemy.getHealth() - damageDeal);
        //update healthBar
        enemy.getCurrentHealthBar().setWidth((double) enemy.getHealth() * enemy.getHealthBar().getWidth() / enemy.getMaxHealth());
        if (enemy.getHealth() <= 0) enemy.setDead(true);
    }
}
