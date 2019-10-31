package game.tower;

public abstract class Tower {
    public int Price = 0;
    public int Damage = 0;
    public int ArmorPenetration = 0;
    public int AttackSpeed = 0;

    public Tower() {
    }
    public abstract void Shoot();

    public Tower(int price, int damage, int armorPenetration, int attackSpeed) {
        Price = price;
        Damage = damage;
        ArmorPenetration = armorPenetration;
        AttackSpeed = attackSpeed;
    }
}
