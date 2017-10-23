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
import com.mphj.accountry.adapter.CustomerListAdapter;
import com.mphj.accountry.interfaces.F_CustomerListView;
import com.mphj.accountry.models.db.Customer;
import com.mphj.accountry.presenters.F_CustomerListPresenter;
import com.mphj.accountry.presenters.F_CustomerListPresenterImpl;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.RealmResults;

public class CustomerListFragment extends Fragment implements F_CustomerListView {

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    CustomerListAdapter customerListAdapter;

    F_CustomerListPresenter presenter;

    public CustomerListFragment(){

    }

    public static CustomerListFragment newInstance(){
        return new CustomerListFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new F_CustomerListPresenterImpl(this);
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

    @Override
    public void setAdapter(RealmResults<Customer> realmResults) {
        customerListAdapter = new CustomerListAdapter(realmResults, getActivity());
        recyclerView.setAdapter(customerListAdapter);
    }
}
