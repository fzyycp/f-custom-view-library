package cn.faury.android.library.view.custom;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ArgbEvaluator;
import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v7.widget.GridLayout;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import cn.faury.android.library.common.util.CollectionsUtils;
import cn.faury.android.library.common.util.StringUtils;
import cn.faury.android.library.common.util.WindowUtils;
import cn.faury.android.library.view.custom.common.BaseItem;

/**
 * 底部弹出菜单
 */

public class ActionSheet extends Fragment {

    private List<BaseItem> items = new ArrayList<>();
    private String titleText;
    private int title;
    private String cancelText;
    private int cancel;
    private TYPE type = TYPE.LIST;
    private OnItemClickListener onItemClickListener;
    private OnCancelListener onCancelListener;

    //是否已经关闭
    boolean isDismiss = true;

    View decorView;
    //添加进入的view
    View rootView;
    //添加进入的第一个view
    View itemsView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        try {
            InputMethodManager manager = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            if (manager != null && manager.isActive()) {
                View focusView = getActivity().getCurrentFocus();
                manager.hideSoftInputFromWindow(focusView.getWindowToken(), 0);
            }
        } catch (Exception ignored) {
        }

        initViews(inflater, container);

        decorView = getActivity().getWindow().getDecorView();
        ((ViewGroup) decorView).addView(rootView);
        startPlay();

