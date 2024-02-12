package com.database.model;

public class Assignment {
    public Integer id;
    public String name;
    public String category;
    public Float points;

    public Assignment(Integer id, String name, String category,  Float points) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.points = points;
    }

    public Integer getId(){
        return id;
    }
    public String getName() {

        return name;
    }
    public String getCategory() {

        return category;
    }
    public Float getPoints() {
        return points;
    }
}