package cn.faury.android.demo.custom.view;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Toast;

import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.faury.android.library.view.custom.ActionSheet;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

    private void show(String message){
        Toast.makeText(MainActivity.this,message,Toast.LENGTH_LONG).show();
    }

    @OnClick(R.id.list_btn)
    public void listBtnClick(){
        ActionSheet.build(getFragmentManager())
                .setType(ActionSheet.Builder.TYPE.LIST)
                .setTitle("")
                .setItems(new String[]{"菜单1", "菜单2", "菜单3", "菜单4"})
                .setOnItemClickListener(new ActionSheet.OnItemClickListener() {
                    @Override
                    public void onItemClick(int i) {
                        show("select "+i);
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

    @OnClick(R.id.grid_btn)
    public void gridBtnClick(){
        ActionSheet.build(getFragmentManager())
                .setType(ActionSheet.Builder.TYPE.GRID)
                .setTitle("")
                .setItems(new String[]{"菜单1", "菜单2"})
                .setOnItemClickListener(new ActionSheet.OnItemClickListener() {
                    @Override
                    public void onItemClick(int i) {
                        show("select "+i);
                    }
                })
                .setImages(new int[]{R.drawable.btn_login_qq, R.drawable.btn_login_weixin})
                .show();
    }
}
