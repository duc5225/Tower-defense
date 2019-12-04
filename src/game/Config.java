package game;

import java.awt.*;

import javafx.scene.image.Image;

public final class Config {

    // one second in nanosecond
    private static final long SECOND = 1000000000;

    public static final int TILE_SIZE = 64;

    private static final int TILE_HORIZONTAL = 20;

    private static final int TILE_VERTICAL = 15;

    public static final int SCREEN_WIDTH = TILE_SIZE * TILE_HORIZONTAL;

    public static final int SCREEN_HEIGHT = TILE_SIZE * TILE_VERTICAL;

    public static final int ORIGINAL_STAGE = 1;

    // ========================================================================================
    // BULLET
    // ========================================================================================

    public static final Image NORMAL_BULLET_IMG = new Image("file:src/game/resources/assets/PNG/Default size/towerDefense_tile297.png");
    public static final Image SNIPER_BULLET_IMG = new Image("file:src/game/resources/assets/PNG/Default size/towerDefense_tile252.png");
    public static final Image MACHINE_GUN_BULLET_IMG = new Image("file:src/game/resources/assets/PNG/Default size/towerDefense_tile295.png");

    // ========================================================================================
    // TOWER
    // ========================================================================================

    // normal tower
    public static final Image NORMAL_TOWER_IMG = new Image("file:src/game/resources/assets/PNG/Default size/towerDefense_tile249.png");
    public static final int NORMAL_TOWER_PRICE = 10;
    public static final int NORMAL_TOWER_DAMAGE = 200;
    public static final int NORMAL_TOWER_DELAY_TIME = (int) SECOND;
    public static final int NORMAL_TOWER_RANGE = 250;

    // sniper tower
    public static final Image SNIPER_TOWER_IMG = new Image("file:src/game/resources/assets/PNG/Default size/towerDefense_tile206.png");
    public static final int SNIPER_TOWER_PRICE = 20;
    public static final int SNIPER_TOWER_DAMAGE = 500;
    public static final int SNIPER_TOWER_DELAY_TIME = (int) (SECOND * 2.5);
    public static final int SNIPER_TOWER_RANGE = 400;

    // machine gun tower
    public static final Image MACHINE_GUN_TOWER_IMG = new Image("file:src/game/resources/assets/PNG/Default size/towerDefense_tile250.png");
    public static final int MACHINE_GUN_TOWER_PRICE = 40;
    public static final int MACHINE_GUN_TOWER_DAMAGE = 75;
    public static final int MACHINE_GUN_TOWER_DELAY_TIME = (int) (0.2 * SECOND);
    public static final int MACHINE_GUN_TOWER_RANGE = 160;

    // ========================================================================================
    // ENEMY
    // ========================================================================================

    // normal enemy
    public static final Image NORMAL_ENEMY_IMG = new Image("file:src/game/resources/assets/PNG/Default size/towerDefense_tile245.png");
    public static final int NORMAL_ENEMY_SPEED = 100;
    public static final int NORMAL_ENEMY_HEALTH = 300;
    public static final int NORMAL_ENEMY_ARMOR = 10;
    public static final int NORMAL_ENEMY_REWARD = 2;

    // smaller enemy
    public static final Image SMALLER_ENEMY_IMG = new Image("file:src/game/resources/assets/PNG/Default size/towerDefense_tile246.png");
    public static final int SMALLER_ENEMY_SPEED = 180;
    public static final int SMALLER_ENEMY_HEALTH = 150;
    public static final int SMALLER_ENEMY_ARMOR = 5;
    public static final int SMALLER_ENEMY_REWARD = 4;

    // tanker enemy
    public static final Image TANKER_ENEMY_IMG = new Image("file:src/game/resources/assets/PNG/Default size/towerDefense_tile269.png");
    //    public static final Image TANKER_ENEMY_IMG = new Image("file:src/game/resources/assets/PNG/Default size/towerDefense_tile269.png");
    public static final int TANKER_ENEMY_SPEED = 70;
    public static final int TANKER_ENEMY_HEALTH = 500;
    public static final int TANKER_ENEMY_ARMOR = 40;
    public static final int TANKER_ENEMY_REWARD = 20;

    // boss enemy
    public static final Image BOSS_ENEMY_IMG = new Image("file:src/game/resources/assets/kenney_topdowntanksredux/PNG/Default size/tank_bigRed.png");
    public static final int BOSS_ENEMY_SPEED = 60;
    public static final int BOSS_ENEMY_HEALTH = 1000;
    public static final int BOSS_ENEMY_ARMOR = 200;
    public static final int BOSS_ENEMY_REWARD = 50;

    // ========================================================================================
    // HILL
    // ========================================================================================

    public static final Image HILL_IMG = new Image("file:src/game/resources/assets/PNG/Default size/towerDefense_tile181.png");
    public static final int HILLS_LENGTH = 5;
    // delay time of a tower when shooting in nanosecond
    public static final long SHOOTING_DELAY_TIME = SECOND;
    //delay time when spawning new enemy in nanosecond
    public static final long SPAWN_DELAY_TIME = (long) (SECOND * 0.5);
}
