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
import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.scene.Cursor;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
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
    private Canvas canvas;
    private GraphicsContext gc;
    private Store store;

    private Score score = new Score(0);
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

    public static Path createPath() {
        Path path = new Path();
        if (GameStage.stage == 1) {
            MoveTo spawn = new MoveTo(2.5 * Config.TILE_SIZE, 15 * Config.TILE_SIZE);
            LineTo line1 = new LineTo(2.5 * Config.TILE_SIZE, 8.5 * Config.TILE_SIZE);
            LineTo line2 = new LineTo(6.5 * Config.TILE_SIZE, 8.5 * Config.TILE_SIZE);
            LineTo line3 = new LineTo(6.5 * Config.TILE_SIZE, 3.5 * Config.TILE_SIZE);
            LineTo line4 = new LineTo(11.5 * Config.TILE_SIZE, 3.5 * Config.TILE_SIZE);
            LineTo line5 = new LineTo(11.5 * Config.TILE_SIZE, 11.5 * Config.TILE_SIZE);
            LineTo line6 = new LineTo(22.1 * Config.TILE_SIZE, 11.5 * Config.TILE_SIZE);

            path.getElements().addAll(spawn, line1, line2, line3, line4, line5, line6);
        }
        return path;
    }

    public void play() {
        store.handleMouseEvent(root, hills, towers);

        Font theFont = Font.font("Helvetica", FontWeight.BOLD, 50);
        gc.setFont(theFont);
        gc.setFill(Color.BLACK);
        gc.setLineWidth(1);
        gc.strokeText("Score: " + score.value, score.X, score.Y);


        new AnimationTimer() {
            int NUMBER_OF_ENEMIES = 10;

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
        }.start();
    }

    private void renderAttackAnimation(Tower tower, Enemy enemy) {
        Bullet bullet = new Bullet();
        Path path = new Path(new MoveTo(tower.getX(), tower.getY()), new LineTo(enemy.getX(), enemy.getY()));

        PathTransition shootTransition = new PathTransition(Duration.millis(100), path, bullet.getImageView());
        shootTransition.setCycleCount(1);
        shootTransition.setInterpolator(Interpolator.LINEAR);

        shootTransition.setOnFinished(event -> {
            bullet.getImageView().setVisible(false);
            root.getChildren().remove(bullet.getImageView());
            if (enemy.isDead() && enemies.contains(enemy)) {
                System.out.println("die bitch");
                enemy.getImageView().setVisible(false);
                enemies.remove(enemy);
                //update score
                gc.fillRect(0, 0, Config.SCREEN_WIDTH, Config.SCREEN_HEIGHT);
                renderMap();
                score.value++;
                gc.strokeText("Score: " + score.value, score.X, score.Y);
                root.getChildren().remove(enemy.getImageView());
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
        hills.add(new Hill(4, 7));
        hills.add(new Hill(5, 12));
        hills.add(new Hill(14, 14));
        hills.add(new Hill(17, 10));
//        setupMouseDragging();
    }

    private void setupMouseDragging() {
        setupGestureTarget(canvas);
//        hills.forEach(hill -> {
//            setupGestureTarget(hill.getImageView());
//            System.out.println("hill.getImageView() getX getY " + hill.getImageView().getX()+" "+hill.getImageView().getX());
//        });
    }

    private void setupGestureTarget(final Node target) {
//        Circle circle;
        target.setOnDragOver(event -> {
            System.out.println(event.getSceneX() + " " + event.getSceneY());
//            target.setOnMouseMoved(event1 -> {
//                root.getChildren().remove(circle);
//            });
//            hills.forEach(hill -> {
//                if (hill.isUsable(event.getSceneX(), event.getSceneY())) {
//                    Circle circle = new Circle(hill.getCenterX(), hill.getCenterY(), Config.NORMAL_TOWER_RANGE);
//                    circle.setStroke(Color.BLACK);
//                    circle.setFill(Color.TRANSPARENT);
//                    root.getChildren().add(circle);
//                } else {
////                    root.getChildren().remove(circle);
//                }
//            });
//            System.out.println("drag over " + target);
//            gc.setStroke(Color.BLACK);
//            gc.strokeOval(event.getSceneX()-Config.NORMAL_TOWER_RANGE,event.getSceneY()-Config.NORMAL_TOWER_RANGE, Config.NORMAL_TOWER_RANGE * 2, Config.NORMAL_TOWER_RANGE * 2);
//            renderMap();
            Dragboard db = event.getDragboard();

            if (db.hasImage()) {
                event.acceptTransferModes(TransferMode.ANY);
            }

            event.consume();
        });

        target.setOnDragDropped(event -> {
//            System.out.println("drag dropped " + target);

            hills.forEach(hill -> {
                if (hill.isUsable(event.getSceneX(), event.getSceneY())) {
                    Tower tower = new NormalTower();
                    tower.setPosition(hill.getX(), hill.getY());
                    towers.add(tower);
                    hill.setUsed(true);

//                    Circle circle = new Circle(tower.getX(), tower.getY(), tower.getRange());
//                    circle.setStroke(Color.BLACK);
//                    circle.setFill(Color.TRANSPARENT);
                    root.getChildren().addAll(/*circle,*/ tower.getImageView());
                }
            });

            Dragboard db = event.getDragboard();

            if (db.hasImage()) {

                Point2D localPoint = target.sceneToLocal(new Point2D(event.getSceneX(), event.getSceneY()));
                event.setDropCompleted(true);
            } else {
                event.setDropCompleted(false);
            }
            event.consume();
        });
    }
}
