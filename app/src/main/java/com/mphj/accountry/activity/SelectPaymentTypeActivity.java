package com.mphj.accountry.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioGroup;

import com.alirezaafkar.sundatepicker.DatePicker;
import com.alirezaafkar.sundatepicker.interfaces.DateSetListener;
import com.mphj.accountry.R;
import com.mphj.accountry.interfaces.SelectPaymentTypeView;
import com.mphj.accountry.models.db.Check;
import com.mphj.accountry.models.db.Transaction;
import com.mphj.accountry.presenters.SelectPaymentTypePresenter;
import com.mphj.accountry.presenters.SelectPaymentTypePresenterImpl;
import com.mphj.accountry.utils.ViewUtils;

import org.parceler.Parcels;

import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTouch;

public class SelectPaymentTypeActivity extends BaseActivity
        implements SelectPaymentTypeView, DateSetListener{


    public static final int SELECT_DATE = 1;

    @BindView(R.id.radio_group)
    RadioGroup radioGroup;

    @BindView(R.id.check_info)
    LinearLayout checkInfo;

    @BindView(R.id.container)
    LinearLayout container;

    @BindView(R.id.input_serial)
    EditText serial;

    @BindView(R.id.input_price)
    EditText price;

    @BindView(R.id.input_duedate)
    EditText duedate;

    @BindView(R.id.input_bank)
    EditText bank;

    @BindView(R.id.input_description)
    EditText description;

    long duedateTime;

    SelectPaymentTypePresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_payment_type);
        ButterKnife.bind(this);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (group.getCheckedRadioButtonId() == R.id.radio_button_check) {
                    showCheckInfo();
                } else {
                    hideCheckInfo();
                }
            }
        });
        presenter = new SelectPaymentTypePresenterImpl(this);
    }

    @OnTouch(R.id.input_duedate)
    boolean onDueDateFocusChange(View view, MotionEvent motionEvent) {
        if (ViewUtils.isInViewBound(view, motionEvent) && motionEvent.getAction() == MotionEvent.ACTION_UP) {
            DatePicker.Builder datePicker = new DatePicker.Builder()
                    .theme(R.style.DatePickerDialog)
                    .id(SELECT_DATE)
                    .date(Calendar.getInstance());
            DatePicker datePickerDialog = datePicker.build(this);
            datePickerDialog.show(getSupportFragmentManager(), "Date Picker");
        }
        return true;
    }

    @OnClick(R.id.submit)
    void onSubmit() {
        presenter.submitResult(
                (radioGroup.getCheckedRadioButtonId() == R.id.radio_button_check) ?
                        Transaction.PAYMENT_CHECK : Transaction.PAYMENT_CREDIT,
                serial.getText().toString(),
                price.getText().toString(),
                duedateTime,
                bank.getText().toString(),
                description.getText().toString()
        );
    }

    @Override
    public void showCheckInfo() {
        if (checkInfo.getVisibility() == View.VISIBLE)
            return;
        checkInfo.setVisibility(View.VISIBLE);
        checkInfo.setAlpha(0);
        checkInfo.animate()
                .alpha(1)
                .setDuration(500)
                .start();
    }

    @Override
    public void hideCheckInfo() {
        checkInfo.setVisibility(View.GONE);
    }

    @Override
    public void finishActivity(Check check, int paymentType) {
        Intent data = new Intent();
        if (check != null) {
            data.putExtra("check", Parcels.wrap(Check.class, check));
        }
        data.putExtra("type", paymentType);
        setResult(Activity.RESULT_OK, data);
        finish();
    }

    @Override
    public void onDateSet(int id, @Nullable Calendar calendar, int day, int month, int year) {
        duedate.setText("" + year + "/" + month + "/" + day);
        duedateTime = calendar.getTime().getTime();
        duedate.clearFocus();
    }
}
