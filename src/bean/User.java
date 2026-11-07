package bean;

import java.util.Random;

public class User {
    //    属性：id、用户名、密码、状态
    String id;  //格式为：heima+5位数字的随机数
    String username;
    String password;
    boolean status;     //false 禁用  true 启用

    public User() {
//        随机生成ID
        id = createId();
//        boolean默认为false,修改为true
        status = true;
    }

    public User(String username, String password) {
        id = createId();
        this.username = username;
        this.password = password;
        status = true;
    }

    //    用户无法设置，是自动生成的，格式为：heima+5位数字的随机数
    public String createId() {
        StringBuilder sb = new StringBuilder("heima");
        for (int i = 0; i < 5; i++) {
            Random r = new Random(10);
            sb.append(r);
        }
        return sb.toString();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

}
