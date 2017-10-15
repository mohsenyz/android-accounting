package com.mphj.accountry.activity;

import android.animation.Animator;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.CardView;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.mphj.accountry.R;
import com.mphj.accountry.activity.interfaces.LoginView;
import com.mphj.accountry.activity.presenters.LoginPresenter;
import com.mphj.accountry.activity.presenters.LoginPresenterImpl;
import com.mphj.accountry.activity.utils.ViewAnimator;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends BaseActivity implements LoginView {

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
        // Why to use presenter when it makes works harder??!!
        showLoginContainer();
    }

    public void initView(){
        forgetPasswordContainer.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                forgetPasswordContainer.setVisibility(View.GONE);
            }
        });
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

    }

    @Override
    public void hideProgressBar() {

    }

    @Override
    public void networkFailed(){

    }

    @Override
    public void loginSucceed() {
        startActivity(new Intent(this, MainActivity.class));
    }

    @Override
    public void badUsernameOrPassword() {

    }

    @Override
    public void badEmail() {

    }

    @Override
    public void unknownProblem(int status) {

    }

    @OnClick(R.id.login)
    void onLoginClick(){
        loginPresenter.login(inputUsername.getText().toString(), inputPassword.getText().toString());
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
}
