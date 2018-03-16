package cn.faury.android.library.view.custom;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

import com.github.promeg.pinyinhelper.Pinyin;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import cn.faury.android.library.common.util.StringUtils;
import cn.faury.android.library.view.custom.common.BaseRecyclerView;
import cn.faury.android.library.view.custom.group.decoration.DivideItemDecoration;
import cn.faury.android.library.view.custom.group.decoration.GroupHeaderItemDecoration;
import cn.faury.android.library.view.custom.group.weiget.SideBar;

/**
 * 地区视图
 */

public class GroupRecyclerView extends BaseRecyclerView {

    /**
     * 右侧控制条
     */
    protected SideBar sideBar;

    /**
     * 提示文本
     */
    protected TextView tipsTv;

    /**
     * 分组头部
     */
    protected GroupHeaderItemDecoration groupHeaderItemDecoration;
    /**
     * 分组分割线
     */
    protected DivideItemDecoration divideItemDecoration;

    private boolean groupHeaderSuspensionShow;
    private float groupHeaderHeight;
    private float groupHeaderLeftPadding;
    private int groupHeaderBgColor;
    private float groupHeaderTitleSize;
    private int groupHeaderTitleColor;

    /**
     * 构造函数
     *
     * @param context 上下文
     * @param attrs   属性
     */
    public GroupRecyclerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    /**
     * 返回布局ID
     *
     * @return 布局ID
     */
    @Override
    public int getLayoutId() {
        return R.layout.f_cvl_group_recycler_view;
    }

    /**
     * 返回布局中recyclerView的ID
     *
     * @return recyclerView的ID
     */
    @Override
    public int getRecyclerViewId() {
        return R.id.f_library_custom_view_grv_rv;
    }

    /**
     * 获取配置参数
     *
     * @param attrs 配置属性
     * @return 配置参数
     */
    @Override
    public AttrConfigure getAttrs(AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.GroupRecyclerView);
        boolean animator = typedArray.getBoolean(R.styleable.GroupRecyclerView_animator, true);
        float lineHeight = typedArray.getDimension(R.styleable.GroupRecyclerView_itemLineHeight, getResources().getDimension(R.dimen.f_cvl_item_recycler_view_item_height));
        float titleSize = typedArray.getDimension(R.styleable.GroupRecyclerView_itemTitleSize, getResources().getDimension(R.dimen.f_cvl_item_recycler_view_item_title_size));
        int titleColor = typedArray.getColor(R.styleable.GroupRecyclerView_itemTitleColor, getResources().getColor(R.color.f_cvl_item_recycler_view_item_title_color));
        float placeSize = typedArray.getDimension(R.styleable.GroupRecyclerView_itemPlaceSize, getResources().getDimension(R.dimen.f_cvl_item_recycler_view_item_place_size));
        int placeColor = typedArray.getColor(R.styleable.GroupRecyclerView_itemPlaceColor, getResources().getColor(R.color.f_cvl_item_recycler_view_item_place_color));

