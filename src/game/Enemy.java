package game;

public class Enemy {
    public int HP = 0;
    public int armor = 0;
    public int speed = 0;
    public int reward = 0;

    public Enemy() {
    }

    public Enemy(int HP, int armor, int speed, int reward) {
        this.HP = HP;
        this.armor = armor;
        this.speed = speed;
        this.reward = reward;
    }
}
