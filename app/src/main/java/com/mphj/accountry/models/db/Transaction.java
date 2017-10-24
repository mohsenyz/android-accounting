package com.mphj.accountry.models.db;

import org.json.JSONObject;
import org.parceler.Parcel;

import io.realm.RealmObject;
import io.realm.annotations.Ignore;
import io.realm.annotations.PrimaryKey;

/**
 * Created by mphj on 10/20/2017.
 */

@Parcel(value = Parcel.Serialization.BEAN, analyze = {Transaction.class})
public class Transaction extends RealmObject{

    @Ignore
    public static final int TYPE_INCOMING = 1, TYPE_OUTGOING = 2;

    @PrimaryKey
    private int id;
    private int customerId;
    private boolean canceled;
    private int storageId;
    private long createdAt;
    private int type;
    private String description;
    private double tax;
    private double off;
    private int serverId;

    public static String toJson(Transaction transaction) {
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("customerId", transaction.getCustomerId());
            jsonObject.put("canceled", transaction.isCanceled());
            jsonObject.put("storageId", transaction.getStorageId());
            jsonObject.put("createdAt", transaction.getCreatedAt());
            jsonObject.put("type", transaction.getType());
            jsonObject.put("description", transaction.getDescription());
            jsonObject.put("tax", transaction.getTax());
            jsonObject.put("off", transaction.getOff());
            jsonObject.put("serverId", transaction.getServerId());
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

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public boolean isCanceled() {
        return canceled;
    }

    public void setCanceled(boolean canceled) {
        this.canceled = canceled;
    }

    public int getStorageId() {
        return storageId;
    }

    public void setStorageId(int storageId) {
        this.storageId = storageId;
    }

    public long getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(long createdAt) {
        this.createdAt = createdAt;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getTax() {
        return tax;
    }

    public void setTax(double tax) {
        this.tax = tax;
    }

    public double getOff() {
        return off;
    }

    public void setOff(double off) {
        this.off = off;
    }

    public int getServerId() {
        return serverId;
    }

    public void setServerId(int serverId) {
        this.serverId = serverId;
    }
}
