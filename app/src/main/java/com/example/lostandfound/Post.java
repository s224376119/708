package com.example.lostandfound;

import java.io.Serializable;

public class Post implements Serializable {

    String Type,Name ,Phone ,Description ,Date ,Location ;
    int Key;

    public Post()
    {

    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public String getLocation() {
        return Location;
    }

    public void setLocation(String location) {
        Location = location;
    }

    public int getKey() {
        return Key;
    }

    public void setKey(int key) {
        Key = key;
    }

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        Type = type;
    }





    public Post(String Type, String name, String phone, String description, String date, String location) {
        Name = name;
        Phone = phone;
        Description = description;
        Date = date;
        Location = location;
        this.Type=Type;
     ;
    }
}