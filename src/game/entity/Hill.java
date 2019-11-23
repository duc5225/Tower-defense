package game.entity;

import game.Config;
import javafx.scene.image.ImageView;

public class Hill extends GameEntity {
    // x,y coordinate relative to 20x15 tile screen
    private int x;
    private int y;

    private boolean used;

    public Hill(int x, int y) {
        this.x = x;
        this.y = y;
        this.used = false;
        this.image = Config.HILL_IMG;
        this.imageView = new ImageView(image);
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public boolean isUsed() {
        return used;
    }

    public void setUsed(boolean used) {
        this.used = used;
    }

    // x coordinate for printing purpose
    public long getSceneX() {
        return (long) (x - 1) * Config.TILE_SIZE;
    }

    // y coordinate for printing purpose
    public long getSceneY() {
        return (long) (y - 1) * Config.TILE_SIZE;
    }

    public boolean isUsable(double towerX, double towerY) {
        double x = towerX / Config.TILE_SIZE;
        double y = towerY / Config.TILE_SIZE;
        return this.x - 1 <= x && this.x - 1 >= (x - 1) && this.y - 1 <= y && this.y - 1 >= (y - 1) && !used;
    }

    public boolean canBePlaceTileSizeInput(int towerX, int towerY) {
        return this.x <= towerX && this.x >= (towerX - 1) && this.y <= towerY && this.y >= (towerY - 1);
    }
}
