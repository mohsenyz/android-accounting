package com.mphj.accountry.models.db;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.json.JSONObject;
import org.parceler.Parcel;

/**
 * Created by mphj on 10/20/2017.
 */

@Parcel
@Entity
public class Customer {

    @Id
    private Long id;
    private String name;
    private String phone;
    private long createdAt;
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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public long getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(long createdAt) {
        this.createdAt = createdAt;
    }

    public int getServerId() {
        return serverId;
    }

    public void setServerId(int serverId) {
        this.serverId = serverId;
    }

    public static String toJson(Customer customer){
        try{
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("name", customer.getName());
            jsonObject.put("phone", customer.getPhone());
            jsonObject.put("createdAt", customer.getCreatedAt());
            jsonObject.put("serverId", customer.getServerId());
            return jsonObject.toString();
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
}
