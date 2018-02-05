package com.kingkingyyk.uhunter.uhunt;

import org.json.JSONObject;

public class User {
    private int rank;
    private int id;
    private String name;
    private String username;
    private int ac;
    private int noOfSubmissions;

    public static User fromJSONObject (JSONObject obj) {
        User u=null;
        try {
            u=new User(obj.getInt("rank"),obj.getInt("userid"), obj.getString("name"), obj.getString("username"), obj.getInt("ac"), obj.getInt("nos"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return u;
    }

    public User (int rank, int id, String name, String username, int ac, int noOfSubmissions) {
        this.rank=rank;
        this.id=id;
        this.name=name;
        this.username=username;
        this.ac=ac;
        this.noOfSubmissions=noOfSubmissions;
    }

    public int getRank() {
        return rank;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getUsername() {
        return username;
    }

    public int getAc() {
        return ac;
    }

    public int getNoOfSubmissions() {
        return noOfSubmissions;
    }
}
