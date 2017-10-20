package com.mphj.accountry.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.mphj.accountry.R;
import com.mphj.accountry.models.db.Customer;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.RealmResults;

/**
 * Created by mphj on 10/20/2017.
 */

public class CustomerListAdapter extends RecyclerView.Adapter<CustomerListAdapter.CustomerViewHolder> {

    RealmResults<Customer> list;

    public CustomerListAdapter(RealmResults<Customer> list){
        this.list = list;
    }

    @Override
    public CustomerViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.customer_list_item, viewGroup, false);
        return new CustomerViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(CustomerViewHolder viewHolder, int i) {
        Customer customer = list.get(i);
        if (customer.getServerId() != 0)
            viewHolder.loading.setVisibility(View.GONE);
        viewHolder.text.setText(customer.getName());
        viewHolder.subText.setText(customer.getPhone());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class CustomerViewHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.text)
        public TextView text;

        @BindView(R.id.subText)
        public TextView subText;

        @BindView(R.id.loading)
        public ImageView loading;

        public CustomerViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

}
