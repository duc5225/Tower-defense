package game;

import javafx.fxml.FXML;
import javafx.geometry.Point2D;
import javafx.scene.Cursor;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.AnchorPane;

public class StoreController {
//    private FXMLLoader fxmlLoader;
//    private AnchorPane storePane;

//    Store(AnchorPane storePane) {
//        this.storePane = storePane;
//    }

    @FXML
    private ImageView normalTower;
    @FXML
    private ImageView machineGunTower;


    @FXML
    private ImageView sniperTower;
    // haven't used yet

    @FXML
    private AnchorPane pane;

    public AnchorPane getPane() {
        return pane;
    }

    public ImageView getNormalTower() {
        return normalTower;
    }

    public ImageView getMachineGunTower() {
        return machineGunTower;
    }

    public ImageView getSniperTower() {
        return sniperTower;
    }

    @FXML
    private void onMouseEntered() {
        System.out.println("entered");
        normalTower.setCursor(Cursor.CLOSED_HAND);
        machineGunTower.setCursor(Cursor.CLOSED_HAND);
        sniperTower.setCursor(Cursor.CLOSED_HAND);
    }
}
