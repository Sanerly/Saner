package com.saner.ui.photo;

/**
 * Created by sunset on 2018/3/21.
 */

public class PhotoModel {

    private String url;
    private boolean isSelected;
    private boolean isCheckEnabled;


    public boolean isCheckEnabled() {
        return isCheckEnabled;
    }

    public void setCheckEnabled(boolean checkEnabled) {
        isCheckEnabled = checkEnabled;
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
