package com.mphj.accountry.models.db;

import org.json.JSONObject;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by mphj on 10/20/2017.
 */

public class ProductPrice extends RealmObject {

    @PrimaryKey
    private int id;
    private int productId;
    private double price;
    private double off;
    private long createdAt;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getOff() {
        return off;
    }

    public void setOff(double off) {
        this.off = off;
    }

    public long getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(long createdAt) {
        this.createdAt = createdAt;
    }

    public static String toJson(ProductPrice productPrice) {
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("productId", productPrice.productId);
            jsonObject.put("price", productPrice.price);
            jsonObject.put("off", productPrice.off);
            jsonObject.put("createdAt", productPrice.createdAt);
            return jsonObject.toString();
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
}
