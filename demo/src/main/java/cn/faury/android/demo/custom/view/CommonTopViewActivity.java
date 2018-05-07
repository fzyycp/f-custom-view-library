package cn.faury.android.demo.custom.view;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import cn.faury.android.library.view.custom.CommonTopView;

public class CommonTopViewActivity extends Activity implements CommonTopView.InitViews {
    CommonTopView commonTopView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_common_top_view);
        commonTopView = findViewById(R.id.common_top_view_ctv);
        commonTopView.initViews(this);
    }

    @Override
    public void initCommonTopLayout(LinearLayout layout) {

    }

    @Override
    public void initCommonTopLeftIv(ImageView leftIv) {

    }

    @Override
    public void initCommonTopLeftTv(TextView leftTv) {
        leftTv.setText("返回");
        leftTv.setVisibility(View.VISIBLE);
    }

    @Override
    public void initCommonTopCenterTv(TextView centerTv) {
        centerTv.setText("标题");
    }

    @Override
    public void initCommonTopRightIv(ImageView rightIv) {

    }

    @Override
    public void initCommonTopRightTv(TextView rightTv) {

    }
}
