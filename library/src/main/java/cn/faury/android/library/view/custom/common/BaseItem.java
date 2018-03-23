package cn.faury.android.library.view.custom.common;

import android.support.annotation.DrawableRes;
import android.support.annotation.StringRes;

import java.io.Serializable;

/**
 * 列表项
 */

public class BaseItem implements Serializable {

    /**
     * 项目ID
     */
    private int id = -1;

    /**
     * 项目图片
     */
    private int icon = -1;

    /**
     * 项目内容
     */
    private int title = -1;

    /**
     * 项目内容文本
     */
    private String titleText = "";

    /**
     * 显示图标
     */
    private boolean showIcon = false;

    /**
     * 构造函数
     *
     * @param id    项目ID
     * @param title 项目内容ID
     */
        public BaseItem(int id, @StringRes int title) {
        this.id = id;
        this.title = title;
    }

    /**
     * 构造函数
     *
     * @param id    项目ID
     * @param title 项目内容
     */
        public BaseItem(int id, String title) {
        this.id = id;
        this.titleText = title;
    }

    /**
     * 构造函数
     *
     * @param id     项目ID
     * @param iconId 图标ID(小于等于0不显示)
     * @param title  项目内容ID
     */
        public BaseItem(int id, @DrawableRes int iconId, @StringRes int title) {
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
     * @param id     项目ID
     * @param iconId 图标ID(小于等于0不显示)
     * @param title  项目内容
     */
        public BaseItem(int id, @DrawableRes int iconId, String title) {
        this.id = id;
        if (iconId > 0) {
            this.icon = iconId;
            showIcon = true;
        }
        this.titleText = title;
    }

    public int getId() {
        return id;
    }

    @DrawableRes
    public int getIcon() {
        return icon;
    }

    @StringRes
    public int getTitle() {
        return title;
    }

    public String getTitleText() {
        return titleText;
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
     * 设置文本
     *
     * @param title 文本ID
     * @return 当前对象
     */
    public BaseItem setTitle(int title) {
        this.title = title;
        return this;
    }

    /**
     * 设置文本
     *
     * @param title 文本
     * @return 当前对象
     */
    public BaseItem setTitleText(String title) {
        this.titleText = title;
        return this;
    }
}
