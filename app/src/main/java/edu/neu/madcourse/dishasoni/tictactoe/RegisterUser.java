package edu.neu.madcourse.dishasoni.tictactoe;

/**
 * Created by dishasoni on 10/29/17.
 */

public class RegisterUser {
    private String userName;
    private String registrationId;
    private RegisterUser(){};
    public RegisterUser(String username,String registrationId){
        this.userName = username;
        this.registrationId = registrationId;
    }

    public String getRegistrationId() {
        return registrationId;
    }

    public void setRegistrationId(String registrationId) {
        this.registrationId = registrationId;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserName() {
        return userName;
    }


}
