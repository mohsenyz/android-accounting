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
public class TransactionProduct {

    @Id
    private Long id;
    private int transactionId;
    private int productId;
    private int count;

    public static String toJson(TransactionProduct transactionProduct) {
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("transactionId", transactionProduct.getTransactionId());
            jsonObject.put("productId", transactionProduct.getProductId());
            jsonObject.put("count", transactionProduct.getCount());
            return jsonObject.toString();
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(int transactionId) {
        this.transactionId = transactionId;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
