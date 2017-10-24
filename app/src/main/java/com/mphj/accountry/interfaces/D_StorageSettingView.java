package com.mphj.accountry.interfaces;

import com.mphj.accountry.models.SimpleListModel;

import java.util.List;

/**
 * Created by mphj on 10/23/2017.
 */

public interface D_StorageSettingView {
    void setAdapter(List<SimpleListModel> list);
    void showProductList();
}
