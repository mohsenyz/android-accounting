package com.mphj.accountry.models.db;

import org.json.JSONObject;
import org.parceler.Parcel;
import org.parceler.Transient;

import io.realm.RealmObject;
import io.realm.annotations.Ignore;
import io.realm.annotations.PrimaryKey;

/**
 * Created by mphj on 10/20/2017.
 */
@Parcel(value = Parcel.Serialization.BEAN, analyze = {Product.class})
public class Product extends RealmObject {

    @PrimaryKey
    private int id;
    private String name;
    private long createdAt;
    private String token;
    private int serverId;

    @Ignore @Transient
    private ProductPrice currentProductPrice;

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
