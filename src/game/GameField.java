package game;

import game.entity.Hill;
import game.entity.bullet.Bullet;
import game.entity.enemy.Enemy;
import game.entity.enemy.NormalEnemy;
import game.entity.tower.NormalTower;
import game.entity.tower.Tower;
import javafx.animation.AnimationTimer;
import javafx.animation.Interpolator;
import javafx.animation.PathTransition;
import javafx.scene.Cursor;
import javafx.scene.Group;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
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

public final class GameField {

    private Group root;
    private GraphicsContext gc;
    private List<Enemy> enemies;
    private List<Tower> towers;

    public GameField(Group root, GraphicsContext gc) {
        this.root = root;
        this.gc = gc;
        enemies = new ArrayList<>();
        towers = new ArrayList<>();
    }

    public void renderMap() {
        if (GameStage.stage == 0) {
            try {
                gc.drawImage(new Image("file:src/game/resources/map/map" + GameStage.stage + ".png"), 0, 0);
            } catch (Exception e) {
                System.out.println("Error Loading Map");
            }
        }
        if (GameStage.stage == 1) {
            try {
                gc.drawImage(new Image("file:src/game/resources/map/map" + GameStage.stage + ".png"), 0, 0);
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
//        ???
        ImageView storeImage = new ImageView(Config.NORMAL_TOWER_IMG);
        storeImage.setY(100);
        storeImage.setX(100);
        root.getChildren().add(storeImage);
        ImageView draggableTower = new ImageView(Config.NORMAL_TOWER_IMG);
        draggableTower.setY(100);
        draggableTower.setX(100);
        draggableTower.setCursor(Cursor.CLOSED_HAND);
        root.getChildren().add(draggableTower);

        Hill hill1 = new Hill(4, 7);
        GameStage.hills.add(hill1);
        Hill hill2 = new Hill(10, 6);
        GameStage.hills.add(hill2);
//      ???
//        storeImage.setOnMouseClicked(event -> {
//        });

        draggableTower.setOnMouseDragged(event -> {
            ((ImageView) (event.getSource())).setTranslateX(event.getSceneX() - 132);
            ((ImageView) (event.getSource())).setTranslateY(event.getSceneY() - 132);
            System.out.println(String.format("x: %s, y:%s", event.getSceneX() / Config.TILE_SIZE, event.getSceneY() / Config.TILE_SIZE));
            if (hill1.canBePlacePixelSizeInput(event.getSceneX(), event.getSceneY()) || hill2.canBePlacePixelSizeInput(event.getSceneX(), event.getSceneY())) {
                draggableTower.setCursor(Cursor.CROSSHAIR);
            } else draggableTower.setCursor(Cursor.CLOSED_HAND);
        });
        draggableTower.setOnMouseReleased(event -> {
            draggableTower.setTranslateX(storeImage.getX() - 100);
            draggableTower.setTranslateY(storeImage.getY() - 100);
            draggableTower.setCursor(Cursor.CLOSED_HAND);
            if (hill1.canBePlacePixelSizeInput(event.getSceneX(), event.getSceneY())) {
                NormalTower normalTower1 = new NormalTower();
                normalTower1.setPosition(4, 7);
                towers.add(normalTower1);

                Circle circle2 = new Circle(normalTower1.getX(), normalTower1.getY(), normalTower1.getRange());
                circle2.setStroke(Color.BLACK);
                circle2.setFill(Color.TRANSPARENT);

                root.getChildren().addAll(normalTower1.getImageView(), circle2);
            }
            if (hill2.canBePlacePixelSizeInput(event.getSceneX(), event.getSceneY())) {
                NormalTower normalTower2 = new NormalTower();
                normalTower2.setPosition(10, 6);
                towers.add(normalTower2);

                Circle circle3 = new Circle(normalTower2.getX(), normalTower2.getY(), normalTower2.getRange());
                circle3.setStroke(Color.BLACK);
                circle3.setFill(Color.TRANSPARENT);
                root.getChildren().addAll(normalTower2.getImageView(), circle3);
            }
        });
//      ???
        Font theFont = Font.font("Helvetica", FontWeight.BOLD, 20);
        gc.setFont(theFont);
        gc.setStroke(Color.AQUA);
        gc.setLineWidth(1);


        new AnimationTimer() {
            int NUMBER_OF_ENEMIES = 10;

            int i = 0;
            // update every one second
            long startTime = System.nanoTime();
            // update every time a tower shoot a bullet
            long startDelayTime = System.nanoTime();

            public void handle(long currentNanoTime) {
                if (GameStage.stage == 1) {
                    try {
                        // spawn new enemy after a fixed time until max number of enemies reached
                        if (currentNanoTime - startTime >= Config.SPAWN_DELAY_TIME) {
                            if (i < NUMBER_OF_ENEMIES) {
                                Enemy enemy = new NormalEnemy();
                                enemies.add(enemy);
                                root.getChildren().add(enemy.getImageView());
                                enemies.forEach(e -> {
                                    try {
                                        e.renderAnimation();
                                    } catch (Exception ex) {
                                        ex.printStackTrace();
                                    }
                                });
                                ++i;
                            }
                            startTime = currentNanoTime;
                        }
                        for (Tower tower : towers) {
                            for (Enemy enemy : enemies) {
                                // if enemy is in the radius of the tower
                                if (tower.canReach(enemy)) {
                                    tower.rotateTo(enemy);
                                    if (currentNanoTime - tower.getStartDelayTime() >= Config.SHOOTING_DELAY_TIME) {
                                        tower.dealDamageTo(enemy);
                                        renderAttackAnimation(tower, enemy);
                                        tower.setStartDelayTime(currentNanoTime);
                                    }
                                    // found the enemy, break and let another tower find enemy
                                    break;
                                }
                            } // end enemy iterate
                        } //  end tower iterate
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                }
            }
        }.start();
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
            }
        });
        root.getChildren().add(bullet.getImageView());
        shootTransition.play();
    }
}
