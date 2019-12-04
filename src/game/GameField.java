package game;

import game.entity.Hill;
import game.entity.bullet.Bullet;
import game.entity.enemy.*;
import game.entity.tower.Tower;
import javafx.animation.*;
import javafx.scene.Group;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.List;

public final class GameField {

    private Group root;
    private Canvas canvas;
    private GraphicsContext gc;
    private Store store;

    private Text money, health;

    private final List<Hill> hills = new ArrayList<>();
    private List<Enemy> enemies;
    private List<Tower> towers;

    public GameField(Group root, Canvas canvas, Store store) {
        this.root = root;
        this.store = store;
        this.canvas = canvas;
        this.gc = canvas.getGraphicsContext2D();
        enemies = new ArrayList<>();
        towers = new ArrayList<>();
        // init hills
        initHills();
    }

    public void renderMap() {
        if (GameStage.stage == 1) {
            try {
                gc.drawImage(new Image("file:src/game/resources/map/map" + GameStage.stage + ".png"), 0, 0);
                hills.forEach(hill -> gc.drawImage(hill.getImage(), hill.getSceneX(), hill.getSceneY()));
            } catch (Exception e) {
                System.out.println("Error Loading Map");
            }
        }
    }

    public static Animation[] createTransition(ImageView imageView, int speed) {
        if (GameStage.stage == 1) {
            RotateTransition rotate0 = new RotateTransition(Duration.ONE, imageView);
            rotate0.setToAngle(-90);
//            Path path = new Path();
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
            LineTo line6 = new LineTo(22.1 * Config.TILE_SIZE, 11.5 * Config.TILE_SIZE);
            PathTransition path6 = new PathTransition(Duration.seconds(10.6 * Config.TILE_SIZE / speed), new Path(spawn6, line6), imageView);
            RotateTransition rotate6 = new RotateTransition(Duration.ONE, imageView);

//            path.getElements().addAll(spawn, line1, line2, line3, line4, line5, line6);
            Transition[] sequential = new Transition[]{rotate0, path1, rotate1, path2, rotate2, path3, rotate3, path4, rotate4, path5, rotate5, path6, rotate6};
//            makeLinear(sequential);
            for (Transition transition : sequential) {
                transition.setInterpolator(Interpolator.LINEAR);
            }
            return sequential;
        }
        return null;
    }

    public void play() {
        store.handleMouseEvent(root, hills, towers);
        createText();
        new AnimationTimer() {
            int NUMBER_OF_ENEMIES = 100;

            int i = 0;
            // update every one second
            long startTime = System.nanoTime();

            public void handle(long currentNanoTime) {
                if (GameStage.stage == 1) {
                    updateText();
                    // spawn new enemy after a fixed time until max number of enemies reached
                    if (currentNanoTime - startTime >= Config.SPAWN_DELAY_TIME) {
                        if (i < NUMBER_OF_ENEMIES) {
                            spawnEnemy();
                            ++i;
                        }
                        startTime = currentNanoTime;
                    }
                    for (Tower tower : towers) {
                        for (Enemy enemy : enemies) {
                            // if enemy is in the radius of the tower
                            if (tower.canReach(enemy)) {
                                tower.rotateTo(enemy);
                                if (currentNanoTime - tower.getStartDelayTime() >= tower.getDelayTime()) {
                                    tower.dealDamageTo(enemy);
                                    renderAttackAnimation(tower, enemy);
                                    tower.setStartDelayTime(currentNanoTime);
                                }
                                // found the enemy, break and let another tower find enemy
                                break;
                            }
                        } // end enemy iterate
                    } //  end tower iterate
                }
            }
        }.start();
    }

    private void renderAttackAnimation(Tower tower, Enemy enemy) {
        Bullet bullet = tower.getBullet();
        Path path = new Path(new MoveTo(tower.getX(), tower.getY()), new LineTo(enemy.getX(), enemy.getY()));

        PathTransition shootTransition = new PathTransition(Duration.millis(100), path, bullet.getImageView());
        shootTransition.setCycleCount(1);
        shootTransition.setInterpolator(Interpolator.EASE_IN);

        shootTransition.setOnFinished(event -> {
            bullet.getImageView().setVisible(false);
            root.getChildren().remove(bullet.getImageView());

            if (enemy.isDead() && enemies.contains(enemy)) {
                remove(enemy);
                //update money
                GameStage.money += enemy.getReward();
            }
        });

        root.getChildren().add(bullet.getImageView());

        shootTransition.play();
    }

    private void remove(Enemy enemy) {
        enemy.getImageView().setVisible(false);
        enemies.remove(enemy);
        root.getChildren().remove(enemy.getImageView());
        enemy.setDead(true);
    }

    private Enemy generateEnemy() {
        double x = Math.random();
        if (x < 0.5) return new NormalEnemy();
        if (x < 0.8) return new SmallerEnemy();
        if (x < 0.95) return new TankerEnemy();
        return new BossEnemy();
    }

    private void spawnEnemy() {
        Enemy enemy = generateEnemy();
        enemy.getTransition().setOnFinished(event -> {
            if (!enemy.isDead()) {
                remove(enemy);
                GameStage.health--;
            }
        });
        enemies.add(enemy);
        root.getChildren().add(enemy.getImageView());
        enemies.forEach(Enemy::renderAnimation);
    }

    private void initHills() {
        hills.add(new Hill(1, 7));
        hills.add(new Hill(3, 3));
        hills.add(new Hill(4, 7));
        hills.add(new Hill(5, 12));
        hills.add(new Hill(8, 13));
        hills.add(new Hill(9, 6));
        hills.add(new Hill(9, 10));
        hills.add(new Hill(10, 1));
        hills.add(new Hill(10, 6));
        hills.add(new Hill(14, 10));
        hills.add(new Hill(14, 14));
        hills.add(new Hill(17, 10));
        hills.add(new Hill(18, 10));
    }

    private void updateText() {
        money.setText("Money: " + GameStage.money);
        health.setText("Health: " + GameStage.health);
    }

    private void createText() {
        money = new Text(10, 120, "Money: " + GameStage.money);
        money.setFont(Font.font("Helvetica", FontWeight.BOLD, 50));
        money.setFill(Color.YELLOW);
        money.setStroke(Color.BLACK);

        health = new Text(10, 50, "Health: " + GameStage.health);
        health.setFont(Font.font("Helvetica", FontWeight.BOLD, 50));
        health.setFill(Color.YELLOW);
        health.setStroke(Color.BLACK);
        root.getChildren().addAll(money, health);
    }
}
