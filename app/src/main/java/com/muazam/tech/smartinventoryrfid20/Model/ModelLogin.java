package com.muazam.tech.smartinventoryrfid20.Model;

public class ModelLogin {
    String UserCode,Password;

    public ModelLogin(String userCode, String password) {
        UserCode = userCode;
        Password = password;
    }

    public String getUserCode() {
        return UserCode;
    }

    public void setUserCode(String userCode) {
        UserCode = userCode;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }
}
