package com.mphj.accountry.models.db;


import com.mphj.jodiff.JoDiffReporter;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.parceler.Parcel;

/**
 * Created by mphj on 10/20/2017.
 */
@Entity
@Parcel
public class Category implements JoDiffReporter<Category>, Cloneable {

    @Id(autoincrement = true)
    public Long id;
    public String name;
    public int serverId;

    @Generated(hash = 445730730)
    public Category(Long id, String name, int serverId) {
        this.id = id;
        this.name = name;
        this.serverId = serverId;
    }

    @Generated(hash = 1150634039)
    public Category() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getServerId() {
        return serverId;
    }

    public void setServerId(int serverId) {
        this.serverId = serverId;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
