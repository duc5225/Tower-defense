package game;

import game.entity.Hill;
import game.entity.bullet.Bullet;
import game.entity.enemy.*;
import game.entity.tower.Tower;
import game.store.Store;
import javafx.animation.*;
import javafx.scene.Group;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
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
    private GraphicsContext gc;
    private Store store;

    private Text money, health;

    private final List<Hill> hills = new ArrayList<>();
    private List<Enemy> enemies;
    private List<Tower> towers;

    public GameField(Group root, Canvas canvas, Store store) {
        this.root = root;
        this.store = store;
        this.gc = canvas.getGraphicsContext2D();
        enemies = new ArrayList<>();
        towers = new ArrayList<>();
        // init hills
        initHills();
    }

    public void renderMap() {
        try {
            gc.drawImage(new Image("file:src/game/resources/map/map1.png"), 0, 0);
            hills.forEach(hill -> gc.drawImage(hill.getImage(), hill.getSceneX(), hill.getSceneY()));
        } catch (Exception e) {
            System.out.println("Error Loading Map");
        }
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
                    switch (wave) {
                        case 1:
                            normal_pct = 50;
                            smaller_pct = 30;
                            tanker_pct = 15;
                            boss_pct = 5;
                            enemy_num = 30;
                            break;
                        case 2:
                            normal_pct = 25;
                            smaller_pct = 40;
                            tanker_pct = 20;
                            boss_pct = 15;
                            enemy_num = 60;
                            break;
                        case 3:
                            normal_pct = 10;
                            smaller_pct = 40;
                            tanker_pct = 10;
                            boss_pct = 40;
                            enemy_num = 100;
                            break;
                        default:
                            // done 3 waves, end game
                            stop();
                            GameField.this.stop();
                    }
                    // spawn new enemy after a fixed time until max number of enemies reached
                    if (currentNanoTime - startTime >= Config.SPAWN_DELAY_TIME && i < enemy_num) {
                        spawnEnemy(normal_pct, smaller_pct, tanker_pct, boss_pct);
                        ++i;
                        startTime = currentNanoTime;
                    }
                    // if there's no more enemy on the screen, new wave
                    if (i >= enemy_num && enemies.isEmpty()) {
                        wave++;
                        i = 0;
                    }
                    // check each tower with each enemy
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
                    enemies.forEach(Enemy::showHealthBar);
                } else {
                    //lose
                    stop();
                    GameField.this.stop();
                }
            }
        };
        Button button = new Button("Back to Menu");
        button.setOnAction(e -> {
            stop();
            timer.stop();
        });
        button.setPrefSize(150, 40);
        button.setLayoutX(Config.SCREEN_WIDTH - button.getPrefWidth() - 5);
        button.setLayoutY(Config.SCREEN_HEIGHT - button.getPrefHeight() - 5);
        root.getChildren().add(button);
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

        int duration = 100;
        if (tower.getBullet().getImage() == Config.SNIPER_BULLET_IMG) duration = 150;

        PathTransition shootTransition = new PathTransition(Duration.millis(duration), path, bullet.getImageView());
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
        hills.add(new Hill(2, 7));
        hills.add(new Hill(3, 3));
        hills.add(new Hill(4, 7));
        hills.add(new Hill(5, 12));
        hills.add(new Hill(8, 13));
        hills.add(new Hill(9, 6));
        hills.add(new Hill(9, 10));
        hills.add(new Hill(10, 2));
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
        health = new Text(10, 40, "Health: " + GameStage.health);
        health.setFont(Font.font("Helvetica", FontWeight.BOLD, 35));
        health.setFill(Color.YELLOW);
        health.setStroke(Color.BLACK);

        money = new Text(10, 85, "Money: " + GameStage.money);
        money.setFont(Font.font("Helvetica", FontWeight.BOLD, 35));
        money.setFill(Color.YELLOW);
        money.setStroke(Color.BLACK);
        root.getChildren().addAll(money, health);
    }
}
