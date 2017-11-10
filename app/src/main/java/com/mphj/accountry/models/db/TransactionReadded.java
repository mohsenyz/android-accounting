package com.mphj.accountry.models.db;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Transient;
import org.parceler.Parcel;

/**
 * Created by mphj on 11/10/2017.
 */

@Parcel
@Entity
public class TransactionReadded
{
    @Transient
    public static final int INC = 1, DEC = 2;

    @Id(autoincrement = true)
    private Long id;
    private int type;
    private long transactionId;
    private double price;


    @Generated(hash = 616866546)
    public TransactionReadded(Long id, int type, long transactionId, double price) {
        this.id = id;
        this.type = type;
        this.transactionId = transactionId;
        this.price = price;
    }

    @Generated(hash = 324107990)
    public TransactionReadded() {
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public long getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(long transactionId) {
        this.transactionId = transactionId;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
