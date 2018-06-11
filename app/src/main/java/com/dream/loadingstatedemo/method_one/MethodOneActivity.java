package com.dream.loadingstatedemo.method_one;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.dream.loadingstatedemo.R;

import java.lang.reflect.Method;


/**
 * @author Administrator
 * @date 2018/6/11
 */

public class MethodOneActivity extends BaseOneActivity {


    private boolean isUserBaseToolbar;

    @Override
    protected int getlayoutId() {
        return R.layout.activity_method_one;
    }

    @Override
    protected void initView() {
        //如果不使用base布局的Toolbar 则可以定义一个假的Toolbar

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


    /**
     * 初始化自己定义的toolbar
     */
    @Override
    protected void initToolbar() {
        isUserBaseToolbar = getIntent().getBooleanExtra("params", false);
        if (isUserBaseToolbar) {
            findViewById(R.id.my_toolbar).setVisibility(View.GONE);
        } else {
            TextView tvToolbarTitle = findViewById(R.id.tv_title);
            tvToolbarTitle.setText("MethodOneActivity");
            findViewById(R.id.iv_back).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });
        }
        super.initToolbar();
    }

    public static void start(Context mContext, boolean isUseBaseToolbar) {
        Intent intent = new Intent(mContext, MethodOneActivity.class);
        intent.putExtra("params", isUseBaseToolbar);
        mContext.startActivity(intent);
    }


    @Override
    protected void reload() {
        Toast.makeText(this, "重新加载数据", Toast.LENGTH_SHORT).show();
    }

    /**
     * 隐藏base的toolbar 定义一个假的toolbar
     *
     * @return
     */
    @Override
    protected boolean isHideToolbar() {
        if (isUserBaseToolbar) {
            return false;
        } else {
            return true;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_method_one, menu);
        return true;
    }

    /**
     * 让菜单同时显示图标和文字
     *
     * @param featureId Feature id
     * @param menu Menu
     * @return menu if opened
     */
    @Override
    public boolean onMenuOpened(int featureId, Menu menu) {
        if (menu != null) {
            if ("MenuBuilder".equalsIgnoreCase(menu.getClass().getSimpleName())) {
                try {
                    @SuppressLint("PrivateApi")
                    Method method = menu.getClass().getDeclaredMethod("setOptionalIconsVisible", Boolean.TYPE);
                    method.setAccessible(true);
                    method.invoke(menu, true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return super.onMenuOpened(featureId, menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.search:
                Toast.makeText(this, "搜索", Toast.LENGTH_SHORT).show();
                break;
            default:
                break;
        }
        return true;
    }
}
