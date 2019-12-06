package game;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.AnchorPane;
import javafx.scene.control.Button;

import java.awt.*;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MainMenuController implements Initializable {
    @FXML
    private Button playBtn;

    @FXML
    private Button exitBtn;

    @FXML
    private AnchorPane store;

    public AnchorPane getStore() {
        return store;
    }

    public Button getPlayBtn() {
        return playBtn;
    }

    public Button getExitBtn() {
        return exitBtn;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
//        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("store.fxml"));
//        try {
//            Parent root = fxmlLoader.load();
//            store.getChildren().add(root);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }

//    public static void addStore() {
//        FXMLLoader fxmlLoader = new FXMLLoader(MainMenu.class.getResource("store.fxml"));
//        try {
//            Parent root = fxmlLoader.load();
//            store.getChildren().add(root);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
}
