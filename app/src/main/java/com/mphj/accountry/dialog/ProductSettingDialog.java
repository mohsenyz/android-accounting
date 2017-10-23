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
import com.mphj.accountry.interfaces.D_ProductSettingView;
import com.mphj.accountry.models.SimpleListModel;
import com.mphj.accountry.models.db.Product;
import com.mphj.accountry.presenters.D_ProductSettingPresenter;
import com.mphj.accountry.presenters.D_ProductSettingPresenterImpl;

import org.parceler.Parcels;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by mphj on 10/23/2017.
 */

public class ProductSettingDialog extends BottomSheetDialogFragment implements D_ProductSettingView {


    D_ProductSettingPresenter presenter;

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    @BindView(R.id.title)
    TextView title;

    SimpleListAdapter simpleListAdapter;

    Product product;

    public static ProductSettingDialog create(Product product){
        ProductSettingDialog dialog = new ProductSettingDialog();
        Bundle bundle = new Bundle();
        bundle.putParcelable("product", Parcels.wrap(Product.class, product));
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
            product = Parcels.unwrap(getArguments().getParcelable("product"));
            title.setText(Html.fromHtml(getResources().getString(R.string.html_product_title).replace("xxx", product.getName())));
        }
        presenter = new D_ProductSettingPresenterImpl(this);
        presenter.loadList();
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
