package com.mphj.accountry.dialog;

import android.app.Dialog;
import android.os.Bundle;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.View;
import android.widget.TextView;

import com.mphj.accountry.R;
import com.mphj.accountry.adapter.SimpleListAdapter;
import com.mphj.accountry.interfaces.D_StorageSettingView;
import com.mphj.accountry.models.SimpleListModel;
import com.mphj.accountry.models.db.Storage;
import com.mphj.accountry.presenters.D_StorageSettingPresenter;
import com.mphj.accountry.presenters.D_StorageSettingPresenterImpl;

import org.parceler.Parcels;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by mphj on 10/23/2017.
 */

public class StorageSettingDialog extends BottomSheetDialogFragment implements D_StorageSettingView {


    D_StorageSettingPresenter presenter;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    @BindView(R.id.title)
    TextView title;

    SimpleListAdapter simpleListAdapter;

    Storage storage;

    public static StorageSettingDialog create(Storage storage){
        StorageSettingDialog dialog = new StorageSettingDialog();
        Bundle bundle = new Bundle();
        bundle.putParcelable("storage", Parcels.wrap(Storage.class, storage));
        dialog.setArguments(bundle);
        return dialog;
    }

    @Override
    public void setupDialog(Dialog dialog, int style) {
        super.setupDialog(dialog, style);
        View contentView = View.inflate(getContext(), R.layout.bs_dialog_storage_setting, null);
        dialog.setContentView(contentView);
        ButterKnife.bind(this, contentView);
        setupRecyclerView();
        if (getArguments() != null){
            storage = Parcels.unwrap(getArguments().getParcelable("storage"));
            title.setText(Html.fromHtml(getResources().getString(R.string.html_storage_title).replace("xxx", storage.getName())));
        }
        presenter = new D_StorageSettingPresenterImpl(this);
        presenter.loadList(storage);
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
