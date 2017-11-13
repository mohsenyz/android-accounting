package com.mphj.accountry.dialog;

import android.app.Dialog;
import android.os.Bundle;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.mphj.accountry.R;
import com.mphj.accountry.adapter.SimpleListAdapter;
import com.mphj.accountry.interfaces.D_CustomerSettingView;
import com.mphj.accountry.models.SimpleListModel;
import com.mphj.accountry.models.db.Customer;
import com.mphj.accountry.presenters.D_CustomerSettingPresenter;
import com.mphj.accountry.presenters.D_CustomerSettingPresenterImpl;

import org.parceler.Parcels;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by mphj on 10/23/2017.
 */

public class CustomerSettingDialog extends BottomSheetDialogFragment implements D_CustomerSettingView {


    D_CustomerSettingPresenter presenter;

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    SimpleListAdapter simpleListAdapter;

    Customer customer;

    public static CustomerSettingDialog create(Customer customer){
        CustomerSettingDialog dialog = new CustomerSettingDialog();
        Bundle bundle = new Bundle();
        bundle.putParcelable("customer", Parcels.wrap(Customer.class, customer));
        dialog.setArguments(bundle);
        return dialog;
    }

    @Override
    public void setupDialog(Dialog dialog, int style) {
        super.setupDialog(dialog, style);
        View contentView = View.inflate(getContext(), R.layout.bs_dialog_simple_setting, null);
        dialog.setContentView(contentView);
        ButterKnife.bind(this, contentView);
        setupRecyclerView();
        if (getArguments() != null) {
            customer = Parcels.unwrap(getArguments().getParcelable("customer"));
        }
        presenter = new D_CustomerSettingPresenterImpl(this);
        presenter.loadList(customer);
    }

    public void setupRecyclerView(){
        final LinearLayoutManager layoutManager = new LinearLayoutManager(recyclerView.getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
    }

    @Override
    public void setAdapter(List<SimpleListModel> list) {
        simpleListAdapter = new SimpleListAdapter(list);
        recyclerView.setAdapter(simpleListAdapter);
    }
}
