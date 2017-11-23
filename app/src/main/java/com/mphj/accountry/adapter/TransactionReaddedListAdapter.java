package com.mphj.accountry.adapter;

import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mphj.accountry.R;
import com.mphj.accountry.interfaces.OnObjectItemClick;
import com.mphj.accountry.models.db.TransactionReAdded;
import com.mphj.accountry.utils.LocaleUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by mphj on 11/10/2017.
 */

public class TransactionReaddedListAdapter extends RecyclerView.Adapter<TransactionReaddedListAdapter.TransactionReaddedListViewHolder>{
    List<TransactionReAdded> list;
    FragmentActivity fragmentActivity;
    OnObjectItemClick<TransactionReAdded> onObjectItemClick;

    public TransactionReaddedListAdapter(List<TransactionReAdded> list, FragmentActivity fragmentActivity){
        this.list = list;
        this.fragmentActivity = fragmentActivity;
    }

    public TransactionReaddedListAdapter(List<TransactionReAdded> list, FragmentActivity fragmentActivity, OnObjectItemClick<TransactionReAdded> click){
        this.list = list;
        this.fragmentActivity = fragmentActivity;
        this.onObjectItemClick = click;
    }

    @Override
    public TransactionReaddedListViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.transaction_readded_list_item, viewGroup, false);
        return new TransactionReaddedListViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(TransactionReaddedListViewHolder viewHolder, int i) {
        final TransactionReAdded transactionReAdded = list.get(i);
        viewHolder.title.setText(transactionReAdded.getDescription());
        viewHolder.icon.setImageResource(
                (transactionReAdded.getType() == TransactionReAdded.INC)? R.drawable.ic_plus : R.drawable.ic_remove
        );
        viewHolder.leftText.setText(LocaleUtils.englishNumberToArabic("" + (int) transactionReAdded.getPrice()) + " تومان");
        viewHolder.container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onObjectItemClick != null){
                    onObjectItemClick.onClick(v, transactionReAdded);
                    return;
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class TransactionReaddedListViewHolder extends RecyclerView.ViewHolder{


        @BindView(R.id.container)
        RelativeLayout container;

        @BindView(R.id.title)
        TextView title;

        @BindView(R.id.leftText)
        TextView leftText;

        @BindView(R.id.icon)
        ImageView icon;

        public TransactionReaddedListViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

}
