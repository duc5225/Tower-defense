package game;

import game.entity.Hill;
import game.entity.bullet.Bullet;
import game.entity.enemy.Enemy;
import game.entity.enemy.NormalEnemy;
import game.entity.tower.MachineGunTower;
import game.entity.tower.NormalTower;
import game.entity.tower.SniperTower;
import game.entity.tower.Tower;
import javafx.animation.AnimationTimer;
import javafx.animation.Interpolator;
import javafx.animation.PathTransition;
import javafx.event.EventHandler;
import javafx.scene.Cursor;
import javafx.scene.Group;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

public final class GameField {

    private Group root;
    private GraphicsContext gc;
    private final List<Hill> hills = new ArrayList<>();
    private List<Enemy> enemies;
    private List<Tower> towers;

    public GameField(Group root, GraphicsContext gc) {
        this.root = root;
        this.gc = gc;
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

    public static Path createPath() {
        Path path = new Path();
        if (GameStage.stage == 1) {
            MoveTo spawn = new MoveTo(2.5 * Config.TILE_SIZE, 15 * Config.TILE_SIZE);
            LineTo line1 = new LineTo(2.5 * Config.TILE_SIZE, 8.5 * Config.TILE_SIZE);
            LineTo line2 = new LineTo(6.5 * Config.TILE_SIZE, 8.5 * Config.TILE_SIZE);
            LineTo line3 = new LineTo(6.5 * Config.TILE_SIZE, 3.5 * Config.TILE_SIZE);
            LineTo line4 = new LineTo(11.5 * Config.TILE_SIZE, 3.5 * Config.TILE_SIZE);
            LineTo line5 = new LineTo(11.5 * Config.TILE_SIZE, 11.5 * Config.TILE_SIZE);
            LineTo line6 = new LineTo(20.0 * Config.TILE_SIZE, 11.5 * Config.TILE_SIZE);

            path.getElements().addAll(spawn, line1, line2, line3, line4, line5, line6);
        }
        return path;
    }

    public void play() {
        ImageView storeImageNormalTower = new ImageView(Config.NORMAL_TOWER_IMG);
        storeImageNormalTower.setY(100);
        storeImageNormalTower.setX(400);
        root.getChildren().add(storeImageNormalTower);

        ImageView draggableTower = new ImageView(Config.NORMAL_TOWER_IMG);
        draggableTower.setY(storeImageNormalTower.getY());
        draggableTower.setX(storeImageNormalTower.getX());
        draggableTower.setCursor(Cursor.CLOSED_HAND);
        root.getChildren().add(draggableTower);

        EventHandler<MouseEvent> mouseDragged = event -> {
            ((ImageView) (event.getSource())).setTranslateX(event.getSceneX() - storeImageNormalTower.getX() - 32);
            ((ImageView) (event.getSource())).setTranslateY(event.getSceneY() - storeImageNormalTower.getY() - 32);

            AtomicBoolean hovering = new AtomicBoolean(false);
            // if mouse hovering on a hill then change cursor type
            hills.forEach(hill -> {
                if (hill.isUsable(event.getSceneX(), event.getSceneY())) {
                    draggableTower.setCursor(Cursor.CROSSHAIR);
                    hovering.set(true);
                }
            });
            if (!hovering.get()) {
                draggableTower.setCursor(Cursor.CLOSED_HAND);
            }
        };

        EventHandler<MouseEvent> mouseReleased = event -> {
            draggableTower.setTranslateX(storeImageNormalTower.getX() - storeImageNormalTower.getX());
            draggableTower.setTranslateY(storeImageNormalTower.getY() - storeImageNormalTower.getY());
            draggableTower.setCursor(Cursor.CLOSED_HAND);
            hills.forEach(hill -> {
                if (hill.isUsable(event.getSceneX(), event.getSceneY()) && hill.isEnoughMoney(10)) {
                    Tower tower = new NormalTower();
                    tower.setPosition(hill.getX(), hill.getY());
                    towers.add(tower);
                    hill.setUsed(true);

                    Circle circle = new Circle(tower.getX(), tower.getY(), tower.getRange());
                    circle.setStroke(Color.BLACK);
                    circle.setFill(Color.TRANSPARENT);

                    root.getChildren().addAll(tower.getImageView(), circle);
                    draggableTower.toFront();
                    tower.getImageView().toFront();
                    towers.forEach(t -> t.getImageView().setOnMouseClicked(e -> {
                        System.out.println("work");
                    }));
                    GameStage.money -= tower.getPrice();
                }
            });
        };
        Tower tower = new SniperTower();
        tower.setPosition(10, 11);
        towers.add(tower);

        Circle circle = new Circle(tower.getX(), tower.getY(), tower.getRange());
        circle.setStroke(Color.BLACK);
        circle.setFill(Color.TRANSPARENT);

        root.getChildren().addAll(tower.getImageView(), circle);
        tower.getImageView().toFront();

        Tower tower2 = new MachineGunTower();
        tower2.setPosition(6, 11);
        towers.add(tower2);

        Circle circle2 = new Circle(tower2.getX(), tower2.getY(), tower2.getRange());
        circle2.setStroke(Color.BLACK);
        circle2.setFill(Color.TRANSPARENT);

        root.getChildren().addAll(tower2.getImageView(), circle2);
        tower2.getImageView().toFront();

        draggableTower.setOnMouseDragged(mouseDragged);
        draggableTower.setOnMouseReleased(mouseReleased);

        Font theFont = Font.font("Helvetica", FontWeight.BOLD, 20);
        gc.setFont(theFont);
        gc.setStroke(Color.AQUA);
        gc.setLineWidth(1);

        new AnimationTimer() {
            int NUMBER_OF_ENEMIES = 100;

            int i = 0;
            // update every one second
            long startTime = System.nanoTime();

            public void handle(long currentNanoTime) {
                if (GameStage.stage == 1) {
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
        }.

                start();

    }

    private void renderAttackAnimation(Tower tower, Enemy enemy) {
        Bullet bullet = new Bullet();
        Path path = new Path(new MoveTo(tower.getX(), tower.getY()), new LineTo(enemy.getX(), enemy.getY()));

        PathTransition shootTransition = new PathTransition(Duration.millis(120), path, bullet.getImageView());
        shootTransition.setCycleCount(1);
        shootTransition.setInterpolator(Interpolator.LINEAR);

        shootTransition.setOnFinished(event -> {
            bullet.getImageView().setVisible(false);
            root.getChildren().remove(bullet.getImageView());
            if (enemy.isDead()) {
                enemy.getImageView().setVisible(false);
                enemies.remove(enemy);
                root.getChildren().remove(enemy.getImageView());
                GameStage.money += Config.NORMAL_ENEMY_REWARD;
            }
        });

        root.getChildren().add(bullet.getImageView());

        shootTransition.play();
    }

    private void spawnEnemy() {
        Enemy enemy = new NormalEnemy();
        enemies.add(enemy);
        root.getChildren().add(enemy.getImageView());
        enemies.forEach(Enemy::renderAnimation);
    }

    private void initHills() {
        hills.add(new Hill(10, 6));
        hills.add(new Hill(9, 6));
        hills.add(new Hill(10, 7));
        hills.add(new Hill(9, 7));
        hills.add(new Hill(4, 7));
        hills.add(new Hill(5, 12));
        hills.add(new Hill(14, 14));
        hills.add(new Hill(17, 10));
    }
}
