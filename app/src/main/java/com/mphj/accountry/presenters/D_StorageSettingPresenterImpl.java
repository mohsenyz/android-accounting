package com.mphj.accountry.presenters;

import android.content.Intent;
import android.view.View;

import com.mphj.accountry.R;
import com.mphj.accountry.activity.ImportProductActivity;
import com.mphj.accountry.interfaces.D_StorageSettingView;
import com.mphj.accountry.models.SimpleListModel;
import com.mphj.accountry.models.db.Storage;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mphj on 10/23/2017.
 */

public class D_StorageSettingPresenterImpl implements D_StorageSettingPresenter {
    D_StorageSettingView view;

    public D_StorageSettingPresenterImpl(D_StorageSettingView view){
        this.view = view;
    }
    @Override
    public void onResume() {

    }

    @Override
    public void onDestroy() {

    }

    @Override
    public void loadList(final Storage storage) {
        List<SimpleListModel> list = new ArrayList<>();
        SimpleListModel model = new SimpleListModel("ویرایش", R.drawable.ic_gray_edit);
        list.add(model);
        model = new SimpleListModel("ورود محصول جدید", R.drawable.ic_gray_import, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(v.getContext(), ImportProductActivity.class);
                i.putExtra("storage_id", storage.getId());
                v.getContext().startActivity(i);
            }
        });
        list.add(model);
        model = new SimpleListModel("لیست محصولات", R.drawable.ic_gray_list);
        list.add(model);
        view.setAdapter(list);
    }
}
