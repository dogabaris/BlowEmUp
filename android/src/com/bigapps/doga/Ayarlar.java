package com.bigapps.doga;

/**
 * Created by shadyfade on 11.05.2016.
 */
public class Ayarlar {
    public int ses=1;
    public int background=1;

    public Ayarlar() {
    }

    public int getSes() {
        return ses;
    }

    public void setSes(int ses) {
        this.ses = ses;
    }

    public int getBackground() {
        return background;
    }

    public void setBackground(int background) {
        this.background = background;
    }
    private static Ayarlar self = new Ayarlar();
    public static Ayarlar get() { return self; }
}
