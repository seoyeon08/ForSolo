package com.example.forsolo;

// user data set
public class UserData {
    private String user_email;   // 사용자 e-mail
    private String user_password;// 사용자 비밀번호

    public UserData() {
    }

    public UserData(String user_email, String user_password) {
        this.user_email = user_email;
        this.user_password = user_password;
    }

    public String getUser_Email() {
        return user_email;
    }
    public String getUser_Password() {
        return user_password;
    }

    public void setUser_Email(String user_email) {
        this.user_email = user_email;
    }
    public void setUser_Password(String user_password) {
        this.user_password = user_password;
    }
}
