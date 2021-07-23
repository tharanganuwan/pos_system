package tm;

public class LoginTM {
    private String date;
    private String name;
    private String loginTime;
    private String logOutTime;


    public LoginTM() {

    }

    public LoginTM(String date, String userId, String loginTime, String logOutTime) {
        this.date = date;
        this.name = userId;
        this.loginTime = loginTime;
        this.logOutTime = logOutTime;

    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLoginTime() {
        return loginTime;
    }

    public void setLoginTime(String loginTime) {
        this.loginTime = loginTime;
    }

    public String getLogOutTime() {
        return logOutTime;
    }

    public void setLogOutTime(String logOutTime) {
        this.logOutTime = logOutTime;
    }

}
