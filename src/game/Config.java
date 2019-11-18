package game;

import java.awt.*;

import javafx.scene.image.Image;

public final class Config {
    public static final int TILE_SIZE = 64;

    private static final int TILE_HORIZONTAL = 20;

    private static final int TILE_VERTICAL = 15;

    public static final int SCREEN_WIDTH = TILE_SIZE * TILE_HORIZONTAL;

    public static final int SCREEN_HEIGHT = TILE_SIZE * TILE_VERTICAL;

    public static final int ORIGINAL_STAGE = 1;

    public static final Image BULLET_IMG = new Image("file:src/game/resources/assets/PNG/Default size/towerDefense_tile272.png");

    public static final Image NORMAL_TOWER_IMG = new Image("file:src/game/resources/assets/PNG/Default size/towerDefense_tile249.png");

    public static final Image NORMAL_ENEMY_IMG = new Image("file:src/game/resources/assets/PNG/Default size/towerDefense_tile245.png");
    // delay time of a tower when shooting in nanosecond
    public static final long SHOOTING_DELAY_TIME = 1000000000;
    //delay time when spawning new enemy in nanosecond
    public static final long SPAWN_DELAY_TIME = 1000000000;
}
