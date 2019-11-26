package game;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class StoreController {
    private FXMLLoader fxmlLoader;
    private AnchorPane storePane;

//    Store(AnchorPane storePane) {
//        this.storePane = storePane;
//    }

    public AnchorPane createStore() throws IOException {
        return new FXMLLoader(getClass().getResource("store.fxml")).load();
//        AnchorPane
    }
}
