package game;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{

        primaryStage.setTitle("Tower defense");
        GameStage currentGameStage = new GameStage(0);
        currentGameStage.setGameStage(1);

        Group root = new Group();
        Scene theScene = new Scene( root );
        primaryStage.setScene(theScene);

        Canvas canvas = new Canvas( 800, 600 );
        root.getChildren().add( canvas );

        GraphicsContext gc = canvas.getGraphicsContext2D();

        Font theFont = Font.font( "Helvetica", FontWeight.BOLD, 20 );
        gc.setFont( theFont );
        gc.setStroke( Color.AQUA );
        gc.setLineWidth(1);

        new AnimationTimer()
        {
            public void handle(long currentNanoTime)
            {
                // Clear the canvas
                gc.setFill( new Color(1, 1, 1, 1.0) );
                gc.fillRect(0,0, 800,600);

                gc.setFill( Color.BLUE );

                String pointsText = "Money: " + currentGameStage.getStartMoney();
                gc.fillText( pointsText, 600, 36 );
                gc.strokeText( pointsText, 600, 36 );
            }
        }.start();

        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
