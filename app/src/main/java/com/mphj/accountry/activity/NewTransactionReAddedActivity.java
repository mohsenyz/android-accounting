package com.mphj.accountry.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.RadioGroup;

import com.mphj.accountry.R;
import com.mphj.accountry.interfaces.NewTransactionReaddedView;
import com.mphj.accountry.models.db.TransactionReAdded;
import com.mphj.accountry.presenters.NewTransactionReaddedPresenter;
import com.mphj.accountry.presenters.NewTransactionReaddedPresenterImpl;

import org.parceler.Parcels;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class NewTransactionReAddedActivity extends BaseActivity implements NewTransactionReaddedView{

    @BindView(R.id.radio_group)
    RadioGroup radioGroup;


    @BindView(R.id.input_description)
    EditText description;

    @BindView(R.id.input_price)
    EditText price;

    @BindString(R.string.err_input_not_valid)
    String errInputNotValid;

    NewTransactionReaddedPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_transaction_readded);
        ButterKnife.bind(this);
        presenter = new NewTransactionReaddedPresenterImpl(this);
    }

    @Override
    public void invalidDescription() {
        description.setError(errInputNotValid);
    }

    @Override
    public void invalidPrice() {
        price.setError(errInputNotValid);
    }

    @OnClick(R.id.submit)
    void submit() {
        presenter.sendResult(description.getText().toString(),
                price.getText().toString(),
                (radioGroup.getCheckedRadioButtonId() == R.id.radio_button_dec) ? TransactionReAdded.DEC : TransactionReAdded.INC);
    }

    @Override
    public void finishActivity(TransactionReAdded result) {
        Intent i = new Intent();
        i.putExtra("readded", Parcels.wrap(TransactionReAdded.class, result));
        setResult(Activity.RESULT_OK, i);
        finish();
    }
}
