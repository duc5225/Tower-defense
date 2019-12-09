package game;

import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MainMenuController {
    @FXML
    private Button playBtn;

    @FXML
    private Button optionBtn;

    @FXML
    private Button creditBtn;

    @FXML
    private Button exitBtn;

    @FXML
    private AnchorPane store;

    @FXML
    private Pane helper;

    public AnchorPane getStore() {
        return store;
    }

    public Button getPlayBtn() {
        return playBtn;
    }

    public Pane getHelper() {
        return helper;
    }

    public Button getExitBtn() {
        return exitBtn;
    }

    public void handleCredit() throws IOException {
        helper.getChildren().clear();
        helper.getChildren().add(new FXMLLoader(getClass().getResource("credit.fxml")).load());
    }

    public void handleOptions() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("options.fxml"));
        helper.getChildren().clear();
        helper.getChildren().add(loader.load());
    }
}
