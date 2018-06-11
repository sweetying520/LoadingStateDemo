package com.dream.loadingstatedemo;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.dream.loadingstatedemo.method_one.MethodOneActivity;
import com.dream.loadingstatedemo.method_three.MethodThreeActivity;
import com.dream.loadingstatedemo.method_two.MethodTwoActivity;

/**
 * @author Administrator
 */
public class MainActivity extends AppCompatActivity {

    private Context mContext;
    private AlertDialog methodOneDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mContext = this;

        findViewById(R.id.btn_method_one).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initMethodOneDialog();
            }
        });


        findViewById(R.id.btn_method_two).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MethodTwoActivity.start(mContext);
            }
        });

        findViewById(R.id.btn_method_three).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MethodThreeActivity.start(mContext);
            }
        });
    }

    private void initMethodOneDialog() {
        if (methodOneDialog == null) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            methodOneDialog = builder.setTitle("提示")
                    .setMessage("是否使用Base里面的Toolbar")
                    .setPositiveButton("是", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            MethodOneActivity.start(mContext,true);
                        }
                    })
                    .setNegativeButton("否", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            MethodOneActivity.start(mContext,false);
                        }
                    }).create();

            methodOneDialog.show();
        }else {
            methodOneDialog.show();
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        methodOneDialog.dismiss();
    }
}
