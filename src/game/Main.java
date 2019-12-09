package game;

import game.store.Store;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.scene.control.Button;


import java.io.IOException;


public class Main extends Application {
    private FXMLLoader fxmlLoader;
    private AnchorPane mainPane;
    private Stage primaryStage;

    @Override
    public void start(Stage primaryStage) throws Exception {
        this.primaryStage = primaryStage;
        //Create window, scene, set the program name
        fxmlLoader = new FXMLLoader(getClass().getResource("main.fxml"));
        mainPane = fxmlLoader.load();

        initMainMenu();

        primaryStage.setOnCloseRequest(event -> {
            event.consume();
            closeGame();
        });

        Scene theScene = new Scene(mainPane);
        primaryStage.getIcons().add(new Image("file:src/game/resources/assets/icon/tower.png"));
        primaryStage.setTitle("Tower defense");
        primaryStage.setScene(theScene);
        primaryStage.setResizable(false);
        primaryStage.sizeToScene();
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

    private void initMainMenu() {
        // get main menu controller
        MainMenuController mainMenuController = fxmlLoader.getController();
        Button playBtn = mainMenuController.getPlayBtn();
        Button exitBtn = mainMenuController.getExitBtn();
        Config.BACKGROUND_MUSIC.play();
        Config.BACKGROUND_MUSIC.repeat();

        playBtn.setOnAction(event -> {
            try {
                Config.BACKGROUND_MUSIC.stop();
                startGame();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        exitBtn.setOnAction(event -> closeGame());
    }

    private void startGame() throws IOException {
        Group root = new Group();
        mainPane.getChildren().add(root);
        //Create canvas
        Canvas canvas = new Canvas(Config.SCREEN_WIDTH, Config.SCREEN_HEIGHT);

        root.getChildren().addAll(canvas);

        Store store = new Store(mainPane, fxmlLoader.getController());
        new GameStage(root, canvas, store).start();
    }

    private void closeGame() {
        primaryStage.close();
    }
}