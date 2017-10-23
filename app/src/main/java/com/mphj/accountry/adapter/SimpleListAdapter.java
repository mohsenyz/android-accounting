package com.mphj.accountry.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.mphj.accountry.R;
import com.mphj.accountry.models.SimpleListModel;
import com.mphj.accountry.views.BorderedCard;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by mphj on 10/23/2017.
 */

public class SimpleListAdapter extends RecyclerView.Adapter<SimpleListAdapter.SimpleViewHolder> {

    List<SimpleListModel> list;

    public SimpleListAdapter(List<SimpleListModel> list){
        this.list = list;
    }

    @Override
    public SimpleViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.simple_list_item, viewGroup, false);
        return new SimpleViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(SimpleViewHolder viewHolder, int i) {
        SimpleListModel model = list.get(i);
        if (model.getIcon() != 0){
            viewHolder.icon.setVisibility(View.VISIBLE);
            viewHolder.icon.setImageResource(model.getIcon());
        } else if (model.getIconBitmap() != null){
            viewHolder.icon.setVisibility(View.VISIBLE);
            viewHolder.icon.setImageBitmap(model.getIconBitmap());
        }
        viewHolder.title.setText(model.getTitle());
        viewHolder.container.setOnClickListener(model.getOnClickListener());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class SimpleViewHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.title)
        public TextView title;

        @BindView(R.id.icon)
        public ImageView icon;

        @BindView(R.id.container)
        public BorderedCard container;

        public SimpleViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

}