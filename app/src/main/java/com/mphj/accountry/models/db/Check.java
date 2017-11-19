package com.mphj.accountry.models.db;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.json.JSONObject;
import org.parceler.Parcel;

/**
 * Created by mphj on 11/19/17.
 */


@Entity
@Parcel
public class Check {

    @Id(autoincrement = true)
    public Long id;
    public String serial;
    public long dueDate;
    public String description;
    public int price;
    public String bank;
    public Long notificationDate;

    @Generated(hash = 1155569284)
    public Check(Long id, String serial, long dueDate, String description,
            int price, String bank, Long notificationDate) {
        this.id = id;
        this.serial = serial;
        this.dueDate = dueDate;
        this.description = description;
        this.price = price;
        this.bank = bank;
        this.notificationDate = notificationDate;
    }

    @Generated(hash = 1080183208)
    public Check() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSerial() {
        return serial;
    }

    public void setSerial(String serial) {
        this.serial = serial;
    }

    public long getDueDate() {
        return dueDate;
    }

    public void setDueDate(long dueDate) {
        this.dueDate = dueDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getBank() {
        return bank;
    }

    public void setBank(String bank) {
        this.bank = bank;
    }

    public Long getNotificationDate() {
        return notificationDate;
    }

    public void setNotificationDate(Long notificationDate) {
        this.notificationDate = notificationDate;
    }
}
