package game;

import game.entity.enemy.Enemy;
import game.entity.enemy.NormalEnemy;
import game.entity.tower.NormalTower;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {

        Group root = new Group();
        Button button = new Button("Play");
        button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                GameStage.stage = 1;
            }
        });
        Scene theScene = new Scene(root, Config.SCREEN_WIDTH, Config.SCREEN_HEIGHT);
        primaryStage.setTitle("Tower defense");
        primaryStage.setScene(theScene);
        primaryStage.setResizable(false);
        primaryStage.sizeToScene();

        //create cavas
        Canvas canvas = new Canvas(Config.SCREEN_WIDTH, Config.SCREEN_HEIGHT);
        root.getChildren().add(canvas);

        //create graphic context from canvas
        GraphicsContext gc = canvas.getGraphicsContext2D();

        //print map
        GameStage.renderGameField(gc);

        NormalEnemy normalEnemy = new NormalEnemy();
        NormalTower normalTower = new NormalTower();
        normalTower.setPosition(10, 6);

        // THIS IS A TEST, SHOULD BE REMOVED LATER IF NECESSARY
        Circle circle = new Circle(normalTower.getX(), normalTower.getY(), normalTower.getRange());
        circle.setStroke(Color.BLACK);
        circle.setFill(Color.TRANSPARENT);

        //add to root (including circle for testing)
        root.getChildren().addAll(/*normalEnemy.getImageView(),*/ normalTower.getImageView(), circle, button);


        Font theFont = Font.font("Helvetica", FontWeight.BOLD, 20);
        gc.setFont(theFont);
        gc.setStroke(Color.AQUA);
        gc.setLineWidth(1);

        List<Enemy> enemies = new ArrayList<Enemy>();
        int num = 10;

        new AnimationTimer() {
            int i = 0;
            // update every one second
            long startTime = System.nanoTime();
            //            update every time a tower shoot a bullet
            long startDelayTime = System.nanoTime();

            public void handle(long currentNanoTime) {
                if (GameStage.stage == 1) {
                    try {
                        if (currentNanoTime - startTime >= Config.SPAWN_DELAY_TIME) {
                            if (i < num) {
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
                        boolean found = false;
                        for (Enemy enemy : enemies) {
                            if (found) break;
                            if (!found && normalTower.canReach(enemy)) {
                                normalTower.rotateTo(enemy);
                                if (currentNanoTime - startDelayTime >= Config.SHOOTING_DELAY_TIME) {
                                    normalTower.attack(enemy, root);
                                    startDelayTime = currentNanoTime;
                                    found = true;
                                }
                            }
                        }
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                }
            }
        }.start();
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
