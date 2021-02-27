package com.diu.farmerfriendsupport.Model;

public class ModelUser {

    private String id;
    private String email;
    private String username;
    private String userType;
    private String ImageURL;

    public ModelUser() {
    }

    public ModelUser(String id, String email, String username, String userType, String imageURL) {
        this.id = id;
        this.email = email;
        this.username = username;
        this.userType = userType;
        ImageURL = imageURL;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getImageURL() {
        return ImageURL;
    }

    public void setImageURL(String imageURL) {
        ImageURL = imageURL;
    }
}
