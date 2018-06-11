package com.dream.loadingstatedemo.method_three;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import com.dream.loadingstatedemo.R;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 *
 * @author Administrator
 * @date 2018/6/11
 */

public abstract class BaseThreeActivity extends AppCompatActivity{

    private ViewGroup contentView;
    private View errorView;
    private View loadingView;
    private View emptyView;
    private Unbinder unbinder;


    private static final int CONTENT_STATE = 0;
    private static final int LOADING_STATE = 1;
    private static final int ERROR_STATE = 2;
    private static final int EMPTY_STATE = 3;

    private int currentState = CONTENT_STATE;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        addContent();
        initView();
    }

    protected abstract int getLayoutId();

    protected abstract void initView();

    private void addContent() {
        contentView = findViewById(R.id.content_view);
        if (contentView == null) {
            throw new IllegalStateException(
                    "The subclass of RootActivity must contain a View named 'mNormalView'.");
        }
        if (!(contentView.getParent() instanceof ViewGroup)) {
            throw new IllegalStateException(
                    "mNormalView's ParentView should be a ViewGroup.");
        }

        ViewGroup viewParent = (ViewGroup) contentView.getParent();
        View.inflate(this,R.layout.item_loading_view,viewParent);
        View.inflate(this,R.layout.item_error_view,viewParent);
        View.inflate(this,R.layout.item_empty_view,viewParent);
        loadingView = viewParent.findViewById(R.id.fl_loading);
        errorView = viewParent.findViewById(R.id.fl_error);
        emptyView = viewParent.findViewById(R.id.fl_empty);
        errorView.findViewById(R.id.btn_retry).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reload();
            }
        });
        loadingView.setVisibility(View.GONE);
        errorView.setVisibility(View.GONE);
        emptyView.setVisibility(View.GONE);

        unbinder = ButterKnife.bind(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(unbinder != null){
            unbinder.unbind();
        }
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

    protected void reload(){}


}
