package cn.faury.android.library.view.custom;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.DrawableRes;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 菜单列表
 */

public class MenuRecyclerView extends RelativeLayout {

    /**
     * 上下文
     */
    Context context;

    /**
     * 列表对象
     */
    RecyclerView recyclerView;

    /**
     * 数据适配器
     */
    Adapter adapter;

    /**
     * 构造函数
     *
     * @param context 上下文
     * @param attrs   配置属性
     */
    public MenuRecyclerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        this.context = context;

        // 加载布局文件
        LayoutInflater.from(context).inflate(R.layout.f_library_custom_view_mrv, this);

        // 初始化控件
        recyclerView = findViewById(R.id.f_library_custom_view_mrv_rv);
        recyclerView.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
        initAttrs(attrs);


        adapter = new Adapter(this.context, new ArrayList<Item>(), true, null);
        recyclerView.setAdapter(adapter);
    }

    /**
     * 初始化配置参数
     * @param attrs xml配置参数
     */
    private void initAttrs(@Nullable AttributeSet attrs){
        TypedArray typedArray = context.obtainStyledAttributes(attrs,R.styleable.MenuRecyclerView);
        boolean animator = typedArray.getBoolean(R.styleable.MenuRecyclerView_animator, true);
        if (animator) {
            recyclerView.setItemAnimator(new DefaultItemAnimator());
        }

        int decoration = typedArray.getInt(R.styleable.MenuRecyclerView_decoration,-1);
        if(decoration==DividerItemDecoration.HORIZONTAL){
            recyclerView.addItemDecoration(new DividerItemDecoration(context, DividerItemDecoration.HORIZONTAL));
        } else if(decoration==DividerItemDecoration.VERTICAL){
            recyclerView.addItemDecoration(new DividerItemDecoration(context, DividerItemDecoration.VERTICAL));
        }
    }

    /**
     * 初始化数据
     */
    public void initItem(List<Item> items, boolean showIcon, OnMenuItemClickListener onMenuItemClickListener) {
        adapter.items.addAll(items);
        adapter.onMenuItemClickListener = onMenuItemClickListener;
        adapter.showIcon = showIcon;
        adapter.notifyDataSetChanged();
    }

    /**
     * 添加数据项
     *
     * @param items 数据项
     */
    public MenuRecyclerView addItem(List<Item> items) {
        adapter.items.addAll(items);
        adapter.notifyDataSetChanged();
        return this;
    }

    /**
     * 添加数据项
     *
     * @param item 数据项
     */
    public MenuRecyclerView addItem(Item item) {
        this.addItem(Arrays.asList(item));
        return this;
    }

    /**
     * 清空数据项
     */
    public void clearItem() {
        adapter.items.clear();
        adapter.notifyDataSetChanged();
    }

    /**
     * 数据适配器
     */
    public static class Adapter extends RecyclerView.Adapter<ViewHolder> {

        /**
         * 上下文
         */
        Context context;

        /**
         * 列表项
         */
        List<Item> items;

        /**
         * 是否显示图标
         */
        boolean showIcon;

        /**
         * 单击事件
         */
        OnMenuItemClickListener onMenuItemClickListener;

        /**
         * 构造函数
         *
         * @param context                 上下文
         * @param items                   列表项
         * @param showIcon                是否显示图标
         * @param onMenuItemClickListener 菜单点击事件
         */
        public Adapter(Context context, List<Item> items, boolean showIcon, OnMenuItemClickListener onMenuItemClickListener) {
            this.context = context;
            this.items = (items == null ? new ArrayList<Item>() : items);
            this.showIcon = showIcon;
            this.onMenuItemClickListener = onMenuItemClickListener;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(context).inflate(R.layout.f_library_custom_view_mrv_item, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            final Item item = this.items.get(position);
            int resId = item.getIcon();
            if (showIcon && resId > 0) {
                holder.icon.setVisibility(View.VISIBLE);
                holder.icon.setImageResource(resId);
            } else {
                holder.icon.setVisibility(View.GONE);
            }
            holder.text.setText(item.getText());

            final int pos = holder.getAdapterPosition();
            holder.viewGroup.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onMenuItemClickListener != null) {
                        onMenuItemClickListener.onMenuItemClick(pos, item);
                    }
                }
            });
        }

        @Override
        public int getItemCount() {
            return this.items.size();
        }
    }

    /**
     * 列表界面容器
     */
    public static class ViewHolder extends RecyclerView.ViewHolder {
        ViewGroup viewGroup;
        ImageView icon;
        TextView text;

        ViewHolder(View itemView) {
            super(itemView);
            viewGroup = (ViewGroup) itemView;
            icon = itemView.findViewById(R.id.f_library_custom_view_mrv_item_icon);
            text = itemView.findViewById(R.id.f_library_custom_view_mrv_item_text);
        }
    }

    /**
     * 菜单项
     */
    public static class Item implements Serializable {

        /**
         * 菜单ID
         */
        private int id;

        /**
         * 菜单图片
         */
        private int icon;

        /**
         * 菜单内容
         */
        private int text;


        /**
         * 构造函数
         *
         * @param id   菜单ID
         * @param text 菜单内容ID
         */
        public Item(@IdRes int id, @StringRes int text) {
            this.id = id;
            this.text = text;
        }

        /**
         * 构造函数
         *
         * @param id     菜单ID
         * @param iconId 图标ID
         * @param text   菜单内容ID
         */
        public Item(@IdRes int id, @DrawableRes int iconId, @StringRes int text) {
            this.id = id;
            this.icon = iconId;
            this.text = text;
        }

        @IdRes
        public int getId() {
            return id;
        }

        @DrawableRes
        public int getIcon() {
            return icon;

        }

        @StringRes
        public int getText() {
            return text;
        }
    }

    /**
     * 菜单项点击事件
     */
    public interface OnMenuItemClickListener {
        void onMenuItemClick(int position, Item item);
    }
}
