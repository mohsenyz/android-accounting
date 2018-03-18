package com.mphj.accountry.interfaces.dialog;

import com.mphj.accountry.models.SimpleListModel;

import java.util.List;

/**
 * Created by mphj on 10/23/2017.
 */

public interface CategorySettingView {
    void setAdapter(List<SimpleListModel> list);
    void showProductList();
    void showAddProductActivity();
    void showEditPage();
}
