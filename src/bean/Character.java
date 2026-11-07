package bean;

public class Character {
    //    角色属性
    private String name;
    private int HP;
    private int maxHP;
    private int attack;
    private int defense;

    public Character() {
    }

    public Character(String name, int maxHP, int attack, int defense) {
        this.name = name;
        this.HP = maxHP;
        this.maxHP = maxHP;
        this.attack = attack;
        this.defense = defense;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getHP() {
        return HP;
    }

    public void setHP(int HP) {
        this.HP = HP;
    }

    public int getMaxHP() {
        return maxHP;
    }

    public void setMaxHP(int maxHP) {
        this.maxHP = maxHP;
    }

    public int getAttack() {
        return attack;
    }

    public void setAttack(int attack) {
        this.attack = attack;
    }

    public int getDefense() {
        return defense;
    }

    public void setDefense(int defense) {
        this.defense = defense;
    }

    //    1.是否存活
    public boolean isAlive() {
        return HP > 0;
    }

    //    2.受到治疗
    public void heal(int amount) {
        HP = HP + amount;
        if (HP > maxHP) {
            HP = maxHP;
        }
    }

    //    3.受到伤害
    public void takeDamage(int damage) {
        HP = HP - damage;
        if (HP < 0) {
            HP = 0;
        }
    }

    //    4.展示信息
    public String show() {
        return name + "[当前生命：" + HP + "，攻击：" + attack + "，防御：" + defense + "]";
    }
}
