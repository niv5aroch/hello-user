package com.example.hellouser;

public class Users {
    private String email;
    private String password;
private boolean isAdmin;
    public Users(String email, String password ,boolean isAdmin) {
        this.email = email;
        this.password = password;
        this.isAdmin =false;
    }

    public boolean isIsadmin() {
        return isAdmin;
    }

    public void setIsadmin(boolean isadmin) {
        this.isAdmin = isadmin;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "Users{" +
                "email='" + email + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
