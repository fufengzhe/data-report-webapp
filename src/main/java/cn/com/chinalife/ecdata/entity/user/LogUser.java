package cn.com.chinalife.ecdata.entity.user;

/**
 * Created by xiexiangyu on 2018/3/7.
 */
public class LogUser {
    private String username;
    private String password;
    private String resource;

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

    public String getResource() {
        return resource;
    }

    public void setResource(String resource) {
        this.resource = resource;
    }

    @Override
    public String toString() {
        return "LogUser{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", resource='" + resource + '\'' +
                '}';
    }
}
