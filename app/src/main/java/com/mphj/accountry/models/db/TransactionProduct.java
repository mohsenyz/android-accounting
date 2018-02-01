package com.mphj.accountry.models.db;

import com.mphj.jodiff.JoDiffReporter;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.parceler.Parcel;

/**
 * Created by mphj on 10/20/2017.
 */

@Parcel
@Entity
public class TransactionProduct implements JoDiffReporter<TransactionProduct> {

    @Id(autoincrement = true)
    public Long id;
    public int transactionId;
    public int productId;
    public int count;

    @Generated(hash = 1454147320)
    public TransactionProduct(Long id, int transactionId, int productId,
            int count) {
        this.id = id;
        this.transactionId = transactionId;
        this.productId = productId;
        this.count = count;
    }

    @Generated(hash = 126050318)
    public TransactionProduct() {
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
