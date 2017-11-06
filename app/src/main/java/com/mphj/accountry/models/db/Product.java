package com.mphj.accountry.models.db;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.json.JSONObject;
import org.parceler.Parcel;
import org.parceler.Transient;


/**
 * Created by mphj on 10/20/2017.
 */
@Parcel
@Entity
public class Product {

    @Id
    private Long id;
    private String name;
    private long createdAt;
    private String token;
    private int serverId;

    @Transient
    @org.greenrobot.greendao.annotation.Transient
    private ProductPrice currentProductPrice;

    @org.greenrobot.greendao.annotation.Transient
    @Transient
    private int count;

    public ProductPrice getCurrentProductPrice() {
        return currentProductPrice;
    }

    public void setCurrentProductPrice(ProductPrice currentProductPrice) {
        this.currentProductPrice = currentProductPrice;
    }

    public int getServerId() {
        return serverId;
    }

    public void setServerId(int serverId) {
        this.serverId = serverId;
    }

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

    public long getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(long createdAt) {
        this.createdAt = createdAt;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public static String toJson(Product product) {
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("name", product.name);
            jsonObject.put("token", product.token);
            jsonObject.put("createdAt", product.name);
            jsonObject.put("serverId", product.serverId);
            return jsonObject.toString();
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
}
