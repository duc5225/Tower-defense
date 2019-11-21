package game;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.stage.Stage;

import javax.swing.text.html.ImageView;


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
