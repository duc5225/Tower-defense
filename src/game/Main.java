package game;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.scene.control.Button;


import java.io.IOException;


public class Main extends Application {
    private FXMLLoader fxmlLoader;
    private AnchorPane mainPane;

    @Override
    public void start(Stage primaryStage) throws Exception {

        //Create window, scene, set the program name
        fxmlLoader = new FXMLLoader(getClass().getResource("main.fxml"));
        mainPane = fxmlLoader.load();
        initMainMenu();
//        new MainMenu(mainPane);
//        mainPane.getChildren().add(root);

//        Button button = new Button("CCC");
//        button.setPrefSize(200,Config.SCREEN_HEIGHT);
//        AnchorPane.setRightAnchor(button,0.0);
//        mainPane.getChildren().add(button);

        Scene theScene = new Scene(mainPane);
        primaryStage.setTitle("Tower defense");
        primaryStage.setScene(theScene);
        primaryStage.setResizable(false);
        primaryStage.sizeToScene();

        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

//    public Parent loadFxml(String path) throws IOException {
//        fxmlLoader = new FXMLLoader(Main.class.getResource(path));
//        return fxmlLoader.load();
//    }

    private void initMainMenu() {
        // get main menu controller
        MainMenuController mainMenuController = fxmlLoader.getController();
        Button playBtn = mainMenuController.getPlayBtn();
        playBtn.setOnAction(event -> {
            try {
                startGame();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    private void startGame() throws IOException {
        Group root = new Group();
        mainPane.getChildren().clear();
        mainPane.getChildren().add(root);
        //Create canvas
        Canvas canvas = new Canvas(Config.SCREEN_WIDTH, Config.SCREEN_HEIGHT);

        //Create graphic context from canvas
//        GraphicsContext gc = canvas.getGraphicsContext2D();

        root.getChildren().addAll(canvas);

        Store store = new Store(mainPane, fxmlLoader.getController());
        new GameStage(root, canvas, store).start();
    }

    private void createStore() throws IOException {
//        Store store = new Store(mainPane, fxmlLoader.getController());
//        store.render();
//        store.handleMouseEvent();
    }
}