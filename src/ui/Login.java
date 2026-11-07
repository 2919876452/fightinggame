package ui;

import bean.User;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Login {
    ArrayList<User> list = new ArrayList<>();

    public void start() {
        //ctrl + alt + T 选择对应语句包裹代码
        while (true) {
            System.out.println("╔════════════════════════════════╗");
            System.out.println("    🎮 欢迎来到文字格斗游戏 🎮   ");
            System.out.println("╚════════════════════════════════╝");
            System.out.println("请选择操作：1登录 2注册 3退出");
            Scanner sc = new Scanner(System.in);
            String choose = sc.next();

            switch (choose) {
                case "1" -> login(list);
                case "2" -> register(list);
                case "3" -> System.exit(0);
                default -> System.out.println("无效的选择，请重新输入");
            }
        }
    }

    public void login(ArrayList<User> list) {
//        登录功能：
//        1. 键盘录入用户名
//        2. 键盘录入密码
//        3. 键盘录入验证码
//        4. 登录最多重试三次，三次错误账号锁定
//                **验证要求：**
//	用户名如果未注册提示：用户名未注册，请先注册
//	用户被锁定提示：用户xxx已经锁定，请联系黑马程序员官方客服：XXX-XXXXX
//	验证码错误提示：验证码输入错误，请重新输入，并生成一个新的验证码
//	判断用户名和密码是否正确，有3次机会，满3次账户锁定。
        Scanner sc = new Scanner(System.in);
        System.out.println("请输入用户名：");
        String username = sc.next();
        //	用户名如果未注册提示：用户名未注册，请先注册
        if (!contains(list, username)) {
            System.out.println("用户" + username + "未注册，请返回注册~");
            return;
        }

        //	用户被锁定提示：用户xxx已经锁定，请联系黑马程序员官方客服：XXX-XXXXX
//        1.先获取用户名再容器里的索引，再根据索引获取用户对象的所有信息
        int index = findIndex(list, username);
        User u = list.get(index);
        if (!u.isStatus()) {
            System.out.println("用户" + username + "已经锁定，请联系黑马程序员官方客服：XXX-XXXXX~");
            return;
        }
//	验证码错误提示：验证码输入错误，请重新输入，并生成一个新的验证码

        while (true) {
            String rightCode = getCode();
            System.out.println("正确的验证码是：" + rightCode);
            System.out.println("请输入验证码：");
            String code = sc.next();
            if (!code.equalsIgnoreCase(rightCode)) {
                System.out.println("验证码输入错误，请重新输入");
                continue;
            } else {
                System.out.println("验证码输入正确");
                break;
            }
        }

        //	判断用户名和密码是否正确，有3次机会，满3次账户锁定。
        for (int i = 0; i < 3; i++) {
            System.out.println("请输入密码");
            String password = sc.next();
            if (password.equals(u.getPassword())) {
                System.out.println("账户" + username + "登录成功！");
                break;
            } else if (i == 2) {
                System.out.println("账户" + username + "已锁定，请联系黑马程序员官方客服：XXX-XXXXX");
                u.setStatus(false);
                break;
            } else {
                System.out.println("密码输入错误，还有" + (2 - i) + "次机会");
                continue;
            }
        }
//        进入游戏界面
        FightingGame fg = new FightingGame();
        fg.gameStart(username);
    }

    public void register(ArrayList<User> list) {
//一、创建User对象（空参）
        User u = new User();

//二、键盘录入用户名username

// 用户名要求：
// 用户名唯一
// 长度必须在3 ~ 16位
// 只能由字母、数字组成，不能是纯数字

//        步骤：
//        1.先输入用户名
//        2.判断用户名长度
//        3.判断用户名组成
//        4.判断用户名是否唯一
        Scanner sc = new Scanner(System.in);
        while (true) {
//            1.先输入用户名
            System.out.println("请输入用户名：");
            String username = sc.next();
//            2.判断用户名长度
            if (!checkLen(3, 16, username)) {
                System.out.println("用户名的长度不符合要求，长度必须在3 ~ 16位,请重新输入");
                continue;
            }
//            3.判断用户名组成
            if (!checkUsername(username)) {
                System.out.println("用户名的组成不符合要求，只能由字母、数字组成，不能是纯数字，请重新输入");
                continue;
            }
            if (contains(list, username)) {
                System.out.println("用户名已存在，请重新输入");
                continue;
            }
            //        代码执行到这里，说明用户名符合所有要求，将用户名保存
            u.setUsername(username);
            break;
        }

//三、键盘录入密码password

//  密码要求：
//  密码键盘输入两次，两次一致才可以进行注册
//  长度3 ~ 8位
//  只能是字母加数字的组合，不能有其他字母
        while (true) {
            System.out.println("请输入密码：");
            String password1 = sc.next();
            System.out.println("请再次输入密码：");
            String password2 = sc.next();
//            密码键盘输入两次，两次一致才可以进行注册
            if (!password1.equals(password2)) {
                System.out.println("两次输入的密码不一致，请重新输入");
                continue;
            }

//            长度3 ~ 8位
            if (!checkLen(3, 8, password1)) {
                System.out.println("密码长度不符合要求，长度3 ~ 8位,请重新输入");
                continue;
            }

//            只能是字母加数字的组合，不能有其他字母
            if (!checkPassword(password1)) {
                System.out.println("密码组合不符合要求，只能是字母加数字的组合，不能有其他字母,请重新输入");
                continue;
            }
//            代码执行到这里说明密码符合所有要求，将密码保存
            u.setPassword(password1);
            break;
        }
        //四、把User对象添加到集合中
        list.add(u);


//五、提示创建成功
        System.out.println("用户" + u.getUsername() + "注册成功！");


    }


    //    方法作用：查找用户在容器里的索引
    public int findIndex(ArrayList<User> list, String username) {
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getUsername().equals(username)) {
                return i;
            }
        }
        return -1;
    }

    //方法作用：检查字符串长度是否在范围内
    public boolean checkLen(int mixlen, int maxlen, String str) {
        return str.length() >= mixlen && str.length() <= maxlen;
    }

    //方法作用：检查用户名组成是否符合要求
    public boolean checkUsername(String username) {
        int[] count = getCount(username);
        //    0.字母个数
        //    1.数字个数
        //    2.其他字符个数
        return count[0] > 0 && count[1] >= 0 && count[2] == 0;
    }

    //方法作用：检查用户名是否存在
    public boolean contains(ArrayList<User> list, String username) {
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getUsername().equals(username)) {
                return true;
            }
        }
        return false;
    }

    //方法作用：检查密码组合是否符合要求
    public boolean checkPassword(String password) {

        int[] count = getCount(password);
        //    0.字母个数
        //    1.数字个数
        //    2.其他字符个数
        return count[0] > 0 && count[1] > 0 && count[2] == 0;
    }

    //    方法作用：得到字符串中 字母，数字，其他字符 的个数
