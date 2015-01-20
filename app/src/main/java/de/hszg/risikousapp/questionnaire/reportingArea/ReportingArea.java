package de.hszg.risikousapp.questionnaire.reportingArea;

/**
 * model class for a reporting area
 * includes long name and shortcut
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
