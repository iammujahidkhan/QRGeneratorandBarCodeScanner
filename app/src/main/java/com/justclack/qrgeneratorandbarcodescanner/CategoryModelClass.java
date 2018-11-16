package com.justclack.qrgeneratorandbarcodescanner;


class CategoryModelClass {
    private String id;
    private String title;
    private String user;
    private String image;

    public CategoryModelClass(String id, String title, String user, String image) {
        this.id = id;
        this.title = title;
        this.user = user;
        this.image = image;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}