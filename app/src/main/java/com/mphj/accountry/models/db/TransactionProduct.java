package com.mphj.accountry.models.db;

import org.json.JSONObject;
import org.parceler.Parcel;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by mphj on 10/20/2017.
 */

@Parcel(value = Parcel.Serialization.BEAN, analyze = {TransactionProduct.class})
public class TransactionProduct extends RealmObject {

    @PrimaryKey
    private int id;
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
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
