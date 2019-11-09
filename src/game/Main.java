package game;

import game.entity.enemy.NormalEnemy;
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
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.nio.file.Path;

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
//        ImageView enemy = new ImageView(normalEnemy.image);
//        PathTransition pathTransition = new PathTransition(Duration.seconds(currentGameStage.getRoadLength()/normalEnemy.getSpeed()), GameField.createPath(currentGameStage.getStage()), enemy);


        //add to root
        root.getChildren().add(normalEnemy.getImageView());


        Font theFont = Font.font("Helvetica", FontWeight.BOLD, 20);
        gc.setFont(theFont);
        gc.setStroke(Color.AQUA);
        gc.setLineWidth(1);

        new AnimationTimer() {
            public void handle(long currentNanoTime) {
//                pathTransition.play();
                try {
                    normalEnemy.renderAnimation();
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
