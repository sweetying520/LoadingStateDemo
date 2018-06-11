package com.dream.loadingstatedemo.method_three;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import com.dream.loadingstatedemo.R;

import butterknife.BindView;

/**
 *
 * @author Administrator
 * @date 2018/6/11
 */

public class MethodThreeActivity extends BaseThreeActivity{

    @BindView(R.id.fab)
    FloatingActionButton fab;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_method_three;
    }

    @Override
    protected void initView() {
        initToolbar();
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MethodThreeActivity.this, "点击了Fab", Toast.LENGTH_SHORT).show();
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

    protected void initToolbar() {
        Toolbar mToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowTitleEnabled(false);
            getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        }
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        findViewById(R.id.btn_content_view).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
        });

        findViewById(R.id.btn_error_view).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
                                showError();
                            }
                        });
                    }
                }.start();
            }
        });

        findViewById(R.id.btn_empty_view).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
                                showEmpty();
                            }
                        });
                    }
                }.start();
            }
        });
    }

    public static void start(Context mContext){
        mContext.startActivity(new Intent(mContext,MethodThreeActivity.class));
    }
}
