package com.yxfang.mvpdemo.view.user;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.EditText;

import com.yxfang.mvpdemo.R;
import com.yxfang.mvpdemo.bean.common.Result;
import com.yxfang.mvpdemo.presenter.user.UserLoginPresenter;
import com.yxfang.mvpdemo.view.base.BaseActivity;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * User: yxfang
 * Date: 2016-04-29
 * Time: 10:59
 * ------------- Description -------------
 * ---------------------------------------
 */
public class UserLoginActivity extends BaseActivity implements IUserLoginView
{
    @Bind(R.id.edt_account)
    EditText edtAccount;

    @Bind(R.id.edt_pwd)
    EditText edtPwd;

    private UserLoginPresenter loginPresenter;

    @Override
    protected void onPreInject()
    {
        super.setContentView(R.layout.activity_login);
    }

    @Override
    protected void init(@Nullable Bundle savedInstanceState)
    {
        loginPresenter = new UserLoginPresenter(this);
    }

    @OnClick(R.id.btn_login)
    public void onClick(View view)
    {
        loginPresenter.login(this, getAccount(), getPwd());
    }

    @Override
    public String getAccount()
    {
        return edtAccount.getText().toString().trim();
    }

    @Override
    public String getPwd()
    {
        return edtPwd.getText().toString().trim();
    }

    @Override
    public void loadSuccess(Result result)
    {
        super.loadSuccess(result);
    }

    @Override
    public void loadFailure(String errorMsg)
    {
        super.loadFailure(errorMsg);
        showTip(errorMsg);
    }
}
