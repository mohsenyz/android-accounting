package com.mphj.accountry.activity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.support.design.widget.Snackbar;
import android.support.transition.TransitionManager;
import android.support.v7.widget.CardView;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.mphj.accountry.R;
import com.mphj.accountry.interfaces.LoginView;
import com.mphj.accountry.models.LoginModel;
import com.mphj.accountry.models.rest.LoginRest;
import com.mphj.accountry.models.rest.MainRest;
import com.mphj.accountry.presenters.LoginPresenter;
import com.mphj.accountry.presenters.LoginPresenterImpl;
import com.mphj.accountry.utils.ViewAnimator;
import com.mphj.accountry.utils.ViewUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import es.dmoral.toasty.Toasty;

public class LoginActivity extends BaseActivity implements LoginView, MainRest.StatusListener {

    @BindView(R.id.login_container)
    CardView loginContainer;

    @BindView(R.id.forget_password_container)
    CardView forgetPasswordContainer;

    @BindView(R.id.container)
    LinearLayout container;

    @BindView(R.id.login)
    Button login;

    @BindView(R.id.input_password)
    EditText inputPassword;

    @BindView(R.id.input_username)
    EditText inputUsername;

    @BindView(R.id.input_email)
    EditText inputEmail;

    LoginPresenter loginPresenter;

    boolean isAnimated;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        loginPresenter = new LoginPresenterImpl(this);
        showLoginContainer();
        initView();
        MainRest.getConnected(this);
    }

    public void initView(){
        forgetPasswordContainer.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                forgetPasswordContainer.setVisibility(View.GONE);
            }
        });
        inputPassword.setVisibility(View.GONE);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putCharSequence("username", inputUsername.getText().toString());
        outState.putCharSequence("password", inputPassword.getText().toString());
        outState.putCharSequence("email", inputEmail.getText().toString());
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        inputUsername.setText(savedInstanceState.getCharSequence("username"));
        inputPassword.setText(savedInstanceState.getCharSequence("password"));
        inputEmail.setText(savedInstanceState.getCharSequence("email"));
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        if (hasFocus && !isAnimated){
            showLoginContainer();
            isAnimated = true;
        }
    }


    @Override
    public void showLoginContainer() {
        ViewAnimator.showWithFadeAndTranslationY(loginContainer);
    }

    @Override
    public void hideLoginContainer() {
        ViewAnimator.hideWithFadeAndTranslationY(loginContainer);
    }

    @Override
    public void showForgetPasswordContainer() {
        ViewAnimator.showWithFadeAndTranslationY(forgetPasswordContainer);
    }

    @Override
    public void hideForgetPasswordContainer() {
        ViewAnimator.hideWithFadeAndTranslationY(forgetPasswordContainer);
    }

    @Override
    public void showProgressBar() {
        login.setEnabled(false);
        login.setAlpha(0.7f);
    }

    @Override
    public void hideProgressBar() {
        login.setEnabled(true);
        login.setAlpha(1f);
    }

    @Override
    public void networkFailed(){
        Toasty.error(this, "اتصال به سرور برقرار نشد").show();
    }

    @Override
    public void loginSucceed() {
        startActivity(new Intent(this, DashboardActivity.class));
    }

    @Override
    public void badUsernameOrPassword() {
        Toasty.error(this, "نام کاربری یا کلمه ی عبور اشتباه است").show();
    }

    @Override
    public void badEmail() {
        Toasty.error(this, "ایمیل اشتباه است").show();
    }

    @Override
    public void unknownProblem(int status) {
        Toasty.error(this,  "متاسفانه مشکلی رخ داده است").show();
    }


    boolean isFirstAttempt= true;

    @OnClick(R.id.login)
    void onLoginClick(){
        if (!isFirstAttempt) {
            loginPresenter.login(inputUsername.getText().toString(), inputPassword.getText().toString());
        } else {
            LoginRest.sendCode(inputUsername.getText().toString(), new LoginRest.LoginListener() {
                @Override
                public void onSuccess(LoginModel loginModel) {
                    isFirstAttempt = false;
                    TransitionManager.beginDelayedTransition(loginContainer);
                    inputPassword.setVisibility(View.VISIBLE);
                }

                @Override
                public void onFailed(LoginModel loginModel) {
                    login.setVisibility(View.VISIBLE);
                    unknownProblem(loginModel.getStatus());
                }
            });
        }
    }


    @Override
    protected void onResume() {
        super.onResume();
        loginPresenter.onResume();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        loginPresenter.onDestroy();
    }


    @Override
    public void onSuccess(int status) {}


    @Override
    public void onError(Throwable t) {
        Snackbar snackbar = Snackbar.make(getWindow().getDecorView(), "عدم توانایی اتصال به اینترنت", Snackbar.LENGTH_LONG)
                .setAction("روشن کردن دیتا", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity(new Intent(Settings.ACTION_WIRELESS_SETTINGS));
                    }
                });
        ViewUtils.prepareSnackbar(snackbar, 12);
        snackbar.show();
    }
}
