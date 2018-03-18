package com.mphj.accountry.presenters;

import android.text.TextUtils;

import com.mphj.accountry.interfaces.NewCustomerView;
import com.mphj.accountry.models.db.Customer;
import com.mphj.accountry.models.db.CustomerDao;
import com.mphj.accountry.utils.DaoManager;
import com.mphj.accountry.utils.GsonHelper;
import com.mphj.accountry.utils.LogBuilder;


/**
 * Created by mphj on 10/20/2017.
 */

public class NewCustomerPresenterImpl implements NewCustomerPresenter {

    NewCustomerView view;

    public NewCustomerPresenterImpl(NewCustomerView view){
        this.view = view;
    }

    @Override
    public void onResume() {

    }

    @Override
    public void onDestroy() {

    }

    @Override
    public void createCustomer(String name, String phone) {
        if (TextUtils.isEmpty(name)) {
            view.invalidName();
            return;
        }

        if (TextUtils.isEmpty(phone)) {
            view.invalidPhone();
            return;
        }

        CustomerDao customerDao = DaoManager.session().getCustomerDao();

        Customer customer = new Customer();
        customer.setName(name);
        customer.setPhone(phone);
        customer.setCreatedAt(System.currentTimeMillis() / 1000L);
        customerDao.save(customer);

        try {
            LogBuilder.create(Customer.class)
                    .id(customer.getId().intValue())
                    .object(GsonHelper.toJson(customer.reportDiff(null)))
                    .build()
                    .save();
        } catch (Exception e) {
            e.printStackTrace();
        }

        view.finishActivity();
    }


    @Override
    public void updateCustomer(String name, String phone, int id) {
        if (TextUtils.isEmpty(name)) {
            view.invalidName();
            return;
        }

        if (TextUtils.isEmpty(phone)) {
            view.invalidPhone();
            return;
        }

        CustomerDao customerDao = DaoManager.session().getCustomerDao();

        Customer customer = customerDao.load((long) id);
        Customer oldCustomer = null;
        try {
            oldCustomer = (Customer) customer.clone();
        } catch (Exception e) {
            e.printStackTrace();
        }

        customer.setName(name);
        customer.setPhone(phone);
        customer.setCreatedAt(System.currentTimeMillis() / 1000L);
        customerDao.save(customer);

        try {
            LogBuilder.update(Customer.class)
                    .id(customer.getId().intValue())
                    .object(GsonHelper.toJson(customer.reportDiff(oldCustomer)))
                    .build();
        } catch (Exception e) {
            e.printStackTrace();
        }

        view.finishActivity();
    }
}
