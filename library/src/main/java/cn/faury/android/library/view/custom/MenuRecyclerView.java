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

        adapter = new Adapter(this.context, new ArrayList<Item>(), null);
        recyclerView.setAdapter(adapter);
    }

    /**
     * 初始化配置参数
     *
     * @param attrs xml配置参数
     */
    private void initAttrs(@Nullable AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.MenuRecyclerView);
        boolean animator = typedArray.getBoolean(R.styleable.MenuRecyclerView_animator, true);
        if (animator) {
            recyclerView.setItemAnimator(new DefaultItemAnimator());
        }

        int decoration = typedArray.getInt(R.styleable.MenuRecyclerView_decoration, -1);
        if (decoration == DividerItemDecoration.HORIZONTAL) {
            recyclerView.addItemDecoration(new DividerItemDecoration(context, DividerItemDecoration.HORIZONTAL));
        } else if (decoration == DividerItemDecoration.VERTICAL) {
            recyclerView.addItemDecoration(new DividerItemDecoration(context, DividerItemDecoration.VERTICAL));
        }
    }

    /**
     * 设置监听器
     *
     * @param onMenuItemClickListener 点击监听器
     * @return 当前对象
     */
    public MenuRecyclerView onMenuItemClick(OnMenuItemClickListener onMenuItemClickListener) {
        adapter.onMenuItemClickListener = onMenuItemClickListener;
        return this;
    }

    /**
     * 添加数据项
     *
     * @param items 数据项
     * @return 当前对象
     */
    public MenuRecyclerView addItem(List<Item> items) {
        adapter.items.addAll(items);
        return this;
    }

    /**
     * 添加数据项
     *
     * @param item 数据项
     * @return 当前对象
     */
    public MenuRecyclerView addItem(Item item) {
        adapter.items.add(item);
        return this;
    }

    /**
     * 清空数据项
     *
     * @return 当前对象
     */
    public MenuRecyclerView clearItem() {
        adapter.items.clear();
        return this;
    }

    /**
     * 显示改变
     */
    public void notifyDataSetChanged() {
        adapter.notifyDataSetChanged();
    }

    /**
     * 菜单项点击事件
     */
    public interface OnMenuItemClickListener {
        void onMenuItemClick(int position, Item item);
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
         * 单击事件
         */
        OnMenuItemClickListener onMenuItemClickListener;

        /**
         * 构造函数
         *
         * @param context                 上下文
         * @param items                   列表项
         * @param onMenuItemClickListener 菜单点击事件
         */
        public Adapter(Context context, List<Item> items, OnMenuItemClickListener onMenuItemClickListener) {
            this.context = context;
            this.items = (items == null ? new ArrayList<Item>() : items);
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
            // 图标
            if (item.isShowIcon()) {
                holder.icon.setVisibility(View.VISIBLE);
                if (item.getIcon() > 0) {
                    holder.icon.setImageResource(item.getIcon());
                }
            } else {
                holder.icon.setVisibility(View.GONE);
            }

            // 菜单文本
            holder.text.setText(item.getText());

            // 右侧图标
            if (item.isShowMore()) {
                holder.more.setVisibility(View.VISIBLE);
                if (item.getMore() > 0) {
                    holder.more.setImageResource(item.getMore());
                }
            }
            // placehold内容
            if (item.getPlace() > 0) {
                holder.place.setText(item.getPlace());
            }

            final int pos = holder.getAdapterPosition();
            holder.viewGroup.setOnClickListener(
                    new View.OnClickListener() {
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
        ImageView more;
        TextView text;
        TextView place;

        ViewHolder(View itemView) {
            super(itemView);
            viewGroup = (ViewGroup) itemView;
            icon = itemView.findViewById(R.id.f_library_custom_view_mrv_item_icon_iv);
            more = itemView.findViewById(R.id.f_library_custom_view_mrv_item_more_iv);
            text = itemView.findViewById(R.id.f_library_custom_view_mrv_item_text_tv);
            place = itemView.findViewById(R.id.f_library_custom_view_mrv_item_place_tv);
        }
    }

    /**
     * 菜单项
     */
    public static class Item implements Serializable {

        /**
         * 菜单ID
         */
        private int id = -1;

        /**
         * 菜单图片
         */
        private int icon = -1;

        /**
         * 菜单内容
         */
        private int text = -1;

        /**
         * 菜单右侧更多图标
         */
        private int more = -1;

        /**
         * 菜单placehold内容
         */
        private int place = -1;

        /**
         * 显示图标
         */
        private boolean showIcon = true;

        /**
         * 显示更多按钮
         */
        private boolean showMore = false;

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
         * @param iconId 图标ID(小于等于0不显示)
         * @param text   菜单内容ID
         */
        public Item(@IdRes int id, @DrawableRes int iconId, @StringRes int text) {
            this.id = id;
            if (iconId > 0) {
                this.icon = iconId;
                showIcon = true;
            } else {
                showIcon = false;
            }
            this.text = text;
        }

        /**
         * 构造函数
         *
         * @param id       菜单ID
         * @param iconId   图标ID(小于等于0不显示)
         * @param text     菜单内容ID
         * @param showDefaultMore 显示默认更多按钮
         * @param place    placehold内容
         */
        public Item(@IdRes int id, @DrawableRes int iconId, @StringRes int text, boolean showDefaultMore, @StringRes int place) {
            this.id = id;
            if (iconId > 0) {
                this.icon = iconId;
                showIcon = true;
            } else {
                showIcon = false;
            }
            this.text = text;
            this.showMore = showDefaultMore;
            this.place = place;
        }

        /**
         * 构造函数
         *
         * @param id     菜单ID
         * @param iconId 图标ID(小于等于0不显示)
         * @param text   菜单内容ID
         * @param moreId 更多按钮ID(小于等于0不显示)
         * @param place  placehold内容
         */
        public Item(@IdRes int id, @DrawableRes int iconId, @StringRes int text, @DrawableRes int moreId, @StringRes int place) {
            this.id = id;
            if (iconId > 0) {
                this.icon = iconId;
                showIcon = true;
            } else {
                showIcon = false;
            }
            this.text = text;
            if (moreId > 0) {
                this.more = moreId;
                showMore = true;
            } else {
                showMore = false;
            }
            this.place = place;
        }

        @IdRes
        public int getId() {
            return id;
        }

        @DrawableRes
        public int getIcon() {
            return icon;
        }

        @DrawableRes
        public int getMore() {
            return more;
        }

        @StringRes
        public int getText() {
            return text;
        }

        @StringRes
        public int getPlace() {
            return place;
        }

        public boolean isShowIcon() {
            return showIcon;
        }

        public boolean isShowMore() {
            return showMore;
        }
    }
}
