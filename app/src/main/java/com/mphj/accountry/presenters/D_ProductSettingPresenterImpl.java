package com.mphj.accountry.presenters;

import com.mphj.accountry.R;
import com.mphj.accountry.interfaces.D_ProductSettingView;
import com.mphj.accountry.models.SimpleListModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mphj on 10/23/2017.
 */

public class D_ProductSettingPresenterImpl implements D_ProductSettingPresenter {

    D_ProductSettingView view;

    public D_ProductSettingPresenterImpl(D_ProductSettingView view){
        this.view = view;
    }

    @Override
    public void onResume() {

    }

    @Override
    public void onDestroy() {

    }

    @Override
    public void loadList() {
        List<SimpleListModel> list = new ArrayList<>();
        SimpleListModel model = new SimpleListModel("ویرایش", R.drawable.ic_gray_edit);
        list.add(model);
        model = new SimpleListModel("افزودن به انبار", R.drawable.ic_gray_import);
        list.add(model);
        model = new SimpleListModel("نمودار تغییر قیمت", R.drawable.ic_gray_line_chart);
        list.add(model);
        view.setAdapter(list);
    }
}
