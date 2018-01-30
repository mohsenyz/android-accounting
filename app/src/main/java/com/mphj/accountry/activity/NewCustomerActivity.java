package com.mphj.accountry.activity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.mphj.accountry.R;
import com.mphj.accountry.interfaces.NewCustomerView;
import com.mphj.accountry.models.db.Customer;
import com.mphj.accountry.presenters.NewCustomerPresenter;
import com.mphj.accountry.presenters.NewCustomerPresenterImpl;
import com.mphj.accountry.utils.DaoManager;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class NewCustomerActivity extends BaseActivity implements NewCustomerView{

    @BindView(R.id.input_phone)
    EditText phone;

    @BindView(R.id.input_name)
    EditText name;

    @BindString(R.string.err_input_not_valid)
    String errInputNotValid;

    NewCustomerPresenter presenter;

    @BindView(R.id.submit)
    Button submit;

    @BindView(R.id.title)
    TextView title;

    int customerId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_customer);
        ButterKnife.bind(this);
        presenter = new NewCustomerPresenterImpl(this);
        customerId = getIntent().getIntExtra("id", -1);
        if (customerId != -1) {
            submit.setText("ویرایش");
            title.setText("ویرایش مشتری");
            Customer customer = DaoManager.session().getCustomerDao().load((long) customerId);
            phone.setText(customer.getPhone());
            name.setText(customer.getName());
        }
    }


    @Override
    public void finishActivity() {
        finish();
    }

    @Override
    public void invalidName() {
        name.setError(errInputNotValid);
    }

    @Override
    public void invalidPhone() {
        phone.setError(errInputNotValid);
    }


    @OnClick(R.id.submit)
    void onSubmit(){
        if (customerId == -1) {
            presenter.createCustomer(name.getText().toString(), phone.getText().toString());
        } else {
            presenter.updateCustomer(
                    name.getText().toString(),
                    phone.getText().toString(),
                    customerId
            );
        }
    }
}
