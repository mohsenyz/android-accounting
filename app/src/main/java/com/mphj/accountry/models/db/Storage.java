package com.mphj.accountry.models.db;


import org.json.JSONObject;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by mphj on 10/20/2017.
 */

public class Storage extends RealmObject{

    @PrimaryKey
    private int id;
    private String name;
    private int serverId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getServerId() {
        return serverId;
    }

    public void setServerId(int serverId) {
        this.serverId = serverId;
    }


    public static String toJson(Storage storage) {
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("name", storage.name);
            jsonObject.put("serverId", storage.serverId);
            return jsonObject.toString();
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
}
