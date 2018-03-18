package com.mphj.accountry.dialog;

import android.app.Dialog;
import android.os.Bundle;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.mphj.accountry.R;
import com.mphj.accountry.adapter.SimpleListAdapter;
import com.mphj.accountry.interfaces.dialog.ReportSettingView;
import com.mphj.accountry.models.SimpleListModel;
import com.mphj.accountry.models.db.Transaction;
import com.mphj.accountry.presenters.dialog.ReportSettingPresenter;
import com.mphj.accountry.presenters.dialog.ReportSettingPresenterImpl;

import org.parceler.Parcels;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by mphj on 11/20/17.
 */

public class ReportSettingDialog extends BottomSheetDialogFragment implements ReportSettingView {


    ReportSettingPresenter presenter;

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    SimpleListAdapter simpleListAdapter;

    Transaction transaction;

    public static ReportSettingDialog create(Transaction transaction){
        ReportSettingDialog dialog = new ReportSettingDialog();
        Bundle bundle = new Bundle();
        bundle.putParcelable("transaction", Parcels.wrap(Transaction.class, transaction));
        dialog.setArguments(bundle);
        return dialog;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        View contentView = View.inflate(getContext(), R.layout.bs_dialog_simple_setting, null);
        dialog.setContentView(contentView);
        ButterKnife.bind(this, contentView);
        setupRecyclerView();
        if (getArguments() != null) {
            transaction = Parcels.unwrap(getArguments().getParcelable("transaction"));
        }
        presenter = new ReportSettingPresenterImpl(this);
        presenter.loadList(transaction);
        return dialog;
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
