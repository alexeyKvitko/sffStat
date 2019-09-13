package ru.sff.statistic.model;

import java.io.Serializable;

public class HeaderModel implements Serializable {

    private int emogii;
    private String title;

    public HeaderModel( int emogii, String title ) {
        this.emogii = emogii;
        this.title = title;
    }

    public int getEmogii() {
        return emogii;
    }

    public void setEmogii( int emogii ) {
        this.emogii = emogii;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle( String title ) {
        this.title = title;
    }
}
