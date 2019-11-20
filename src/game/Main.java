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
        Scene theScene = new Scene(root, Config.SCREEN_WIDTH, Config.SCREEN_HEIGHT);
        primaryStage.setTitle("Tower defense");
        primaryStage.setScene(theScene);
        primaryStage.setResizable(false);
        primaryStage.sizeToScene();

        //create cavas
        Canvas canvas = new Canvas(Config.SCREEN_WIDTH, Config.SCREEN_HEIGHT);

        //create graphic context from canvas
        GraphicsContext gc = canvas.getGraphicsContext2D();

        root.getChildren().addAll(canvas);

        new GameStage(root, gc).start();


        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
