package cn.faury.android.demo.custom.view;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.faury.android.library.view.custom.TabLayoutView;

public class TabLayoutActivity extends FragmentActivity implements TabLayoutView.OnCreateViewListener {

    @BindView(R.id.activity_download_tlv)
    TabLayoutView tabLayoutView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab_layout);
        ButterKnife.bind(this);

        this.tabLayoutView.addTabItem(new TabLayoutView.Item(1, R.layout.fragment_user_download_book, "书籍"))
                .addTabItem(new TabLayoutView.Item(2, R.layout.fragment_user_download_code, "二维码"))
                .setOnCreateViewListener(this)
                .notifyDataSetChanged();
    }


    @Override
    public void onCreateView(int id, TabLayoutView.Item item, View view) {

    }
}
