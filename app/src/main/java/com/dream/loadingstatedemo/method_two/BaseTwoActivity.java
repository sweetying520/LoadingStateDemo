package com.dream.loadingstatedemo.method_two;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.FrameLayout;

import com.dream.loadingstatedemo.R;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Administrator on 2018/6/11.
 */

public abstract class BaseTwoActivity extends AppCompatActivity{

    private View rootView;

    private Unbinder unbinder;
    private View errorView;
    private View loadingView;
    private View emptyView;

    private static final int CONTENT_STATE = 0;
    private static final int LOADING_STATE = 1;
    private static final int ERROR_STATE = 2;
    private static final int EMPTY_STATE = 3;

    private int currentState = CONTENT_STATE;
    private FrameLayout flContent;
    private View contentView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        rootView = View.inflate(this, R.layout.activity_base_two, null);
        addContent();
        setContentView(rootView);
        initView();
    }

    protected abstract void initView();

    /**
     * 添加内容
     */
    private void addContent() {
        initToolbar();


        flContent = rootView.findViewById(R.id.fl_content);
        if (flContent == null) {
            return;
        }


        View.inflate(this, R.layout.item_error_view, flContent);
        View.inflate(this, R.layout.item_loading_view, flContent);
        View.inflate(this, R.layout.item_empty_view, flContent);
        View.inflate(this, getLayoutId(), flContent);
        contentView = flContent.findViewById(R.id.content_view);
        errorView = flContent.findViewById(R.id.fl_error);
        errorView.findViewById(R.id.btn_retry).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reload();
            }
        });

        loadingView = flContent.findViewById(R.id.fl_loading);
        emptyView = flContent.findViewById(R.id.fl_empty);
        //第一次进来显示有内容的布局
        errorView.setVisibility(View.GONE);
        emptyView.setVisibility(View.GONE);
        loadingView.setVisibility(View.GONE);


        unbinder = ButterKnife.bind(this, rootView);
    }

    protected void initToolbar() {
        Toolbar mToolbar = rootView.findViewById(R.id.toolbar);
        if (isHideToolbar()) {
            mToolbar.setVisibility(View.GONE);
            return;
        }
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

        rootView.findViewById(R.id.btn_content_view).setOnClickListener(new View.OnClickListener() {
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

        rootView.findViewById(R.id.btn_error_view).setOnClickListener(new View.OnClickListener() {
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

        rootView.findViewById(R.id.btn_empty_view).setOnClickListener(new View.OnClickListener() {
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


    /**
     * 加载中
     */
    protected void showLoading() {
        if (currentState == LOADING_STATE) {
            return;
        }
        hideCurrentState();
        currentState = LOADING_STATE;
        loadingView.setVisibility(View.VISIBLE);
    }

    /**
     * 加载失败的状态
     */
    protected void showError() {
        if (currentState == ERROR_STATE) {
            return;
        }
        hideCurrentState();
        currentState = ERROR_STATE;
        errorView.setVisibility(View.VISIBLE);
    }

    /**
     * 为空的布局
     */
    protected void showEmpty() {
        if (currentState == EMPTY_STATE) {
            return;
        }
        hideCurrentState();
        currentState = EMPTY_STATE;
        emptyView.setVisibility(View.VISIBLE);
    }

    protected void showContent() {
        if (currentState == CONTENT_STATE) {
            return;
        }
        hideCurrentState();
        currentState = CONTENT_STATE;
        contentView.setVisibility(View.VISIBLE);
    }


    private void hideCurrentState() {
        switch (currentState) {
            case CONTENT_STATE:
                contentView.setVisibility(View.GONE);
                break;
            case LOADING_STATE:
                loadingView.setVisibility(View.GONE);
                break;
            case ERROR_STATE:
                errorView.setVisibility(View.GONE);
                break;
            case EMPTY_STATE:
                emptyView.setVisibility(View.GONE);
                break;
            default:
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(unbinder != null){
            unbinder.unbind();
        }
    }

    protected void reload() {

    }

    /**
     * 是否显示Base的Toolbar 如果不显示则在上层用一个假的Toolbar代替
     *
     * @return boolean
     */
    protected boolean isHideToolbar() {
        return false;
    }


    protected abstract int getLayoutId();
}
