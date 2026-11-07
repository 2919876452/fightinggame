import ui.FightingGame;
import ui.Login;

public class App {
    public static void main(String[] args) {
//        启动类，只负责游戏启动，不负责游戏逻辑
        Login l = new Login();
        l.start();
//        FightingGame fg = new FightingGame();
//        fg.gameStart("zhangsan");
    }
}