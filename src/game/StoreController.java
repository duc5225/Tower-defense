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
    private int cursor;

    private ImageView iv;

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

    private void setupGestureSource(final ImageView source) {

        source.setOnDragDetected(event -> {
//            System.out.println("drag detected");

            /* allow any transfer mode */
            Dragboard db = source.startDragAndDrop(TransferMode.MOVE);

            /* put a image on dragboard */
            ClipboardContent content = new ClipboardContent();

            Image sourceImage = source.getImage();
            content.putImage(sourceImage);
            db.setContent(content);

            iv = source;


            event.consume();
        });

        source.setOnMouseEntered(e -> {
            source.setCursor(Cursor.HAND);
//                    System.out.println("e...: "+e.getSceneX());
            cursor = (int) e.getSceneX();
        });
    }

    private void setupGestureTarget(final AnchorPane targetBox) {

        targetBox.setOnDragOver(event -> {
//            System.out.println("drag over " + targetBox);

            Dragboard db = event.getDragboard();

            if (db.hasImage()) {
                event.acceptTransferModes(TransferMode.MOVE);
            }

            event.consume();
        });

        targetBox.setOnDragDropped(event -> {
//            System.out.println("drag dropped " + targetBox);

            Dragboard db = event.getDragboard();

            if (db.hasImage()) {

                iv.setImage(db.getImage());

                Point2D localPoint = targetBox.sceneToLocal(new Point2D(event.getSceneX(), event.getSceneY()));

                System.out.println("event.getSceneX : " + event.getSceneX());
                System.out.println("localPoint.getX : " + localPoint.getX());

                targetBox.getChildren().remove(iv);

                iv.setX((int) (localPoint.getX() - 50/*iv.getBoundsInLocal().getWidth() / 2*/));
                iv.setY((int) (localPoint.getY() - 50/*iv.getBoundsInLocal().getHeight() / 2*/));

                System.out.println("iv.getX : " + iv.getX());
                System.out.println("iv.getY : " + iv.getY());
                System.out.println("********");

                targetBox.getChildren().add(iv);
                event.setDropCompleted(true);
            } else {
                event.setDropCompleted(false);
            }

            event.consume();
        });

    }

    @FXML
    private void onMouseEntered() {
        System.out.println("entered");
        normalTower.setCursor(Cursor.CLOSED_HAND);
        machineGunTower.setCursor(Cursor.CLOSED_HAND);
        sniperTower.setCursor(Cursor.CLOSED_HAND);
    }
}
