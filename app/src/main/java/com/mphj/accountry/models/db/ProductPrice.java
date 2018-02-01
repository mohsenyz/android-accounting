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
public class ProductPrice implements JoDiffReporter<ProductPrice> {

    @Id(autoincrement = true)
    public Long id;
    public int productId;
    public double price;
    public double customerPrice;
    public long createdAt;

    @Generated(hash = 815706842)
    public ProductPrice(Long id, int productId, double price, double customerPrice,
            long createdAt) {
        this.id = id;
        this.productId = productId;
        this.price = price;
        this.customerPrice = customerPrice;
        this.createdAt = createdAt;
    }

    @Generated(hash = 52586929)
    public ProductPrice() {
    }

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

}
