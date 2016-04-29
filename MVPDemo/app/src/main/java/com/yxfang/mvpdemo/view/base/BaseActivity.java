package com.yxfang.mvpdemo.view.base;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.yxfang.mvpdemo.bean.common.Result;

import butterknife.ButterKnife;

/**
 * User: yxfang
 * Date: 2016-04-29
 * Time: 11:00
 * ------------- Description -------------
 * ---------------------------------------
 */
public abstract class BaseActivity extends AppCompatActivity implements IBaseView
{

    protected ProgressDialog progressDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        onPreInject();

        // View 注入
        ButterKnife.bind(this);

        init(savedInstanceState);
    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();

        // 销毁view 注入
        ButterKnife.unbind(this);
    }

    protected abstract void onPreInject();

    protected abstract void init(@Nullable Bundle savedInstanceState);

    @Override
    public void showTip(String msg)
    {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void loadFailure(String errorMsg)
    {

    }

    @Override
    public void loadSuccess(Result result)
    {

    }

    @Override
    public void showLoadingDialog()
    {
        if (progressDialog == null)
        {
            progressDialog = new ProgressDialog(this);
        }
        if (!isFinishing() && !progressDialog.isShowing())
        {
            progressDialog.show();
        }
    }

    @Override
    public void closeLoadingDialog()
    {
        if (progressDialog != null && progressDialog.isShowing())
        {
            progressDialog.dismiss();
        }
    }
}
