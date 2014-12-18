package de.hszg.risikousapp.models;

/**
 * Created by Julian on 17.12.2014.
 */
public class ReportingArea {
    private String name;
    private String shortcut;

    public ReportingArea(String name, String shortcut) {
        this.name = name;
        this.shortcut = shortcut;
    }

    public String getName() {
        return name;
    }

    public String getShortcut() {
        return shortcut;
    }

}
