package game.entity.tower;

import game.Config;
import game.entity.GameEntity;
import javafx.scene.image.ImageView;

public abstract class Tower extends GameEntity {
    public int Price = 0;
    public int Damage = 0;
    public int ArmorPenetration = 0;
    public int AttackSpeed = 0;
    public int Range = 0;
    protected ImageView imageView;

    public Tower() {
    }

    public Tower(int price, int damage, int armorPenetration, int attackSpeed, int range) {
        Price = price;
        Damage = damage;
        ArmorPenetration = armorPenetration;
        AttackSpeed = attackSpeed;
        Range = range;
    }

    public int getPrice() {
        return Price;
    }

    public void setPrice(int price) {
        Price = price;
    }

    public int getDamage() {
        return Damage;
    }

    public void setDamage(int damage) {
        Damage = damage;
    }

    public int getArmorPenetration() {
        return ArmorPenetration;
    }

    public void setArmorPenetration(int armorPenetration) {
        ArmorPenetration = armorPenetration;
    }

    public int getAttackSpeed() {
        return AttackSpeed;
    }

    public void setAttackSpeed(int attackSpeed) {
        AttackSpeed = attackSpeed;
    }

    public int getRange() {
        return Range;
    }

    public void setRange(int range) {
        Range = range;
    }

    public ImageView getImageView() {
        return imageView;
    }

    public void setImageView(ImageView imageView) {
        this.imageView = imageView;
    }

    public void setPosition(int width, int height) {
        imageView.setX(width * Config.TILE_SIZE);
        imageView.setY(height * Config.TILE_SIZE);
    }
}
