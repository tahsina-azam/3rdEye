package com.example.thirdeye.user_registration;

public class User {
    public String fullName,role,email;
    public  User(){

    }
    public User(String fullName,String role, String email){
        this.fullName=fullName;
        this.email=email;
        this.role=role;
    }
}