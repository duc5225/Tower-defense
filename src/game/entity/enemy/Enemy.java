package game.entity.enemy;

import game.Config;
import game.entity.GameEntity;
import javafx.animation.*;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

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

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public int getArmor() {
        return armor;
    }

    public int getReward() {
        return reward;
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

    protected Animation[] createTransition(ImageView imageView, int speed) {
        RotateTransition rotate0 = new RotateTransition(Duration.ONE, imageView);
        rotate0.setToAngle(-90);

        MoveTo spawn1 = new MoveTo(2.5 * Config.TILE_SIZE, 15.2 * Config.TILE_SIZE);
        LineTo line1 = new LineTo(2.5 * Config.TILE_SIZE, 8.5 * Config.TILE_SIZE);
        PathTransition path1 = new PathTransition(Duration.seconds(6.7 * Config.TILE_SIZE / speed), new Path(spawn1, line1), imageView);
        RotateTransition rotate1 = new RotateTransition(Duration.ONE, imageView);
        rotate1.setToAngle(0);

        MoveTo spawn2 = new MoveTo(line1.getX(), line1.getY());
        LineTo line2 = new LineTo(6.5 * Config.TILE_SIZE, 8.5 * Config.TILE_SIZE);
        PathTransition path2 = new PathTransition(Duration.seconds(4.0 * Config.TILE_SIZE / speed), new Path(spawn2, line2), imageView);
        RotateTransition rotate2 = new RotateTransition(Duration.ONE, imageView);
        rotate2.setToAngle(-90);

        MoveTo spawn3 = new MoveTo(line2.getX(), line2.getY());
        LineTo line3 = new LineTo(6.5 * Config.TILE_SIZE, 3.5 * Config.TILE_SIZE);
        PathTransition path3 = new PathTransition(Duration.seconds(5.0 * Config.TILE_SIZE / speed), new Path(spawn3, line3), imageView);
        RotateTransition rotate3 = new RotateTransition(Duration.ONE, imageView);
        rotate3.setToAngle(0);

        MoveTo spawn4 = new MoveTo(line3.getX(), line3.getY());
        LineTo line4 = new LineTo(11.5 * Config.TILE_SIZE, 3.5 * Config.TILE_SIZE);
        PathTransition path4 = new PathTransition(Duration.seconds(5.0 * Config.TILE_SIZE / speed), new Path(spawn4, line4), imageView);
        RotateTransition rotate4 = new RotateTransition(Duration.ONE, imageView);
        rotate4.setToAngle(90);

        MoveTo spawn5 = new MoveTo(line4.getX(), line4.getY());
        LineTo line5 = new LineTo(11.5 * Config.TILE_SIZE, 11.5 * Config.TILE_SIZE);
        PathTransition path5 = new PathTransition(Duration.seconds(8.0 * Config.TILE_SIZE / speed), new Path(spawn5, line5), imageView);
        RotateTransition rotate5 = new RotateTransition(Duration.ONE, imageView);
        rotate5.setToAngle(0);

        MoveTo spawn6 = new MoveTo(line5.getX(), line5.getY());
        LineTo line6 = new LineTo(20.5 * Config.TILE_SIZE, 11.5 * Config.TILE_SIZE);
        PathTransition path6 = new PathTransition(Duration.seconds(9.0 * Config.TILE_SIZE / speed), new Path(spawn6, line6), imageView);
        RotateTransition rotate6 = new RotateTransition(Duration.ONE, imageView);

        Transition[] sequential = new Transition[]{rotate0, path1, rotate1, path2, rotate2, path3, rotate3, path4, rotate4, path5, rotate5, path6, rotate6, new PauseTransition(Duration.millis(100))};

        for (Transition transition : sequential) {
            transition.setInterpolator(Interpolator.LINEAR);
        }
        return sequential;
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
