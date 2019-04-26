package com.example.demo2;

public class UsersModel
{
    private String userName;
    private String userEmail;

    public UsersModel()
    {
    }

    public UsersModel(String userName, String userEmail)
    {
        this.userName  = userName;
        this.userEmail = userEmail;
    }

    public String getUserName()
    {
        return userName;
    }
    public String getUserEmail()
    {
        return userEmail;
    }
}
