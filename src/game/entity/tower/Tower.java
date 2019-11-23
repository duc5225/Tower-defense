package game.entity.tower;

import game.Config;
import game.entity.GameEntity;
import game.entity.GameTile;
import game.entity.bullet.Bullet;
import game.entity.enemy.Enemy;
import javafx.animation.Interpolator;
import javafx.animation.PathTransition;
import javafx.animation.RotateTransition;
import javafx.animation.Transition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.image.ImageView;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.util.Duration;


public abstract class Tower extends GameEntity {
    private int price = 0;
    private int damage = 0;
    private int armorPenetration = 0;
    private int attackSpeed = 0;
    private int range;

    // update every time a tower shoot a bullet
    private long startDelayTime;

    private int x;
    private int y;

    protected RotateTransition transition;

    public Tower() {
    }

    public Tower(int price, int damage, int armorPenetration, int attackSpeed, int range) {
        this.price = price;
        this.damage = damage;
        this.armorPenetration = armorPenetration;
        this.attackSpeed = attackSpeed;
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

    public int getArmorPenetration() {
        return armorPenetration;
    }

    public void setArmorPenetration(int armorPenetration) {
        this.armorPenetration = armorPenetration;
    }

    public int getAttackSpeed() {
        return attackSpeed;
    }

    public void setAttackSpeed(int attackSpeed) {
        this.attackSpeed = attackSpeed;
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

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
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

    public void rotateTo(Enemy enemy) {
        if (enemy.getX() <= x) {
            if (enemy.getY() >= y) {
                // Quadrant III
                transition.setToAngle(-90 - Math.toDegrees(Math.atan((enemy.getY() - y) / (x - enemy.getX()))));
            } else {
                // Quadrant II
                transition.setToAngle(-Math.toDegrees(Math.atan((x - enemy.getX()) / (y - enemy.getY()))));

            }
        } else {
            if (enemy.getY() >= y) {
                //Quadrant IV
                transition.setToAngle(90 + Math.toDegrees(Math.atan((enemy.getY() - y) / (enemy.getX() - x))));
            } else {
                //Quadrant I
                transition.setToAngle(Math.toDegrees(Math.atan((enemy.getX() - x) / (y - enemy.getY()))));

            }
        }
        transition.play();
    }

    public void dealDamageTo(Enemy enemy) {
        enemy.setHealth(enemy.getHealth() - (damage - enemy.getarmor()));
        if (enemy.getHealth() <= 0) enemy.setDead(true);
    }
}
