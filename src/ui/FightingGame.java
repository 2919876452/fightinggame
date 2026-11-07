package ui;

import bean.EnemyCharacter;
import bean.HeorCharacter;

import java.time.chrono.IsoChronology;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class FightingGame {
    public void gameStart(String username) {
//        1.显示游戏标题
        System.out.println("╔════════════════════════════════════════╗");
        System.out.println("🎮 " + username + "欢迎来到文字格斗游戏 🎮   ");
        System.out.println("╚════════════════════════════════════════╝");
//      2.创建玩家角色（名称+属性分配）
        HeorCharacter player = createHeroCharacter(username);

//      3.创建多个敌人列表
        System.out.println("角色创建成功！");
        System.out.println("\uD83C\uDF1F 初始属性:" + player.show());
        System.out.println("\uD83C\uDF1F 拥有技能:" + player.showSkill());

        //    4.准备战斗（依次跟多个敌人战斗）
        ArrayList<EnemyCharacter> enemyList = new ArrayList<EnemyCharacter>();
        enemyList.add(new EnemyCharacter("初级战士",80,15,10,"猛击"));
        enemyList.add(new EnemyCharacter("敏捷刺客",60,20,5,"快速攻击"));
        enemyList.add(new EnemyCharacter("重装坦克",120,10,20,"防御姿态"));
        enemyList.add(new EnemyCharacter("神秘法师",70,25,8,"火球术"));

        //        5.准备战斗（依次跟多个敌人战斗）
        int count = 1;//记录我与第几个敌人战斗
        int wins = 1;//记录胜利了几场

//        进入游戏循环，直到死亡

        while(player.isAlive()){
//            战斗开始

//            5.1 重置敌人的属性，敌人属性每场HP+10, ATK+3, DEF+2（敌人：越来越打）（第二场的时候）
//            如果游戏进行到这里且wins！=0说明上一场战斗胜利了，如果失败人物就死了
            if (wins!=0){
//                把集合里的所有的敌人属性进行增加
                for (int i = 0; i < enemyList.size(); i++) {
                    EnemyCharacter p = enemyList.get(i);
                    p.setMaxHP(p.getMaxHP()+10);
                    p.setHP(p.getMaxHP());
                    p.setAttack(p.getAttack()+3);
                    p.setDefense(p.getDefense()+2);
                    p.setDefending(false);
//                  System.out.println(p.show());
                }
            }

//            5.2 随机选择敌人(Random)
            Random r = new Random();
            int index = r.nextInt(enemyList.size());
            EnemyCharacter enemy = enemyList.get(index);
            System.out.println("敌人" + enemy.getName() + "出现！");
            System.out.println(enemy.show());


//            初始化回合数
            int round = 1;

//            进入战斗回合循环

            while(true){

//            5.3 战斗开始，显示双方状态（生命值）
                System.out.println("═══════════════════════════════════════");
                System.out.println("⚔️ 第 " + count + " 场战斗开始！对手: " + enemy.getName());
                System.out.println("---------------------------------------");
                System.out.println("⚔️ 第 " + round + " 回合开始！");
                System.out.println(getHealthBar(player.getName(), player.getHP(), player.getMaxHP()));
                System.out.println(getHealthBar(enemy.getName(), enemy.getHP(), enemy.getMaxHP()));

//                5.4 玩家回合：选择行动（ 1普通攻击/ 2强力一击/ 3生命汲取 ）
                playTurn(player,enemy);

//                5.5 判断敌人是否被击败（判断敌方血量是否为0）
                if(!enemy.isAlive()){
                    System.out.println("敌人" + enemy.getName() + "被击败了！");
                    wins++;
                    break;
                }

//                5.6 敌人回合：选择行动（ 50%的几率普通攻击 / 50%的几率技能攻击 / 不同的敌人采取不同的技能进行攻击）
                enemyTurn(enemy,player);

//                5.7 判断玩家是否被击败（判断玩家血量是否为0）
                if (!player.isAlive()){
                    System.out.println("你被击败了！");
                    break;
                }else {
                    round++;
                }

            }

//            5.8 跟一个敌人的战斗结束后，玩家胜利（恢复生命值继续战斗）玩家失败（游戏停止）
            if(player.isAlive()){
//                恢复生命值 20~40
                int healHP = r.nextInt(21)+20;
                player.heal(healHP);

                System.out.println("\uD83C\uDF89 你击败了 "+enemy.getName()+"！");
                System.out.println("\uD83D\uDC9A 战斗结束！你恢复了 "+healHP+" 点生命值");
                System.out.println("\uD83C\uDFC6 当前胜场: "+wins);
                System.out.println("═══════════════════════════════════════");
            }

//            5.9 每3胜提升属性
//            每胜利三场，玩家属性增加HP+30, ATK+5, DEF+3
            if(player.isAlive()&&wins>0&&wins%3==0){
                System.out.println("恭喜你的属性得到了提升！ HP+30, ATK+5, DEF+3");
                player.heal(30);
                player.setMaxHP(player.getMaxHP()+30);
                player.setAttack(player.getAttack()+5);
                player.setDefense(player.getDefense()+3);
                System.out.println("目前属性："+player.show());
            }



//            5.10 询问是否继续
            if (player.isAlive()){
                System.out.println("是否继续？(y/n)");
                Scanner sc = new Scanner(System.in);
                String choice = sc.next();
                if ("y".equals(choice)){
                    System.out.println("游戏继续！");
                    count++;
                    continue;
                }else if ("n".equals(choice)){
                    System.out.println("游戏结束！");
                    break;
                }else {
                    System.out.println("没有这个选项，默认游戏继续");
                    continue;
                }
            }

        }

//        6.整个游戏结束，最终结算
        System.out.println("══════════════════════════════════════");
        System.out.println("游戏结束！");
        System.out.println("总胜场："+wins);
        System.out.println("感谢游玩文字格斗游戏！");
        System.exit(0);
    }
//        敌人的回合
    private void enemyTurn(EnemyCharacter enemy, HeorCharacter player) {
        System.out.println("===== 敌人的回合 =====");
        Random r = new Random();
        int num = r.nextInt(2);
        String action;
        if(num==0){
            action = "普通攻击";
        }else{
            action = enemy.getSkill();
        }

        switch (action) {
            case "普通攻击" -> {
                int damage = calculateDamage(enemy.getAttack(),player.getDefense());
                System.out.println("⚔\uFE0F "+enemy.getName()+" 对你使用了普"+action+"，造成 "+damage+" 点伤害!");
                player.takeDamage(damage);
            }

            case "快速攻击" -> {
                int damage = calculateDamage((int)(enemy.getAttack()*0.5),player.getDefense());
                System.out.println("⚔\uFE0F "+enemy.getName()+" 对你使用了"+action+"，造成两次 "+damage+" 点伤害!");
                player.takeDamage(damage);
                player.takeDamage(damage);
            }

            case "猛击" -> {
                int damage = calculateDamage((int)(enemy.getAttack()*1.5),player.getDefense());
                System.out.println("⚔\uFE0F "+enemy.getName()+" 对你使用了"+action+"，造成 "+damage+" 点伤害!");
                player.takeDamage(damage);
            }

            case "防御姿态"->{
                System.out.println("⚔\uFE0F "+enemy.getName()+" 使用了"+action+"!");
                enemy.setDefending(true);
            }

            case "火球术"->{
                int damage = calculateDamage((int)(enemy.getAttack()*1.8),player.getDefense());
                System.out.println("⚔\uFE0F "+enemy.getName()+" 对你使用了"+action+"，造成 "+damage+" 点伤害!");
                player.takeDamage(damage);
            }
        }


    }

    //    玩家回合
    public void playTurn(HeorCharacter player,EnemyCharacter enemy){
//        玩家回合：选择行动（ 1普通攻击/ 2强力一击/ 3生命汲取 ）
        System.out.println("===== 你的回合 =====");
        System.out.println("1. 普通攻击");
        System.out.println("2. 强力一击 (消耗10HP)");
        System.out.println("3. 生命汲取 (消耗10HP，恢复生命)");
        System.out.println("选择行动 (1-3):");
        Scanner sc = new Scanner(System.in);
        int choice = sc.nextInt();
        switch (choice){
            case 1 -> {
                int damage = calculateDamage(player.getAttack(),enemy.getDefense());
                System.out.println("你对 "+enemy.getName()+" 使用了普通攻击，造成 "+damage+" 点伤害!");
                enemy.takeDamage(damage);
            }
            case 2 ->{
                if(player.getHP()<=10){
                    System.out.println("体力不足，无法攻击！");
                    break;
                }
                int damage = calculateDamage((int)(player.getAttack()*1.8),enemy.getDefense());
                System.out.println("\uD83D\uDCA5 消耗10HP，你对 "+enemy.getName()+" 使用了强力一击，造成 "+damage+" 点伤害！");
                player.takeDamage(10);
                enemy.takeDamage(damage);
            }
            case 3 ->{
                if(player.getHP()<=10){
                    System.out.println("体力不足，恢复失败！");
                    break;
                }
                Random r = new Random();
                int heal = r.nextInt(21);
                System.out.println("\uD83D\uDCA5 消耗10HP，你使用了生命汲取，恢复了 "+heal+" 点生命值！");
                player.heal(heal);
            }
            default -> {
                System.out.println("没有这个操作，使用普通攻击");
                int damage = calculateDamage(player.getAttack(),enemy.getDefense());
                System.out.println("你对 "+enemy.getName()+" 使用了普通攻击，造成 "+damage+" 点伤害!");
                enemy.takeDamage(damage);
            }
        }
    }

//    计算伤害
    public int calculateDamage(int attack,int defense){
        int damage = attack - defense;
        if(damage<1) damage=1;
        return damage;
    }

//    方法作用生成血条
    public String getHealthBar(String name,int HP,int maxHP){
//        zhangsan: [████████████████████] 100/100 HP
//        初级战士: [████████████████████] 80/80 HP

//        血条共20格

        double filled = HP*1.0/maxHP;
        int bars = (int) (filled * 20);
        StringBuilder sb = new StringBuilder();
        sb.append(name).append(": [");
        for (int i = 0; i < 20; i++) {
            if(i<bars){
                sb.append("█");
            }else{
                sb.append(" ");
            }
        }
        sb.append("] ").append(HP).append("/").append(maxHP).append(" HP");
        return sb.toString();
    }

    //方法作用：创建游戏角色
    public HeorCharacter createHeroCharacter(String username) {
        System.out.println("创建你的角色：");
        System.out.println("您的角色名为:" + username);
        System.out.println("请分配属性点 (共20点):");
        System.out.println("1. 生命值 (每点+10 HP)");
        System.out.println("2. 攻击力 (每点+2 ATK)");
        System.out.println("3. 防御力 (每点+1 DEF)");
        int points = 20;
        String attribute[] = {"生命值", "攻击力", "防御力"};
        int values[] = new int[3];
        Scanner sc = new Scanner(System.in);
//        输入分配点数
        for (int i = 0; i < 3; i++) {
            System.out.println("请输入分配到 " + attribute[i] + " 的点数 (剩余点数：" + points + ")");
            int point = sc.nextInt();
            if (point < 0) {
                System.out.println("输入属性值小于0，默认添加0点属性值");
                point = 0;
            }
            if (point > points) {
                System.out.println("输入属性值大于剩余点数，默认添加剩余点数属性值");
                point = points;
            }
            values[i] = point;
            points = points - point;
            System.out.println("分配点数" + point + "到 " + attribute[i] + " (剩余点数：" + points + ")");

        }

//        创建角色
        HeorCharacter player = new HeorCharacter(
                username,
                100 + values[0] * 10,
                10 + values[1] * 2,
                0 + values[2] * 1);

//        创建技能列表
        player.getSkillList().add("普通攻击");
        player.getSkillList().add("强力一击");
        player.getSkillList().add("生命汲取");
        return player;

    }

//
}
