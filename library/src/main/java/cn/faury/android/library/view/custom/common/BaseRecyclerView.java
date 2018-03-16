package cn.faury.android.library.view.custom.common;

import android.content.Context;
import android.support.annotation.DrawableRes;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import cn.faury.android.library.view.custom.R;

/**
 * 通用列表对象
 */

public abstract class BaseRecyclerView extends RelativeLayout {

    /**
     * 上下文
     */
    protected Context context;

    /**
     * 列表对象
     */
    protected RecyclerView recyclerView;

    /**
     * 数据适配器
     */
    protected Adapter adapter;

    public BaseRecyclerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        AttrConfigure attsCfg = getAttrs(attrs);

        initViews(attrs,attsCfg);
        initAttrs(attrs,attsCfg);
        initData(attsCfg);
    }

    protected void initViews(AttributeSet attrs, AttrConfigure attsCfg) {
        // 加载布局文件
        LayoutInflater.from(context).inflate(getLayoutId(), this);

        // 初始化控件
        recyclerView = findViewById(getRecyclerViewId());
        recyclerView.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));

        adapter = new Adapter(this.context, attsCfg, new ArrayList<Item>(), null);
        recyclerView.setAdapter(adapter);
    }

    /**
     * 设置自定义属性
     * @param attrs 配置属性
     */
    protected void initAttrs(AttributeSet attrs,@Nullable AttrConfigure attrConfigure) {
        if (attrConfigure == null) {
            return;
        }
        if (attrConfigure.animator) {
            recyclerView.setItemAnimator(new DefaultItemAnimator());
        }

        if (attrConfigure.decoration == DividerItemDecoration.HORIZONTAL) {
            recyclerView.addItemDecoration(new DividerItemDecoration(context, DividerItemDecoration.HORIZONTAL));
        } else if (attrConfigure.decoration == DividerItemDecoration.VERTICAL) {
            recyclerView.addItemDecoration(new DividerItemDecoration(context, DividerItemDecoration.VERTICAL));
        }
    }

    /**
     * 返回布局ID
     * @return 布局ID
     */
    @LayoutRes
    public abstract int getLayoutId();

    /**
     * 返回布局中recyclerView的ID
     * @return recyclerView的ID
     */
    @IdRes
    public abstract int getRecyclerViewId();

    /**
     * 获取配置参数
     * @param attrs 配置属性
     * @return
     */
    public abstract AttrConfigure getAttrs(AttributeSet attrs);

    /**
     * 初始化数据
     *
     * @param attrs 配置参数
     */
    protected void initData(AttrConfigure attrs) {

    }

    /**
     * 设置监听器
     *
     * @param onMenuItemClickListener 点击监听器
     * @return 当前对象
     */
    public BaseRecyclerView onMenuItemClick(OnMenuItemClickListener onMenuItemClickListener) {
        adapter.setOnMenuItemClickListener(onMenuItemClickListener);
        return this;
    }

    /**
     * 添加数据项
     *
     * @param items 数据项
     * @return 当前对象
     */
    public BaseRecyclerView addItem(Collection<Item> items) {
        adapter.addItems(items);
        return this;
    }

    /**
     * 添加数据项
     *
     * @param item 数据项
     * @return 当前对象
     */
    public BaseRecyclerView addItem(Item item) {
        adapter.addItem(item);
        return this;
    }

    /**
     * 清空数据项
     *
     * @return 当前对象
     */
    public BaseRecyclerView clearItem() {
        adapter.getItems().clear();
        return this;
    }

    /**
     * 获取所有列表项
     *
     * @return 列表项
     */
    public List<Item> getItems() {
        return adapter.getItems();
    }

    /**
     * 获取视图
     *
     * @param position 视图序号
     * @return 视图对象
     */
    public ViewHolder getItemViewHolderByPosition(int position) {
        return getItemViewHolderById(adapter.getItems().get(position).getId());
    }

    /**
     * 获取视图
     *
     * @param id 视图ID
     * @return 视图对象
     */
    public ViewHolder getItemViewHolderById(int id) {
        return adapter.getHolders().get(id);
    }

    /**
     * 显示改变
     */
    public void notifyDataSetChanged() {
        adapter.notifyDataSetChanged();
    }

    /**
     * 配置参数
     */
    public static class AttrConfigure {
        private boolean animator;
        private int decoration;
        private float lineHeight;
        private float titleSize;
        private int titleColor;
        private float placeSize;
        private int placeColor;

        public AttrConfigure() {
        }

        public AttrConfigure(boolean animator, int decoration, float lineHeight, float titleSize, int titleColor, float placeSize, int placeColor) {
            this.animator = animator;
            this.decoration = decoration;
            this.lineHeight = lineHeight;
            this.titleSize = titleSize;
            this.titleColor = titleColor;
            this.placeSize = placeSize;
            this.placeColor = placeColor;
        }

        public boolean isAnimator() {
            return animator;
        }

        public AttrConfigure setAnimator(boolean animator) {
            this.animator = animator;
            return this;
        }

        public int getDecoration() {
            return decoration;
        }

        public AttrConfigure setDecoration(int decoration) {
            this.decoration = decoration;
            return this;
        }

        public float getLineHeight() {
            return lineHeight;
        }

        public AttrConfigure setLineHeight(float lineHeight) {
            this.lineHeight = lineHeight;
            return this;
        }

        public float getTitleSize() {
            return titleSize;
        }

        public AttrConfigure setTitleSize(float titleSize) {
            this.titleSize = titleSize;
            return this;
        }

        public int getTitleColor() {
            return titleColor;
        }

        public AttrConfigure setTitleColor(int titleColor) {
            this.titleColor = titleColor;
            return this;
        }

        public float getPlaceSize() {
            return placeSize;
        }

        public AttrConfigure setPlaceSize(float placeSize) {
            this.placeSize = placeSize;
            return this;
        }

        public int getPlaceColor() {
            return placeColor;
        }

        public AttrConfigure setPlaceColor(int placeColor) {
            this.placeColor = placeColor;
            return this;
        }
    }

    /**
     * 数据适配器
     */
    public static class Adapter extends RecyclerView.Adapter<BaseRecyclerView.ViewHolder> {

        /**
         * 上下文
         */
        private Context context;

        /**
         * 自定义配置属性
         */
        private AttrConfigure attrConfigure;

        /**
         * 列表项
         */
        private List<BaseRecyclerView.Item> items;

        /**
         * 视图列表:id,rootView
         */
        private Map<Integer, BaseRecyclerView.ViewHolder> holders;

        /**
         * 单击事件
         */
        private BaseRecyclerView.OnMenuItemClickListener onMenuItemClickListener;

        /**
         * 构造函数
         *
         * @param context                 上下文
         * @param typedArray              配置参数
         * @param items                   列表项
         * @param onMenuItemClickListener 菜单点击事件
         */
        public Adapter(Context context, AttrConfigure typedArray, List<BaseRecyclerView.Item> items, BaseRecyclerView.OnMenuItemClickListener onMenuItemClickListener) {
            this.context = context;
            this.items = (items == null ? new ArrayList<BaseRecyclerView.Item>() : items);
            this.onMenuItemClickListener = onMenuItemClickListener;
            this.attrConfigure = typedArray;
            this.holders = new LinkedHashMap<>(this.items.size());
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(context).inflate(R.layout.f_cvl_item_recycler_view_item, parent, false);
            initAttrs(view);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final BaseRecyclerView.ViewHolder holder, int position) {
            final BaseRecyclerView.Item item = this.items.get(position);
            holder.id = item.id;
            // 图标
            if (item.isShowIcon()) {
                holder.iconIv.setVisibility(View.VISIBLE);
                if (item.getIcon() > 0) {
                    holder.iconIv.setImageResource(item.getIcon());
                }
            } else {
                holder.iconIv.setVisibility(View.GONE);
            }

            // 菜单文本
            if (item.getTitle() > 0) {
                holder.titleTv.setText(item.getTitle());
            } else {
                holder.titleTv.setText(item.getTitleText());
            }

            // 右侧图标
            if (item.isShowMore()) {
                holder.moreIv.setVisibility(View.VISIBLE);
                if (item.getMore() > 0) {
                    holder.moreIv.setImageResource(item.getMore());
                }
            } else {
                holder.moreIv.setVisibility(View.GONE);
            }
            // placehold内容
            if (item.getPlace() > 0) {
                holder.placeTv.setText(item.getPlace());
            } else {
                holder.placeTv.setText(item.getPlaceText());
            }
            // 点击事件
            final int pos = holder.getAdapterPosition();
            holder.rootView.setOnClickListener(
                    new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (onMenuItemClickListener != null) {
                                onMenuItemClickListener.onMenuItemClick(pos, item, holder);
                            }
                        }
                    });
            this.holders.put(item.getId(), holder);
        }

        @Override
        public int getItemCount() {
            return this.items.size();
        }

        /**
         * 获取所有列表项
         *
         * @return 列表项
         */
        public List<Item> getItems() {
            return items;
        }

        /**
         * 添加多个列表项
         *
         * @param items 多个列表项
         */
        public void addItems(Collection<? extends Item> items) {
            this.items.addAll(items);
        }

        /**
         * 添加列表项
         *
         * @param item 列表项
         */
        public void addItem(Item item) {
            this.items.add(item);
        }

        /**
         * 获取所有视图
         *
         * @return 所有视图
         */
        public Map<Integer, ViewHolder> getHolders() {
            return holders;
        }

        public OnMenuItemClickListener getOnMenuItemClickListener() {
            return onMenuItemClickListener;
        }

        public void setOnMenuItemClickListener(OnMenuItemClickListener onMenuItemClickListener) {
            this.onMenuItemClickListener = onMenuItemClickListener;
        }

        /**
         * 设置配置属性
         *
         * @param view 行视图
         */
        private void initAttrs(View view) {
            if (attrConfigure != null) {
                // 行高
                ViewGroup.LayoutParams params = view.getLayoutParams();
                params.height = (int) attrConfigure.lineHeight;
                view.setLayoutParams(params);

                // 列表项文本大小、颜色
                TextView titleTv = view.findViewById(R.id.f_library_custom_view_mrv_item_title_tv);
                titleTv.setTextSize(TypedValue.COMPLEX_UNIT_PX, attrConfigure.titleSize);
                titleTv.setTextColor(attrConfigure.titleColor);

                // placehold文本大小、颜色
                TextView placeTv = view.findViewById(R.id.f_library_custom_view_mrv_item_place_tv);
                placeTv.setTextSize(TypedValue.COMPLEX_UNIT_PX, attrConfigure.placeSize);
                placeTv.setTextColor(attrConfigure.placeColor);
            }
        }
    }

    /**
     * 列表界面容器
     */
    public static class ViewHolder extends RecyclerView.ViewHolder {
        private int id = -1;
        private View rootView;
        private ImageView iconIv;
        private ImageView moreIv;
        private TextView titleTv;
        private TextView placeTv;

        ViewHolder(View itemView) {
            super(itemView);
            rootView = itemView;
            iconIv = itemView.findViewById(R.id.f_library_custom_view_mrv_item_icon_iv);
            moreIv = itemView.findViewById(R.id.f_library_custom_view_mrv_item_more_iv);
            titleTv = itemView.findViewById(R.id.f_library_custom_view_mrv_item_title_tv);
            placeTv = itemView.findViewById(R.id.f_library_custom_view_mrv_item_place_tv);
        }

        /**
         * 获取ID
         *
         * @return ID
         */
        public int getId() {
            return id;
        }

        /**
         * 获取根视图
         *
         * @return 根视图
         */
        public View getRootView() {
            return rootView;
        }

        /**
         * 获取图标
         *
         * @return 图标
         */
        public ImageView getIconIv() {
            return iconIv;
        }

        /**
         * 获取更多图标
         *
         * @return 更多图标
         */
        public ImageView getMoreIv() {
            return moreIv;
        }

        /**
         * 获取标题对象
         *
         * @return 标题对象
         */
        public TextView getTitleTv() {
            return titleTv;
        }

        /**
         * 获取placehold文本
         *
         * @return placehold对象
         */
        public TextView getPlaceTv() {
            return placeTv;
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
        private int title = -1;

        /**
         * 菜单内容文本
         */
        private String titleText = "";

        /**
         * 菜单右侧更多图标
         */
        private int more = -1;

        /**
         * 菜单placehold内容
         */
        private int place = -1;

        /**
         * 菜单placehold内容文本
         */
        private String placeText = "";

        /**
         * 显示图标
         */
        private boolean showIcon = false;

        /**
         * 显示更多按钮
         */
        private boolean showMore = false;

        /**
         * 分组字符串
         */
        private String groupText = null;

        /**
         * 构造函数
         *
         * @param id    菜单ID
         * @param title 菜单内容ID
         */
        public Item(int id, @StringRes int title) {
            this.id = id;
            this.title = title;
        }

        /**
         * 构造函数
         *
         * @param id    菜单ID
         * @param title 菜单内容
         */
        public Item(int id, String title) {
            this.id = id;
            this.titleText = title;
        }

        /**
         * 构造函数
         *
         * @param id    菜单ID
         * @param title 菜单内容
         * @param place placehold内容
         */
        public Item(int id, String title, String place) {
            this.id = id;
            this.titleText = title;
            this.placeText = place;
        }

        /**
         * 构造函数
         *
         * @param id     菜单ID
         * @param iconId 图标ID(小于等于0不显示)
         * @param title  菜单内容ID
         */
        public Item(int id, @DrawableRes int iconId, @StringRes int title) {
            this.id = id;
            if (iconId > 0) {
                this.icon = iconId;
                showIcon = true;
            }
            this.title = title;
        }

        /**
         * 构造函数
         *
         * @param id     菜单ID
         * @param iconId 图标ID(小于等于0不显示)
         * @param title  菜单内容
         */
        public Item(int id, @DrawableRes int iconId, String title) {
            this.id = id;
            if (iconId > 0) {
                this.icon = iconId;
                showIcon = true;
            }
            this.titleText = title;
        }

        /**
         * 构造函数
         *
         * @param id              菜单ID
         * @param iconId          图标ID(小于等于0不显示)
         * @param title           菜单内容ID
         * @param showDefaultMore 显示默认更多按钮
         * @param place           placehold内容
         */
        public Item(int id, @DrawableRes int iconId, @StringRes int title, boolean showDefaultMore, @StringRes int place) {
            this.id = id;
            if (iconId > 0) {
                this.icon = iconId;
                showIcon = true;
            }
            this.title = title;
            this.showMore = showDefaultMore;
            this.place = place;
        }

        /**
         * 构造函数
         *
         * @param id              菜单ID
         * @param iconId          图标ID(小于等于0不显示)
         * @param title           菜单内容
         * @param showDefaultMore 显示默认更多按钮
         * @param place           placehold内容
         */
        public Item(int id, @DrawableRes int iconId, String title, boolean showDefaultMore, String place) {
            this.id = id;
            if (iconId > 0) {
                this.icon = iconId;
                showIcon = true;
            }
            this.titleText = title;
            this.showMore = showDefaultMore;
            this.placeText = place;
        }

        /**
         * 构造函数
         *
         * @param id     菜单ID
         * @param iconId 图标ID(小于等于0不显示)
         * @param title  菜单内容ID
         * @param moreId 更多按钮ID(小于等于0不显示)
         * @param place  placehold内容
         */
        public Item(int id, @DrawableRes int iconId, @StringRes int title, @DrawableRes int moreId, @StringRes int place) {
            this.id = id;
            if (iconId > 0) {
                this.icon = iconId;
                showIcon = true;
            }
            this.title = title;
            if (moreId > 0) {
                this.more = moreId;
                showMore = true;
            } else {
                showMore = false;
            }
            this.place = place;
        }

        /**
         * 构造函数
         *
         * @param id     菜单ID
         * @param iconId 图标ID(小于等于0不显示)
         * @param title  菜单内容ID
         * @param moreId 更多按钮ID(小于等于0不显示)
         * @param place  placehold内容
         */
        public Item(int id, @DrawableRes int iconId, @StringRes int title, @DrawableRes int moreId, String place) {
            this.id = id;
            if (iconId > 0) {
                this.icon = iconId;
                showIcon = true;
            }
            this.title = title;
            if (moreId > 0) {
                this.more = moreId;
                showMore = true;
            } else {
                showMore = false;
            }
            this.placeText = place;
        }

        /**
         * 构造函数
         *
         * @param id     菜单ID
         * @param iconId 图标ID(小于等于0不显示)
         * @param title  菜单内容ID
         * @param moreId 更多按钮ID(小于等于0不显示)
         * @param place  placehold内容
         */
        public Item(int id, @DrawableRes int iconId, String title, @DrawableRes int moreId, String place) {
            this.id = id;
            if (iconId > 0) {
                this.icon = iconId;
                showIcon = true;
            }
            this.titleText = title;
            if (moreId > 0) {
                this.more = moreId;
                showMore = true;
            } else {
                showMore = false;
            }
            this.placeText = place;
        }

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
        public int getTitle() {
            return title;
        }

        public String getTitleText() {
            return titleText;
        }

        public String getPlaceText() {
            return placeText;
        }

        @StringRes
        public int getPlace() {
            return place;
        }

        /**
         * 是否显示图标
         *
         * @return 是否显示
         */
        public boolean isShowIcon() {
            return showIcon;
        }

        /**
         * 是否显示更多图标
         *
         * @return 是否显示
         */
        public boolean isShowMore() {
            return showMore;
        }

        /**
         * 设置文本
         *
         * @param title 文本ID
         * @return 当前对象
         */
        public BaseRecyclerView.Item setTitle(int title) {
            this.title = title;
            return this;
        }

        /**
         * 设置右侧描述
         *
         * @param place 描述文本
         * @return 当前对象
         */
        public BaseRecyclerView.Item setPlace(int place) {
            this.place = place;
            return this;
        }

        /**
         * 设置文本
         *
         * @param title 文本
         * @return 当前对象
         */
        public BaseRecyclerView.Item setTitleText(String title) {
            this.titleText = title;
            return this;
        }

        /**
         * 设置右侧描述文本
         *
         * @param place 文本
         * @return 当前对象
         */
        public BaseRecyclerView.Item setPlaceText(String place) {
            this.placeText = place;
            return this;
        }

        /**
         * 获取分组字符
         * @return 分组字符
         */
        public String getGroupText() {
            return groupText;
        }

        /**
         * 设置分组字符
         * @param groupText 分组字符
         */
        public Item setGroupText(String groupText) {
            this.groupText = groupText;
            return this;
        }
    }

    /**
     * 菜单项点击事件
     */
    public interface OnMenuItemClickListener {
        void onMenuItemClick(int position, BaseRecyclerView.Item item, BaseRecyclerView.ViewHolder viewHolder);
    }
}
