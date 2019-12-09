package game;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Slider;

import java.net.URL;
import java.util.ResourceBundle;

public class OptionsController implements Initializable {
    @FXML
    private Slider music;

    @FXML
    private Slider sfx;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Config.BACKGROUND_MUSIC.getPlayer().volumeProperty().bind(music.valueProperty().divide(100));
        Config.NORMAL_TOWER_SOUND.getPlayer().volumeProperty().bind(sfx.valueProperty().divide(100));
        Config.MACHINE_GUN_TOWER_SOUND.getPlayer().volumeProperty().bind(sfx.valueProperty().divide(100));
        Config.SNIPER_TOWER_SOUND.getPlayer().volumeProperty().bind(sfx.valueProperty().divide(100));
        Config.EXPLODE_SOUND.getPlayer().volumeProperty().bind(sfx.valueProperty().divide(100));
    }
}
