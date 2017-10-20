package com.mphj.accountry.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.mphj.accountry.R;
import com.mphj.accountry.models.db.Storage;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.RealmResults;

/**
 * Created by mphj on 10/20/2017.
 */

public class StorageListAdapter extends RecyclerView.Adapter<StorageListAdapter.StorageViewHolder> {

    RealmResults<Storage> list;

    public StorageListAdapter(RealmResults<Storage> list){
        this.list = list;
    }

    @Override
    public StorageViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.storage_list_item, viewGroup, false);
        return new StorageViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(StorageViewHolder viewHolder, int i) {
        Storage storage = list.get(i);
        if (storage.getServerId() != 0)
            viewHolder.loading.setVisibility(View.GONE);
        viewHolder.text.setText(storage.getName());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class StorageViewHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.text)
        public TextView text;

        @BindView(R.id.loading)
        public ImageView loading;

        public StorageViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

}
