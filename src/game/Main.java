package game;

import game.entity.enemy.Enemy;
import game.entity.enemy.NormalEnemy;
import game.entity.tower.NormalTower;
import javafx.animation.AnimationTimer;
import javafx.animation.PathTransition;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {


        Group root = new Group();
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

        //set game stage
        GameStage currentGameStage = new GameStage(Config.ORIGINAL_STAGE);

        //print map
        currentGameStage.renderGameField(gc);

        NormalEnemy normalEnemy = new NormalEnemy();
        NormalTower normalTower = new NormalTower();
        normalTower.setPosition(10, 6);

        // THIS IS A TEST, SHOULD BE REMOVED LATER IF NECESSARY
        Circle circle = new Circle(normalTower.getX(), normalTower.getY(), normalTower.getRange());
        circle.setStroke(Color.BLACK);
        circle.setFill(Color.TRANSPARENT);

        //add to root (including circle for testing)
        root.getChildren().addAll(normalEnemy.getImageView(), normalTower.getImageView(), circle);


        Font theFont = Font.font("Helvetica", FontWeight.BOLD, 20);
        gc.setFont(theFont);
        gc.setStroke(Color.AQUA);
        gc.setLineWidth(1);


        new AnimationTimer() {
            public void handle(long currentNanoTime) {
                try {
                    normalEnemy.renderAnimation();
                    if (normalTower.canReach(normalEnemy)) {
                        System.out.println("attacking");
                        normalTower.rotateTo(normalEnemy);
                    }
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            }
        }.start();
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
