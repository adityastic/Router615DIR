package com.adityagupta.router615dir.data;

import android.graphics.Bitmap;
import androidx.fragment.app.Fragment;

public class DrawerItemData {

    public Bitmap image;
    public String title,actiontitle;
    public Fragment fragment;

    public DrawerItemData(Bitmap image, String title, String actiontitle, Fragment fragment) {
        this.image = image;
        this.title = title;
        this.actiontitle = actiontitle;
        this.fragment = fragment;
    }

    public Bitmap getImage() {
        return image;
    }

    public void setImage(Bitmap image) {
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getActiontitle() {
        return actiontitle;
    }

    public void setActiontitle(String actiontitle) {
        this.actiontitle = actiontitle;
    }

    public Fragment getFragment() {
        return fragment;
    }

    public void setFragment(Fragment fragment) {
        this.fragment = fragment;
    }
}
