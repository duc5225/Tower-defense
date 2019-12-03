package game;

import game.entity.Hill;
import game.entity.tower.MachineGunTower;
import game.entity.tower.NormalTower;
import game.entity.tower.SniperTower;
import game.entity.tower.Tower;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Cursor;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

public class Store {
    private AnchorPane mainPane;
    private MainMenuController mainMenuController;
    private final FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("store.fxml"));

    private ImageView normalTower;
    private ImageView machineGunTower;
    private ImageView sniperTower;

    private HashMap<ImageView, Integer> radius;

    public Store(AnchorPane mainPane, MainMenuController mainMenuController) {
        this.mainPane = mainPane;
        this.mainMenuController = mainMenuController;
        this.radius = new HashMap<>();
    }

    public void init() throws IOException {
        AnchorPane store = mainMenuController.getStore();
        mainPane.getChildren().add(store);
        // render
        Parent root = fxmlLoader.load();
        store.getChildren().add(root);

        StoreController storeController = fxmlLoader.getController();
        normalTower = storeController.getNormalTower();
        machineGunTower = storeController.getMachineGunTower();
        sniperTower = storeController.getSniperTower();
        // add to hash map
        radius.put(normalTower, Config.NORMAL_TOWER_RANGE);
        radius.put(machineGunTower, Config.MACHINE_GUN_TOWER_RANGE);
        radius.put(sniperTower, Config.SNIPER_TOWER_RANGE);
    }

    public void handleMouseEvent(Group root, List<Hill> hills, List<Tower> towers) {


        EventHandler<MouseEvent> mousePressed = event -> {
            if (event.getSource() == normalTower) {
                // do something
            } else if (event.getSource() == machineGunTower) {
                // do something
            }
            handleDragDropEvent((ImageView) event.getSource(), root, hills, towers);
        };

        normalTower.setOnMousePressed(mousePressed);
        machineGunTower.setOnMousePressed(mousePressed);
        sniperTower.setOnMousePressed(mousePressed);

    }

    private void handleDragDropEvent(ImageView source, Group root, List<Hill> hills, List<Tower> towers) {
        Circle tempCircle = new Circle(-1000, -1000, radius.get(source));
        tempCircle.setStroke(Color.BLACK);
        tempCircle.setFill(Color.TRANSPARENT);
        root.getChildren().add(tempCircle);

        EventHandler<MouseEvent> mouseDragged = event -> {
//            ImageView source = ((ImageView) (event.getSource()));
            source.setTranslateX(event.getSceneX() - 32 - (mainPane.getWidth() - mainMenuController.getStore().getWidth()));
            source.setTranslateY(event.getSceneY() - 32 - source.getLayoutY());

            tempCircle.setCenterX(event.getSceneX());
            tempCircle.setCenterY(event.getSceneY());

            AtomicBoolean hovering = new AtomicBoolean(false);
            // if mouse hovering on a hill then change cursor type
            hills.forEach(hill -> {
                if (hill.isUsable(event.getSceneX(), event.getSceneY())) {
                    source.setCursor(Cursor.CROSSHAIR);
                    hovering.set(true);
                }
            });
            if (!hovering.get()) {
                source.setCursor(Cursor.CLOSED_HAND);
            }
        };
        // FIX HERE WHEN ADDED NEW TOWER
        EventHandler<MouseEvent> mouseReleased = event -> {
//            resetPosition();
            source.setTranslateX(0);
            source.setTranslateY(0);

            root.getChildren().remove(tempCircle);
//            normalTower.setCursor(Cursor.CLOSED_HAND);
            hills.forEach(hill -> {
                if (hill.isUsable(event.getSceneX(), event.getSceneY())) {
                    Tower tower;
//                    =======FIX HERE=======
                    if (normalTower.equals(source)) {
                        tower = new NormalTower();
                    } else if (machineGunTower.equals(source)) {
                        tower = new MachineGunTower();
                    } else /*if (sniperTower.equals(source))*/ {
                        tower = new SniperTower();
                    }

                    if (GameStage.money >= tower.getPrice()) {
                        System.out.println("placed");
                        tower.setPosition(hill.getX(), hill.getY());
                        towers.add(tower);
                        hill.setUsed(true);

                        Circle circle = new Circle(tower.getX(), tower.getY(), tower.getRange());
                        circle.setStroke(Color.BLACK);
                        circle.setFill(Color.TRANSPARENT);

                        root.getChildren().addAll(tower.getImageView(), circle);
                        tower.getImageView().toFront();
                        towers.forEach(t -> t.getImageView().setOnMouseClicked(e -> {
                            System.out.println("work");
                        }));
                        GameStage.money -= tower.getPrice();
                    } else {
                        System.out.println("Not enough money");
                    }

                }
            });
        };

        source.setOnMouseDragged(mouseDragged);
        source.setOnMouseReleased(mouseReleased);
    }

}
