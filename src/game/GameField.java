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
        // STORE
        // Normal tower in the store
        ImageView storeImageNormalTower = new ImageView(Config.NORMAL_TOWER_IMG);
        storeImageNormalTower.setX(1000);
        storeImageNormalTower.setY(880);
        // Sniper tower in the store
        ImageView storeImageSniperTower = new ImageView(Config.SNIPER_TOWER_IMG);
        storeImageSniperTower.setX(1100);
        storeImageSniperTower.setY(880);
        // Machine gun tower in the store
        ImageView storeImageMachineGunTower = new ImageView(Config.MACHINE_GUN_TOWER_IMG);
        storeImageMachineGunTower.setX(1200);
        storeImageMachineGunTower.setY(880);
        root.getChildren().addAll(storeImageNormalTower, storeImageSniperTower, storeImageMachineGunTower);

        // DRAGGABLE
        // Normal Tower image to drag
        ImageView draggableNormalTower = new ImageView(Config.NORMAL_TOWER_IMG);
        draggableNormalTower.setX(storeImageNormalTower.getX());
        draggableNormalTower.setY(storeImageNormalTower.getY());
        draggableNormalTower.setCursor(Cursor.CLOSED_HAND);
        // Sniper Tower image to drag
        ImageView draggableSniperTower = new ImageView(Config.SNIPER_TOWER_IMG);
        draggableSniperTower.setX(storeImageSniperTower.getX());
        draggableSniperTower.setY(storeImageSniperTower.getY());
        draggableSniperTower.setCursor(Cursor.CLOSED_HAND);
        // Machine gun tower to drag
        ImageView draggableMachineGunTower = new ImageView(Config.MACHINE_GUN_TOWER_IMG);
        draggableMachineGunTower.setX(storeImageMachineGunTower.getX());
        draggableMachineGunTower.setY(storeImageMachineGunTower.getY());
        draggableMachineGunTower.setCursor(Cursor.CLOSED_HAND);
        root.getChildren().addAll(draggableNormalTower, draggableSniperTower, draggableMachineGunTower);

        // Circle to show tower range when dragged
        Circle circleDragged = new Circle(-1000, -1000, 250);
        circleDragged.setStroke(Color.BLACK);
        circleDragged.setFill(Color.TRANSPARENT);
        // Add to root in order
        root.getChildren().add(circleDragged);

        // Mouse press event
        EventHandler<MouseEvent> mousePressed = event -> {
            Config.orgX = ((ImageView)(event.getSource())).getX();
            Config.orgY = ((ImageView)(event.getSource())).getY();
        };

        // Mouse drag event
        EventHandler<MouseEvent> mouseDragged = event -> {
            ((ImageView) (event.getSource())).setTranslateX(event.getSceneX() - Config.orgX - 32);
            ((ImageView) (event.getSource())).setTranslateY(event.getSceneY() - Config.orgY - 32);
            circleDragged.setCenterX(event.getSceneX());
            circleDragged.setCenterY(event.getSceneY());

            AtomicBoolean hovering = new AtomicBoolean(false);
            // if mouse hovering on a hill then change cursor type
            hills.forEach(hill -> {
                if (hill.isUsable(event.getSceneX(), event.getSceneY())) {
                    ((ImageView) (event.getSource())).setCursor(Cursor.CROSSHAIR);
                    hovering.set(true);
                }
            });
            if (!hovering.get()) {
                ((ImageView) (event.getSource())).setCursor(Cursor.CLOSED_HAND);
            }
        };

        // Mouse release event
        EventHandler<MouseEvent> mouseReleased = event -> {
            ((ImageView) (event.getSource())).setTranslateX(0);
            ((ImageView) (event.getSource())).setTranslateY(0);
            ((ImageView) (event.getSource())).setCursor(Cursor.CLOSED_HAND);
            circleDragged.setCenterX(-1000);
            hills.forEach(hill -> {
                if (hill.isUsable(event.getSceneX(), event.getSceneY())) {
                    Tower tower = new NormalTower();
                    tower.setPosition(hill.getX(), hill.getY());
                    towers.add(tower);
                    hill.setUsed(true);

                    root.getChildren().addAll(tower.getImageView());
                    ((ImageView) (event.getSource())).toFront();
                    tower.getImageView().toFront();
                    towers.forEach(t -> t.getImageView().setOnMouseClicked(e -> {
                        System.out.println("work");
                    }));
                    GameStage.money -= tower.getPrice();
                }
            });
        };

        draggableNormalTower.setOnMousePressed(mousePressed);
        draggableNormalTower.setOnMouseDragged(mouseDragged);
        draggableNormalTower.setOnMouseReleased(mouseReleased);
        draggableSniperTower.setOnMousePressed(mousePressed);
        draggableSniperTower.setOnMouseDragged(mouseDragged);
        draggableSniperTower.setOnMouseReleased(mouseReleased);
        draggableMachineGunTower.setOnMousePressed(mousePressed);
        draggableMachineGunTower.setOnMouseDragged(mouseDragged);
        draggableMachineGunTower.setOnMouseReleased(mouseReleased);

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
