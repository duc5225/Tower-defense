package game;

import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.AnchorPane;
import javafx.scene.control.Button;

import java.awt.*;

public class MainMenu {
    @FXML
    private Button playBtn;

    public Button getPlayBtn() {
        return playBtn;
    }

    public void playClicked() {
        System.out.println("clicked");
    }
}
