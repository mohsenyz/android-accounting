package com.mphj.accountry.fragment.export_activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mphj.accountry.R;
import com.mphj.accountry.activity.GetCountActivity;
import com.mphj.accountry.activity.SelectProductActivity;
import com.mphj.accountry.adapter.ProductListAdapter;
import com.mphj.accountry.interfaces.OnObjectItemClick;
import com.mphj.accountry.interfaces.fragment.export_activity.ProductListView;
import com.mphj.accountry.models.db.Product;
import com.mphj.accountry.presenters.fragment.export_activity.ProductListPresenter;
import com.mphj.accountry.presenters.fragment.export_activity.ProductListPresenterImpl;

import org.parceler.Parcels;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by mphj on 11/10/2017.
 */

public class ProductListFragment extends Fragment implements
        ProductListView, OnObjectItemClick<Product>{

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    @BindView(R.id.fab)
    FloatingActionButton fab;

    ProductListPresenter presenter;

    Product pendingProduct;

    public static final int GET_COUNT_REQUEST = 1, SELECT_PRODUCT = 2;

    public static ProductListFragment newInstance(){
        return new ProductListFragment();
    }

    public static ProductListFragment newInstance(boolean isForSelect){
        ProductListFragment exportActivityProductListFragment = new ProductListFragment();
        Bundle bundle = new Bundle();
        bundle.putBoolean("select", isForSelect);
        exportActivityProductListFragment.setArguments(bundle);
        return exportActivityProductListFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new ProductListPresenterImpl(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_pattern_layout_1, container, false);
        ButterKnife.bind(this, view);
        fab.setVisibility(View.VISIBLE);
        setupRecyclerView();
        return view;
    }


    @Override
    public void onResume() {
        super.onResume();
        presenter.loadList();
    }

    public void setupRecyclerView(){
        final LinearLayoutManager layoutManager = new LinearLayoutManager(recyclerView.getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
    }

    @Override
    public void loadList(List<Product> list) {
        ProductListAdapter productListAdapter = new ProductListAdapter(list, getActivity(), this);
        recyclerView.setAdapter(productListAdapter);
    }

    @Override
    public void addProduct(Product product) {
        presenter.addProduct(product);
    }

    @Override
    public List<Product> getList() {
        return presenter.getList();
    }

    @Override
    public void onClick(View v, Product object) {
        this.pendingProduct = object;
        startActivityForResult(new Intent(getActivity(), GetCountActivity.class), GET_COUNT_REQUEST);
    }


    @OnClick(R.id.fab)
    void onFabClick() {
        startActivityForResult(new Intent(getActivity(), SelectProductActivity.class), SELECT_PRODUCT);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == GET_COUNT_REQUEST) {
            if (resultCode == Activity.RESULT_OK) {
                this.pendingProduct.setPendingCount(data.getIntExtra("count", 0));
                if (this.pendingProduct.getPendingCount() <= 0) {
                    this.pendingProduct = null;
                    return;
                }
                addProduct(pendingProduct);
            }
        }

        if (requestCode == SELECT_PRODUCT) {
            if (resultCode == Activity.RESULT_OK) {
                this.pendingProduct = Parcels.unwrap(data.getParcelableExtra("product"));
                onClick(null, this.pendingProduct);
            }
        }
    }
}
