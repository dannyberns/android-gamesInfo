package com.example.gamingnews.Server.Models;

public class Cover {
    private String image_id;

    public String getImage_id() {
        return image_id;
    }

    public void setImage_id(String image_id) {
        this.image_id = image_id;
    }

    public Cover(String image_id) {
        this.image_id = image_id;
    }
}
