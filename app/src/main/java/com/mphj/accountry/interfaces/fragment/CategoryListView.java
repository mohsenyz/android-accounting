package com.mphj.accountry.interfaces.fragment;

import com.mphj.accountry.models.db.Category;

import java.util.List;


/**
 * Created by mphj on 10/20/2017.
 */

public interface CategoryListView {
    void setAdapter(List<Category> realmResults);
}
