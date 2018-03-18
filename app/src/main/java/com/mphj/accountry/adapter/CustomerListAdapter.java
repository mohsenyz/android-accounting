package com.mphj.accountry.adapter;

import android.content.Intent;
import android.net.Uri;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.mphj.accountry.R;
import com.mphj.accountry.activity.NewCustomerActivity;
import com.mphj.accountry.dialog.CustomerSettingDialog;
import com.mphj.accountry.interfaces.OnObjectItemClick;
import com.mphj.accountry.models.db.Customer;
import com.mphj.accountry.utils.LocaleUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by mphj on 10/20/2017.
 */

public class CustomerListAdapter extends RecyclerView.Adapter<CustomerListAdapter.CustomerViewHolder> {

    List<Customer> list;

    FragmentActivity fragmentActivity;

    OnObjectItemClick<Customer> onObjectItemClick;

    public CustomerListAdapter(List<Customer> list, FragmentActivity fragmentActivity){
        this.list = list;
        this.fragmentActivity = fragmentActivity;
    }

    public CustomerListAdapter(List<Customer> list, FragmentActivity fragmentActivity, OnObjectItemClick<Customer> click){
        this.list = list;
        this.fragmentActivity = fragmentActivity;
        this.onObjectItemClick = click;
    }

    @Override
    public CustomerViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.customer_list_item, viewGroup, false);
        return new CustomerViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(CustomerViewHolder viewHolder, int i) {
        final Customer customer = list.get(i);
        if (customer.getServerId() != 0)
            viewHolder.loading.setVisibility(View.GONE);
        viewHolder.subText.setText(Html.fromHtml(
                viewHolder.text.getResources().getString(R.string.html_phone_eq).replace("xxx", LocaleUtils.englishNumberToArabic(customer.getPhone()))));
        viewHolder.text.setText(customer.getName());
        viewHolder.container.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                BottomSheetDialogFragment bottomSheetDialogFragment = CustomerSettingDialog.create(customer);
                bottomSheetDialogFragment.show(fragmentActivity.getSupportFragmentManager(), "");
                return true;
            }
        });
        viewHolder.container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onObjectItemClick != null){
                    onObjectItemClick.onClick(v, customer);
                }
            }
        });
        viewHolder.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(v.getContext(), NewCustomerActivity.class);
                i.putExtra("id", customer.getId().intValue());
                v.getContext().startActivity(i);
            }
        });
        viewHolder.call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", customer.getPhone(), null));
                v.getContext().startActivity(i);
            }
        });
        viewHolder.msg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse("sms:" + customer.getPhone()));
                v.getContext().startActivity(i);
            }
        });
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

        @BindView(R.id.container)
        CardView container;

        @BindView(R.id.bottombar_call)
        TextView call;

        @BindView(R.id.bottombar_msg)
        TextView msg;

        @BindView(R.id.bottombar_edit)
        TextView edit;

        public CustomerViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

}
