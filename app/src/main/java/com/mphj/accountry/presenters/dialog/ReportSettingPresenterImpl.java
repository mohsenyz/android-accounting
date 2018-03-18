package com.mphj.accountry.presenters.dialog;

import android.content.Intent;
import android.view.View;

import com.mphj.accountry.R;
import com.mphj.accountry.activity.ExportTypeActivity;
import com.mphj.accountry.interfaces.dialog.ReportSettingView;
import com.mphj.accountry.models.SimpleListModel;
import com.mphj.accountry.models.db.Transaction;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mphj on 11/20/17.
 */


public class ReportSettingPresenterImpl implements ReportSettingPresenter {

    ReportSettingView view;

    public ReportSettingPresenterImpl(ReportSettingView view){
        this.view = view;
    }

    @Override
    public void onResume() {

    }

    @Override
    public void onDestroy() {

    }

    @Override
    public void loadList(final Transaction transaction) {
        List<SimpleListModel> list = new ArrayList<>();
        SimpleListModel model = new SimpleListModel("گرفتن خروجی", R.drawable.ic_export, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(v.getContext(), ExportTypeActivity.class);
                i.putExtra("id", transaction.getId().intValue());
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                v.getContext().startActivity(i);
            }
        });
        list.add(model);
        view.setAdapter(list);
    }
}
