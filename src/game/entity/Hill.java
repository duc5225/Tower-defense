package game.entity;

import game.Config;

public class Hill extends GameEntity {
    private double x;
    private double y;

    public Hill() {
    }

    public Hill(int x, int y) {
        this.x = x-1;
        this.y = y-1;
    }

    public double getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setPosition(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public boolean canBePlacePixelSizeInput(double towerX, double towerY) {
        double x = towerX / Config.TILE_SIZE;
        double y = towerY / Config.TILE_SIZE;
        if (this.x <= x && this.x >= (x - 1) && this.y <= y && this.y >= (y - 1)) return true;
        return false;
    }

    public boolean canBePlaceTileSizeInput(int towerX, int towerY) {
        if (this.x <= towerX && this.x >= (towerX - 1) && this.y <= towerY && this.y >= (towerY - 1)) return true;
        return false;
    }
}
