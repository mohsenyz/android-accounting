package com.mphj.accountry.presenters.dialog;

import android.content.Intent;
import android.net.Uri;
import android.view.View;

import com.mphj.accountry.R;
import com.mphj.accountry.activity.NewCustomerActivity;
import com.mphj.accountry.interfaces.dialog.CustomerSettingView;
import com.mphj.accountry.models.SimpleListModel;
import com.mphj.accountry.models.db.Customer;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mphj on 10/23/2017.
 */

public class CustomerSettingPresenterImpl implements CustomerSettingPresenter {

    CustomerSettingView view;

    public CustomerSettingPresenterImpl(CustomerSettingView view){
        this.view = view;
    }

    @Override
    public void onResume() {

    }

    @Override
    public void onDestroy() {

    }

    @Override
    public void loadList(final Customer customer) {
        List<SimpleListModel> list = new ArrayList<>();
        SimpleListModel model = new SimpleListModel("ویرایش", R.drawable.ic_gray_edit, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(v.getContext(), NewCustomerActivity.class);
                i.putExtra("id", customer.getId().intValue());
                v.getContext().startActivity(i);
            }
        });
        list.add(model);
        model = new SimpleListModel("تماس", R.drawable.ic_gray_call, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", customer.getPhone(), null));
                v.getContext().startActivity(i);
            }
        });
        list.add(model);
        model = new SimpleListModel("ارسال پیام", R.drawable.ic_gray_message, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse("sms:" + customer.getPhone()));
                v.getContext().startActivity(i);
            }
        });
        list.add(model);
        view.setAdapter(list);
    }
}
