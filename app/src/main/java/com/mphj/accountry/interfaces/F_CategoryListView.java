package com.mphj.accountry.interfaces;

import com.mphj.accountry.models.db.Category;

import java.util.List;


/**
 * Created by mphj on 10/20/2017.
 */

public interface F_CategoryListView {
    void setAdapter(List<Category> realmResults);
}
