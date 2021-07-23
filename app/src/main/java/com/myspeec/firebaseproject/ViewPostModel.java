package com.myspeec.firebaseproject;

public class ViewPostModel {
    String imageName;
    String imageURL;

    String description;
    String rentAmount;
    String location;
    String email;
    String postTime;

    public ViewPostModel(){}
    public ViewPostModel(String imageName, String imageUrl, String description, String rentAmount, String location, String email, String postTime) {
        this.imageName = imageName;
        this.imageURL = imageUrl;
        this.description = description;
        this.rentAmount = rentAmount;
        this.location = location;
        this.email = email;
        this.postTime=postTime;
    }

    public String getRentAmount() {
        return rentAmount;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPostTime() {
        return postTime;
    }

    public void setPostTime(String postTime) {
        this.postTime = postTime;
    }

    public void setRentAmount(String rentAmount) {
        this.rentAmount = rentAmount;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    public String getImageUrl() {
        return imageURL;
    }

    public void setImageUrl(String imageUrl) {
        this.imageURL = imageUrl;
    }
}
