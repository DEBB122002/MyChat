package com.example.dchat.Model;

public class Users {
    private String id;
    private String username;
    private String imageUrl;


    public Users(){}

    public Users(String id,String usernamem,String imageUrl)
    {this .id=id;
    this.username=usernamem;
    this.imageUrl=imageUrl;



    }

    public String getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
