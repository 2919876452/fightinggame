package bean;

public class EnemyCharacter extends Character {
    private String skill;
    private boolean defending;

    public boolean getDefending(){
        return defending;
    }

    public void setDefending(boolean defending){
        this.defending = defending;
    }

    public String getSkill() {
        return skill;
    }

    public EnemyCharacter() {
        super();
    }

    public EnemyCharacter(String name, int maxHP, int attack, int defense, String skill) {
        super(name, maxHP, attack, defense);
        this.skill = skill;
    }


    //    重写受到攻击
//    最小伤害：1点
    public void takeDamage(int damage){
        int max;
        if(defending){
            max = damage/2 > 1 ? damage/2 : 1;
            defending = false;
        }else{
            max = damage;
        }
        super.takeDamage(max);
    }
}
