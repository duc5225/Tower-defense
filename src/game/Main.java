package game;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.stage.Stage;


public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {

        //Create window, scene, set the program name
        Group root = new Group();
        Scene theScene = new Scene(root, Config.SCREEN_WIDTH, Config.SCREEN_HEIGHT);
        primaryStage.setTitle("Tower defense");
        primaryStage.setScene(theScene);
        primaryStage.setResizable(false);
        primaryStage.sizeToScene();

        //Create canvas
        Canvas canvas = new Canvas(Config.SCREEN_WIDTH, Config.SCREEN_HEIGHT);

        //Create graphic context from canvas
        GraphicsContext gc = canvas.getGraphicsContext2D();

        root.getChildren().addAll(canvas);

        new GameStage(root, gc).start();

        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
