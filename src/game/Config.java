package game;

import java.awt.*;

import javafx.scene.image.Image;

public final class Config {
    public static double orgX;
    public static double orgY;

    public static final int TILE_SIZE = 64;

    private static final int TILE_HORIZONTAL = 20;

    private static final int TILE_VERTICAL = 15;

    public static final int SCREEN_WIDTH = TILE_SIZE * TILE_HORIZONTAL;

    public static final int SCREEN_HEIGHT = TILE_SIZE * TILE_VERTICAL;

    public static final int ORIGINAL_STAGE = 1;

    public static int currentTower = 0;

    public static final Image BULLET_IMG = new Image("file:src/game/resources/assets/PNG/Default size/towerDefense_tile272.png");
    // TOWER

    // normal tower
    public static final Image NORMAL_TOWER_IMG = new Image("file:src/game/resources/assets/PNG/Default size/towerDefense_tile249.png");

    // sniper tower
    public static final Image SNIPER_TOWER_IMG = new Image("file:src/game/resources/assets/PNG/Default size/towerDefense_tile203.png");

    // machine gun tower
    public static final Image MACHINE_GUN_TOWER_IMG = new Image("file:src/game/resources/assets/PNG/Default size/towerDefense_tile250.png");

    // ENEMY

    // normal enemy
    public static final Image NORMAL_ENEMY_IMG = new Image("file:src/game/resources/assets/PNG/Default size/towerDefense_tile245.png");
    public static final int NORMAL_ENEMY_REWARD = 10;

    // HILL
    public static final Image HILL_IMG = new Image("file:src/game/resources/assets/PNG/Default size/towerDefense_tile181.png");
    public static final int HILLS_LENGTH = 5;

    // delay time of a tower when shooting in nanosecond
    public static final int SECOND = 1000000000;
    //delay time when spawning new enemy in nanosecond
    public static final long SPAWN_DELAY_TIME = 500000000;
}
