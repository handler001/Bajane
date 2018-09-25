package com.jiefutong.emall.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.jiefutong.emall.R;

public class MainSelectActivity extends BaseActivity implements View.OnClickListener {

    private Button mBtXxmall;
    private Button mBtCenter;
    private Button mBtTpc;
    private Button mBtHzmall;
    private Button mBtZymall;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_select);
        initView();
        settitleColor(R.color.main_sel);
    }

    private void initView() {
        mBtXxmall = (Button) findViewById(R.id.bt_xxmall);
        mBtCenter = (Button) findViewById(R.id.bt_center);
        mBtTpc = (Button) findViewById(R.id.bt_tpc);
        mBtHzmall = (Button) findViewById(R.id.bt_hzmall);
        mBtZymall = (Button) findViewById(R.id.bt_zymall);
        mBtXxmall.setOnClickListener(this);
        mBtCenter.setOnClickListener(this);
        mBtTpc.setOnClickListener(this);
        mBtHzmall.setOnClickListener(this);
        mBtZymall.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_xxmall:
                showToast("开发中");
                break;
            case R.id.bt_center:
                openActivity(new Intent(context, MainActivity.class).putExtra("position", 4));
                break;
            case R.id.bt_tpc:
                openActivity(new Intent(context, MainActivity.class).putExtra("position", 2));
                break;
            case R.id.bt_hzmall:
                openActivity(new Intent(context, MainActivity.class).putExtra("position", 1));
                break;
            case R.id.bt_zymall:
                openActivity(new Intent(context, MainActivity.class).putExtra("position", 0));
                break;
        }
    }
}
