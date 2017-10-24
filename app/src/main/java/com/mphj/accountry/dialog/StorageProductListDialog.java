package com.mphj.accountry.dialog;

import android.app.Dialog;
import android.os.Bundle;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.mphj.accountry.R;
import com.mphj.accountry.adapter.ProductListAdapter;
import com.mphj.accountry.interfaces.D_StorageProductListView;
import com.mphj.accountry.interfaces.OnObjectItemClick;
import com.mphj.accountry.models.db.Product;
import com.mphj.accountry.models.db.Storage;
import com.mphj.accountry.presenters.D_StorageProductListPresenter;
import com.mphj.accountry.presenters.D_StorageProductListPresenterImpl;

import org.parceler.Parcels;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by mphj on 10/24/2017.
 */

public class StorageProductListDialog extends BottomSheetDialogFragment implements D_StorageProductListView {
    D_StorageProductListPresenter presenter;

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    @BindView(R.id.title)
    TextView title;

    ProductListAdapter productListAdapter;

    Storage storage;

    public static StorageProductListDialog create(Storage storage){
        StorageProductListDialog dialog = new StorageProductListDialog();
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
            title.setText("لیست محصولات");
        }
        presenter = new D_StorageProductListPresenterImpl(this);
        presenter.loadList(storage);
    }

    public void setupRecyclerView(){
        final LinearLayoutManager layoutManager = new LinearLayoutManager(recyclerView.getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
    }

    @Override
    public void setAdapter(List<Product> list) {
        productListAdapter = new ProductListAdapter(list, getActivity(), new OnObjectItemClick<Product>() {
            @Override
            public void onClick(View v, Product object) {

            }
        });
        recyclerView.setAdapter(productListAdapter);
    }
}
