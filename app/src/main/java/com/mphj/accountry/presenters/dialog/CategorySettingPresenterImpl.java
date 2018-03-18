package com.mphj.accountry.presenters.dialog;

import android.view.View;

import com.mphj.accountry.R;
import com.mphj.accountry.interfaces.dialog.CategorySettingView;
import com.mphj.accountry.models.SimpleListModel;
import com.mphj.accountry.models.db.Category;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mphj on 10/23/2017.
 */

public class CategorySettingPresenterImpl implements CategorySettingPresenter {
    CategorySettingView view;

    public CategorySettingPresenterImpl(CategorySettingView view){
        this.view = view;
    }
    @Override
    public void onResume() {

    }

    @Override
    public void onDestroy() {

    }

    @Override
    public void loadList(final Category category) {
        List<SimpleListModel> list = new ArrayList<>();
        SimpleListModel model = new SimpleListModel("ویرایش", R.drawable.ic_gray_edit, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                view.showEditPage();
            }
        });
        list.add(model);
        model = new SimpleListModel("لیست محصولات", R.drawable.ic_gray_list, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                view.showProductList();
            }
        });
        list.add(model);
        model = new SimpleListModel("افزودن محصول", R.drawable.ic_add, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                view.showAddProductActivity();
            }
        });
        list.add(model);
        view.setAdapter(list);
    }
}