//    0.字母个数
//    1.数字个数
//    2.其他字符个数
    public int[] getCount(String str) {
        int charcount = 0;
        int numcount = 0;
        int othercount = 0;
        for (int i = 0; i < str.length(); i++) {
            int c = str.charAt(i);
            if (c >= 'a' && c <= 'z') charcount++;
            else if (c >= '0' && c <= '9') numcount++;
            else othercount++;
        }
        return new int[]{charcount, numcount, othercount};
    }

    //  方法作用：生成验证码
    public String getCode() {
//  验证码规则：
//  长度为5
//	由4位大写或者小写字母和1位数字组成，同一个字母可重复
//	数字可以出现在任意位置
//  如：aQa1K

//        1.先把随机字母放在容器里面
        Random r = new Random();
        ArrayList<Character> code = new ArrayList<>();
        for (int i = 0; i < 26; i++) {
            code.add((char) ('a' + i));
            code.add((char) ('A' + i));
        }
//        2.抽取4次字母，拼接成字符串
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 4; i++) {
            int index = r.nextInt(code.size());
            sb.append(code.get(index));
        }
//        3.添加一位数字，且数字的位置随机，
//        实现：将sb转换为字符串，再转换为字符数组，再随机交换数字位置
        sb.append(r.nextInt(10));
        char[] arr = sb.toString().toCharArray();
        int index = r.nextInt(arr.length - 1);
        char temp = arr[index];
        arr[index] = arr[arr.length - 1];
        arr[arr.length - 1] = temp;
        String str = new String(arr);
        return str;
    }
}