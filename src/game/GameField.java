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
            LineTo line6 = new LineTo(20.5 * Config.TILE_SIZE, 11.5 * Config.TILE_SIZE);
            PathTransition path6 = new PathTransition(Duration.seconds(9.0 * Config.TILE_SIZE / speed), new Path(spawn6, line6), imageView);
            RotateTransition rotate6 = new RotateTransition(Duration.ONE, imageView);

            Transition[] sequential = new Transition[]{rotate0, path1, rotate1, path2, rotate2, path3, rotate3, path4, rotate4, path5, rotate5, path6, rotate6, new PauseTransition(Duration.millis(100))};

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
        AnimationTimer timer = new AnimationTimer() {
            // destination coordinate
            final double desX = 20.5 * Config.TILE_SIZE;
            final double desY = 11.5 * Config.TILE_SIZE;

            int wave = 1;

            int enemy_num = 100;

            int i = 0;
            // update every one second
            long startTime = System.nanoTime();

            double normal_pct = 0, smaller_pct = 0, tanker_pct = 0, boss_pct = 0;

            public void handle(long currentNanoTime) {
                updateText();
                if (GameStage.health > 0) {
                    if (GameStage.stage == 1) {
                        // spawn new enemy after a fixed time until max number of enemies reached
                        if (currentNanoTime - startTime >= Config.SPAWN_DELAY_TIME) {
                            if (i < enemy_num) {
                                spawnEnemy(normal_pct, smaller_pct, tanker_pct, boss_pct);
                                ++i;
                            }
                            startTime = currentNanoTime;
                        }
                        for (Tower tower : towers) {
                            double minDistance = 99999;
                            Enemy closest = null;
                            for (Enemy enemy : enemies) {
                                // if enemy is in the range of the tower
                                if (tower.canReach(enemy)) {
                                    if (minDistance > Math.sqrt(Math.pow(enemy.getX() - desX, 2) + Math.pow(enemy.getY() - desY, 2))) {
                                        minDistance = Math.sqrt(Math.pow(enemy.getX() - desX, 2) + Math.pow(enemy.getY() - desY, 2));
                                        closest = enemy;
                                    }
                                }
                            } // end enemy iterate
                            if (closest != null) {
                                tower.rotateTo(closest);
                                if (currentNanoTime - tower.getStartDelayTime() >= tower.getDelayTime()) {
                                    tower.dealDamageTo(closest);
                                    renderAttackAnimation(tower, closest);
                                    tower.setStartDelayTime(currentNanoTime);
                                }
                            }
                        } //  end tower iterate
                        for (Enemy enemy : enemies) {
                            enemy.showHealthBar();
                        }
                    }
                    if (wave == 1) {
                        normal_pct = 50;
                        smaller_pct = 30;
                        tanker_pct = 15;
                        boss_pct = 5;
                        enemy_num = 30;
                    } else if (wave == 2) {
                        normal_pct = 25;
                        smaller_pct = 40;
                        tanker_pct = 20;
                        boss_pct = 15;
                        enemy_num = 60;
                    }
                    // spawn new enemy after a fixed time until max number of enemies reached
                    if (currentNanoTime - startTime >= Config.SPAWN_DELAY_TIME && i < enemy_num) {
                        spawnEnemy(normal_pct, smaller_pct, tanker_pct, boss_pct);
                        ++i;
                        startTime = currentNanoTime;
                    }
                    if (i >= enemy_num && enemies.isEmpty()) {
                        wave++;
                        i = 0;
                    }
                    for (Tower tower : towers) {
                        double minDistance = 99999;
                        Enemy closest = null;
                        for (Enemy enemy : enemies) {
                            // if enemy is in the range of the tower
                            if (tower.canReach(enemy)) {
                                if (minDistance > Math.sqrt(Math.pow(enemy.getX() - desX, 2) + Math.pow(enemy.getY() - desY, 2))) {
                                    minDistance = Math.sqrt(Math.pow(enemy.getX() - desX, 2) + Math.pow(enemy.getY() - desY, 2));
                                    closest = enemy;
                                }
                            }
                        } // end enemy iterate
                        if (closest != null) {
                            tower.rotateTo(closest);
                            if (currentNanoTime - tower.getStartDelayTime() >= tower.getDelayTime()) {
                                tower.dealDamageTo(closest);
                                renderAttackAnimation(tower, closest);
                                tower.setStartDelayTime(currentNanoTime);
                            }
                        }
                    } //  end tower iterate
                } else {
                    //lose
                    System.out.println("u lost");
                    stop();
                    GameField.this.stop();
                }
            }
        };
        timer.start();
    }

    private void stop() {
        enemies.forEach(enemy -> {
            enemy.getTransition().stop();
            enemy.getImageView().setVisible(false);
            root.getChildren().remove(enemy);
        });
        towers.forEach(tower -> {
            tower.getTransition().stop();
            tower.getImageView().setVisible(false);
            root.getChildren().remove(tower);
        });
        enemies.clear();
        towers.clear();
        hills.clear();

        Config.yeahBoy.stop();
        Config.BACKGROUND_MUSIC.play();
        Config.BACKGROUND_MUSIC.repeat();

        gc.clearRect(0, 0, Config.SCREEN_WIDTH, Config.SCREEN_HEIGHT);
        root.getChildren().clear();
        money.setVisible(false);
        health.setVisible(false);

        store.disable();
    }

    private void renderAttackAnimation(Tower tower, Enemy enemy) {
        Bullet bullet = tower.getBullet();
        Path path = new Path(new MoveTo(tower.getX(), tower.getY()), new LineTo(enemy.getX(), enemy.getY()));

        // Play sound
        tower.getShootingSound().play();

        PathTransition shootTransition = new PathTransition(Duration.millis(100), path, bullet.getImageView());
        shootTransition.setCycleCount(1);
        shootTransition.setInterpolator(Interpolator.EASE_IN);

        shootTransition.setOnFinished(event -> {
            tower.getShootingSound().stop();
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
        root.getChildren().removeAll(enemy.getHealthBar(), enemy.getCurrentHealthBar());
        enemy.getImageView().setVisible(false);
        enemies.remove(enemy);
        root.getChildren().remove(enemy.getImageView());
        enemy.setDead(true);
    }

    private Enemy generateEnemy(double normal, double small, double tanker, double boss) {
        double x = Math.random() * 100;
        if (x < normal) return new NormalEnemy();
        if (x < normal + small) return new SmallerEnemy();
        if (x < normal + small + tanker) return new TankerEnemy();
        return new BossEnemy();
    }

    private void spawnEnemy(double normal, double smaller, double tanker, double boss) {
        Enemy enemy = generateEnemy(normal, smaller, tanker, boss);
        root.getChildren().addAll(enemy.getHealthBar(), enemy.getCurrentHealthBar());
        enemy.getTransition().setOnFinished(event -> {
            if (!enemy.isDead()) {
                remove(enemy);
                explode();
                GameStage.health--;
            }
        });
        enemies.add(enemy);
        root.getChildren().add(enemy.getImageView());
        enemies.forEach(Enemy::renderAnimation);
    }

    private void explode() {
        Group explosion = new Group();
        explosion.setTranslateX(20 * Config.TILE_SIZE);
        explosion.setTranslateY(11 * Config.TILE_SIZE);
        Timeline t = new Timeline();
        t.setCycleCount(1);
        t.getKeyFrames().add(new KeyFrame(Duration.millis(50), event -> explosion.getChildren().setAll(Config.EXPLOSION1)));
        t.getKeyFrames().add(new KeyFrame(Duration.millis(100), event -> explosion.getChildren().setAll(Config.EXPLOSION2)));
        t.getKeyFrames().add(new KeyFrame(Duration.millis(150), event -> explosion.getChildren().setAll(Config.EXPLOSION3)));
        t.getKeyFrames().add(new KeyFrame(Duration.millis(200), event -> explosion.getChildren().setAll(Config.EXPLOSION4)));
        t.getKeyFrames().add(new KeyFrame(Duration.millis(250), event -> explosion.getChildren().setAll(Config.EXPLOSION5)));
        root.getChildren().add(explosion);
        Config.EXPLODE_SOUND.stop();
        Config.EXPLODE_SOUND.play();
        t.play();
        t.setOnFinished(event -> {
            explosion.setVisible(false);
            explosion.getChildren().clear();
        });
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
