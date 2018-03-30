package com.saner.ui.photo;

/**
 * Created by sunset on 2018/3/21.
 */

public class PhotoModel {

    private String url;
    private boolean isSelected;
    private boolean isMulti;

    public boolean isMulti() {
        return isMulti;
    }

    public void setMulti(boolean multi) {
        isMulti = multi;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }
}
