package com.jiefutong.emall.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.jiefutong.emall.R;

public class TpcFriendShareActivity extends BaseActivity implements View.OnClickListener {

    private ImageView mIvBack;
    private TextView mTvTitle;
    private ImageView mIvCode;
    private Button mBtCopy;
    private TextView mTvShareHistory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tpc_friend_share);
        initView();
        settitlewhite();
    }

    private void initView() {
        mIvBack = (ImageView) findViewById(R.id.iv_back);
        mIvBack.setOnClickListener(this);
        mTvTitle = (TextView) findViewById(R.id.tv_title);
        mTvTitle.setText("分享好友");
        mIvCode = (ImageView) findViewById(R.id.iv_code);
        mBtCopy = (Button) findViewById(R.id.bt_copy);
        mTvShareHistory = (TextView) findViewById(R.id.tv_share_history);

        mBtCopy.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_copy:

                break;
            case R.id.iv_back:
                finish();
                break;
        }
    }
}
