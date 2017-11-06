package com.mphj.accountry.models.db;


import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.json.JSONObject;
import org.parceler.Parcel;

/**
 * Created by mphj on 10/20/2017.
 */
@Entity
@Parcel
public class Storage{

    @Id
    private Long id;
    private String name;
    private int serverId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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
