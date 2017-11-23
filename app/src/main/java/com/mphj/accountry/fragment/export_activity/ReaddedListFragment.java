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
import com.mphj.accountry.activity.AlertActivity;
import com.mphj.accountry.activity.NewTransactionReAddedActivity;
import com.mphj.accountry.adapter.TransactionReaddedListAdapter;
import com.mphj.accountry.interfaces.OnObjectItemClick;
import com.mphj.accountry.interfaces.fragment.export_activity.ReaddedListView;
import com.mphj.accountry.models.db.TransactionReAdded;
import com.mphj.accountry.presenters.fragment.export_activity.ReaddedListPresenter;
import com.mphj.accountry.presenters.fragment.export_activity.ReaddedListPresenterImpl;

import org.parceler.Parcels;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by mphj on 11/10/2017.
 */

public class ReaddedListFragment extends Fragment implements
        ReaddedListView, OnObjectItemClick<TransactionReAdded> {

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    @BindView(R.id.fab)
    FloatingActionButton fab;

    ReaddedListPresenter presenter;

    TransactionReAdded pendingReadded;

    public static final int SELECT_READDED = 2, REMOVE_READDED = 3;

    public static ReaddedListFragment newInstance() {
        return new ReaddedListFragment();
    }

    public static ProductListFragment newInstance(boolean isForSelect) {
        ProductListFragment exportActivityProductListFragment = new ProductListFragment();
        Bundle bundle = new Bundle();
        bundle.putBoolean("select", isForSelect);
        exportActivityProductListFragment.setArguments(bundle);
        return exportActivityProductListFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new ReaddedListPresenterImpl(this);
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

    public void setupRecyclerView() {
        final LinearLayoutManager layoutManager = new LinearLayoutManager(recyclerView.getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
    }

    @Override
    public void loadList(List<TransactionReAdded> list) {
        TransactionReaddedListAdapter transactionReaddedListAdapter = new TransactionReaddedListAdapter(list, getActivity(), this);
        recyclerView.setAdapter(transactionReaddedListAdapter);
    }

    @Override
    public void addNew(TransactionReAdded transactionReAdded) {
        presenter.addNew(transactionReAdded);
    }

    @Override
    public List<TransactionReAdded> getList() {
        return presenter.getList();
    }

    @Override
    public void onClick(View v, TransactionReAdded object) {
        pendingReadded = object;
        startActivityForResult(new Intent(getActivity(), AlertActivity.class), REMOVE_READDED);
    }


    @OnClick(R.id.fab)
    void onFabClick() {
        startActivityForResult(new Intent(getActivity(), NewTransactionReAddedActivity.class), SELECT_READDED);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == SELECT_READDED) {
            if (resultCode == Activity.RESULT_OK) {
                TransactionReAdded transactionReAdded = Parcels.unwrap(data.getParcelableExtra("readded"));
                addNew(transactionReAdded);
            }
        }

        if (requestCode == REMOVE_READDED && pendingReadded != null) {
            if (resultCode == AlertActivity.SUBMIT) {
                presenter.delete(pendingReadded);
            }
        }
    }
}