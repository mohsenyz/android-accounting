package com.mphj.accountry.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.mphj.accountry.R;
import com.mphj.accountry.adapter.ProductListAdapter;
import com.mphj.accountry.interfaces.dialog.CategoryProductListView;
import com.mphj.accountry.models.db.Category;
import com.mphj.accountry.models.db.Product;
import com.mphj.accountry.presenters.dialog.CategoryProductListPresenter;
import com.mphj.accountry.presenters.dialog.CategoryProductListPresenterImpl;

import org.parceler.Parcels;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import es.dmoral.toasty.Toasty;

import static com.mphj.accountry.dialog.ProductSettingDialog.INCREASE_PRODUCT;

/**
 * Created by mphj on 10/24/2017.
 */

public class CategoryProductListDialog extends BottomSheetDialogFragment implements CategoryProductListView {
    CategoryProductListPresenter presenter;

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    ProductListAdapter productListAdapter;

    Category category;

    public static CategoryProductListDialog create(Category category){
        CategoryProductListDialog dialog = new CategoryProductListDialog();
        Bundle bundle = new Bundle();
        bundle.putParcelable("category", Parcels.wrap(Category.class, category));
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
            category = Parcels.unwrap(getArguments().getParcelable("category"));
        }
        presenter = new CategoryProductListPresenterImpl(this);
        presenter.loadList(category);
        return dialog;
    }

    public void setupRecyclerView(){
        final LinearLayoutManager layoutManager = new LinearLayoutManager(recyclerView.getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
    }

    @Override
    public void setAdapter(List<Product> list) {
        productListAdapter = new ProductListAdapter(list, getActivity(), this);
        recyclerView.setAdapter(productListAdapter);
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.loadList(category);
    }

    @Override
    public void increasedSuccessfully() {
        Toasty.success(getActivity(), "تعداد محصول با موفقیت افزایش یافت").show();
    }

    public Product pendingProduct = null;

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == INCREASE_PRODUCT) {
            if (resultCode == Activity.RESULT_OK) {
                presenter.increaseProduct(pendingProduct, data.getIntExtra("count", 0));
                pendingProduct = null;
            }
        }
    }
}
