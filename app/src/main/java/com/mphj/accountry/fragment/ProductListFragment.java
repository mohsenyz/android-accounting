package com.mphj.accountry.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mphj.accountry.R;
import com.mphj.accountry.adapter.ProductListAdapter;
import com.mphj.accountry.interfaces.F_ProductListView;
import com.mphj.accountry.interfaces.OnObjectItemClick;
import com.mphj.accountry.models.db.Product;
import com.mphj.accountry.presenters.F_ProductListPresenter;
import com.mphj.accountry.presenters.F_ProductListPresenterImpl;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by mphj on 10/22/2017.
 */

public class ProductListFragment extends Fragment implements F_ProductListView {

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    ProductListAdapter productListAdapter;

    F_ProductListPresenter presenter;

    OnObjectItemClick<Product> click;

    public ProductListFragment(){

    }

    public static ProductListFragment newInstance(){
        return new ProductListFragment();
    }

    public static ProductListFragment newInstance(boolean isForSelect){
        ProductListFragment productListFragment = new ProductListFragment();
        Bundle bundle = new Bundle();
        bundle.putBoolean("select", isForSelect);
        productListFragment.setArguments(bundle);
        return productListFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new F_ProductListPresenterImpl(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_storage_list, container, false);
        ButterKnife.bind(this, view);
        return view;
    }


    public void setupRecyclerView(){
        final LinearLayoutManager layoutManager = new LinearLayoutManager(recyclerView.getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
    }

    @Override
    public void onResume() {
        super.onResume();
        setupRecyclerView();
        presenter.onResume();
        presenter.loadList();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        presenter.onDestroy();
    }

    public void setOnObjectItemClickListener(OnObjectItemClick<Product> click){
        this.click = click;
    }

    @Override
    public void setAdapter(List<Product> realmResults) {
        if (getArguments() != null && getArguments().getBoolean("select")){
            productListAdapter = new ProductListAdapter(realmResults, getActivity(), click);
        } else {
            productListAdapter = new ProductListAdapter(realmResults, getActivity());
        }
        recyclerView.setAdapter(productListAdapter);
    }
}
