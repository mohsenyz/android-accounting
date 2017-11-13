package com.mphj.accountry.models.db;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Transient;
import org.json.JSONObject;
import org.parceler.Parcel;

/**
 * Created by mphj on 10/20/2017.
 */

@Parcel
@Entity
public class Transaction {

    @Transient
    @org.parceler.Transient
    public static final int TYPE_INCOMING = 1, TYPE_OUTGOING = 2;

    @Id(autoincrement = true)
    public Long id;
    public int customerId;
    public boolean canceled;
    public long createdAt;
    public int type;
    public String description;
    public double tax;
    public double off;
    public int serverId;

    @Generated(hash = 316430056)
    public Transaction(Long id, int customerId, boolean canceled, long createdAt,
            int type, String description, double tax, double off, int serverId) {
        this.id = id;
        this.customerId = customerId;
        this.canceled = canceled;
        this.createdAt = createdAt;
        this.type = type;
        this.description = description;
        this.tax = tax;
        this.off = off;
        this.serverId = serverId;
    }

    @Generated(hash = 750986268)
    public Transaction() {
    }

    public static String toJson(Transaction transaction) {
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("customerId", transaction.getCustomerId());
            jsonObject.put("canceled", transaction.isCanceled());
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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

    public boolean getCanceled() {
        return this.canceled;
    }
}
