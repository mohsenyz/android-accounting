package com.mphj.accountry.models.db;

import org.json.JSONObject;
import org.parceler.Parcel;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by mphj on 10/20/2017.
 */

@Parcel(value = Parcel.Serialization.BEAN, analyze = {Customer.class})
public class Customer extends RealmObject {

    @PrimaryKey
    private int id;
    private String name;
    private String phone;
    private long createdAt;
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
