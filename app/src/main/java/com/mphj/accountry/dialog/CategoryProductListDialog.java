package com.mphj.accountry.dialog;

import android.os.Bundle;
import android.support.annotation.Nullable;
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
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View contentView = View.inflate(getContext(), R.layout.bs_dialog_simple_setting, null);
        getDialog().setContentView(contentView);
        ButterKnife.bind(this, contentView);
        setupRecyclerView();
        if (getArguments() != null) {
            category = Parcels.unwrap(getArguments().getParcelable("category"));
        }
        presenter = new CategoryProductListPresenterImpl(this);
        presenter.loadList(category);
    }

    public void setupRecyclerView(){
        final LinearLayoutManager layoutManager = new LinearLayoutManager(recyclerView.getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
    }

    @Override
    public void setAdapter(List<Product> list) {
        productListAdapter = new ProductListAdapter(list, getActivity());
        recyclerView.setAdapter(productListAdapter);
    }
}
