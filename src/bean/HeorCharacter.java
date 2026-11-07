package bean;

import java.util.ArrayList;

public class HeorCharacter extends Character {
    ArrayList<String> skillList;

    public HeorCharacter() {
        super();
        skillList = new ArrayList<>();
    }

    public HeorCharacter(String name, int maxHP, int attack, int defense) {
        super(name, maxHP, attack, defense);
        skillList = new ArrayList<>();
    }

    public ArrayList<String> getSkillList() {
        return skillList;
    }

    public String showSkill() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < skillList.size(); i++) {
            sb.append(skillList.get(i));
            if (i != skillList.size() - 1) {
                sb.append(", ");
            }
        }
        return sb.toString();
    }
}
