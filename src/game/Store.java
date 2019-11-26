package game;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class Store {
    private AnchorPane mainPane;
    private MainMenuController mainMenuController;

    public Store(AnchorPane mainPane, MainMenuController mainMenuController) {
        this.mainPane = mainPane;
        this.mainMenuController = mainMenuController;
    }

    public void render() throws IOException {
        AnchorPane store = mainMenuController.getStore();
        mainPane.getChildren().add(store);

        Parent root = new FXMLLoader(getClass().getResource("store.fxml")).load();
        store.getChildren().add(root);
    }
}
