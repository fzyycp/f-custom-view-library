package cn.faury.android.library.view.custom;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * 通用头部导航视图
 */

public class CommonTopView extends RelativeLayout {

    /**
     * 整个头部布局
     */
    private RelativeLayout layout;

    /**
     * 左侧图片按钮
     */
    private ImageView leftIv;

    /**
     * 左侧文本按钮
     */
    private TextView leftTv;

    /**
     * 中间标题文本
     */
    private TextView centerTv;

    /**
     * 右侧图片按钮
     */
    private ImageView rightIv;

    /**
     * 右侧文本按钮
     */
    private TextView rightTv;

    /**
     * 构造函数
     *
     * @param context 上下文
     * @param attrs   xml配置属性
     */
    public CommonTopView(final Context context, AttributeSet attrs) {
        super(context, attrs);

        // 加载布局文件
        LayoutInflater.from(context).inflate(R.layout.f_library_custom_view_ctv, this);

        // 初始化控件
        layout = findViewById(R.id.f_library_custom_view_ctv_layout);
        leftIv = findViewById(R.id.f_library_custom_view_ctv_left_iv);
        leftTv = findViewById(R.id.f_library_custom_view_ctv_left_tv);
        centerTv = findViewById(R.id.f_library_custom_view_ctv_center_tv);
        rightIv = findViewById(R.id.f_library_custom_view_ctv_right_iv);
        rightTv = findViewById(R.id.f_library_custom_view_ctv_right_tv);
    }

    /**
     * 初始化导航栏
     *
     * @param init 配置参数
     */
    public void initViews(InitViews init) {
        // 配置控件参数
        if (init != null) {
            init.initCommonTopLayout(layout);
            init.initCommonTopLeftIv(leftIv);
            init.initCommonTopLeftTv(leftTv);
            init.initCommonTopCenterTv(centerTv);
            init.initCommonTopRightIv(rightIv);
            init.initCommonTopRightTv(rightTv);
        }
    }


    /**
     * 配置
     */
    public static abstract class InitViews {

        /**
         * 初始化整个导航栏
         *
         * @param layout 导航栏
         */
        public void initCommonTopLayout(final RelativeLayout layout) {
        }


        /**
         * 初始化左侧图片按钮
         *
         * @param leftIv 左侧图片按钮
         */
        public abstract void initCommonTopLeftIv(final ImageView leftIv);

        /**
         * 初始化左侧文字按钮
         *
         * @param leftTv 左侧文字按钮
         */
        public void initCommonTopLeftTv(final TextView leftTv) {
            leftTv.setVisibility(GONE);
        }

        /**
         * 初始化中间文字标题
         *
         * @param centerTv 中部文字标题
         */
        public abstract void initCommonTopCenterTv(final TextView centerTv);

        /**
         * 初始化右侧图片按钮
         *
         * @param rightIv 右侧图片按钮
         */
        public void initCommonTopRightIv(final ImageView rightIv) {
            rightIv.setVisibility(GONE);
        }

        /**
         * 初始化右侧文字按钮
         *
         * @param rightTv 右侧文字按钮
         */
        public void initCommonTopRightTv(final TextView rightTv) {
            rightTv.setVisibility(GONE);
        }
    }
}