        return super.onCreateView(inflater, container, savedInstanceState);
    }

    // 初始化布局
    private void initViews(LayoutInflater inflater, ViewGroup container) {
        rootView = inflater.inflate(R.layout.f_cvl_action_sheet, container, false);
        itemsView = rootView.findViewById(R.id.f_cvl_action_sheet_ll);
        itemsView.setVisibility(View.INVISIBLE);
        rootView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        // 配置参数
        // 设置标题
        TextView titleTv = rootView.findViewById(R.id.f_cvl_action_sheet_title_tv);
        if (this.title > 0) {
            titleTv.setText(this.title);
            titleTv.setVisibility(View.VISIBLE);
        } else if (StringUtils.isNotEmpty(this.titleText)) {
            titleTv.setText(this.titleText);
            titleTv.setVisibility(View.VISIBLE);
        } else {
            titleTv.setVisibility(View.GONE);
        }
        // 设置标题分割线
        View titleSplitV = rootView.findViewById(R.id.f_cvl_action_sheet_title_split_v);
        titleSplitV.setVisibility(titleTv.getVisibility());

        switch (type) {
            case LIST:
                initListViews();
                break;
            case GRID:
                initGridViews();
                break;
        }
    }

    // 初始化表格框
    @SuppressLint("ResourceType")
    private void initGridViews() {
        GridLayout gridGl = rootView.findViewById(R.id.f_cvl_action_sheet_grid_gl);
        gridGl.setVisibility(View.VISIBLE);
        if (getActivity() != null) {
            int width = (getScreenWidth(getActivity()) - WindowUtils.dip2px(getActivity(), 20)) / 4;
            if (!CollectionsUtils.isEmpty(items)) {
                for (int i = 0; i < items.size(); i++) {
                    final int i_ = i;
                    View viewChild = LayoutInflater.from(getActivity()).inflate(R.layout.f_cvl_action_sheet_grid_item, null, false);
                    LinearLayout gridItemLl = viewChild.findViewById(R.id.f_cvl_action_sheet_grid_item_ll);
                    gridItemLl.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (onItemClickListener != null) {
                                onItemClickListener.onItemClick(i_, items.get(i_));
                            }
                            dismiss();
                        }
                    });
                    ImageView gridItemIconIv = viewChild.findViewById(R.id.f_cvl_action_sheet_grid_item_icon_iv);
                    gridItemIconIv.setImageResource(items.get(i).getIcon());

                    TextView gridItemTitleTv = viewChild.findViewById(R.id.f_cvl_action_sheet_grid_item_title_tv);
                    if (items.get(i).getTitle() > 0) {
                        gridItemTitleTv.setText(items.get(i).getTitle());
                    } else {
                        gridItemTitleTv.setText(items.get(i).getTitleText());
                    }

                    GridLayout.LayoutParams params = new GridLayout.LayoutParams();
                    params.width = width;
                    params.height = WindowUtils.dip2px(getActivity(), 120);
                    params.columnSpec = GridLayout.spec(i % 4);
                    params.rowSpec = GridLayout.spec(i / 4);
                    gridGl.addView(viewChild, params);
                }
            }
        }
    }

    // 初始化列表框
    private void initListViews() {
        if (this.cancel > 0 || StringUtils.isNotEmpty(this.cancelText)) {
            TextView cancelTv = rootView.findViewById(R.id.f_cvl_action_sheet_cancel_tv);
            cancelTv.setVisibility(View.VISIBLE);
            if (this.cancel>0){
                cancelTv.setText(cancel);
            } else {
                cancelTv.setText(this.cancelText);
            }
            cancelTv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onCancelListener != null) {
                        onCancelListener.onCancelClick();
                    }
                    dismiss();
                }
            });
        }

        if (getActivity() != null) {
            ListView itemsLv = rootView.findViewById(R.id.f_cvl_action_sheet_items_lv);

            Adapter adapter = new Adapter(getActivity(), items);
            itemsLv.setAdapter(adapter);
            itemsLv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    if (onItemClickListener != null) {
                        onItemClickListener.onItemClick(position,items.get(position));
                        dismiss();
                    }
                }
            });
        }
    }

    // 显示弹框
    private void show(final FragmentManager manager, final String tag) {
        if (manager.isDestroyed() || !isDismiss) {
            return;
        }
        isDismiss = false;
        new Handler().post(new Runnable() {
            @Override
            public void run() {
                FragmentTransaction transaction = manager.beginTransaction();
                transaction.add(ActionSheet.this, tag);
                transaction.addToBackStack(null);
                transaction.commitAllowingStateLoss();
            }
        });
    }

    // 关闭弹框
    private void dismiss() {
        if (isDismiss) {
            return;
        }
        isDismiss = true;
        new Handler().post(new Runnable() {
            @Override
            public void run() {
                getChildFragmentManager().popBackStack();
                try {
                    FragmentTransaction transaction = getFragmentManager().beginTransaction();
                    transaction.remove(ActionSheet.this);
                    transaction.commitAllowingStateLoss();
                } catch (Exception ignore) {
                }
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        stopPlay();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                ((ViewGroup) decorView).removeView(rootView);
            }
        }, 500);
    }

    public static ActionSheet.Builder build(FragmentManager fragmentManager, TYPE type) {
        ActionSheet.Builder builder = new ActionSheet.Builder(fragmentManager,type);
        return builder;
    }

    /**
     * 构造器
     */
    public static class Builder {
        ActionSheet fragment;
        FragmentManager fragmentManager;

        //默认tag，用来校验fragment是否存在
        String tag = "ActionSheetFragment";

        public Builder(FragmentManager fragmentManager, TYPE type) {
            this.fragmentManager = fragmentManager;
            this.fragment = new ActionSheet();
            this.fragment.type = type;
        }

        public void show() {
            fragment.show(fragmentManager, tag);
        }

        public Builder setTag(String tag) {
            this.tag = tag;
            return this;
        }

        public Builder setTitle(@StringRes int title) {
            this.fragment.title = title;
            return this;
        }

        public Builder setTitle(String title) {
            this.fragment.titleText = title;
            return this;
        }

        public Builder setCancel(@StringRes int cancel) {
            this.fragment.cancel = cancel;
            return this;
        }

        public Builder setCancel(String cancel) {
            this.fragment.cancelText = cancel;
            return this;
        }

        public Builder addItem(BaseItem item) {
            this.fragment.items.add(item);
            return this;
        }

        public Builder addItem(List<BaseItem> item) {
            this.fragment.items.addAll(item);
            return this;
        }

        public Builder setOnItemClickListener(OnItemClickListener onItemClickListener) {
            this.fragment.onItemClickListener = onItemClickListener;
            return this;
        }

        public Builder setOnCancelListener(OnCancelListener onCancelListener) {
            this.fragment.onCancelListener = onCancelListener;
            return this;
        }
    }


    public interface OnCancelListener {
        void onCancelClick();
    }

    public interface OnItemClickListener {
        void onItemClick(int position, BaseItem item);
    }

    /**
     * 获取状态栏高度
     *
     * @param context
     * @return
     */
    public static int getStatusBarHeight(Context context) {
        int result = 0;
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = context.getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

    /**
     * 得到屏幕高度
     *
     * @return 单位:px
     */
    public static int getScreenHeight(Context context) {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics dm = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(dm);
        return dm.heightPixels;
    }

    /**
     * 得到屏幕宽度
     *
     * @param context
     * @return
     */
    public static int getScreenWidth(Context context) {
        WindowManager manager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics dm = new DisplayMetrics();
        manager.getDefaultDisplay().getMetrics(dm);
        return dm.widthPixels;
    }

    private void startPlay() {
        itemsView.post(new Runnable() {
            @Override
            public void run() {
                final int moveHeight = itemsView.getMeasuredHeight();
                ValueAnimator valueAnimator = ValueAnimator.ofFloat(0, 1);
                valueAnimator.setDuration(500);
                valueAnimator.addListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationStart(Animator animation) {
                        super.onAnimationStart(animation);
                        itemsView.setVisibility(View.VISIBLE);
                    }
                });
                valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator animation) {
                        ArgbEvaluator argbEvaluator = new ArgbEvaluator();
                        rootView.setBackgroundColor((Integer) argbEvaluator.evaluate(animation.getAnimatedFraction(), Color.parseColor("#00000000"), Color.parseColor("#70000000")));
                        //当底部存在导航栏并且decorView获取的高度不包含底部状态栏的时候，需要去掉这个高度差
                        if (getNavBarHeight(itemsView.getContext()) > 0 && decorView.getMeasuredHeight() != getScreenHeight(itemsView.getContext())) {
                            itemsView.setTranslationY((moveHeight + getNavBarHeight(itemsView.getContext())) * (1 - animation.getAnimatedFraction()) - getNavBarHeight(itemsView.getContext()));
                        } else {
                            itemsView.setTranslationY(moveHeight * (1 - animation.getAnimatedFraction()));
                        }
                    }
                });
                valueAnimator.start();
            }
        });
    }

    private void stopPlay() {
        itemsView.post(new Runnable() {
            @Override
            public void run() {
                final int moveHeight = itemsView.getMeasuredHeight();
                ValueAnimator valueAnimator = ValueAnimator.ofFloat(0, 1);
                valueAnimator.setDuration(500);
                valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator animation) {
                        ArgbEvaluator argbEvaluator = new ArgbEvaluator();
                        rootView.setBackgroundColor((Integer) argbEvaluator.evaluate(animation.getAnimatedFraction(), Color.parseColor("#70000000"), Color.parseColor("#00000000")));
                        if (getNavBarHeight(itemsView.getContext()) > 0 && decorView.getMeasuredHeight() != getScreenHeight(itemsView.getContext())) {
                            itemsView.setTranslationY((moveHeight + getNavBarHeight(itemsView.getContext())) * animation.getAnimatedFraction() - getNavBarHeight(itemsView.getContext()));
                        } else {
                            itemsView.setTranslationY(moveHeight * animation.getAnimatedFraction());
                        }
                    }
                });
                valueAnimator.start();
            }
        });
    }

    /**
     * 获取底部导航栏高度
     *
     * @param context
     * @return
     */
    public static int getNavBarHeight(Context context) {
        int navigationBarHeight = 0;
        Resources rs = context.getResources();
        int id = rs.getIdentifier("navigation_bar_height", "dimen", "android");
        if (id > 0 && checkDeviceHasNavigationBar(context)) {
            navigationBarHeight = rs.getDimensionPixelSize(id);
        }

        return navigationBarHeight;
    }

    private static boolean checkDeviceHasNavigationBar(Context context) {
        boolean hasNavigationBar = false;
        Resources rs = context.getResources();
        int id = rs.getIdentifier("config_showNavigationBar", "bool", "android");
        if (id > 0) {
            hasNavigationBar = rs.getBoolean(id);
        }
        try {
            Class systemPropertiesClass = Class.forName("android.os.SystemProperties");
            Method m = systemPropertiesClass.getMethod("get", String.class);
            String navBarOverride = (String) m.invoke(systemPropertiesClass, "qemu.hw.mainkeys");
            if ("1".equals(navBarOverride)) {
                hasNavigationBar = false;
            } else if ("0".equals(navBarOverride)) {
                hasNavigationBar = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return hasNavigationBar;
    }

    public static class Adapter extends BaseAdapter {
        Context context;
        List<BaseItem> items;

        public Adapter(Context context, List<BaseItem> items) {
            this.context = context;
            this.items = items;
        }

        @Override
        public int getCount() {
            return items.size();
        }

        @Override
        public Object getItem(int position) {
            return items.get(position);
        }

        @Override
        public long getItemId(int position) {
            return this.items.get(position).getId();
        }

        @SuppressLint("ResourceType")
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            if (convertView == null) {
                convertView = LayoutInflater.from(context).inflate(R.layout.f_cvl_action_sheet_list_item, parent, false);
                holder = new ViewHolder();
                holder.titleTv = convertView.findViewById(R.id.f_cvl_action_sheet_list_item_title_tv);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            if(this.items.get(position).getTitle()>0){
                holder.titleTv.setText(this.items.get(position).getTitle());
            } else {
                holder.titleTv.setText(this.items.get(position).getTitleText());
            }
            return convertView;
        }

        public static class ViewHolder {
            TextView titleTv;
        }
    }

    /**
     * 显示类型
     */
    public enum TYPE {
        LIST, GRID
    }
}
