package com.mphj.wimagepicker.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mphj.wimagepicker.R;
import com.mphj.wimagepicker.model.WimageItem;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by mphj on 12/21/17.
 */

public class WImageListAdapter extends RecyclerView.Adapter<WImageListAdapter.SimpleViewHolder> {

    List<WimageItem> list;
    WimageItem.OnItemClickListener onItemClickListener;

    public WImageListAdapter(List<WimageItem> list){
        this.list = list;
    }

    public void setOnItemClickListener(WimageItem.OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public SimpleViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.wimage_sublist_item, viewGroup, false);
        return new SimpleViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final SimpleViewHolder viewHolder, final int i) {
        final WimageItem model = list.get(i);
        viewHolder.image.setBorderColor(model.borderColor);
        if (model.imageResource != -1) {
            viewHolder.image.setImageResource(model.imageResource);
        } else {
            viewHolder.image.setImageBitmap(model.imageBitmap);
        }
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClickListener.onItemClick(model, viewHolder, i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class SimpleViewHolder extends RecyclerView.ViewHolder{

        public final CircleImageView image;

        public SimpleViewHolder(View itemView) {
            super(itemView);
            image = (CircleImageView) itemView.findViewById(R.id.image);
        }
    }

}
