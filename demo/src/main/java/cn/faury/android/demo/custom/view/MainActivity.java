package cn.faury.android.demo.custom.view;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Toast;

import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.faury.android.library.common.util.ActivityUtils;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

    private void show(String message) {
        Toast.makeText(MainActivity.this, message, Toast.LENGTH_LONG).show();
    }


    @OnClick(R.id.action_sheet_btn)
    public void actionSheetBtnClick() {
        ActivityUtils.startActivity(this, ActionSheetActivity.class,null);
    }

    @OnClick(R.id.tab_layout_btn)
    public void tabLayoutBtnClick() {
        ActivityUtils.startActivity(this, TabLayoutActivity.class,null);
    }

}
