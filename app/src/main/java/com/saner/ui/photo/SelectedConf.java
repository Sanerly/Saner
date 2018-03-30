package com.saner.ui.photo;

import java.io.Serializable;

/**
 * Created by sunset on 2018/3/30.
 */

public class SelectedConf implements Serializable {


    public SelectedConf(Builder builder) {
        this.multiSelected = builder.multiSelected;
        this.maxCount = builder.maxCount;
        this.columns = builder.columns;
    }


    /**
     * 是否允许多种选择
     */
    private boolean multiSelected;

    /**
     * 多选的数量
     */
    private int maxCount;


    /**
     * 一行显示的列数
     */
    private int columns;


    public boolean isMultiSelected() {
        return multiSelected;
    }


    public int getMaxCount() {
        return maxCount;
    }

    public int getColumns() {
        return columns;
    }

    public void setColumns(int columns) {
        this.columns = columns;
    }

    static class Builder implements Serializable {

        private boolean multiSelected;

        private int maxCount;

        private int columns;

        Builder() {

        }

        /**
         * 是否允许多种选择
         */
        Builder setMultiSelected(boolean multiSelected) {
            this.multiSelected = multiSelected;
            return this;
        }

        /**
         * 多选的数量
         */
        Builder setMaxCount(int maxCount) {
            this.maxCount = maxCount;
            return this;
        }

        /**
         * 一行显示的列数
         */
        Builder setColumns(int columns) {
            this.columns = columns;
            return this;
        }


        SelectedConf build() {
            return new SelectedConf(this);
        }

    }
}
