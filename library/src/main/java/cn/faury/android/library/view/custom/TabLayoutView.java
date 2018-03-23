package cn.faury.android.library.view.custom;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.DrawableRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 *
 */

public class TabLayoutView extends LinearLayout {

    /**
     * 上下文
     */
    protected FragmentActivity context;

    /**
     * Tab标签
     */
    protected TabLayout tabLayout;

    /**
     * 分割线
     */
    protected View view;

    /**
     * Tab内容区
     */
    protected ViewPager viewPager;

    /**
     * 数据适配器
     */
    protected Adapter adapter;

    public TabLayoutView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        if (context instanceof FragmentActivity){
            this.context = (FragmentActivity) context;
            initViews(attrs);
        } else {
            throw new IllegalArgumentException("unsupported context, must be FragmentActivity class or sub class");
        }
    }

    protected void initViews(AttributeSet attrs) {
        // 加载布局文件
        LayoutInflater.from(context).inflate(getLayoutId(), this);

        // 初始化控件
        tabLayout = findViewById(R.id.f_cvl_tab_layout_view_tl);
        viewPager = findViewById(R.id.f_cvl_tab_layout_view_vp);
        adapter = new Adapter(this.context, new ArrayList<Item>());
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
    }

    /**
     * 设置布局创建器
     *
     * @param listener 监听器
     * @return 当前对象
     */
    public TabLayoutView setOnCreateViewListener(OnCreateViewListener listener) {
        this.adapter.setOnCreateViewListener(listener);
        return this;
    }

    /**
     * 返回布局ID
     *
     * @return 布局ID
     */
    public int getLayoutId() {
        return R.layout.f_cvl_tab_layout_view;
    }

    /**
     * 获取Tab对象
     *
     * @return Tab对象
     */
    public TabLayout getTabLayout() {
        return this.tabLayout;
    }

    /**
     * 添加Tab页
     *
     * @param item Tab页数据
     * @return 当前对象
     */
    public TabLayoutView addTabItem(Item item) {
        this.adapter.addItem(item);
        return this;
    }

    /**
     * 添加Tab页
     *
     * @param items Tab页数据
     * @return 当前对象
     */
    public TabLayoutView addTabItem(Collection<Item> items) {
        this.adapter.addItem(items);
        return this;
    }

    /**
     * 清空数据项
     *
     * @return 当前对象
     */
    public TabLayoutView clearTabItem() {
        adapter.getItems().clear();
        return this;
    }

    /**
     * 获取所有列表项
     *
     * @return 列表项
     */
    public List<Item> getTabItems() {
        return adapter.getItems();
    }

    /**
     * 获取Tab页Fragment对象
     *
     * @param position 位置
     * @return Fragment对象
     */
    public ContentFragment getTabFragmentByPosition(int position) {
        return adapter.getFragments().get(adapter.getItems().get(position).getId());
    }

    /**
     * 获取Tab页Fragment对象
     *
     * @param id 数据ID
     * @return Fragment对象
     */
    public ContentFragment getTabFragmentById(int id) {
        return adapter.getFragments().get(id);
    }

    /**
     * 显示改变
     */
    public void notifyDataSetChanged() {
        adapter.notifyDataSetChanged();
    }

    /**
     * Tab项
     */
    public static class Item implements Serializable {
        private int id;
        private @LayoutRes int layoutId;
        private @StringRes int title;
        private String titleText;
        private @DrawableRes int icon;

        public Item(int id, int layoutId, int title) {
            this.id = id;
            this.layoutId = layoutId;
            this.title = title;
        }

        public Item(int id, int layoutId, String title) {
            this.id = id;
            this.layoutId = layoutId;
            this.titleText = title;
        }

        public Item(int id, int layoutId, int title,int icon) {
            this(id, layoutId, title);
            this.icon = icon;
        }

        public Item(int id, int layoutId, String title,int icon) {
            this(id, layoutId, title);
            this.icon = icon;
        }

        public int getId() {
            return id;
        }

        public Item setId(int id) {
            this.id = id;
            return this;
        }

        public int getLayoutId() {
            return layoutId;
        }

        public Item setLayoutId(int layoutId) {
            this.layoutId = layoutId;
            return this;
        }

        public int getTitle() {
            return title;
        }

        public Item setTitle(int title) {
            this.title = title;
            return this;
        }

        public String getTitleText() {
            return titleText;
        }

        public Item setTitleText(String titleText) {
            this.titleText = titleText;
            return this;
        }

        public int getIcon() {
            return icon;
        }

        public Item setIcon(int icon) {
            this.icon = icon;
            return this;
        }
    }

    /**
     * 适配器
     */
    public static class Adapter extends FragmentPagerAdapter {
        private FragmentActivity context;
        private List<Item> items;
        private Map<Integer, ContentFragment> fragments;
        private OnCreateViewListener listener;

        public Adapter(@NonNull FragmentActivity context, @NonNull List<Item> items) {
            super(context.getSupportFragmentManager());
            this.context = context;
            this.items = items;
            this.fragments = new LinkedHashMap<>(this.items.size());
            for (Item item : this.items) {
                this.fragments.put(item.getId(), ContentFragment.newInstance(item));
            }
        }

        @Override
        public Fragment getItem(int position) {
            return this.fragments.get(this.items.get(position).getId());
        }

        @Override
        public int getCount() {
            return items.size();
        }

        /**
         * 配置视图创建监听器
         * @param listener 监听器
         * @return 当前对象
         */
        public Adapter setOnCreateViewListener(OnCreateViewListener listener) {
            this.listener = listener;
            for (Map.Entry<Integer,ContentFragment> entry:this.fragments.entrySet()){
                entry.getValue().setOnCreateViewListener(listener);
            }
            return this;
        }

        /**
         * 添加多个列表项
         *
         * @param items 多个列表项
         */
        public Adapter addItem(Collection<? extends Item> items) {
            for (Item item : items) {
                addItem(item);
            }
            return this;
        }

        /**
         * 添加列表项
         *
         * @param item 列表项
         */
        public Adapter addItem(Item item) {
            this.items.add(item);
            this.fragments.put(item.getId(), ContentFragment.newInstance(item).setOnCreateViewListener(this.listener));
            return this;
        }

        /**
         * 获取所有数据项
         *
         * @return 数据项
         */
        public List<Item> getItems() {
            return items;
        }

        /**
         * 获取所有数据项
         *
         * @return 数据项
         */
        public Map<Integer, ContentFragment> getFragments() {
            return this.fragments;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            if (items.get(position).getTitle() > 0) {
                return this.context.getResources().getString(items.get(position).getTitle());
            }
            return items.get(position).getTitleText();
        }
    }

    /**
     * 页面Fragment
     */
    public static class ContentFragment extends Fragment {
        private Item item;
        private OnCreateViewListener listener;

        public static ContentFragment newInstance(Item item) {
            ContentFragment fragment = new ContentFragment();
            fragment.item = item;
            return fragment;
        }

        /**
         * 配置视图创建监听器
         * @param listener 监听器
         * @return 当前对象
         */
        public ContentFragment setOnCreateViewListener(OnCreateViewListener listener) {
            this.listener = listener;
            return this;
        }

        @Nullable
        @Override
        public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            View contentView = inflater.inflate(item.getLayoutId(), null);
            if (listener != null) {
                listener.onCreateView(item.getId(), item, contentView);
            }
            return contentView;
        }
    }

    /**
     * Tab页视图创建监听器
     */
    public interface OnCreateViewListener {
        void onCreateView(int id, Item item, View view);
    }
}
