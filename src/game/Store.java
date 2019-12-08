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
import javafx.scene.ImageCursor;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

import java.io.IOException;
import java.util.ArrayList;
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
        store.setVisible(true);
        store.toFront();
//        mainPane.getChildren().add(store);
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

    public void disable() {
        mainMenuController.getStore().setVisible(false);
        mainMenuController.getStore().getChildren().clear();
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
        tempCircle.setStroke(Color.AQUA);
        tempCircle.setFill(Color.rgb(0, 0, 0, 0.07));
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
                    if (normalTower.equals(source)) {
                        if (GameStage.money < Config.NORMAL_TOWER_PRICE)
                            source.setCursor(new ImageCursor(Config.NOT_ENOUGH_MONEY));
                    } else if (machineGunTower.equals(source)) {
                        if (GameStage.money < Config.MACHINE_GUN_TOWER_PRICE)
                            source.setCursor(new ImageCursor(Config.NOT_ENOUGH_MONEY));
                    } else if (sniperTower.equals(source)) {
                        if (GameStage.money < Config.SNIPER_TOWER_PRICE)
                            source.setCursor(new ImageCursor(Config.NOT_ENOUGH_MONEY));
                    }
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
                    tower.hill = hill;

                    if (GameStage.money >= tower.getPrice()) {
                        tower.setPosition(hill.getX(), hill.getY());
                        towers.add(tower);
                        hill.setUsed(true);

                        root.getChildren().addAll(tower.getImageView());
                        tower.getImageView().toFront();

                        towers.forEach(t -> t.getImageView().setOnMousePressed(e -> {
                            // Check if another tower is being chosen
                            //noinspection PointlessBooleanExpression
                            if (!Config.isOtherTowerChosen) {
                                // Set current state, a tower is being chosen
                                Config.isOtherTowerChosen = true;

                                // Create tower range circle
                                Circle circle = new Circle(t.getX(), t.getY(), t.getRange());
                                circle.setStroke(Color.AQUA);
                                circle.setFill(Color.rgb(0, 0, 0, 0.07));

                                // Create 3 button when click
                                Button upgrade = new Button("", Config.UPGRADE_BUTTON_IMAGE_VIEW);
                                upgrade.setStyle(Config.BUTTON_STYLE);
                                upgrade.setTranslateX(t.getX() - 75);
                                upgrade.setTranslateY(t.getY() - 75);
                                Button sell = new Button("", Config.SELL_BUTTON_IMAGE_VIEW);
                                sell.setStyle(Config.BUTTON_STYLE);
                                sell.setTranslateX(t.getX() + 15);
                                sell.setTranslateY(t.getY() - 75);
                                Button cancel = new Button("", Config.CANCEL_BUTTON_IMAGE_VIEW);
                                cancel.setStyle(Config.BUTTON_STYLE);
                                cancel.setTranslateX(t.getX() - 28);
                                cancel.setTranslateY(t.getY() + 30);

                                // Text show how much money needed for upgrade
                                Text upgradeMoney = new Text(40, 40, "-" + t.getPrice());
                                upgradeMoney.setFont(Font.font("Helvetica", FontWeight.BOLD, 17));
                                upgradeMoney.setFill(Color.YELLOW);
                                upgradeMoney.setStroke(Color.BLACK);

                                // Text show how much money you get when selling
                                Text sellMoney = new Text(40, 40, "+" + t.getPrice() / 2);
                                sellMoney.setFont(Font.font("Helvetica", FontWeight.BOLD, 17));
                                sellMoney.setFill(Color.YELLOW);
                                sellMoney.setStroke(Color.BLACK);

                                // Text show how much damage yours tower has
                                Text towerDamage = new Text(35, 0, "Damage: " + t.getDamage());
                                towerDamage.setFont(Font.font("Helvetica", FontWeight.BOLD, 14));
                                towerDamage.setFill(Color.RED);
                                towerDamage.setStroke(Color.BLACK);

                                // Set money text beside the button, damage text on top
                                upgradeMoney.setTranslateX(upgrade.getTranslateX());
                                upgradeMoney.setTranslateY(upgrade.getTranslateY());
                                towerDamage.setTranslateX(upgrade.getTranslateX());
                                towerDamage.setTranslateY(upgrade.getTranslateY());
                                sellMoney.setTranslateX(sell.getTranslateX());
                                sellMoney.setTranslateY(sell.getTranslateY());

                                root.getChildren().addAll(circle, upgrade, sell, cancel, upgradeMoney, sellMoney,towerDamage);

                                if (t.level == 4) upgradeMoney.setText("Max");
                                //When user click on upgrade button
                                upgrade.setOnMouseClicked(eventUpgrade -> {
                                    if (GameStage.money >= t.getPrice() && t.level <4) {
                                        // Upgrade damage and range
                                        t.setDamage(t.getDamage() + 20);
                                        t.setRange(t.getRange() + 20);

                                        // Increase money each time tower level up
                                        GameStage.money -= t.getPrice();
                                        t.setPrice(t.getPrice() + 10);
                                        upgradeMoney.setText("-" + t.getPrice());

                                        // Update information for user
                                        if (t.level == 3) upgradeMoney.setText("Max");
                                        sellMoney.setText("+" + t.getPrice() / 2);
                                        towerDamage.setText("Damage: " + t.getDamage());
                                        circle.setRadius(t.getRange());

                                        // Add star for each upgrade
                                        ImageView star = new ImageView("file:src/game/resources/assets/star.png");
                                        t.stars.add(star);
                                        star.setX(t.getX() - 30 + t.level * 15);
                                        star.setY(t.getY() + 15);
                                        root.getChildren().add(star);

                                        // Make sure star don't stay in front of cancel button or tower
                                        cancel.toFront();
                                        t.getImageView().toFront();

                                        t.level++;
                                    }
                                });

                                //When user click on sell button
                                sell.setOnMouseClicked(eventSell -> {
                                    root.getChildren().removeAll(t.getImageView(), circle, upgrade, sell, cancel);
                                    root.getChildren().removeAll(upgradeMoney, sellMoney, towerDamage);
                                    towers.remove(t);
                                    GameStage.money += t.getPrice() / 2;
                                    Config.isOtherTowerChosen = false;
                                    t.hill.setUsed(false);
                                    t.stars.forEach(star -> {root.getChildren().remove(star);});
                                });

                                // When user click on cancel button
                                cancel.setOnMouseClicked(eventCancel -> {
                                    root.getChildren().removeAll(circle, upgrade, sell, cancel, upgradeMoney, sellMoney, towerDamage);
                                    Config.isOtherTowerChosen = false;
                                });
                                towers.forEach(tower1 -> tower1.getImageView().toFront());
                            }
                        }));
                        GameStage.money -= tower.getPrice();
                    } else System.out.println("Not enough money");
                }
            });
        };
        source.setOnMouseDragged(mouseDragged);
        source.setOnMouseReleased(mouseReleased);
    }

}
