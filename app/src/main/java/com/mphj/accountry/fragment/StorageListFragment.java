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
import com.mphj.accountry.adapter.StorageListAdapter;
import com.mphj.accountry.interfaces.F_StorageListView;
import com.mphj.accountry.interfaces.OnObjectItemClick;
import com.mphj.accountry.models.db.Storage;
import com.mphj.accountry.presenters.F_StorageListPresenter;
import com.mphj.accountry.presenters.F_StorageListPresenterImpl;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.RealmResults;

public class StorageListFragment extends Fragment implements F_StorageListView {

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    StorageListAdapter storageListAdapter;

    F_StorageListPresenter presenter;

    OnObjectItemClick<Storage> click;

    public StorageListFragment(){

    }

    public static StorageListFragment newInstance(){
        return new StorageListFragment();
    }

    public static StorageListFragment newInstance(boolean isForSelect){
        StorageListFragment storageListFragment = new StorageListFragment();
        Bundle bundle = new Bundle();
        bundle.putBoolean("select", isForSelect);
        storageListFragment.setArguments(bundle);
        return storageListFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new F_StorageListPresenterImpl(this);
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

    public void setOnObjectItemClickListener(OnObjectItemClick<Storage> click){
        this.click = click;
    }

    @Override
    public void setAdapter(RealmResults<Storage> realmResults) {
        if (getArguments() != null && getArguments().getBoolean("select")) {
            storageListAdapter = new StorageListAdapter(realmResults, getActivity(), click);
        } else {
            storageListAdapter = new StorageListAdapter(realmResults, getActivity());
        }
        recyclerView.setAdapter(storageListAdapter);
    }
}
