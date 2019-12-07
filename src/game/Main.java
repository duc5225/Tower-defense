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
        Button exitBtn = mainMenuController.getExitBtn();
        Config.rabi.play();
        Config.rabi.repeat();

        playBtn.setOnAction(event -> {
            try {
                Config.rabi.stop();
                Config.yeahBoy.play();
                Config.yeahBoy.repeat();
                startGame();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        exitBtn.setOnAction(event -> closeGame());
    }

    private void startGame() throws IOException {
        Group root = new Group();
//        mainPane.getChildren().clear();
        mainPane.getChildren().add(root);
        //Create canvas
        Canvas canvas = new Canvas(Config.SCREEN_WIDTH, Config.SCREEN_HEIGHT);

        //Create graphic context from canvas
//        GraphicsContext gc = canvas.getGraphicsContext2D();

        root.getChildren().addAll(canvas);

        Store store = new Store(mainPane, fxmlLoader.getController());
        new GameStage(root, canvas, store).start();
    }

    private void closeGame() {
        primaryStage.close();
    }
}