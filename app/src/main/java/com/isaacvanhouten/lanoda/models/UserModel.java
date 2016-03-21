package com.isaacvanhouten.lanoda.models;

/**
 * Created by isaac on 3/20/2016.
 */
public class UserModel extends BaseModel {
    public String FirstName;
    public String LastName;
    public String Email;
    private String Password;
    private boolean IsAdmin;

    public UserModel(String firstName, String lastName, boolean isAdmin,
                     String email, String password) {

        FirstName = firstName;
        LastName = lastName;
        IsAdmin = isAdmin;
        Email = email;
        Password = password;
    }

    public boolean IsUserAdmin() {
        return IsAdmin;
    }

    public String GetFullName() {
        return FirstName + " " + LastName;
    }

}
