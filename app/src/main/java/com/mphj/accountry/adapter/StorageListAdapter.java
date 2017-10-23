package com.mphj.accountry.adapter;

import android.support.design.widget.BottomSheetDialogFragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.mphj.accountry.R;
import com.mphj.accountry.dialog.StorageSettingDialog;
import com.mphj.accountry.models.db.Storage;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.RealmResults;

/**
 * Created by mphj on 10/20/2017.
 */

public class StorageListAdapter extends RecyclerView.Adapter<StorageListAdapter.StorageViewHolder> {

    RealmResults<Storage> list;
    FragmentActivity fragmentActivity;

    public StorageListAdapter(RealmResults<Storage> list, FragmentActivity fragmentActivity){
        this.list = list;
        this.fragmentActivity = fragmentActivity;
    }

    @Override
    public StorageViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.storage_list_item, viewGroup, false);
        return new StorageViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(StorageViewHolder viewHolder, int i) {
        final Storage storage = list.get(i);
        if (storage.getServerId() != 0)
            viewHolder.loading.setVisibility(View.GONE);
        viewHolder.text.setText(storage.getName());
        viewHolder.container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BottomSheetDialogFragment bottomSheetDialogFragment = StorageSettingDialog.create(storage.getName());
                bottomSheetDialogFragment.show(fragmentActivity.getSupportFragmentManager(), "");
            }
        });
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

        @BindView(R.id.container)
        public CardView container;

        public StorageViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

}
