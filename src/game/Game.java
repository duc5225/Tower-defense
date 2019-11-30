//package game;
//
//import javafx.scene.Group;
//import javafx.scene.canvas.Canvas;
//import javafx.scene.canvas.GraphicsContext;
//import javafx.scene.layout.AnchorPane;
//
//// DELETE IF NOT NECESSARY
//public class Game {
//    private AnchorPane mainPane;
//    private Group root;
//
//    Game(AnchorPane pane) {
//        this.mainPane = pane;
//    }
//
//    public void start() {
//        root = new Group();
//        mainPane.getChildren().clear();
//        mainPane.getChildren().add(root);
//
//        //Create canvas
//        Canvas canvas = new Canvas(Config.SCREEN_WIDTH, Config.SCREEN_HEIGHT);
//
//        //Create graphic context from canvas
//        GraphicsContext gc = canvas.getGraphicsContext2D();
//
//        root.getChildren().addAll(canvas);
//        new GameStage(root, canvas).start();
//
//    }
//}
