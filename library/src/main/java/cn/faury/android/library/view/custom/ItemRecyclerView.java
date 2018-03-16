package cn.faury.android.library.view.custom;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;

import cn.faury.android.library.view.custom.common.BaseRecyclerView;

/**
 * 菜单列表
 */

public class ItemRecyclerView extends BaseRecyclerView {

    /**
     * 构造函数
     *
     * @param context 上下文
     * @param attrs   配置属性
     */
    public ItemRecyclerView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    /**
     * 返回布局ID
     *
     * @return 布局ID
     */
    @Override
    public int getLayoutId() {
        return R.layout.f_cvl_item_recycler_view;
    }

    /**
     * 返回布局中recyclerView的ID
     *
     * @return recyclerView的ID
     */
    @Override
    public int getRecyclerViewId() {
        return R.id.f_library_custom_view_mrv_rv;
    }

    @Override
    public AttrConfigure getAttrs(AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.ItemRecyclerView);
        boolean animator = typedArray.getBoolean(R.styleable.ItemRecyclerView_animator, true);
        int decoration = typedArray.getInt(R.styleable.ItemRecyclerView_decoration, -1);
        float lineHeight = typedArray.getDimension(R.styleable.ItemRecyclerView_itemLineHeight, getResources().getDimension(R.dimen.f_cvl_item_recycler_view_item_height));
        float titleSize = typedArray.getDimension(R.styleable.ItemRecyclerView_itemTitleSize, getResources().getDimension(R.dimen.f_cvl_item_recycler_view_item_title_size));
        int titleColor = typedArray.getColor(R.styleable.ItemRecyclerView_itemTitleColor, getResources().getColor(R.color.f_cvl_item_recycler_view_item_title_color));
        float placeSize = typedArray.getDimension(R.styleable.ItemRecyclerView_itemPlaceSize, getResources().getDimension(R.dimen.f_cvl_item_recycler_view_item_place_size));
        int placeColor = typedArray.getColor(R.styleable.ItemRecyclerView_itemPlaceColor, getResources().getColor(R.color.f_cvl_item_recycler_view_item_place_color));
        return new AttrConfigure(animator, decoration, lineHeight, titleSize, titleColor, placeSize, placeColor);
    }
}
