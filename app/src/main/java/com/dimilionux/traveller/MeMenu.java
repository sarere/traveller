package com.dimilionux.traveller;

/**
 * Created by sarere on 12/2/17.
 */

public class MeMenu {
    private String title;
    private int icon;

    private boolean isGroupHeader = false;

    public MeMenu(String title){
        this.setTitle(title);
        setGroupHeader(true);
    }

    public MeMenu(int icon, String title){
        this.setTitle(title);
        this.setIcon(icon);
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public boolean isGroupHeader() {
        return isGroupHeader;
    }

    public void setGroupHeader(boolean groupHeader) {
        isGroupHeader = groupHeader;
    }
}
