package com.mphj.accountry.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.mphj.accountry.R;
import com.mphj.accountry.interfaces.NewProductView;
import com.mphj.accountry.models.db.Category;
import com.mphj.accountry.models.db.CategoryDao;
import com.mphj.accountry.models.db.Product;
import com.mphj.accountry.models.db.ProductPriceDao;
import com.mphj.accountry.presenters.NewProductPresenter;
import com.mphj.accountry.presenters.NewProductPresenterImpl;
import com.mphj.accountry.utils.DaoManager;
import com.mphj.accountry.utils.ViewUtils;

import org.parceler.Parcels;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTextChanged;
import butterknife.OnTouch;

public class NewProductActivity extends BaseActivity implements NewProductView {

    @BindView(R.id.input_name)
    EditText name;

    @BindView(R.id.input_serial)
    EditText serial;

    @BindView(R.id.input_price)
    EditText price;

    @BindView(R.id.input_customer_price)
    EditText customerPrice;

    @BindView(R.id.input_category)
    EditText category;

    @BindView(R.id.input_count)
    EditText count;

    @BindView(R.id.serial_img)
    ImageView serialImage;

    @BindView(R.id.title)
    TextView title;

    @BindView(R.id.submit)
    Button submit;

    @BindString(R.string.err_input_not_valid)
    String errInputNotValid;

    private Category productCategory;

    final Handler handler = new Handler();

    public static final int BARCODE_READER_REQUEST_CODE = 1, SELECT_CATEGORY_REQUEST_CODE = 2;

    NewProductPresenter presenter;

    int productId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_product);
        ButterKnife.bind(this);
        presenter = new NewProductPresenterImpl(this);
        productId = getIntent().getIntExtra("id", -1);
        if (productId != -1) {
            title.setText("ویرایش محصول");
            submit.setText("ویرایش");
            Product product = DaoManager.session().getProductDao().load((long) productId);
            product.setCurrentProductPrice(
                    DaoManager.session().getProductPriceDao().queryBuilder().where(
                            ProductPriceDao.Properties.ProductId.eq(productId)
                    ).orderDesc(ProductPriceDao.Properties.CreatedAt).limit(1).list().get(0)
            );
            name.setText(product.getName());
            setSerial(product.getToken());
            price.setText(String.valueOf((int) product.getCurrentProductPrice().getPrice()));
            customerPrice.setText(String.valueOf((int) product.getCurrentProductPrice().getCustomerPrice()));
            Category category = DaoManager.session().getCategoryDao().queryBuilder()
                    .where(CategoryDao.Properties.Id.eq(product.getCategoryId()))
                    .unique();
            setCategory(category);
            count.setText(String.valueOf(product.getCount()));
            count.setEnabled(false);
        }
    }

    @OnTextChanged(R.id.input_serial)
    void onSerialTextChange() {
        handler.removeCallbacksAndMessages(null);
        handler.postDelayed(typingStatusRunnable, 1000);
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
    public void invalidCustomerPrice() {
        customerPrice.setError(errInputNotValid);
    }

    @Override
    public void invalidCategory() {
        category.setError(errInputNotValid);
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
    public void invalidCount() {
        count.setError(errInputNotValid);
    }

    @Override
    public void setCategory(Category category) {
        this.productCategory = category;
        this.category.setText(category.getName());
    }

    @Override
    public void finishActivity() {
        finish();
    }

    @OnClick(R.id.submit)
    void onSubmit(){
        if (productId == -1) {
            presenter.createProduct(
                    name.getText().toString(),
                    serial.getText().toString(),
                    price.getText().toString(),
                    customerPrice.getText().toString(),
                    productCategory,
                    count.getText().toString()
            );
        } else {
            presenter.updateProduct(
                    name.getText().toString(),
                    serial.getText().toString(),
                    price.getText().toString(),
                    customerPrice.getText().toString(),
                    productCategory,
                    count.getText().toString(),
                    productId
            );
        }
    }


    @OnClick(R.id.pick_from_camera)
    void onRequestCamera(){
        startActivityForResult(new Intent(this, BarcodeReaderActivity.class), BARCODE_READER_REQUEST_CODE);
    }

    @OnTouch(R.id.input_category)
    boolean onRequestCategory(View view, MotionEvent motionEvent) {
        if (ViewUtils.isInViewBound(view, motionEvent) && motionEvent.getAction() == MotionEvent.ACTION_UP)
            startActivityForResult(new Intent(this, SelectCategoryActivity.class), SELECT_CATEGORY_REQUEST_CODE);
        return true;
    }

    @OnClick(R.id.pick_random)
    void onRequestRandom(){
        presenter.generateBarcode();
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
                setSerial(serial);
            }
        }

        if (requestCode == SELECT_CATEGORY_REQUEST_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                Category category = Parcels.unwrap(data.getParcelableExtra("category"));
                setCategory(category);
            }
        }
    }
}
