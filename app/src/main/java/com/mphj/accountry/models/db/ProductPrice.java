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
public class ProductPrice {

    @Id
    private Long id;
    private int productId;
    private double price;
    private double customerPrice;
    private double off;
    private long createdAt;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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

    public double getCustomerPrice() {
        return customerPrice;
    }

    public void setCustomerPrice(double customerPrice) {
        this.customerPrice = customerPrice;
    }

    public static String toJson(ProductPrice productPrice) {
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("productId", productPrice.productId);
            jsonObject.put("price", productPrice.price);
            jsonObject.put("off", productPrice.off);
            jsonObject.put("customerPrice", productPrice.customerPrice);
            jsonObject.put("createdAt", productPrice.createdAt);
            return jsonObject.toString();
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
}
