package com.dream.loadingstatedemo.method_two;


import android.content.Context;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.view.View;
import android.widget.Toast;

import com.dream.loadingstatedemo.R;

import butterknife.BindView;

/**
 *
 * @author Administrator
 * @date 2018/6/11
 */

public  class MethodTwoActivity extends BaseTwoActivity {

    @BindView(R.id.fab)
    FloatingActionButton fab;

    @Override
    protected void initView() {
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MethodTwoActivity.this, "fab", Toast.LENGTH_SHORT).show();
            }
        });


        showLoading();
        new Thread() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        showContent();
                    }
                });
            }
        }.start();


    }

    @Override
    protected void reload() {
        Toast.makeText(this, "重新加载", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_method_two;
    }

    public static void start(Context mContext){
        mContext.startActivity(new Intent(mContext,MethodTwoActivity.class));
    }
}
