package cn.faury.android.demo.custom.view;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.faury.android.library.view.custom.ActionSheet;
import cn.faury.android.library.view.custom.common.BaseItem;

/**
 *
 */

public class ActionSheetActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_action_sheet);
        ButterKnife.bind(this);
    }

    private void show(String message) {
        Toast.makeText(ActionSheetActivity.this, message, Toast.LENGTH_LONG).show();
    }

    static List<BaseItem> items = new ArrayList<>();

    static {
        items.add(new BaseItem(1, R.drawable.btn_login_qq, "菜单1"));
        items.add(new BaseItem(2, R.drawable.btn_login_weixin, "菜单2"));
        items.add(new BaseItem(3, R.drawable.btn_login_qq, "菜单3"));
        items.add(new BaseItem(4, R.drawable.btn_login_weixin, "菜单4"));
    }

    @OnClick(R.id.list_title_cancle_btn)
    public void listTitleCancleBtnClick() {
        ActionSheet.build(getFragmentManager(), ActionSheet.TYPE.LIST)
                .setTitle("这是标题")
                .setCancel("取消")
                .addItem(items)
                .setOnItemClickListener(new ActionSheet.OnItemClickListener() {
                    @Override
                    public void onItemClick(int position, BaseItem item) {
                        show("position=" + position);
                    }
                })
                .setOnCancelListener(new ActionSheet.OnCancelListener() {
                    @Override
                    public void onCancelClick() {
                        show("cancel");
                    }
                })
                .show();
    }

    @OnClick(R.id.list_title_btn)
    public void listTitleBtnClick() {
        ActionSheet.build(getFragmentManager(), ActionSheet.TYPE.LIST)
                .setTitle("这是标题")
                .addItem(items)
                .setOnItemClickListener(new ActionSheet.OnItemClickListener() {
                    @Override
                    public void onItemClick(int position, BaseItem item) {
                        show("position=" + position);
                    }
                })
                .setOnCancelListener(new ActionSheet.OnCancelListener() {
                    @Override
                    public void onCancelClick() {
                        show("cancel");
                    }
                })
                .show();
    }

    @OnClick(R.id.list_cancel_btn)
    public void listCancelBtnClick() {
        ActionSheet.build(getFragmentManager(), ActionSheet.TYPE.LIST)
                .setCancel("隐藏")
                .addItem(items)
                .setOnItemClickListener(new ActionSheet.OnItemClickListener() {
                    @Override
                    public void onItemClick(int position, BaseItem item) {
                        show("position=" + position);
                    }
                })
                .setOnCancelListener(new ActionSheet.OnCancelListener() {
                    @Override
                    public void onCancelClick() {
                        show("cancel");
                    }
                })
                .show();
    }

    @OnClick(R.id.list_btn)
    public void listBtnClick() {
        ActionSheet.build(getFragmentManager(), ActionSheet.TYPE.LIST)
                .addItem(items)
                .setOnCancelListener(new ActionSheet.OnCancelListener() {
                    @Override
                    public void onCancelClick() {
                        show("cancel");
                    }
                })
                .show();
    }

    @OnClick(R.id.grid_title_btn)
    public void gridTitleBtnClick() {
        ActionSheet.build(getFragmentManager(), ActionSheet.TYPE.GRID)
                .setTitle("标题")
                .addItem(items)
                .setOnItemClickListener(new ActionSheet.OnItemClickListener() {
                    @Override
                    public void onItemClick(int position, BaseItem item) {
                        show("position=" + position);
                    }
                })
                .show();
    }

    @OnClick(R.id.grid_btn)
    public void gridBtnClick() {
        ActionSheet.build(getFragmentManager(), ActionSheet.TYPE.GRID)
                .addItem(items)
                .setOnItemClickListener(new ActionSheet.OnItemClickListener() {
                    @Override
                    public void onItemClick(int position, BaseItem item) {
                        show("position=" + position);
                    }
                })
                .show();
    }
}