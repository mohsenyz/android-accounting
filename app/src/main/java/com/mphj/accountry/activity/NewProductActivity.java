package com.mphj.accountry.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.ImageView;

import com.mphj.accountry.R;
import com.mphj.accountry.interfaces.NewProductView;
import com.mphj.accountry.presenters.NewProductPresenter;
import com.mphj.accountry.presenters.NewProductPresenterImpl;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class NewProductActivity extends BaseActivity implements NewProductView {

    @BindView(R.id.input_name)
    EditText name;

    @BindView(R.id.input_serial)
    EditText serial;

    @BindView(R.id.input_price)
    EditText price;

    @BindView(R.id.input_off)
    EditText off;

    @BindView(R.id.serial_img)
    ImageView serialImage;

    @BindString(R.string.err_input_not_valid)
    String errInputNotValid;

    final Handler handler = new Handler();

    public static final int BARCODE_READER_REQUEST_CODE = 1;

    NewProductPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_product);
        ButterKnife.bind(this);
        serial.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                handler.removeCallbacksAndMessages(null);
                handler.postDelayed(typingStatusRunnable, 1000);
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });
        presenter = new NewProductPresenterImpl(this);
    }

    @Override
    public void invalidName() {
        name.setError(errInputNotValid);
    }

    @Override
    public void invalidSerial() {
        serial.setError(errInputNotValid);
    }

    @Override
    public void invalidPrice() {
        price.setError(errInputNotValid);
    }

    @Override
    public void invalidOff() {
        off.setError(errInputNotValid);
    }

    @Override
    public void setSerial(Bitmap bitmap) {
        serialImage.setImageBitmap(bitmap);
    }

    @Override
    public void setSerial(String serial) {
        this.serial.setText(serial);
    }

    @Override
    public void finishActivity() {
        finish();
    }

    @OnClick(R.id.submit)
    void onSubmit(){
        presenter.createProduct(
                name.getText().toString(),
                serial.getText().toString(),
                price.getText().toString(),
                off.getText().toString());
    }


    @OnClick(R.id.pick_from_camera)
    void onRequestCamera(){
        startActivityForResult(new Intent(this, BarcodeReaderActivity.class), BARCODE_READER_REQUEST_CODE);
    }

    final Runnable typingStatusRunnable = new Runnable() {
        @Override
        public void run() {
            presenter.generateBarcode(serial.getText().toString());
        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == BARCODE_READER_REQUEST_CODE) {
            if(resultCode == Activity.RESULT_OK){
                String serial = data.getStringExtra("serial");
                presenter.generateBarcode(serial);
            }
        }
    }
}