        this.groupHeaderSuspensionShow = typedArray.getBoolean(R.styleable.GroupRecyclerView_groupHeaderSuspensionShow, true);
        this.groupHeaderHeight = typedArray.getDimension(R.styleable.GroupRecyclerView_groupHeaderHeight, 24);
        this.groupHeaderLeftPadding = typedArray.getDimension(R.styleable.GroupRecyclerView_groupHeaderLeftPadding, 22);
        this.groupHeaderBgColor = typedArray.getColor(R.styleable.GroupRecyclerView_groupHeaderBgColor, Color.parseColor("#FFEEEEEE"));
        this.groupHeaderTitleSize = typedArray.getDimension(R.styleable.GroupRecyclerView_groupHeaderTitleSize, getResources().getDimension(R.dimen.f_cvl_item_recycler_view_item_title_size));
        this.groupHeaderTitleColor = typedArray.getColor(R.styleable.GroupRecyclerView_itemTitleColor, Color.parseColor("#FF999999"));
        return new AttrConfigure(animator, -1, lineHeight, titleSize, titleColor, placeSize, placeColor);
    }

    @Override
    protected void initViews(AttributeSet attrs, AttrConfigure attsCfg) {
        super.initViews(attrs, attsCfg);
        this.sideBar = findViewById(R.id.f_library_custom_view_grv_sidebar);
        this.tipsTv = findViewById(R.id.f_library_custom_view_grv_tips_tv);
    }

    /**
     * 设置自定义属性
     *
     * @param attrs 配置属性
     */
    @Override
    protected void initAttrs(AttributeSet attrs, AttrConfigure attsCfg) {
        super.initAttrs(attrs, attsCfg);

        this.afterUpdateItems();
    }

    private void afterUpdateItems() {
        this.recyclerView.removeItemDecoration(this.groupHeaderItemDecoration);
        this.recyclerView.removeItemDecoration(this.divideItemDecoration);

        /**
         * 分组标签
         */
        GroupTools<Item> groupTools = new GroupTools<Item>(getItems()) {
            /**
             * 排序字段
             *
             * @param data 列表项
             * @return 排序字段
             */
            @Override
            public String sortField(Item data) {
                if (data != null) {
                    if (StringUtils.isNotEmpty(data.getGroupText())) {
                        return data.getGroupText();
                    }

                    if (StringUtils.isNotEmpty(data.getTitleText())) {
                        return data.getTitleText();
                    }

                    if (data.getTitle() > 0) {
                        return getResources().getString(data.getTitle());
                    }
                }
                return "";
            }
        };

        this.groupHeaderItemDecoration = new GroupHeaderItemDecoration(this.context);
        this.groupHeaderItemDecoration.setTags(groupTools.getGroupTags())
                .setGroupHeaderHeight((int) this.groupHeaderHeight)
                .setGroupHeaderLeftPadding((int) this.groupHeaderLeftPadding)
                .setGroupHeaderColor(this.groupHeaderBgColor)
                .setGroupHeaderTextSize(this.groupHeaderTitleSize)
                .setGroupHeaderTextColor(this.groupHeaderTitleColor)
                .showSuspensionGroupHeader(this.groupHeaderSuspensionShow);
        this.recyclerView.addItemDecoration(this.groupHeaderItemDecoration);

        //设置分割线
        this.divideItemDecoration = new DivideItemDecoration();
        this.divideItemDecoration.setTags(groupTools.getGroupTags());
        this.recyclerView.addItemDecoration(this.divideItemDecoration);

        // 右侧sidebar
        sideBar.setOnSideBarTouchListener(groupTools.getGroupTags(), new SideBar.OnSideBarTouchListener() {
            /**
             * 触摸SideBar时回调
             *
             * @param text     SideBar上选中的索引字符
             * @param position RecyclerView将要滚动到的位置(-1代表未找到目标位置，则列表不用滚动)
             */
            @Override
            public void onTouch(String text, int position) {
                tipsTv.setVisibility(View.VISIBLE);
                tipsTv.setText(text);
                if ("↑".equals(text)) {
                    ((LinearLayoutManager) recyclerView.getLayoutManager()).scrollToPositionWithOffset(0, 0);
                    return;
                }
                if (position != -1) {
                    ((LinearLayoutManager) recyclerView.getLayoutManager()).scrollToPositionWithOffset(position, 0);
                }
            }

            /**
             * 触摸结束回调
             */
            @Override
            public void onTouchEnd() {
                tipsTv.setVisibility(View.GONE);
            }
        });
    }

    /**
     * 显示改变
     */
    @Override
    public void notifyDataSetChanged() {
        this.afterUpdateItems();
        super.notifyDataSetChanged();
    }

    /**
     * 分组工具
     *
     * @param <T>
     */
    abstract class GroupTools<T extends Item> {
        private List<T> items;

        public GroupTools(List<T> items) {
            this.items = items;
            this.sortByLetter();
        }

        /**
         * 按照列表中data指定指定字段进行字母升序排列
         */
        private void sortByLetter() {
            if (this.items != null) {
                for (T item : this.items) {

                    char fist = sortField(item).toCharArray()[0];
                    if (String.valueOf(fist).matches("[A-Za-z]")) {
                        item.setGroupText(String.valueOf(fist).toUpperCase());
                    } else if (Pinyin.isChinese(fist)) {
                        item.setGroupText(Pinyin.toPinyin(fist).substring(0, 1));
                    } else {
                        //特殊字符情况
                        item.setGroupText("#");
                    }
                }
                Collections.sort(this.items, new Comparator<T>() {
                    @Override
                    public int compare(T o1, T o2) {
                        if (o1.getGroupText().equals("#")) {
                            return 1;
                        } else if (o2.getGroupText().equals("#")) {
                            return -1;
                        } else {
                            return o1.getGroupText().compareTo(o2.getGroupText());
                        }
                    }
                });
            }
        }

        /**
         * 从已排序的列表中提取tag
         *
         * @return 获取标签
         */
        public List<String> getGroupTags() {
            List<String> tags = new ArrayList<>();
            if (this.items != null && this.items.size() > 0) {
                for (Item item : this.items) {
                    if (tags.contains(item.getGroupText())) {
                        continue;
                    }
                    tags.add(item.getGroupText());
                }
            }

            return tags;
        }

        /**
         * 排序字段
         *
         * @param data 列表项
         * @return 排序字段
         */
        public abstract String sortField(T data);
    }

}
